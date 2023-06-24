package linklibrary.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(title = "로그인 요청 ", description = "로그인 폼 작성하기 ")

public class LoginFormDto {
    @NotBlank(message = "아이디를 입력해주세요")
    @Schema(title = "로그인 ID", example = "abcde1")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Schema(title ="로그인 패스워드", example = "abcdefg1!")
    private String password;
}

