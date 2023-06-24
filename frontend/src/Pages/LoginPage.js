import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Container,
  LoginBox,
  Form1,
  Form2,
  Input,
  Button,
  Background,
  ImgForm,
  BottomMargin,
  Logo,
} from '../Style/LoginPageStyle';
import CheckBoxOutlineBlankIcon from '@mui/icons-material/CheckBoxOutlineBlank';
import CheckBoxIcon from '@mui/icons-material/CheckBox';
import LockIcon from '@mui/icons-material/Lock';
import LinkLibraryLogo from '../images/LinkLibraryLogo.png';
import { login } from './Async';

export const LoginPage = () => {
  const navigate = useNavigate();
  const [userId, setUserId] = useState('');
  const [password, setPassword] = useState('');

  const [checkClicked, setCheckClicked] = useState(false);

  const handleLogin = async (event) => {
    event.preventDefault();
    try {
      const { message, accessToken } = await login(userId, password);
      console.log('Response data:', message, accessToken);
      if (
        message === '아이디를 다시 학인해주세요' ||
        message === '비밀번호가 일치하지 않습니다.'
      ) {
        alert(message);
        return;
      }

      if (message === '로그인 완료!') {
        alert(message);
        localStorage.setItem('accessToken', accessToken);
        navigate('/');
        window.location.reload();
      }
    } catch (error) {
      const result = error.response.data;
      alert(result.message);
    }
  };

  const handleCheckClick = () => {
    setCheckClicked(!checkClicked);
  };

  // useEffect(() => {
  //   if (isRegistered !== null) {
  //     navigate('/', { replace: true });
  //     window.location.reload();
  //   }
  // }, [isRegistered, navigate]);

  return (
    <Background>
      <Container>
        <LoginBox>
          <Form1>
            <Logo src={LinkLibraryLogo} alt="Link Library Logo" />
            <ImgForm />
            <BottomMargin />
          </Form1>
          <Form2 onSubmit={handleLogin}>
            <h1>로그인</h1>
            <BottomMargin />
            <Input
              type="text"
              placeholder="아이디"
              value={userId}
              onChange={(event) => setUserId(event.target.value)}
            />
            <Input
              type="password"
              placeholder="비밀번호"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
            />
            <div style={{ display: 'flex' }}>
              <div onClick={handleCheckClick} style={{ color: '#4dabf7' }}>
                {checkClicked ? <CheckBoxIcon /> : <CheckBoxOutlineBlankIcon />}
              </div>
              <div
                style={{ marginLeft: 10, marginBottom: 20, fontSize: '1.2rem' }}
              >
                로그인 상태 유지
              </div>
            </div>
            <div style={{ display: 'flex' }}>
              <div style={{ color: '#4dabf7' }}>
                <LockIcon />
              </div>
              <div
                style={{ marginLeft: 10, marginBottom: 20, fontSize: '1.2rem' }}
              >
                아이디 | 비밀번호 찾기
              </div>
            </div>

            <Button type="submit">로그인</Button>
            <Button onClick={() => navigate('/join')}>회원가입</Button>
            {/* navigate는 이벤트로 사용시 useNavigate로 사용해야함. */}
          </Form2>
        </LoginBox>
      </Container>
    </Background>
  );
};

export default LoginPage;
