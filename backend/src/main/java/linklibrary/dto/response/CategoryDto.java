package linklibrary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "카테고리 응답", description = "카테고리 응답")
public class CategoryDto {

    @Schema(title = " 카테고리 ID", example = "1")
    private Long categoryId;
    @Schema(title = " 카테고리 이름", example = "카테고리1")
    private String name;

}
