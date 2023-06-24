package linklibrary.controller;

import io.swagger.annotations.ApiOperation;
import linklibrary.dto.request.*;
import linklibrary.dto.response.ResponseData;
import linklibrary.dto.response.UserPageDto;
import linklibrary.entity.ProfileImg;
import linklibrary.entity.User;
import linklibrary.security.auth.PrincipalDetails;
import linklibrary.security.dto.TokenDto;
import linklibrary.security.service.AuthService;
import linklibrary.service.PostService;
import linklibrary.service.ProfileImgService;
import linklibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final ProfileImgService profileImgService;
    private final AuthService authService;


    /**
     * authService 와 userService 나중에 합쳐야 함
     */
    
    /**
     * 회원가입
     */
    @ApiOperation(value = "유저 생성", notes = "전부 필수값 (없을 시 예외 메세지 전송)")
    @PostMapping("/join")
    public ResponseEntity<ResponseData> joinUser(@Valid @RequestBody JoinFormDto joinFormDto) {
        Long savedUserId = authService.join(joinFormDto);
        return new ResponseEntity<>(new ResponseData("회원가입 완료", savedUserId), HttpStatus.OK);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginFormDto loginFormDto) {
        TokenDto tokenDto = authService.login(loginFormDto);
        return ResponseEntity.ok(new ResponseData("로그인 완료!", tokenDto));
    }

    /**
     * 로그아웃
     */
    @ApiOperation(value = "로그아웃", notes = "필수값: accessToken (없을 시 예외 메세지 전송, 로그인 했을 때 받은 토큰 보내주세요")
    @PostMapping("/logoutUser")
    public ResponseEntity<?> logout(@Valid @RequestBody LogoutDto logoutDto) {
        return authService.logout(logoutDto);
    }


    /**
     * 회원가입시 같은 아이디가 있는지 중복체크
     */
    @ApiOperation(value = "ID 중복 체크", notes = "아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.")
    @PostMapping("/validation-id")
    public ResponseEntity<ResponseData> validateUserId(@Valid @RequestBody ValidateIdForm validateIdForm) {
        Boolean useful = userService.validLoginId(validateIdForm.getLoginId());
        String msg = "";
        if (useful) {
            msg = "사용 가능한 아이디 입니다.";
        } else {
            msg = "이미 사용중인 아이디 입니다.";
        }
        return ResponseEntity.ok(new ResponseData(msg, null));
    }

    /**
     * 회원가입시 같은 닉네임이 있는지 중복체크
     */
    @ApiOperation(value = "닉네임 중복 체크", notes = "닉네임은 최대 8글자까지 가능합니다.")
    @PostMapping("/validation-nickname")
    public ResponseEntity<?> validateNickname(@Valid @RequestBody ValidateNicknameForm validateNicknameForm) {
        Boolean useful = userService.validNickname(validateNicknameForm.getNickname());
        String msg = "";
        if (useful) msg = "사용 가능한 닉네임 입니다.";
        else msg = "이미 사용중인 닉네임 입니다.";
        return ResponseEntity.ok(new ResponseData(msg, null));
    }

    /**
     * 닉네임 변경
     */
    @ApiOperation(value = "닉네임 변경", notes = "닉네임은 최대 8글자까지 가능합니다.")
    @PutMapping("/nickname")
    public ResponseEntity<?> updateNickname(@Valid @RequestBody ValidateNicknameForm validateNicknameForm,
                                            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.validNickname(validateNicknameForm.getNickname());
        String updateNickname = userService.updateNickname(validateNicknameForm.getNickname(), principalDetails.getUserDto().getUserId());
        return ResponseEntity.ok(new ResponseData("닉네임 업데이트 완료", updateNickname));
    }

    /**
     * 회원 프로필 이미지 업로드
     */
    @PostMapping(path = "/profileImg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImg(@AuthenticationPrincipal PrincipalDetails principalDetails, ProfileImgDto profileImgDto) throws IOException {
        Long userId = principalDetails.getUserDto().getUserId();
        User user = userService.findUser(principalDetails.getUserDto().getLoginId());

        ProfileImg profileImg = profileImgService.uploadImg(profileImgDto.getProfileImg(), userId);
        return ResponseEntity.ok(new ResponseData("이미지 업로드 완료", profileImg.getStoreFileName()));
    }

    /**
     * 마이페이지 조회
     */
    @GetMapping("/user-info")
    public ResponseEntity<?> getUserPage(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        //여기서 마이페이지 정보들과 사진이 저장된 이름(UUID.png) 를 넘김
        //프론트단에서 src="images/{imgName} 하면 getImage() 에서 넘겨줌
        //따라서 여기선 저장된 이미지 이름만 넘기면 됨
        Long userId = principalDetails.getUserDto().getUserId();
        UserPageDto userPageDto = userService.getUserPage(userId);
        return ResponseEntity.ok(new ResponseData("마이페이지 조회 완료", userPageDto));
    }

    /**
     * 로컬 경로에 해당하는 이미지 불러오기
     */
    @ApiOperation(value = "프로필 이미지 불러오기", notes = "프로필 이미지 업로드 할 때 반환되었던 파일명 fileName 파라미티에 넣고 요청")
    @GetMapping("/images/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {
        // 로컬 경로에 있는 이미지 파일을 읽어와서 byte 배열로 변환합니다.
        String fullPath = profileImgService.getFullPath(fileName);
        File imageFile = new File(fullPath); //파일경로에 해당하는걸 File 로 만듦
        byte[] imageBytes = new byte[(int) imageFile.length()]; //File 크기만큼의 byte 배열 생성
        FileInputStream inputStream = new FileInputStream(imageFile); //File 로 FileInputStream 생성
        inputStream.read(imageBytes); //byte 배열 크기만큼 inputStream 에서 읽어 배열에 담음
        inputStream.close();

        // HTTP 응답으로 byte 배열과 MIME 타입을 전송
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) //메서드는 응답 바디의 타입을 image/jpeg 로 설정
                .body(imageBytes);
    }



    @DeleteMapping("delete-user")
    public ResponseEntity<?> delete(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.delete(principalDetails.getUserDto().getUserId());
        return ResponseEntity.ok(new ResponseData("회원삭제완료", null));
    }

    @GetMapping("/joinCheck")
    public String test(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return principalDetails.getUsername();
    }
////////////////////////////////////////////// 2023 - 05 - 30
    @PutMapping("/user-info")
    public ResponseEntity<?> updateUserPage(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                            @Valid @RequestBody final UpdateUserPageFormDto formDto) {
        Long userId = principalDetails.getUserDto().getUserId();
        userService.change(userId, formDto);
        UserPageDto userPageDto = userService.getUserPage(userId);
        return ResponseEntity.ok(new ResponseData("마이페이지 수정 완료", userPageDto));
    }
    //////////////////////////////////////////////////

}
