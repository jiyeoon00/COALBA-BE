package com.project.coalba.global.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/s3/image")
@RestController
public class AwsS3Controller {
    private final AwsS3Service awsS3Service;

    @PostMapping
    public String uploadImage(@RequestPart("file") MultipartFile file) {
        return awsS3Service.uploadImage(file);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteImage(@RequestParam String fileUrl) {
        awsS3Service.deleteImage(fileUrl);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
