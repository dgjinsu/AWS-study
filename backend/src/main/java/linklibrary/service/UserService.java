package linklibrary.service;

import linklibrary.dto.request.JoinFormDto;
import linklibrary.dto.request.UpdateUserPageFormDto;
import linklibrary.dto.response.UserPageDto;
import linklibrary.entity.Post;
import linklibrary.entity.Role;
import linklibrary.entity.User;
import linklibrary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final PostService postService;

    /**
     * 회원 가입
     */
    public Long join(JoinFormDto joinFormDto) {
        String encode = encoder.encode(joinFormDto.getPassword());
        User user = User.builder()
                .loginId(joinFormDto.getLoginId())
                .password(encoder.encode(joinFormDto.getPassword())) //비밀번호 인코딩
                .nickname(joinFormDto.getNickname())
                .role(Role.ROLE_USER) //user 등급으로 회원가입
                .build();
        validateDuplicateUser(user); // 중복회원 검사
        userRepository.save(user);
        return user.getId();
    }

    /**
     * 아이디 중복 체크
     */
    public Boolean validLoginId(String loginId) {
        User user = userRepository.findByLoginId(loginId);
        return user == null ? true : false;
    }

    /**
     * 닉네임 중복 체크
     */
    public Boolean validNickname(String nickname) {
        User user = userRepository.findByNickname(nickname);
        return user == null ? true : false;
    }

    /**
     * 회원 찾기
     */
    public User findUser(String loginId) {
        User user = userRepository.findByLoginId(loginId);
        if (user == null) {
            throw new EntityNotFoundException("유저 엔티티가 없습니다");
        }
        return user;
    }

    private void validateDuplicateUser(User user) {
        User findUser = userRepository.findByLoginId(user.getLoginId());
        if (findUser != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    public UserPageDto getUserPage(Long userId) {
        Integer totalPost = postService.findTotalPostNumberByUser(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저 엔티티가 없습니다"));
        String storeFileName =
                (user.getProfileImg() != null) ? user.getProfileImg().getStoreFileName() : null;
        UserPageDto userPageDto = UserPageDto.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .storeFileName(storeFileName)
                .totalPost(totalPost)
                .build();
        return userPageDto;
    }

    /////////////////////////////////////////////2022 - 05 -30
    // 마이페이지 수정
    public void change(Long userId, UpdateUserPageFormDto formDto)  {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저 엔티티가 없습니다"));

        Integer totalPost = postService.findTotalPostNumberByUser(userId);

        if (formDto.getNickname() != null && formDto.getPassword() == null) { //Nickname만 받을경우
            if(user.getNickname().equals(formDto.getNickname())) {
                throw new IllegalStateException("이미 존재하는 닉네임입니다.");
            }
            user.setNickname(formDto.getNickname());
        }
        else {
            log.info("=========================");
            log.info(formDto.getPassword());
            if(encoder.matches(formDto.getPassword(), user.getPassword())) {
                throw new IllegalStateException("기존 비밀번호와 동일합니다.");
            }
            user.setPassword(encoder.encode(formDto.getPassword()));
        }

    }


///////////////////////////////////////////////////////////////////////////////////////////////////

    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * 닉네임 변경
     */
    public String updateNickname(String nickname, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저 엔티티가 없습니다"));
        user.setNickname(nickname);
        return user.getNickname();
    }
}
