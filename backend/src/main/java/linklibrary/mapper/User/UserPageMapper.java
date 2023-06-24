package linklibrary.mapper.User;

import linklibrary.dto.response.UserPageDto;
import linklibrary.entity.User;
import lombok.Builder;

public class UserPageMapper {
    @Builder
    public static UserPageDto convertToDto(String storeFilename, User user, Integer totalPost) {
        UserPageDto userPageDto = UserPageDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .storeFileName(storeFilename)
                .totalPost(totalPost)
                .build();
        return userPageDto;
    }
}
