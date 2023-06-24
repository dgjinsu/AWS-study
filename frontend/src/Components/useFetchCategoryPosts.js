import { useRecoilValue, useSetRecoilState } from 'recoil';
import { activeCategoryPostsAtom } from '../atoms';

function Category({ category }) { // 특정 카테고리 클릭시 post 로드 비동기 통신 실행
  const fetchCategoryPosts = useFetchCategoryPosts();

  const handleClick = ( {category.id} ) => {
    fetchCategoryPosts(category.id);
  };
  return <div onClick={handleClick(category.id)}>{category.name}</div>;
}

// 카테고리에 대한 포스트 정보 비동기 통신으로 불러오는 예시 코드
function useFetchCategoryPosts() {
  const setActiveCategoryPosts = useSetRecoilState(activeCategoryPostsAtom);

  const fetchCategoryPosts = async (categoryId) => {
    try {
      // 비동기 통신으로 카테고리 포스트 정보 가져오기
      const response = await fetch(`/posts/${categoryId}`);
      const posts = await response.json();

      setActiveCategoryPosts(posts); // atom에 값 저장
    } catch (error) {
      console.error('포스트 정보 가져오는데 실패함: ', error);
    }
  };

  return fetchCategoryPosts;
}



function activeCategoryList() { // atom에서 값 불러와서 페이지에 포스트 요소 렌더링
  const posts= useRecoilValue(activeCategoryPostsAtom);

  return (
    <div>
      {posts.map((post) => (
        <div key={post.id}>{post.name}</div>
      ))}
    </div>
  );
}