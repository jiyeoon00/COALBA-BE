package com.project.coalba.domain.message.mapper;

import com.project.coalba.domain.message.dto.response.MessageBoxListResponse;
import com.project.coalba.domain.message.service.dto.MessageBoxServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    interface MessageBoxListRef extends Supplier<List<MessageBoxServiceDto>> {}
    default MessageBoxListResponse toDto(MessageBoxListRef ref) {
        List<MessageBoxListResponse.BoxResponse> messageBoxList = ref.get().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new MessageBoxListResponse(messageBoxList);
    }

    @Mappings({
            @Mapping(source = "staff.id", target = "staff.staffId"),
            @Mapping(source = "staff.realName", target = "staff.name"),
            @Mapping(source = "staff.imageUrl", target = "staff.imageUrl"),
            @Mapping(source = "latestMessage", target = "latestMessage"),
            @Mapping(source = "latestDateTime", target = "latestDateTime")
    })
    MessageBoxListResponse.BoxResponse toDto(MessageBoxServiceDto serviceDto);
}
