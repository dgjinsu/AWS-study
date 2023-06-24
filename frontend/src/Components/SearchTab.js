import React, { useState } from 'react';
import InputBase from '@mui/material/InputBase';
import SearchIcon from '@mui/icons-material/Search';
import { Box, IconButton } from '@mui/material';
import { getPostDataByKeyword } from '../Pages/Async';
import { useRecoilState, useSetRecoilState } from 'recoil';
import {
  postDataState,
  postPageState,
  searchPostByKeywordState,
  selectedCategoryIdState,
  selectedCategoryNameState,
  totalPostAmountBySelectedCategoryState,
} from '../atoms';

const SearchTab = () => {
  const [searchValue, setSearchValue] = useRecoilState(
    searchPostByKeywordState
  );
  const [postcards, setPostcards] = useRecoilState(postDataState);
  const setTotalPostAmount = useSetRecoilState(
    totalPostAmountBySelectedCategoryState
  );
  const [page, setPage] = useRecoilState(postPageState);
  const setSelectedCategoryId = useSetRecoilState(selectedCategoryIdState);
  const setSelectedCategoryName = useSetRecoilState(selectedCategoryNameState);

  const handleInputChange = (event) => {
    setSearchValue(event.target.value);
  };

  const handleSearchClick = async () => {
    // 검색 버튼 핸들러
    console.log('clicked the search btn');
    const { message, postData, totalPostAmount } = await getPostDataByKeyword(
      0,
      searchValue
    );
    if (message === '찜목록 or 전체페이지 조회 완료') {
      console.log(`${searchValue}로 검색된 값 목록 조회 완료`);
      setPostcards(postData);
      setTotalPostAmount(totalPostAmount);
      setPage(1);
      setSelectedCategoryId(-2);
      setSelectedCategoryName('검색된 링크 카드 목록');
    }
  };

  return (
    <Box
      sx={{
        display: 'flex',
        alignItems: 'center',
        borderBottom: '2px solid transparent',
        borderColor: '#74C0FC',
        mt: 1.5,
      }}
    >
      <InputBase
        value={searchValue}
        onChange={handleInputChange}
        placeholder="링크 검색"
        sx={{
          flex: 1,
          color: 'inherit',
          pt: 1,
          pl: 0.5,
        }}
      />
      <IconButton
        onClick={handleSearchClick}
        sx={{
          color: '#74C0FC',
          p: 1,
        }}
      >
        <SearchIcon />
      </IconButton>
    </Box>
  );
};

export default SearchTab;
