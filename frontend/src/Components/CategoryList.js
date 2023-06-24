import React, { useState } from 'react';
import { ExpandLess, ExpandMore } from '@mui/icons-material';
import PlaylistAddIcon from '@mui/icons-material/PlaylistAdd';
import { Typography } from '@mui/material';
import { useRecoilState, useSetRecoilState } from 'recoil';
import {
  categoryDataState,
  expandedCategoryState,
  isCreatingNewCategoryState,
  postDataState,
  postPageState,
  selectedCategoryIdState,
  selectedCategoryNameState,
  totalPostAmountBySelectedCategoryState,
} from '../atoms';
import { List, ListItemButton, ListItemText, Collapse } from '@mui/material';
import SortableList from './SortableList';
import { getPostDataBySelectedCategory } from '../Pages/Async';

const CategoryList = ({ categories }) => {
  const [userCategories, setUserCategories] = useRecoilState(categoryDataState);

  const [isCreatingNewCategory, setIsCreatingNewCategory] = useRecoilState(
    isCreatingNewCategoryState
  );

  const [expandedCategories, setExpandedCategories] = useRecoilState(
    expandedCategoryState
  );

  const [selectedCategoryId, setSelectedCategoryId] = useRecoilState(
    selectedCategoryIdState
  ); // 선택된 카테고리 ID 추적

  const [currentPostData, setPostData] = useRecoilState(postDataState);
  const setTotalPostAmount = useSetRecoilState(
    totalPostAmountBySelectedCategoryState
  );
  const setSelectedCategoryName = useSetRecoilState(selectedCategoryNameState);
  const setPostPage = useSetRecoilState(postPageState);

  const handleExpandClick = (rootId) => {
    // 카테고리 요소 열고 닫기 핸들러
    setExpandedCategories({
      ...expandedCategories,
      [rootId]: !expandedCategories[rootId],
    });
  };

  const handleCategoryClick = async (categoryId, categoryName) => {
    // 선택된 카테고리 id와 이름 변경 핸들러
    const { message, postData, totalPostAmount } =
      await getPostDataBySelectedCategory(categoryId, 0);
    if (message === '카테고리별 게시글 조회 완료') {
      setSelectedCategoryId(categoryId);
      console.log(`categoryName: ${categoryName}, categoryId: ${categoryId}`);
      setSelectedCategoryName(categoryName);
      setPostData(postData);
      console.log(`포스트 개수: ${totalPostAmount}`);
      setTotalPostAmount(totalPostAmount);
      setPostPage(1);
    }
  };

  const handleAddIconClick = (event, rootId) => {
    // 새 카테고리 추가 버튼 클릭시
    event.stopPropagation();
    setIsCreatingNewCategory(rootId);

    setExpandedCategories({
      ...expandedCategories,
      [rootId]: true,
    });
  };

  return (
    <List>
      {userCategories.map(
        (
          root // 상위 카테고리
        ) => (
          <React.Fragment key={root.id}>
            <ListItemButton
              onClick={() => handleExpandClick(root.id)}
              sx={{
                padding: '20px',
                border: '1px solid',
                borderColor: 'primary.main',
                '&:hover .addIcon': {
                  opacity: 1,
                },
              }}
            >
              <ListItemText>
                <Typography fontWeight="fontWeightBold">{root.name}</Typography>
              </ListItemText>
              <PlaylistAddIcon // 카테고리 생성 버튼
                className="addIcon"
                onClick={(event) => handleAddIconClick(event, root.id)}
                sx={{
                  color: '#339af0',
                  marginRight: '5px',
                  cursor: 'pointer',
                  transition: 'opacity 0.2s',
                  opacity: 0,
                }}
              />
              {expandedCategories[root.id] ? ( // 카테고리 목록 열고 닫기
                <ExpandLess sx={{ color: '#A5D8FF' }} />
              ) : (
                <ExpandMore sx={{ color: '#A5D8FF' }} />
              )}
            </ListItemButton>
            <Collapse
              in={expandedCategories[root.id]}
              timeout="auto"
              unmountOnExit
            >
              <SortableList
                root={root}
                userCategories={userCategories}
                setUserCategories={setUserCategories}
                isCreatingNewCategory={isCreatingNewCategory}
                setIsCreatingNewCategory={setIsCreatingNewCategory}
                selectedCategoryId={selectedCategoryId}
                handleCategoryClick={handleCategoryClick}
                setSelectedCategoryName={setSelectedCategoryName}
              />
            </Collapse>
          </React.Fragment>
        )
      )}
    </List>
  );
};

export default CategoryList;
