import styled, { css } from 'styled-components';
import loginBackground from '../images/logInBackground.png';
import { LoginSlider } from '../Components/LoginSlider';

export const Logo = styled.img`
  width: 250px;
  height: 50px;
  align-self: flex-start;
  margin-top: 22px;

  @media screen and (max-width: 768px) {
    align-self: center;
    margin-top: 0;
  }
`;

export const Background = styled.div`
  background-image: url(${loginBackground});
  background-size: cover;
  background-position: center;
  position: fixed;
  background-repeat: no-repeat;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow-y: auto;
`;

export const Container = styled.div`
  width: ${({ width }) => (width ? width : '70vw')};
  height: 70vh;
  display: flex;
  justify-content: center;
  align-items: center;

  @media screen and (max-width: 768px) {
    width: 90vw;
    height: 70vh;
    flex-direction: column;
    justify-content: flex-start;
  }
`;

export const LoginBox = styled.div`
  width: 100%;
  background-color: #ffffff;
  border-radius: 10px;
  padding: 30px;
  box-shadow: 0px 10px 10px 0px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: row;
  justify-content: space-evenly;

  @media screen and (max-width: 768px) {
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 20px;
  }
`;

export const RegistBox = styled.div`
  width: 100%;
  background-color: #ffffff;
  border-radius: 10px;
  padding: 10px;
  box-shadow: 0px 10px 10px 0px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  align-items: center;

  @media screen and (max-width: 768px) {
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 20px;
  }
`;

export const RegistForm = styled.form`
  width: 27vw;
`;

export const Form1 = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 27vw;

  @media screen and (max-width: 768px) {
    padding-left: 0;
    margin-bottom: 30px;
    justify-content: center;
  }
`;

export const Form2 = styled.form`
  display: flex;
  flex-direction: column;
  width: 25vw;
  text-align: center;
  font-size: 1.2rem;
  @media screen and (max-width: 768px) {
    width: 60vw;
    justify-content: center;
  }
`;

export const BottomMargin = styled.div`
  margin-bottom: 5vh;
`;

export const ImgForm = () => {
  return (
    <>
      <BottomMargin />
      <LoginSlider />
    </>
  );
};

export const Input = styled.input`
  height: 45px;
  padding-left: 10px;
  margin-bottom: 20px;
  font-size: 1rem;
  border: none;
  background-color: #dee2e6;
  border-radius: 5px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  box-sizing: border-box; // 요소의 전체 너비와 높이에 패딩과 테두리를 포함하여 일관성을 유지합니다. padding-left로 width 달라지는 문제 수정.
  @media screen and (max-width: 768px) {
    width: 100%;
  }
`;

export const Button = styled.button`
  height: 45px;
  background-color: #f03e3e;
  color: #ffffff;
  border: none;
  border-radius: 5px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  margin-bottom: 20px;
  font-size: 1.4rem;
  width: 100%;
  box-sizing: border-box;

  ${({ small, blue, check }) =>
    small &&
    blue &&
    css`
      margin-left: 10px;
      width: 100px;
      height: 45px;
      font-size: 1rem;
      background-color: ${check ? '#51cf66' : '#339af0'};
    `}

  ${({ big, blue }) =>
    big &&
    blue &&
    css`
      background-color: #339af0;
    `};

  @media screen and (max-width: 768px) {
    width: 100%;
  }

  &:hover {
    ${({ small, blue }) =>
      small && blue
        ? 'background-color: #74c0fc;'
        : 'background-color: #ff6b6b;'};
  }
`;
