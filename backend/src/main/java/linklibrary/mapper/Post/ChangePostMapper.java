package linklibrary.mapper.Post;

import linklibrary.dto.request.PostFormDto;
import linklibrary.entity.Category;
import linklibrary.entity.Post;
import linklibrary.entity.User;
import lombok.Builder;

public class ChangePostMapper {
    //받은 Dto -> Post (저장위해)
    @Builder
    public static void convertToEntity (Category category, PostFormDto postFormDto) {
        Post.builder().title(postFormDto.getTitle())
                .memo(postFormDto.getMemo())
                .url(postFormDto.getUrl())
                .category(category)
                .bookmark(postFormDto.getBookmark())
                .build();
    }
}
