package linklibrary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Schema(title = "로그아웃 요청", description = "로그인된 토큰의 남은 유효 시간동안 블랙리스트에 올림 ")
public class LogoutDto {
    @NotEmpty(message = "잘못된 요청입니다")
    @Schema(title = "로그아웃 요청", example = "쫘르르.. 로그인된 토큰 가져옴. 직접 입력하지 않음. ")
    private String accessToken;
}
