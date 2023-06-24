package linklibrary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Schema(title = "회원 가입 요청", description = "회원가입 폼을 작성하셔야 합니다.")
public class JoinFormDto {
    @Schema(title = "로그인 ID", example = "abcde1")
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
    private String loginId;

    @Schema(title = "로그인 PASSWORD", example = "abcdefg1!")
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
            message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password;

    @Schema(title = "닉네임 작성", example = "nickname1")
    @NotBlank(message = "닉네임을 입력해주세요")
    private String nickname;
}
