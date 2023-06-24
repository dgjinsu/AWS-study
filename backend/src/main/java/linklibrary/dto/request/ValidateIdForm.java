package linklibrary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "로그인 중복 확인 요청 ", description = "로그인 중복 확인하기. 로그인 아이디)를 필요로합니다. ")
public class ValidateIdForm {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
    @Schema(title = "사용자 로그인 아이디 요청", example = "abcde1")
    private String loginId;
}

