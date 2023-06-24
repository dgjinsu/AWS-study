package linklibrary.service;

import linklibrary.dto.request.PostFormDto;
import linklibrary.dto.response.CategoryDto;
import linklibrary.dto.response.MainPageDto;
import linklibrary.dto.response.PostDto;
import linklibrary.dto.response.PostDto1;
import linklibrary.entity.Category;
import linklibrary.entity.Post;
import linklibrary.entity.User;
import linklibrary.mapper.PostMapper;
import linklibrary.repository.CategoryRepository;
import linklibrary.repository.PostRepository;
import linklibrary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    /**
     * 포스트 생성
     */
    public PostDto1 createPost(PostFormDto postFormDto, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("회원 엔티티가 없습니다. [postService]"));
        Category category = categoryRepository.findById(postFormDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("카테고리 엔티티가 없습니다. [postService]"));
        Post post = Post.builder().title(postFormDto.getTitle())
                .memo(postFormDto.getMemo())
                .url(postFormDto.getUrl())
                .category(category)
                .user(user)
                .bookmark(postFormDto.getBookmark())
                .createdBy(user.getNickname())
                .build();

        this.postRepository.save(post);
        return PostDto1.builder().postId(post.getId()).categoryId(post.getCategory().getId()).build();

    }

    /**
     * 포스트 삭제
     */
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("포스트 엔티티가 없습니다"));
        Optional<Category> byId = categoryRepository.findById(4L);
        postRepository.delete(post);
    }

    /**
     * 포스트 수정
     */
    public PostDto1 change(Long postId, PostFormDto postFormDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("포스트 엔티티가 없습니다"));
        Category category = categoryRepository.findById(postFormDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("카테고리 엔티티가 없습니다. [postService]"));
        post.setTitle(postFormDto.getTitle());
        post.setMemo(postFormDto.getMemo());
        post.setUrl(postFormDto.getUrl());
        post.setCategory(category);
        post.setBookmark(postFormDto.getBookmark());
        return PostDto1.builder().postId(post.getId()).categoryId(post.getCategory().getId()).build();
    }
    /**
     * 포스트 목록 전체 조회
     */
    public List<PostDto> getPostList(Long userId, String keyword, String sort, Boolean bookmark, Pageable pageRequest) {
        Slice<Post> posts;
        if (bookmark != null) { // 북마크 해준 경우
            if (Objects.equals(sort, "byName")) {
                posts = postRepository.findByUserIdAndBookmarkAndTitleContainingOrderByTitleAsc(userId, bookmark, keyword, pageRequest);
            } else { // "byDate"를 기본값으로 사용
                posts = postRepository.findByUserIdAndBookmarkAndTitleContainingOrderByCreatedAtDesc(userId, bookmark, keyword, pageRequest);
            }
        } else { // 북마크 안 해준 경우
            if (Objects.equals(sort, "byName")) {
                posts = postRepository.findByUserIdAndTitleContainingOrderByTitleAsc(userId, keyword, pageRequest);
            } else { // "byDate"를 기본값으로 사용
                posts = postRepository.findByUserIdAndTitleContainingOrderByCreatedAtDesc(userId, keyword, pageRequest);
            }
        }
        List<Post> content = posts.getContent();
        return PostMapper.convertToDtoListAll(content);
    }

    public List<PostDto> getPostListByCategoryId(Long userId, String keyword, String sort, Long categoryId, Pageable pageable) {
        Slice<Post> posts;

        if (Objects.equals(sort, "byName")) {
            posts = postRepository.findByUserIdAndCategoryIdAndTitleContainingOrderByTitleAsc(userId, categoryId, keyword, pageable);
        } else { // "byDate"를 기본값으로 사용
            posts = postRepository.findByUserIdAndCategoryIdAndTitleContainingOrderByCreatedAtDesc(userId, categoryId, keyword, pageable);
        }
        List<Post> content = posts.getContent();
        return PostMapper.convertToDtoListAll(content);
    }

    public Integer findTotalPostNumberByUser(Long userId) {
        /**
         * //찜목록, 카테고리별 개수가 아닌 전체 개수만 가져와져서 수정해야됩니다.
         */
        Integer totalPostNumberByUser = postRepository.findTotalPostNumberByUser(userId);
        return totalPostNumberByUser;
    }

    /**
     * 카테고리로 포스트 전체 조회
     * 북마크로 포스트 전체 조회
     * 포스트 전체 조회
     */
    public MainPageDto getPosts(Long userId, String bookmark, String sort, String keyword, Long categoryId, Pageable pageable) {
        List<Category> categories = categoryRepository.findByUserId(userId);   //  Id로 만든 카테고리들 찾아옴


        List<CategoryDto> categoryDtoList = categories.stream()    // 카테고리 ->DTO시키기
                .map(c -> new CategoryDto(c.getId(), c.getName()))
                .collect(Collectors.toList());  //카테고리DTO에는  ID와 NAME만 있음.

        String current = bookmark == null ? "전체조회" : "찜목록"; //bookmark 여부에 따라 currentCategory 이름 설정

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("카테고리 아이디에 해당하는 엔티티가 없습니다. [PostService]"));
            current = category.getName(); //만약 Category 목록을 조회했다면 current 에 카테고리명
            //Response 로 뿌려줄 화면
            Page<PostDto1> postDtos = postRepository.findPostDtos(userId, bookmark, sort, keyword, categoryId, pageable);
            long totalPost = postDtos.getTotalElements(); //포스트의 개수,
            long size = category.getPosts().size();// 05 27 추가
            MainPageDto mainPageDto = MainPageDto.builder()
                    .categoryDtoList(categoryDtoList) //카테고리 리스트
                    .postDtoList(postDtos)  //포스트 리스트
                    .total(totalPost) //총 포스트 개수
                    .currentCategory(current)  //현재 카테고리이름
                    .size(size)
                    .build();
            return mainPageDto;
        } else {
            //Response 로 뿌려줄 화면
            Page<PostDto1> postDtos = postRepository.findPostDtos(userId, bookmark, sort, keyword, categoryId, pageable);
            long totalPost = postDtos.getTotalElements(); //포스트의 개수,
            MainPageDto mainPageDto = MainPageDto.builder()
                    .categoryDtoList(categoryDtoList) //카테고리 리스트
                    .postDtoList(postDtos)  //포스트 리스트
                    .total(totalPost) //총 포스트 개수
                    .currentCategory(current)  //현재 카테고리이름
                    .size(0L)
                    .build();
            return mainPageDto;
        }
    }
}
