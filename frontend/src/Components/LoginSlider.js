import React from 'react';
import Slider from 'react-slick';
import styled from 'styled-components';

import LoginImage1 from '../images/LoginImage1.png';
import LoginImage2 from '../images/LoginImage2.png';
import LoginImage3 from '../images/LoginImage3.png';

import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';

const LoginImg1 = styled.img`
  width: 320px;
  height: 250px;
  margin-top: 50px;
`;

const LoginImg2 = styled.img`
  width: 300px;
  height: 250px;
  margin-top: 50px;
  /* padding-left: 330px; */
`;

const LoginImg3 = styled.img`
  width: 320px;
  height: 250px;
  margin-top: 50px;
  /* padding-left: 330px; */
`;

const ImgText = styled.div`
  text-align: center;
  margin-top: 10px;
  margin-bottom: 10px;
  margin-left: 20px;
`;

const SlideContainer = styled.div`
  max-width: 320px;
  max-height: 240px;
  margin-bottom: 150px;
  justify-content: center;
  outline: none;
  .slick-slide > div {
    margin-right: 20px;
  }
`;

export const LoginSlider = () => {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    arrows: false,
  };

  return (
    <SlideContainer>
      <div className="carousel">
        <Slider {...settings}>
          <div>
            <LoginImg1 src={LoginImage1} alt="LoginImage1" />
            <ImgText>
              자주 방문하는 웹사이트들을
              <br /> 효율적으로 관리하세요.
            </ImgText>
          </div>
          <div>
            <LoginImg2 src={LoginImage2} alt="LoginImage2" />
            <ImgText>
              카테고리 별 페이지
              <br />
              분류 기능을 제공합니다.
            </ImgText>
          </div>
          <div>
            <LoginImg3 src={LoginImage3} alt="LoginImage3" />
            <ImgText>
              링크들에 대한 정보를 시각적으로
              <br />
              배치하고 편집할 수 있습니다.
            </ImgText>
          </div>
        </Slider>
      </div>
    </SlideContainer>
  );
};
export default LoginSlider;
