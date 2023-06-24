import {
  Box,
  Divider,
  Typography,
  TextField,
  Button,
  Avatar,
} from '@mui/material';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import { selectedUserImg, userInfoState } from '../atoms';
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import AddPhotoAlternateIcon from '@mui/icons-material/AddPhotoAlternate';
import LayersIcon from '@mui/icons-material/Layers';
import { useRef, useState } from 'react';
import {
  EditProfileImg,
  EditUserNickname,
  EditUserPassword,
} from '../Pages/Async';

const AccountInfoSection = () => {
  return (
    <>
      <Typography variant="h5">계정 관리</Typography>
      <Divider sx={{ borderColor: '#A5D8FF', borderWidth: '2px' }} />
    </>
  );
};

const RecordInfoSection = () => {
  return (
    <>
      <Typography variant="h5">기록 조회</Typography>
      <Divider sx={{ borderColor: '#A5D8FF', borderWidth: '2px' }} />
    </>
  );
};

const NicknameChangeSection = () => {
  // const navigate = useNavigate();
  const prevNickname = useRecoilValue(userInfoState);
  const [nickname, setNickname] = useState(prevNickname.nickname);

  const handleNicknameChangeBtn = async () => {
    console.log(nickname);
    if (nickname === '') {
      alert('변경할 닉네임을 입력한 후 변경 버튼을 눌러주세요.');
      return;
    } else if (nickname.length >= 9) {
      alert('닉네임은 8글자 이하로 작성해 주세요.');
      return;
    } else if (nickname === prevNickname.nickname) {
      alert('현재 사용중인 닉네임입니다.');
    } else {
      const { message, newNickname } = await EditUserNickname(nickname);
      if (message === '마이페이지 수정 완료') {
        alert(message);
        // navigate('/', { replace: true });
        // window.location.reload();
      } else if (message === '이미 존재하는 닉네임입니다.') {
        alert(message);
        return;
      } else {
        console.log('WTF?');
      }
    }
  };

  return (
    <Box sx={{ margin: '50px' }}>
      <Typography variant="h6">닉네임 변경</Typography>
      <Box sx={{ display: 'flex', alignItems: 'center', marginTop: '30px' }}>
        <TextField
          defaultValue={prevNickname.nickname}
          variant="outlined"
          size="small"
          sx={{ backgroundColor: 'white' }}
          onChange={(e) => setNickname(e.target.value)}
        />
        <Button
          variant="outlined"
          onClick={handleNicknameChangeBtn}
          sx={{
            marginLeft: '10px',
            width: '80px',
            backgroundColor: '#4dabf7',
            color: 'white',
            fontSize: '16px',
            '&:hover': {
              backgroundColor: '#3b8ac9',
            },
          }}
        >
          변경
        </Button>
      </Box>
    </Box>
  );
};

const PasswordChangeSection = () => {
  const [password, setPassword] = useState('');
  const handlePasswordChangeBtn = async () => {
    console.log(password);
    if (password === '') {
      alert('변경할 비밀번호를 입력한 후 변경 버튼을 눌러주세요.');
      return;
    } else {
      const { message } = await EditUserPassword(password);
      console.log(message);
      if (message === '마이페이지 수정 완료') {
        alert(message);
        // navigate('/', { replace: true });
        // window.location.reload();
      } else if (message === '중복된 비밀번호 입니다.') {
        alert(message);
      } else if (
        message ===
        '비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.'
      ) {
        alert(message);
      } else {
        console.log(message);
      }
    }
  };

  return (
    <Box sx={{ margin: '50px' }}>
      <Typography variant="h6">비밀번호 변경</Typography>
      <Box sx={{ display: 'flex', alignItems: 'center', marginTop: '30px' }}>
        <TextField
          variant="outlined"
          size="small"
          type="password"
          sx={{ backgroundColor: 'white' }}
          onChange={(e) => setPassword(e.target.value)}
        />
        <Button
          variant="outlined"
          onClick={handlePasswordChangeBtn}
          sx={{
            marginLeft: '10px',
            width: '80px',
            backgroundColor: '#4dabf7',
            color: 'white',
            fontSize: '16px',
            '&:hover': {
              backgroundColor: '#3b8ac9',
            },
          }}
        >
          변경
        </Button>
      </Box>
    </Box>
  );
};

