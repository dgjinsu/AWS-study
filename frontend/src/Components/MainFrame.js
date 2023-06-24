import React from 'react';
import { useRecoilValue } from 'recoil';
import { isSidebarOpenState } from '../atoms';
import Sidebar from './Sidebar';
import MainComponent from './MainComponent';

export const MainFrame = () => {
  const isSidebarOpen = useRecoilValue(isSidebarOpenState);
  return (
    <div
      style={{
        // 메인 화면 컴포넌트가 오른쪽으로 밀리는 효과
        display: 'flex',
        flexDirection: 'row',
        backgroundColor: '#e7f5ff',
        marginLeft: isSidebarOpen ? '250px' : '0',
        transition: 'margin-left 0.5s ease-in-out',
        position: 'absolute',
        top: '78px',
        left: '0',
        bottom: '0',
        right: '0',
        overflowY: 'hidden',
      }}
    >
      <Sidebar />
      <MainComponent />
    </div>
  );
};

export default MainFrame;
