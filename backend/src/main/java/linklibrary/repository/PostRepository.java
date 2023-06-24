package linklibrary.repository;

import linklibrary.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom{

    /**
     * 회원페이지에서 총 post 수 조회
     */
    @Query("select count(p) from Post p where p.user.id=:userId")
    Integer findTotalPostNumberByUser(@Param("userId") Long userId);

    /**
     * 카테고리 ID 에 해당하는 포스트 전부 삭제
     */
    @Modifying
    @Query("delete from Post p where p.category.id=:id")
    void deleteByCategoryId(@Param("id") Long id);

    //    //포스트검색 + 제목명 A-Z 정렬:
//    List<Post> findByTitleContainingOrderByTitleAsc(String title);
//
//    //포스트검색 + 생성날짜 최신순:
//    List<Post> findByTitleContainingOrderByCreatedAtDesc(String title);
//
//    //포스트검색 + 제목명 A-Z 정렬 + 북마크 온:
//    List<Post> findByBookmarkAndTitleContainingOrderByTitleAsc(Boolean bookmark,String title);
//
//    //포스트검색 + 생성날짜 최신순 + 북마크 온 :
    List<Post> findByUserIdAndTitleContainingOrderByTitleAsc(Long userId, String title);

    List<Post> findByUserIdAndTitleContainingOrderByCreatedAtDesc(Long userId, String title);

    List<Post> findByUserIdAndBookmarkAndTitleContainingOrderByTitleAsc(Long userId, Boolean bookmark, String title);

    List<Post> findByUserIdAndBookmarkAndTitleContainingOrderByCreatedAtDesc(Long userId, Boolean bookmark, String title);
/** 포스틑 이름으로 했었던거.*/
//    // PostRepository.java
//    List<Post> findByUserIdAndCategoryNameAndTitleContainingOrderByTitleAsc(Long userId, String categoryName, String keyword);
//
//    List<Post> findByUserIdAndCategoryNameAndTitleContainingOrderByCreatedAtDesc(Long userId, String categoryName, String keyword);
   /** 포스트 아이디로 */
    List<Post> findByUserIdAndCategoryIdAndTitleContainingOrderByTitleAsc(Long userId, Long categoryId, String keyword);

    List<Post> findByUserIdAndCategoryIdAndTitleContainingOrderByCreatedAtDesc(Long userId, Long categoryId, String keyword);



    /** 페이징 추가 */
    Slice<Post> findByUserIdAndTitleContainingOrderByTitleAsc(Long userId, String title, Pageable pageable);

    Slice<Post> findByUserIdAndTitleContainingOrderByCreatedAtDesc(Long userId, String title,Pageable pageable);

    Slice<Post> findByUserIdAndBookmarkAndTitleContainingOrderByTitleAsc(Long userId, Boolean bookmark, String title,Pageable pageable);

    Slice<Post> findByUserIdAndBookmarkAndTitleContainingOrderByCreatedAtDesc(Long userId, Boolean bookmark, String title,Pageable pageable);


    Slice<Post> findByUserIdAndCategoryIdAndTitleContainingOrderByTitleAsc(Long userId, Long categoryId, String keyword,Pageable pageable);

    Slice<Post> findByUserIdAndCategoryIdAndTitleContainingOrderByCreatedAtDesc(Long userId, Long categoryId, String keyword,Pageable pageable);



}
