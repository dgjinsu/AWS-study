package linklibrary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import linklibrary.entity.Category;
import linklibrary.entity.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "포스트 생성 폼 요청", description = "포스트 생성 폼 작성하기 ")
public class PostFormDto { //포스트 생성 폼

    @Schema(title = "포스트 제목", example = "이거 나중에 볼 영화")
    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    @Schema(title = "포스트 내용", example = " 20대들이 많이보고, 커플들이 보면 안좋은 영화 ")
    private String memo;

    @Schema(title = "가져올 url", example = "https://www.youtube.com/watch?v=rG8bra867kk ")
    @NotBlank(message = "주소를 입력해주세요")
    private String url;
    @Schema(title = "카테고리 ID", example = "4") //카테고리는 ID가 4,5,6 으로 들어가서 4로 고정하겠습니다
    @NotNull(message = "카테고리는 필수 선택 사항입니다.")
    private Long categoryId;

    @Schema(title = "북마크 on/off", example = "true")
    private Boolean bookmark;


}
