import React, { useEffect } from 'react';
import { useRecoilState, useSetRecoilState } from 'recoil';
import {
  categoryDataState,
  isSidebarOpenState,
  postDataState,
  selectedUserImg,
  totalPostAmountBySelectedCategory,
  totalPostAmountBySelectedCategoryState,
  userInfoState,
} from '../atoms';
import { useNavigate } from 'react-router-dom';
import Header from '../Components/Header';
import MainFrame from '../Components/MainFrame';
import {
  GetProfileImg,
  getCategoryAndPostData,
  getUserInfo,
  logoutUser,
} from './Async';

export const MainPage = () => {
  const navigate = useNavigate();
  const [categoryData, setCategoryData] = useRecoilState(categoryDataState);
  const [postData, setPostData] = useRecoilState(postDataState);
  const [selectedImageFinal, setSelectedImageFinal] =
    useRecoilState(selectedUserImg);
  const setUserInfo = useSetRecoilState(userInfoState);
  const handleLogout = async () => {
    const msg = await logoutUser(localStorage.getItem('accessToken'));
    if (msg === '잘못된 요청입니다.') {
      alert(msg);
      return;
    }
    if (msg === '로그아웃 되었습니다') {
      alert(msg);
      localStorage.removeItem('accessToken');
      navigate('/login', { replace: true });
      window.location.reload();
    }
  };

  const [isSidebarOpen, setIsSidebarOpen] = useRecoilState(isSidebarOpenState); // 사이드바 관리 State
  const setTotalPostAmountBySelectedCategory = useSetRecoilState(
    totalPostAmountBySelectedCategoryState
  );
  const handleMenuClick = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  useEffect(() => {
    console.log(1);
    const fetchData = async () => {
      const { message, categoryData, postData, totalPostAmount } =
        await getCategoryAndPostData();
      if (message === '찜목록 or 전체페이지 조회 완료') {
        console.log(message);
        console.log(totalPostAmount);
        setTotalPostAmountBySelectedCategory(totalPostAmount);
        setCategoryData((prevCategoryData) => {
          const filteredCategories = prevCategoryData.filter(
            (category) => category.name !== '페이지 목록'
          );

          return [
            ...filteredCategories,
            {
              id: '0', // Assign a unique key such as '0'
              name: '페이지 목록',
              categories: categoryData,
            },
          ];
        });
        // setPostData(postData);
      } else {
        console.error('Failed to fetch post data');
      }
    };
    if (localStorage.getItem('accessToken') === null) {
      navigate('/login', { replace: true });
      window.location.reload();
    } else {
      fetchData();
    }
  }, []);

  useEffect(() => {
    const getUserData = async () => {
      const { message, nickname, totalPost, storeFileName } =
        await getUserInfo();
      if (message === '마이페이지 조회 완료') {
        console.log(`nickname: ${nickname}`);
        console.log(`totalPost: ${totalPost}`);
        console.log(`storeFileName: ${storeFileName}`);

        setUserInfo({
          nickname: nickname,
          totalPost: totalPost,
          storeFileName: storeFileName,
        });
        const userImg = await GetProfileImg(storeFileName);
        setSelectedImageFinal(userImg);
      }
    };
    getUserData();
  }, []);

  return (
    <div
      style={{
        overflowX: 'hidden',
        display: 'flex',
        flexDirection: 'column',
        height: '100vh',
        overflowY: 'hidden',
      }}
    >
      <Header handleLogout={handleLogout} handleMenuClick={handleMenuClick} />
      <div style={{ display: 'flex', flexGrow: 1, overflowY: 'hidden' }}>
        {/* <Category isOpen={isSidebarOpen} onClose={() => setIsSidebarOpen(false)} /> */}
        <MainFrame />
      </div>
    </div>
  );
};

export default MainPage;
