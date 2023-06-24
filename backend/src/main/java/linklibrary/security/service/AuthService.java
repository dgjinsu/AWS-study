package linklibrary.security.service;

import linklibrary.dto.request.JoinFormDto;
import linklibrary.dto.request.LoginFormDto;
import linklibrary.dto.request.LogoutDto;
import linklibrary.dto.response.ResponseData;
import linklibrary.entity.Role;
import linklibrary.entity.User;
import linklibrary.repository.UserRepository;
import linklibrary.security.filter.TokenProvider;
import linklibrary.security.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

import static linklibrary.security.filter.JwtFilter.BEARER_PREFIX;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisTemplate redisTemplate;

    @Transactional
    public Long join(JoinFormDto joinFormDto) {
        User user = User.builder()
                .loginId(joinFormDto.getLoginId())
                .password(passwordEncoder.encode(joinFormDto.getPassword())) //비밀번호 인코딩
                .nickname(joinFormDto.getNickname())
                .role(Role.ROLE_USER) //user 등급으로 회원가입
                .build();
        validateDuplicateUser(user); // 중복회원 검사
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public TokenDto login(LoginFormDto loginFormDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginFormDto.getLoginId(), loginFormDto.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        try {
            //아이디가 맞지 않을 경우 loadUserByUsername 에서 예외 발생
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            // 3. 인증 정보를 기반으로 JWT 토큰 생성
            TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
            // 4. 토큰 발급
            return tokenDto;
        } catch (BadCredentialsException e) {
            //비밀번호가 맞지 않을 시 예외
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void validateDuplicateUser(User user) {
        User findUser = userRepository.findByLoginId(user.getLoginId());
        if (findUser != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    public ResponseEntity<?> logout(LogoutDto logoutDto) {
        String token = resolveToken(logoutDto.getAccessToken());
        if(!tokenProvider.checkToken(token)) {
            return ResponseEntity.ok(new ResponseData("잘못된 요청입니다.",null));
        }

        // 2. Access Token 에서 authentication 가져옴
        Authentication authentication = tokenProvider.getAuthentication(token);

        // 3. 해당 Access Token 유효시간을 갖고 와서 BlackList 로 저장
        Long expiration = tokenProvider.getExpiration(token);
        redisTemplate.opsForValue()
                .set(token, "logout", expiration, TimeUnit.MILLISECONDS);
        return ResponseEntity.ok(new ResponseData("로그아웃 되었습니다", null));
    }

    private String resolveToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}