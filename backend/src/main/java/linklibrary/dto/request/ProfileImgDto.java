package linklibrary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 프로필 이미지 업로드 시 받아오는 DTO
 */
@Data
@AllArgsConstructor
@Schema(title = "프로필 이미지 요청", description = "프로필 이미지 요청받기 ")
public class ProfileImgDto {

    private MultipartFile profileImg;
}
