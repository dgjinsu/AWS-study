import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  MenuItem,
  Menu,
  IconButton,
} from '@mui/material';
import { useEffect, useState } from 'react';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import {
  categoryDataState,
  postDataState,
  postPageState,
  selectedCategoryIdState,
  selectedCategoryNameState,
  totalPostAmountBySelectedCategoryState,
} from '../atoms';
import MenuIcon from '@mui/icons-material/Menu';
import { getPostDataBySelectedCategory, postEdit } from '../Pages/Async';

const EditPostcardDialog = ({
  open,
  handleClose,
  postId,
  url,
  memo,
  nickname,
  updatedAt,
  bookmark,
  title,
  categoryId,
  categoryName,
  storeFileName,
}) => {
  const userCategories = useRecoilValue(categoryDataState); // 카테고리 관리 atom 불러오기
  const [anchorEl, setAnchorEl] = useState(null); // 메뉴바 위치 추적
  const [postData, setPostData] = useRecoilState(postDataState);
  const setPlaceCategoryNameMoveOn = useSetRecoilState(
    selectedCategoryNameState
  );
  const setPostPage = useSetRecoilState(postPageState);
  const setPlaceCategoryIdMoveOn = useSetRecoilState(selectedCategoryIdState);
  const setTotalPostAmount = useSetRecoilState(
    totalPostAmountBySelectedCategoryState
  );
  const getPostByCategory = async (categoryId, categoryName) => {
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
    }
  };

  const handleEdit = async () => {
    // 포스트 정보 수정 메서드
    const {
      message,
      newCategoryId, // 변경된 카테고리 id 값
    } = await postEdit(
      postId,
      bookmark,
      selectedCategoryId,
      newMemo,
      newTitle,
      newUrl
    );

    if (message === '포스트 수정 완료') {
      console.log(message);
      const updatedPostData = postData.map((post) => {
        if (post.postId === postId) {
          return {
            ...post,
            postId: postId,
            title: newTitle,
            memo: newMemo,
            url: newUrl,
            bookmark: bookmark,
            nickname: nickname,
            updatedAt: updatedAt,
            categoryName: selectedCategoryName,
            storeFileName: storeFileName,
            categoryId: newCategoryId,
          };
        }
        return post;
      });
      setPostData(updatedPostData);
      setPlaceCategoryNameMoveOn(selectedCategoryName);
      setPlaceCategoryIdMoveOn(newCategoryId);
      getPostByCategory(selectedCategoryId, selectedCategoryName);
      setPostPage(1);
      handleClose();
    }
  };

  const handleMenuClick = (event) => {
    setAnchorEl(event.currentTarget);
    event.stopPropagation();
  };
  const handleMenuClose = (categoryName) => {
    setAnchorEl(null);
    if (typeof categoryName === 'string') {
      const selectedCategory = pageListSubcategories.find(
        (subcategory) => subcategory.name === categoryName
      );
      setSelectedCategoryName(categoryName);
      setSelectedCategoryId(selectedCategory?.categoryId || null);
      console.log(
        `categoryName: ${categoryName}, categoryId: ${selectedCategory?.categoryId}`
      );
    }
  };

  const pageListCategory = userCategories.find(
    // 페이지 목록 하위 카테고리만 골라내기
    (category) => category.name === '페이지 목록'
  );

  const pageListSubcategories = pageListCategory
    ? pageListCategory.categories
    : [];

  const [selectedCategoryId, setSelectedCategoryId] = useState(categoryId);
  const [selectedCategoryName, setSelectedCategoryName] =
    useState(categoryName);

  const [newTitle, setNewTitle] = useState('');
  const [newUrl, setNewUrl] = useState('');
  const [newMemo, setNewMemo] = useState('');

  useEffect(() => {
    // 팝업창 열리면 포스트에 정보 불러오기
    setNewTitle(title);
    setNewUrl(url);
    setNewMemo(memo);
    setSelectedCategoryId(categoryId);
    setSelectedCategoryName(categoryName);
  }, [categoryId, categoryName, memo, title, url]);

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>웹사이트 수정</DialogTitle>
      <DialogContent>
        <TextField
          label="웹 사이트 이름"
          fullWidth
          margin="normal"
          value={newTitle}
          onClick={(event) => event.stopPropagation()}
          onChange={(e) => setNewTitle(e.target.value)}
        />
        <TextField
          label="사이트 URL"
          fullWidth
          margin="normal"
          value={newUrl}
          onClick={(event) => event.stopPropagation()}
          onChange={(e) => setNewUrl(e.target.value)}
        />
        <TextField
          label="사이트 설명(최대 40자)"
          fullWidth
          multiline
          rows={4}
          margin="normal"
          value={newMemo}
          onClick={(event) => event.stopPropagation()}
          onChange={(e) => setNewMemo(e.target.value)}
        />
        <TextField
          label="카테고리"
          value={
            selectedCategoryName
              ? selectedCategoryName
              : '카테고리를 선택하세요.'
          }
          fullWidth
          margin="normal"
          onClick={handleMenuClick}
          readOnly
          InputProps={{
            endAdornment: (
              <IconButton>
                <MenuIcon />
              </IconButton>
            ),
          }}
        />
        <Menu
          anchorEl={anchorEl}
          open={Boolean(anchorEl)}
          onClose={handleMenuClose}
        >
          {pageListSubcategories.map((subcategory, index) => (
            <MenuItem
              key={index}
              onClick={() => handleMenuClose(subcategory.name)}
            >
              {subcategory.name}
            </MenuItem>
          ))}
        </Menu>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose}>취소하기</Button>
        <Button variant="contained" onClick={handleEdit}>
          수정하기
        </Button>
      </DialogActions>
    </Dialog>
  );
};
export default EditPostcardDialog;
