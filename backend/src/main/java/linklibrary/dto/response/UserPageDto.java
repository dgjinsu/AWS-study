package linklibrary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(title = "유저 마이페이지 응답", description = "유저 마이페이지 응답")
public class UserPageDto {
    @Schema(title = "유저 ID", description = "1")
    private Long userId;

    @Schema(title = "유저 닉네임",example ="nickname1")
    private String nickname; //닉네임
    @Schema(title = "유저의 포스트 개수", description = "2")
    private Integer totalPost; //총 post 수
    @Schema(title = "유저가 저장한 파일이름", description = "...")
    private String storeFileName; //저장된 파일 이름.
}
