package linklibrary.service;

import linklibrary.dto.response.CategoryDto;
import linklibrary.dto.request.CategoryFormDto;
import linklibrary.entity.Category;
import linklibrary.entity.User;
import linklibrary.repository.CategoryRepository;
import linklibrary.repository.PostRepository;
import linklibrary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public Long createCategory(CategoryFormDto categoryFormDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저 엔티티가 없습니다. [categoryService]"));
        Category category = Category.builder().name(categoryFormDto.getName()).user(user).build();
        categoryRepository.save(category);
        return category.getId();
    }

    public Long editCategory(CategoryFormDto categoryFormDto, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("카테고리 엔티티가 없습니다"));
        category.setName(categoryFormDto.getName());
        return category.getId();
    }

    /**
     * 회원에 해당하는 카테고리 전부 반환
     */
    @Transactional(readOnly = true)
    public List<CategoryDto> findAll(Long userId) {
        List<Category> categoryList = categoryRepository.findByUserId(userId);//회원에 해당하는 카테고리들 반환
        List<CategoryDto> categoryDtoList =
                categoryList.stream()
                        .map(c -> new CategoryDto(c.getId(), c.getName()))
                        .collect(Collectors.toList());
        return categoryDtoList;
    }


    public void deleteCategory(Long categoryId, Long id) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("카테고리 엔티티가 없습니다."));
        postRepository.deleteByCategoryId(category.getId());

        categoryRepository.delete(category);
    }

}
