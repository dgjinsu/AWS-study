package linklibrary.service;

import linklibrary.dto.request.PostFormDto;
import linklibrary.dto.response.PostDto;
import linklibrary.dto.response.PostDto1;
import linklibrary.entity.Category;
import linklibrary.entity.Post;
import linklibrary.entity.Role;
import linklibrary.entity.User;
import linklibrary.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;

    @Test
    public void 포스트생성() throws Exception {
        //given
        User user = createUser();
        Category category = createCategory(user);
        PostFormDto postFormDto = createPostForm(category);
        //when
        PostDto1 postDto = postService.createPost(postFormDto, user.getId());
        //then
//        em.flush();
//        em.clear();
        Post post = postRepository.findById(postDto.getPostId()).get();
        assertThat(post.getTitle()).isEqualTo(postFormDto.getTitle()); //title 비교
        assertThat(post.getMemo()).isEqualTo(postFormDto.getMemo()); //memo 비교
        assertThat(post.getUrl()).isEqualTo(postFormDto.getUrl()); //url 비교
        assertThat(post.getCategory().getId()).isEqualTo(postFormDto.getCategoryId()); //카테고리 id 비교
    }

    @Test
    public void 포스트수정() throws Exception {
        //given
        User user = createUser();
        Category category = createCategory(user);
        Post post = createPost(user, category);
        PostFormDto postForm = createPostForm(category);
        postForm.setTitle("바뀐제목1");
        //when
        PostDto1 postDto = postService.change(post.getId(), postForm);
        //then
//        em.flush();
//        em.clear();
        Post updatedPost = postRepository.findById(postDto.getPostId()).get();
        assertThat(updatedPost.getTitle()).isEqualTo(postForm.getTitle());
    }

    @Test
    public void 포스트삭제() throws Exception {
        //given
        User user = createUser();
        Category category = createCategory(user);
        Post post = createPost(user, category);
        //when
        postService.deletePost(post.getId());
        //then
//        em.flush();
//        em.clear();
        Optional<Post> findPost = postRepository.findById(post.getId());
        assertThat(findPost).isEmpty();
    }

    private Post createPost(User user, Category category) {
        Post post = Post.builder().title("제목1").memo("메모1").url("www.abc.com").bookmark(true).category(category).user(user).build();
        em.persist(post);
        return post;
    }

    private PostFormDto createPostForm(Category category) {
        return PostFormDto.builder().title("제목1").memo("메모1").url("www.abc.com").bookmark(true).categoryId(category.getId()).build();
    }

    private User createUser() {
        User user = User.builder().loginId("abcde").password("abcdefg1!").nickname("nickname").role(Role.ROLE_USER).build();
        em.persist(user);
        return user;
    }

    private Category createCategory(User user) {
        Category category = Category.builder().name("카테고리1").user(user).build();
        em.persist(category);
        return category;
    }
}