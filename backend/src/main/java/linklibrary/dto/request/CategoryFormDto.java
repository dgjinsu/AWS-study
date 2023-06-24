package linklibrary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "카테고리 작명 요청", description = "카테고리 작명 하기")
public class CategoryFormDto {
    @Schema(title = "제목", example = "카테고리1")
    @NotBlank(message = "카테고리명을 입력해주세요.")
    private String name;
}
