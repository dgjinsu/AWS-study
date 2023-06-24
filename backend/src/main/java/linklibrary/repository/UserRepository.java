package linklibrary.repository;

import linklibrary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginId(String loginId);

    User findByNickname(String nickname);
}
