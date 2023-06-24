package linklibrary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(title = "메인 페이지 응답", description = "카테고리 리스트,게시글 리스트 및 전체 수,현재 카테고리 응답  ")
public class MainPageDto {

    private List<CategoryDto> categoryDtoList;
    private Page<PostDto1> postDtoList;
    private Long total;
    private String currentCategory;
    private Long size; // 05 27 추가
}
