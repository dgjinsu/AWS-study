package linklibrary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import linklibrary.entity.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(title = "유저 정보 응답", description = "유저 정보 응답하기")
public class UserDto {

    @Schema(title = "유저 ID", example = "1")
    private Long userId;

    @Schema(title = "유저 로그인ID", description = "abcde1")
    private String loginId;

    @Schema(title = "유저 패스워드", description = "abcdefg1!")
    private String password;

    @Schema(title = "유저 등급", example = "ROLE_USER")
    private Role role;
}
