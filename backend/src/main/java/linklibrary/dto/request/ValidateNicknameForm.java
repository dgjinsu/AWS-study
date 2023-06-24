package linklibrary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "사용자 닉네임 중복확인 요청", description = "사용자 닉네임 중복확인 요청")
public class ValidateNicknameForm {
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Size(max = 8, message = "닉네임은 8글자 이하로 작성해 주세요.")
    @Schema(title = "사용자 닉네임 중복확인 요청", example = "nickname1")
    private String nickname;
}
