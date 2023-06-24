//package linklibrary;
//
//
//import linklibrary.entity.Category;
//import linklibrary.entity.Post;
//import linklibrary.entity.Role;
//import linklibrary.entity.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import javax.persistence.EntityManager;
//
//@Component
//@RequiredArgsConstructor
//public class InitDB {
//
//    private final InitService initService;
//
//    @PostConstruct
//    public void init() {
//        initService.init();
//    }
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    static class InitService {
//        private final EntityManager em;
//        private final BCryptPasswordEncoder encoder;
//
//
//        /**
//         * USER1 : id:abcde1  pw:abcdefg1!  |  Category1(post1,post2,post3)  Category2(post4,post5)
//         * USER2 : id:abcde2  pw:abcdefg1!  |  Category3(post6)
//         * USER3 : id:abcde3  pw:abcdefg1!  |
//         */
//        public void init() {
//            User user1 = User.builder()
//                    .loginId("abcde1")
//                    .password(encoder.encode("abcdefg1!"))
//                    .nickname("nick1")
//                    .role(Role.ROLE_USER)
//                    .build();
//            User user2 = User.builder()
//                    .loginId("abcde2")
//                    .password(encoder.encode("abcdefg1!"))
//                    .nickname("nick2")
//                    .role(Role.ROLE_USER)
//                    .build();
//            User user3 = User.builder()
//                    .loginId("abcde3")
//                    .password(encoder.encode("abcdefg1!"))
//                    .nickname("nick3")
//                    .role(Role.ROLE_USER)
//                    .build();
//            em.persist(user1);
//            em.persist(user2);
//            em.persist(user3);
//
//            Category cateogry1 = Category.builder().name("카테고리1").user(user1).build();
//            Category cateogry2 = Category.builder().name("카테고리2").user(user1).build();
//            Category cateogry3 = Category.builder().name("카테고리3").user(user2).build();
//            em.persist(cateogry1);
//            em.persist(cateogry2);
//            em.persist(cateogry3);
//
//
//            Post post1 = Post.builder().title("제목1").memo("메모1").bookmark(true).url("www.naver.com")
//                    .createdBy(user1.getNickname()).category(cateogry1).user(user1).build();
//            Post post2 = Post.builder().title("제목2").memo("메모2").bookmark(true).url("www.nate.com")
//                    .createdBy(user1.getNickname()).category(cateogry1).user(user1).build();
//            Post post3 = Post.builder().title("제목3").memo("메모3").bookmark(false).url("www.donga.com")
//                    .createdBy(user1.getNickname()).category(cateogry1).user(user1).build();
//            Post post4 = Post.builder().title("제목4").memo("메모4").bookmark(true).url("www.nexon.com")
//                    .createdBy(user1.getNickname()).category(cateogry2).user(user1).build();
//            Post post5 = Post.builder().title("제목5").memo("메모5").bookmark(false).url("www.github.com")
//                    .createdBy(user1.getNickname()).category(cateogry2).user(user1).build();
//            Post post6 = Post.builder().title("제목6").memo("메모6").bookmark(true).url("www.google.com")
//                    .createdBy(user2.getNickname()).category(cateogry3).user(user2).build();
//            for(int i = 0; i<30; i++) {
//                Post post = Post.builder().title("제목" + i).memo("메모" + i).bookmark(true).url("www.naver.com")
//                        .createdBy(user1.getNickname()).category(cateogry1).user(user1).build();
//                em.persist(post);
//            }
//
//            em.persist(post1);
//            em.persist(post2);
//            em.persist(post3);
//            em.persist(post4);
//            em.persist(post5);
//            em.persist(post6);
//        }
//    }
//
//}
//
//
