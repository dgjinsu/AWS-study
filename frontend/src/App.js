import React, { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { authState, isLoggedInState } from './atoms';
import { MainPage } from './Pages/MainPage';
import { LoginPage } from './Pages/LoginPage';
import { RegistrationPage } from './Pages/RegitrationPage';

const App = () => {
  useEffect(() => {
    const storedAuthState = localStorage.getItem('accessToken');
    if (storedAuthState) {
      console.log(storedAuthState);
    }
  }, []);

  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            localStorage.getItem('accessToken') ? (
              <MainPage />
            ) : (
              <Navigate to="/login" replace />
            )
          }
        />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/join" element={<RegistrationPage />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
