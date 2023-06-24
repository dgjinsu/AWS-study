package linklibrary.controller;

import io.swagger.annotations.ApiOperation;
import linklibrary.dto.request.PostFormDto;
import linklibrary.dto.response.*;
import linklibrary.security.auth.PrincipalDetails;
import linklibrary.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    /**
     * 포스트 생성,
     */
    @ApiOperation(value = "포스트 생성", notes = "필수값: title, url, categoryId (없을 시 예외 메세지 전송)")
    @PostMapping("/post")
    public ResponseEntity<ResponseData> createPost(@Valid @RequestBody final PostFormDto postFormDto,
                                                   @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException {
        PostDto1 postDto = postService.createPost(postFormDto, principalDetails.getUserDto().getUserId());

        return new ResponseEntity<>(new ResponseData("포스터 생성 완료. 전체 조회 화면으로 이동", postDto), HttpStatus.OK);
    }

    /**
     * 포스트 삭제하기
     */
    @DeleteMapping("post/{postId}")
    public ResponseEntity<ResponseData> deletePost(@PathVariable final long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(new ResponseData("포스트 삭제 완료", null), HttpStatus.OK);
    }

    /**
     * 포스트 수정
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("post/{postId}")
    public ResponseEntity<ResponseData> updatePost(@PathVariable final long postId,
                                                   @Valid @RequestBody final PostFormDto postFormDto) {
        PostDto1 postDto = postService.change(postId, postFormDto);
        return new ResponseEntity<>(new ResponseData("포스트 수정 완료", postDto), HttpStatus.OK);
    }


    /**
     * 전체 게시글 조회 & 찜목록 조회
     * 정렬 & 검색 & 페이징
     */
    @ApiOperation(value = "포스트 전체 or 찜목록 조회", notes = "bookmark 값이 null이면 전체조회 null이 아니면 찜목록 조회  sort=\"byDate\"이면 시간순, sort=\"byTitle\"이면 제목순")
    @GetMapping("/posts")
    public ResponseEntity<?> getPosts(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(required = false) String bookmark,
            @RequestParam(required = false, defaultValue = "byDate") String sort,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page) {
        log.info("컨트롤러 진입");
        PageRequest pageable = PageRequest.of(page, 16);
        Long userId = principalDetails.getUserDto().getUserId();
        MainPageDto mainPageDto = postService.getPosts(userId, bookmark, sort, keyword, null, pageable);
        return ResponseEntity.ok(new ResponseData("찜목록 or 전체페이지 조회 완료", mainPageDto));
    }

    /**
     * 카테고리별 게시글 조회
     * 정렬 & 검색 & 페이징
     */
    @GetMapping("/posts/{categoryId}")
    public ResponseEntity<?> getPostsByCategory(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(required = false, defaultValue = "byDate") String sort,
            @RequestParam(required = false, defaultValue = "") String keyword,
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page) {
        PageRequest pageable = PageRequest.of(page, 16);
        Long userId = principalDetails.getUserDto().getUserId();
        MainPageDto mainPageDto = postService.getPosts(userId, null, sort, keyword, categoryId, pageable);
        return ResponseEntity.ok(new ResponseData("카테고리별 게시글 조회 완료", mainPageDto));
    }

}
