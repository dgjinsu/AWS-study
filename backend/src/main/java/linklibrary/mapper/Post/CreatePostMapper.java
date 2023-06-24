package linklibrary.mapper.Post;

import linklibrary.dto.request.PostFormDto;
import linklibrary.entity.Category;
import linklibrary.entity.Post;
import linklibrary.entity.User;
import lombok.Builder;

public class CreatePostMapper {
    //받은 Dto -> Post (저장위해)
    @Builder
    public static Post convertToEntity (User user, Category category, PostFormDto postFormDto) {
        Post post = Post.builder().title(postFormDto.getTitle())
                .memo(postFormDto.getMemo())
                .url(postFormDto.getUrl())
                .category(category)
                .user(user)
                .bookmark(postFormDto.getBookmark())
                .createdBy(user.getNickname())
                .build();
        return post;
    }
}
