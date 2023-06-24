package linklibrary.controller;

import io.swagger.annotations.ApiOperation;
import linklibrary.dto.request.CategoryFormDto;
import linklibrary.dto.response.ResponseData;
import linklibrary.security.auth.PrincipalDetails;
import linklibrary.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    @ApiOperation(value = "카테고리 생성", notes = "필수값: name (없을 시 예외 메세지 전송)")
    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryFormDto categoryFormDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long savedCategoryId = categoryService.createCategory(categoryFormDto, principalDetails.getUserDto().getUserId());
        return ResponseEntity.ok(new ResponseData("카테고리 생성 완료", savedCategoryId));
    }
    @ApiOperation(value = "카테고리 수정", notes = "필수값: name (없을 시 예외 메세지 전송)")
    @PutMapping("/category/{categoryId}")
    public ResponseEntity<?> editCategory(@Valid @RequestBody CategoryFormDto categoryFormDto, @PathVariable final Long categoryId) {
        Long editedCategoryId = categoryService.editCategory(categoryFormDto, categoryId);
        return ResponseEntity.ok(new ResponseData("카테고리 수정 완료", editedCategoryId));
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<?> editCategory(@PathVariable final Long categoryId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        categoryService.deleteCategory(categoryId, principalDetails.getUserDto().getUserId());
        return ResponseEntity.ok(new ResponseData("카테고리 삭제 완료", null));
    }
}
