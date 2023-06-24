package linklibrary.repository;

import linklibrary.dto.response.PostDto1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {
    Page<PostDto1> findPostDtos(Long userId, String bookmark, String sort, String keyword, Long categoryId, Pageable pageable);
}


