import { useRecoilState, useRecoilValue } from 'recoil';
import {
  isSidebarOpenState,
  postDataState,
  postPageState,
  searchPostByKeywordState,
  selectedCategoryIdState,
  selectedCategoryNameState,
  selectedSortTypeState,
  totalPostAmountBySelectedCategoryState,
} from '../atoms';
import FilterTab from './FilterTab';
import {
  Box,
  Button,
  Grid,
  Typography,
  useMediaQuery,
  useTheme,
} from '@mui/material';
import { PostCard } from './PostCard';
import '../Animations/postcard-transitions.css';
import { TransitionGroup, CSSTransition } from 'react-transition-group';
import {
  getLikePostData,
  getPostDataByKeyword,
  getPostDataBySelectedCategory,
  getPostDataBySort,
  postDelete,
} from '../Pages/Async';
import { useEffect, useState } from 'react';
import UserInfoComponent from './UserInfoComponent';

export const MainComponent = () => {
  const theme = useTheme();
  const isXs = useMediaQuery(theme.breakpoints.down('xs'));
  const isSm = useMediaQuery(theme.breakpoints.between('sm', 'md'));
  const isMd = useMediaQuery(theme.breakpoints.between('md', 'lg'));
  const isLg = useMediaQuery(theme.breakpoints.up('lg'));

  const selectedCategoryName = useRecoilValue(selectedCategoryNameState);
  const selectedCategoryId = useRecoilValue(selectedCategoryIdState);
  const isSidebarOpen = useRecoilValue(isSidebarOpenState);

  const [postcards, setPostcards] = useRecoilState(postDataState);
  const [totalPostAmountBySelectedCategory, setTotalPostAmount] =
    useRecoilState(totalPostAmountBySelectedCategoryState);

  const getPostByCategory = async (categoryId) => {
    // 선택된 카테고리 id와 이름 변경 핸들러
    const { message, postData, totalPostAmount } =
      await getPostDataBySelectedCategory(categoryId, 0);
    if (message === '카테고리별 게시글 조회 완료') {
      console.log(`포스트 삭제후 해당 카테고리 포스트 개수 로드 완료`);
      console.log(`포스트 개수: ${totalPostAmount}`);
      setTotalPostAmount(totalPostAmount);
    }
  };

  const handleDelete = async (id) => {
    console.log(id);
    const message = await postDelete(id);
    console.log(message);
    if (message === '포스트 삭제 완료') {
      console.log(message);
      setPostcards(postcards.filter((postcard) => postcard.postId !== id));
      getPostByCategory(selectedCategoryId);
    }
  };

  const getGridTemplateColumns = () => {
    if (isXs || isSm || isMd || isLg) {
      return 'repeat(auto-fit, minmax(260px, 260px))';
    }
  };

  const getGridGap = () => {
    if (isXs) return '16px';
    if (isSm) return '24px';
    if (isMd) return '32px';
    if (isLg) {
      return isSidebarOpen ? '32px' : '48px';
    }
  };

  const getMarginLeft = () => {
    if (isSidebarOpen) return '80px';
    return '50px';
  };

  // const getMarginRight = () => {
  //   if (isSidebarOpen) return '50px';
  //   return '50px';
  // };

  const [page, setPage] = useRecoilState(postPageState);
  const selectedSortType = useRecoilValue(selectedSortTypeState);
  const searchValue = useRecoilValue(searchPostByKeywordState);

  const loadMorePostData = async () => {
    // 포스트 더보기 버튼 눌렀을 때 불러올 데이터
    if (selectedCategoryName === 'bookmark') {
      // 북마크 조회 클릭된 경우
      const { message, postData, totalPostAmount } = await getLikePostData(
        page
      );
      if (message === '찜목록 or 전체페이지 조회 완료') {
        console.log(`totalPostAmount: ${totalPostAmount}`);
        setPostcards((prevPostData) => [...prevPostData, ...postData]);
        setTotalPostAmount(totalPostAmount);
        setPage(page + 1);
      }
    } else if (selectedCategoryName === '검색된 링크 카드 목록') {
      const { message, postData, totalPostAmount } = await getPostDataByKeyword(
        page,
        searchValue
      );
      if (message === '찜목록 or 전체페이지 조회 완료') {
        console.log(`${searchValue}로 검색된 값 목록 추가 조회 완료`);
        setPostcards((prevPostData) => [...prevPostData, ...postData]);
        setTotalPostAmount(totalPostAmount);
        setPage(page + 1);
      }
    } else {
      // 단순 카테고리 소속 추가 포스트 정보 불러오기
      const { message, postData, totalPostAmount } = await getPostDataBySort(
        page,
        selectedCategoryId,
        selectedSortType
      );
      if (message === '카테고리별 게시글 조회 완료') {
        console.log(`page=${page}, 추가 포스트 데이터 로드 완료`);
        setPage(page + 1);
        setPostcards((prevPostData) => [...prevPostData, ...postData]);
        setTotalPostAmount(totalPostAmount);
      }
    }
  };

  return (
    <Box
      sx={{
        height: '100vh',
        width: isSidebarOpen ? 'calc(100vw - 250px)' : '100vw',
        marginLeft: getMarginLeft(),
        // marginRight: getMarginRight(),
        overflow: 'hidden',
      }}
    >
      <Box
        sx={{
          flexGrow: 1,
          position: 'sticky',
          top: 0,
          zIndex: 1,
          overflowY: 'auto',
        }}
      >
        <Typography variant="h4" component="h4" sx={{ paddingTop: '20px' }}>
          {selectedCategoryName === 'bookmark'
            ? '전체 북마크 링크 목록'
            : selectedCategoryName}
        </Typography>
        {selectedCategoryName !== '프로필' &&
          totalPostAmountBySelectedCategory !== undefined && (
            <>
              <Typography>
                {`총 링크 카드 수: ${totalPostAmountBySelectedCategory}`}
              </Typography>
              <FilterTab />
            </>
          )}
        {selectedCategoryName === '프로필' && <UserInfoComponent />}
      </Box>
      <Box
        sx={{
          marginTop: 2,
          overflowY: 'auto',
          height: 'calc(100vh - 240px)',
        }}
      >
        <Grid
          container
          sx={{
            display: 'grid',
            gridTemplateColumns: getGridTemplateColumns(),
            gap: getGridGap(),
            justifyContent: 'flex-start',
            paddingTop: '30px',
            maxWidth: '100%',
            transition: 'grid-template-columns 0.5s ease-in-out',
          }}
        >
          <TransitionGroup component={null}>
            {selectedCategoryName !== 'bookmark' &&
              postcards
                .filter(
                  (postcard) => postcard.categoryName === selectedCategoryName
                )
                .map((postcard) => (
                  <CSSTransition
                    key={postcard.postId}
                    timeout={300}
                    classNames="postcard"
                  >
                    <Grid item>
                      <PostCard
                        key={postcard.postId}
                        id={postcard.postId}
                        title={postcard.title}
                        url={postcard.url}
                        description={postcard.memo}
                        categoryName={postcard.categoryName}
                        categoryId={postcard.categoryId}
                        bookmark={postcard.bookmark}
                        nickname={postcard.nickname}
                        storeFileName={postcard.storeFileName}
                        onDelete={handleDelete}
                        creationTime={postcard.updatedAt}
                      />
                    </Grid>
                  </CSSTransition>
                ))}
            {selectedCategoryName === '검색된 링크 카드 목록' &&
              postcards.map((postcard) => (
                <CSSTransition
                  key={postcard.postId}
                  timeout={300}
                  classNames="postcard"
                >
                  <Grid item>
                    <PostCard
                      key={postcard.postId}
                      id={postcard.postId}
                      title={postcard.title}
                      url={postcard.url}
                      description={postcard.memo}
                      categoryName={postcard.categoryName}
                      categoryId={postcard.categoryId}
                      bookmark={postcard.bookmark}
                      nickname={postcard.nickname}
                      storeFileName={postcard.storeFileName}
                      onDelete={handleDelete}
                      creationTime={postcard.updatedAt}
                    />
                  </Grid>
                </CSSTransition>
              ))}
            {/* 북마크 조회는 전체 카테고리에 대해서 리스트를 뽑아옴 */}
            {selectedCategoryName === 'bookmark' &&
              postcards.map((postcard) => (
                <CSSTransition
                  key={postcard.postId}
                  timeout={300}
                  classNames="postcard"
                >
                  <Grid item>
                    <PostCard
                      key={postcard.postId}
                      id={postcard.postId}
                      title={postcard.title}
                      url={postcard.url}
                      description={postcard.memo}
                      categoryName={postcard.categoryName}
                      categoryId={postcard.categoryId}
                      bookmark={postcard.bookmark}
                      nickname={postcard.nickname}
                      storeFileName={postcard.storeFileName}
                      onDelete={handleDelete}
                      creationTime={postcard.updatedAt}
                    />
                  </Grid>
                </CSSTransition>
              ))}
          </TransitionGroup>
          {selectedCategoryName !== '프로필' &&
            totalPostAmountBySelectedCategory !== undefined && (
              <Button onClick={loadMorePostData}>
                {totalPostAmountBySelectedCategory === 0
                  ? '+버튼을 눌러 링크 카드를 생성할 수 있습니다.'
                  : 'more...'}
              </Button>
            )}
        </Grid>
      </Box>
    </Box>
  );
};
export default MainComponent;
