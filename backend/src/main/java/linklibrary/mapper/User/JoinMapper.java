package linklibrary.mapper.User;

import linklibrary.dto.request.JoinFormDto;
import linklibrary.entity.Role;
import linklibrary.entity.User;
import lombok.Builder;

public class JoinMapper {
    @Builder
    public static User convertToEntity(JoinFormDto joinFormDto,String encode) {
        User user = User.builder()
                .loginId(joinFormDto.getLoginId())
                .password(encode) //비밀번호 인코딩
                .nickname(joinFormDto.getNickname())
                .role(Role.ROLE_USER) //user 등급으로 회원가입
                .build();
     return user;
    }
}
