package com.project.coalba.global.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.project.coalba.global.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwsS3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;

        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata fileMetaData = getFileMetaData(file);
        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, fileMetaData)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_UPLOAD_ERROR);
        } catch (SdkClientException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(ErrorCode.FILE_UPLOAD_ERROR);
        }
        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    public void deleteImage(String fileUrl) {
        if (!StringUtils.hasText(fileUrl)) return;

        try {
            amazonS3.deleteObject(bucketName, parseFileName(fileUrl));
        } catch (SdkClientException e) {
            log.error(e.getMessage(), e);
        }
    }

    //업로드 → 삭제
    public String replaceImage(MultipartFile srcFile, String destFileUrl) {
        String imageUrl = uploadImage(srcFile);
        deleteImage(destFileUrl);
        return imageUrl;
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(parseExtension(fileName));
    }

    private String parseExtension(String fileName) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf("."));
            if (!List.of(".png", ".jpg", ".jpeg").contains(extension)) {
                throw new BusinessException(ErrorCode.INVALID_IMAGE_FILE_EXTENSION);
            }
            return extension;
        } catch (IndexOutOfBoundsException e) {
            throw new BusinessException(ErrorCode.INVALID_FILE_NAME_FORM);
        }
    }

    private ObjectMetadata getFileMetaData(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(ContentType.IMAGE_PNG.getMimeType()); //항상 image/png 타입
        return objectMetadata;
    }

    private String parseFileName(String fileUrl) {
        try {
            return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        } catch (IndexOutOfBoundsException e) {
            throw new BusinessException(ErrorCode.INVALID_FILE_URL_FORM);
        }
    }
}
