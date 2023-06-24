package linklibrary.service;

import linklibrary.dto.request.JoinFormDto;
import linklibrary.entity.User;
import linklibrary.repository.UserRepository;
import linklibrary.security.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;

    @Test
    public void 회원가입() throws Exception {
        //given
        JoinFormDto joinFormDto = getJoinFormDto();
        //when
        Long joinUserId = authService.join(joinFormDto);
        //then
        User findUser = userRepository.findById(joinUserId).get();
        assertThat(findUser.getLoginId()).isEqualTo(joinFormDto.getLoginId());
        assertThat(findUser.getNickname()).isEqualTo(joinFormDto.getNickname());
    }

    @Test
    public void 아이디중복체크() throws Exception {
        //given
        JoinFormDto joinFormDto1 = getJoinFormDto();
        authService.join(joinFormDto1);
        JoinFormDto joinFormDto2 = getJoinFormDto(); //중복 아이디
        JoinFormDto joinFormDto3 = getJoinFormDto(); //중복되지 않은 아이디
        joinFormDto3.setLoginId("abcdef");
        //when
        Boolean useful1 = userService.validLoginId(joinFormDto2.getLoginId()); //이미 있는 아이디로 회원가입 하려는 경우
        Boolean useful2 = userService.validLoginId(joinFormDto3.getLoginId());//사용 가능한 아이디로 회원가입 하려는 경우
        //then
        assertThat(useful1).isFalse();
        assertThat(useful2).isTrue();
    }


    private JoinFormDto getJoinFormDto() {
        JoinFormDto joinFormDto = new JoinFormDto();
        joinFormDto.setLoginId("abcde");
        joinFormDto.setPassword("abcdefg1!");
        joinFormDto.setNickname("요롱롱");
        return joinFormDto;
    }
}