const ProfileImgSection = () => {
  const fileInputRef = useRef(null);

  const [selectedImageFinal, setSelectedImageFinal] =
    useRecoilState(selectedUserImg);
  const [selectedImage, setSelectedImage] = useState(selectedImageFinal);
  const [selectedOriginImg, setSelectedOriginImg] = useState(null);

  const handleImgClick = () => {
    fileInputRef.current.click();
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    setSelectedImage(URL.createObjectURL(file));
    setSelectedOriginImg(file);
    console.log(file);
  };

  const handleEditProfileImg = async () => {
    // 프로필 이미지 최종 변경 버튼
    const { message, imgName } = await EditProfileImg(selectedOriginImg);
    console.log(message); // or handle the response as required
    if (message === '이미지 업로드 완료') {
      alert(message);
      setSelectedImageFinal(selectedImage);
      //깔끔하게 리다이렉트 ㄱㄱ
    }
  };

  return (
    <Box sx={{ margin: '50px', position: 'relative' }}>
      <Typography variant="h6">프로필 사진 변경</Typography>
      <div
        onMouseEnter={(e) => {
          const addIcon = e.currentTarget.children[1];
          addIcon.style.opacity = '1';
        }}
        onMouseLeave={(e) => {
          const addIcon = e.currentTarget.children[1];
          addIcon.style.opacity = '0';
        }}
      >
        <Avatar
          sx={{
            width: '300px',
            height: '300px',
            fontSize: '130px',
            color: '#ffffff',
            cursor: 'pointer',
            border: '8px solid #ffffff',
            boxShadow: '0px 5px 15px rgba(0, 0, 0, 0.15)',
          }}
          onClick={handleImgClick}
          src={selectedImage || undefined}
        />
        <AddPhotoAlternateIcon
          sx={{
            fontSize: '80px',
            position: 'absolute',
            bottom: '-20px',
            right: '-20px',
            transform: 'translate(-50%, -50%)',
            opacity: '0',
            transition: 'opacity 0.3s',
            color: '#339AF3',
            cursor: 'pointer',
          }}
          onClick={handleImgClick}
        />
      </div>
      <input
        type="file"
        accept="image/*"
        ref={fileInputRef}
        style={{ display: 'none' }}
        onChange={handleFileChange}
      />
      <Button
        variant="contained"
        color="primary"
        onClick={handleEditProfileImg}
        sx={{ marginTop: '20px' }}
      >
        프로필 이미지 변경
      </Button>
    </Box>
  );
};

const UserInfoComponent = () => {
  const [nickname, setNickname] = useRecoilState(userInfoState);
  return (
    <Box
      sx={{
        marginRight: '80px',
        marginTop: '50px',
        marginBottom: '50px',
        height: 'calc(100vh - 240px)',
        overflow: 'auto',
      }}
    >
      <AccountInfoSection />
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'space-between',
          alignItems: 'flex-start',
          maxWidth: '1000px',
          margin: '0 auto',
        }}
      >
        <Box>
          <NicknameChangeSection />
          <PasswordChangeSection />
        </Box>
        <ProfileImgSection />
      </Box>
      <Box sx={{ overflow: 'auto' }}>
        <RecordInfoSection />
        <Box sx={{ margin: '50px', display: 'flex', alignItems: 'center' }}>
          <LayersIcon sx={{ fontSize: '30px', color: '#339AF0' }} />
          <Typography variant="h6" sx={{ paddingLeft: '10px' }}>
            총 링크 카드 수: {nickname.totalPost}
          </Typography>
        </Box>
      </Box>
    </Box>
  );
};

export default UserInfoComponent;
