package linklibrary.repository;

import linklibrary.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.user.id=:userId")
    List<Category> findByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("delete from Category c where c.id=:id")
    void deleteById(@Param("id") Long id);
}
