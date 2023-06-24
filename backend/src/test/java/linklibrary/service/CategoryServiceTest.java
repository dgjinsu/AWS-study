package linklibrary.service;

import linklibrary.dto.response.CategoryDto;
import linklibrary.dto.request.CategoryFormDto;
import linklibrary.entity.Category;
import linklibrary.entity.User;
import linklibrary.repository.CategoryRepository;
import linklibrary.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserService userService;
    @Autowired
    private UserRepository userRepository;


    @Test
    public void 카테고리생성() {
        //gien
        User user = createUser();
        CategoryFormDto categoryFormDto= new CategoryFormDto("카테고리ex1");
        //when
        Long savedCategory = categoryService.createCategory(categoryFormDto, user.getId());
        //then
        Category category = categoryRepository.findById(savedCategory).get();
        assertThat(category.getName()).isEqualTo("카테고리ex1");
    }



    @Test
    public void 카테고리수정() throws Exception {
        //given
        User user = createUser();
        Category category = createCategory(user); //name = 카테고리
        CategoryFormDto categoryFormDto = createcategoryFormDto(); //name = 카테고리ex1
        //when
        Long editCategoryId = categoryService.editCategory(categoryFormDto, category.getId());
        //then
        Category editCategory = categoryRepository.findById(editCategoryId).get();
        assertThat(editCategory.getName()).isEqualTo(categoryFormDto.getName());
    }

    @Test
    public void 카테고리삭제() throws Exception {
        //given
        User user = createUser();
        Category category = createCategory(user);
        //when
//        categoryService.deleteCategory(100L);
        //then
        //없는 카테고리를 찾았을 때 NoSuchElementException 발생
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            categoryRepository.findById(category.getId()+1).get();
        });
    }

    @Test
    public void 카테고리조회() throws Exception {
        //given
        User user = createUser();
        createCategoryList(user); //카테고리 20개 생성
        em.flush();
        em.clear();
        //when
        List<CategoryDto> categoryDtoList = categoryService.findAll(user.getId());
        //then
        for (CategoryDto categoryDto : categoryDtoList) {
            System.out.println(categoryDto.getName());
        }
        assertThat(categoryDtoList.size()).isEqualTo(20);

    }

    public User createUser() {
        User user = User.builder().loginId("abcde").password("aaaaaaaa1!").nickname("nick").build();
        em.persist(user);
        return user;
    }

    public Category createCategory(User user) {
        Category category = Category.builder().name("카테고리").user(user).build();
        em.persist(category);
        return category;
    }

    public void createCategoryList(User user) {
        for(int i = 0; i<20; i++){
            Category category = Category.builder().name("카테고리"+i).user(user).build();
            em.persist(category);
        }
    }

    private CategoryFormDto createcategoryFormDto() {
        CategoryFormDto categoryFormDto = new CategoryFormDto();
        categoryFormDto.setName("카테고리ex1");
        return categoryFormDto;
    }

}





