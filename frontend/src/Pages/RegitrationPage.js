import React, { useState, useEffect } from 'react';
import { useSetRecoilState, useRecoilValue } from 'recoil';
import { saveUserToLocalStorage, usersState } from '../atoms';
import { useNavigate } from 'react-router-dom';
import {
  Background,
  Container,
  Input,
  RegistBox,
  RegistForm,
} from '../Style/LoginPageStyle';
import { Button } from '../Style/LoginPageStyle';
import { registUser, validateId, validateNickname } from './Async';
import { Box } from '@material-ui/core';

export const RegistrationPage = () => {
  const [userId, setUserId] = useState(''); // 이걸 아이디 스테이트로 쓰고
  const [userName, setUserName] = useState(''); // 이걸 닉네임 스테이트로 바꿔야함.
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [isRegistered, setIsRegistered] = useState(false);
  const navigate = useNavigate();
  const users = useRecoilValue(usersState);
  const setUsers = useSetRecoilState(usersState);
  const [idCheck, setIdCheck] = useState(false); // 이거를 아이디 체크로 쓰고
  const [userNameCheck, setUserNameCheck] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!userId || !password || !confirmPassword || !userName) {
      alert('회원가입 양식을 맞춰주세요.');
      return;
    }
    if (password !== confirmPassword) {
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }
    if (idCheck === false) {
      alert('아이디 중복 체크를 확인해주세요.');
      return;
    }
    if (userNameCheck === false) {
      alert('닉네임 중복 체크를 확인해주세요.');
      return;
    }
    const msg = await registUser(userId, userName, password);
    if (
      msg ===
      '비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.'
    ) {
      alert(msg);
      return;
    }
    if (msg === '회원가입 완료') {
      alert(msg);
      navigate('/login');
      return;
    }
    if (msg === '이미 존재하는 회원입니다') {
      alert(msg);
      return;
    }

    const defaultCategories = [
      { id: '1', name: '페이지 목록', categories: [] },
      { id: '2', name: '미구현', categories: [] },
    ];
    const user = { userId, password, userName, categories: defaultCategories };
    setUsers([...users, user]);
    setIsRegistered(true);
    saveUserToLocalStorage(user);
  };

  const handleIdCheck = async (event) => {
    event.preventDefault();
    const msg = await validateId(userId);

    if (
      msg === '이미 사용중인 아이디 입니다.' ||
      msg === '아이디는 영어 소문자와 숫자만 사용하여 4~20자리여야 합니다.'
    ) {
      // 아이디 중복 체크
      alert(msg);
      setIdCheck(false);
      return;
    } else {
      // 중복 없음 확인

      alert('사용 가능한 아이디입니다.');
      setIdCheck(true);
    }
  };

  const handleUsernameCheck = async (event) => {
    event.preventDefault();
    const msg = await validateNickname(userName);

    if (
      msg === '이미 사용중인 닉네임 입니다.' ||
      msg ===
        'Request processing failed; nested exception is org.springframework.dao.IncorrectResultSizeDataAccessException: query did not return a unique result: 2; nested exception is javax.persistence.NonUniqueResultException: query did not return a unique result: 2'
    ) {
      // 닉네임 중복 체크 or 조건 만족 확인
      alert('이미 사용중인 닉네임입니다.');
      setUserNameCheck(false);
      return;
    } else if (msg === '닉네임은 8글자 이하로 작성해 주세요.') {
      alert(msg);
      setUserNameCheck(false);
      return;
    } else {
      // 중복 없음 확인
      alert(msg); // 사용 가능한 닉네임 입니다.
      setUserNameCheck(true);
    }
  };

  const handleUserIdInputChange = (event) => {
    // id입력창에 입력을 받으면 idcheck를 false로 전환
    setUserId(event.target.value);
    setIdCheck(false);
  };

  const handleUsernameInputChange = (event) => {
    // 닉네임입력창에 입력을 받으면 namecheck를 false로 전환
    setUserName(event.target.value);
    setUserNameCheck(false);
  };

  useEffect(() => {
    if (isRegistered) {
      console.log('Registered!');
      navigate('/login', { replace: true });
    }
  }, [isRegistered, navigate]);

  return (
    <Background>
      <Container width="40vw">
        <RegistBox>
          <h1 style={{ textAlign: 'center' }}>회원가입</h1>
          <RegistForm>
            <label>아이디</label>
            <br />
            <div style={{ display: 'flex' }}>
              <Input
                type="text"
                value={userId}
                onChange={handleUserIdInputChange}
                placeholder="영어 소문자, 숫자 4~20자리"
              />
              <Button small blue check={idCheck} onClick={handleIdCheck}>
                중복확인
              </Button>
            </div>

            <label>닉네임</label>
            <br />
            <div style={{ display: 'flex' }}>
              <Input
                type="text"
                value={userName}
                onChange={handleUsernameInputChange}
                placeholder="8자리 이하"
              />
              <Button
                small
                blue
                check={userNameCheck}
                onClick={handleUsernameCheck}
              >
                중복확인
              </Button>
            </div>

            <label>비밀번호</label>
            <br />
            <Input
              type="password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              placeholder="영문 대소문자, 숫자, 특수문자를 1개 이상 포함 8~16자리"
            />
            <br />
            <label>비밀번호 확인</label>
            <br />
            <Input
              type="password"
              value={confirmPassword}
              onChange={(event) => setConfirmPassword(event.target.value)}
            />

            <br />
            <Box sx={{ display: 'flex', gap: 2 }}>
              <Button big blue onClick={handleSubmit}>
                회원가입
              </Button>
              <Button onClick={() => navigate('/login')}>취소</Button>
            </Box>
          </RegistForm>
        </RegistBox>
      </Container>
    </Background>
  );
};

export default RegistrationPage;
