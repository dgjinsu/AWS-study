import instance from './Instance';

export const validateId = async (id) => {
  try {
    const response = await instance.post('/validation-id', {
      loginId: id,
    });

    const result = response.data;
    return result.message;
  } catch (error) {
    console.error('Error validating ID:', error);
    return 'Error occurred while validating the ID';
  }
};

export const validateNickname = async (nick) => {
  try {
    const response = await instance.post('/validation-nickname', {
      nickname: nick,
    });

    const result = response.data;
    return result.message;
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const registUser = async (userId, nickname, userPw) => {
  try {
    const response = await instance.post('/join', {
      loginId: userId,
      nickname: nickname,
      password: userPw,
    });

    const result = response.data;
    return result.message;
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const login = async (loginId, password) => {
  try {
    const response = await instance.post('/login', {
      loginId: loginId,
      password: password,
    });

    const result = response.data;
    return {
      message: result.message,
      accessToken: result.data.accessToken,
    };
  } catch (error) {
    const result = error.response.data;
    return { message: result.message };
  }
};

export const logoutUser = async (accessToken) => {
  try {
    const response = await instance.post('/logoutUser', {
      accessToken: accessToken,
    });

    const result = response.data;
    return result.message;
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const categoryCreate = async (categoryName) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    const response = await instance.post(
      '/category',
      {
        name: categoryName,
      },
      {
        headers: {
          Authorization: accessToken,
        },
      }
    );

    const result = response.data;
    return { message: result.message, receivedId: result.data };
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const categoryEdit = async (categoryName, categoryId) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    const response = await instance.put(
      `/category/${categoryId}`,
      {
        name: categoryName,
      },
      {
        headers: {
          Authorization: accessToken,
        },
      }
    );

    const result = response.data;
    return result.message;
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const categoryDelete = async (categoryId) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    const response = await instance.delete(`/category/${categoryId}`, {
      headers: {
        Authorization: accessToken,
      },
    });

    const result = response.data;
    return result.message;
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const postCreate = async (bookmark, categoryId, memo, title, url) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    const response = await instance.post(
      '/post',
      {
        bookmark: bookmark,
        categoryId: categoryId,
        memo: memo,
        title: title,
        url: url,
      },
      {
        headers: {
          Authorization: accessToken,
        },
      }
    );

    const result = response.data;
    return {
      message: result.message,
      postId: result.data.postId,
      title: result.data.title,
      memo: result.data.memo,
      url: result.data.url,
      bookmark: result.data.bookmark,
      nickname: result.data.nickname,
      updatedAt: result.data.updatedAt,
      categoryName: result.data.categoryName,
      storeFileName: result.data.storeFileName,
      categoryId: result.data.categoryId,
    };
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const postDelete = async (postId) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    const response = await instance.delete(
      `/post/${postId}`,
      {
        postId: postId,
      },
      {
        headers: {
          Authorization: accessToken,
        },
      }
    );

    const result = response.data;
    return result.message;
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const postEdit = async (
  postId,
  bookmark,
  categoryId,
  memo,
  title,
  url
) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    const response = await instance.put(
      `/post/${postId}`,
      {
        postId: postId,
        bookmark: bookmark,
        categoryId: categoryId,
        memo: memo,
        title: title,
        url: url,
      },
      {
        headers: {
          Authorization: accessToken,
        },
      }
    );
    const result = response.data;
    return {
      message: result.message,
      newCategoryId: result.data.categoryId,
    };
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const getCategoryAndPostData = async () => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    const response = await instance.get('/posts', {
      headers: {
        Authorization: accessToken,
      },
    });

    const result = response.data;
    return {
      message: result.message,
      categoryData: result.data.categoryDtoList,
      postData: result.data.postDtoList.content,
      totalPostAmount: result.size,
    };
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const getPostDataBySelectedCategory = async (categoryId, page) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    const response = await instance.get(`/posts/${categoryId}?page=${page}`, {
      headers: {
        Authorization: accessToken,
      },
    });

    const result = response.data;
    return {
      message: result.message,
      postData: result.data.postDtoList.content,
      totalPostAmount: result.data.size,
    };
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const getLikePostData = async (page) => {
  try {
    const accessToken = localStorage.getItem('accessToken');
    const response = await instance.get(
      `/posts?bookmark=${true}&page=${page}`,
      {
        headers: {
          Authorization: accessToken,
        },
      }
    );

    const result = response.data;
    return {
      message: result.message,
      postData: result.data.postDtoList.content,
      totalPostAmount: result.data.total,
    };
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

// export const getPostDataByTitle = async (page, bookmark) => {
//   try {
//     const accessToken = localStorage.getItem('accessToken');
//     let endpoint = '';

//     if (bookmark) {
//       endpoint = `/posts?bookmark=true&page=${page}`;
//     } else {
//       endpoint = `/posts?sort=byTitle&page=${page}`;
//     }

//     const response = await instance.get(endpoint, {
//       headers: {
//         Authorization: accessToken,
//       },
//     });

//     const result = response.data;
//     return {
//       message: result.message,
//       postData: result.data.postDtoList.content,
//       totalPostAmount: result.data.total,
//     };
//   } catch (error) {
//     const result = error.response.data;
//     return result.message;
//   }
// };

export const getPostDataBySort = async (page, categoryId, sort) => {
  try {
    const accessToken = localStorage.getItem('accessToken');

    const response = await instance.get(
      `/posts/${categoryId}?sort=${sort}&page=${page}`,
      {
        headers: {
          Authorization: accessToken,
        },
      }
    );

    const result = response.data;
    return {
      message: result.message,
      postData: result.data.postDtoList.content,
      totalPostAmount: result.data.total,
    };
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const getPostDataByKeyword = async (page, keyword) => {
  try {
    const accessToken = localStorage.getItem('accessToken');

    const response = await instance.get(
      `/posts?keyword=${keyword}&page=${page}`,
      {
        headers: {
          Authorization: accessToken,
        },
      }
    );

    const result = response.data;
    return {
      message: result.message,
      postData: result.data.postDtoList.content,
      totalPostAmount: result.data.total,
    };
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const getUserInfo = async () => {
  try {
    const accessToken = localStorage.getItem('accessToken');

    const response = await instance.get('/user-info', {
      headers: {
        Authorization: accessToken,
      },
    });

    const result = response.data;
    return {
      message: result.message,
      nickname: result.data.nickname,
      totalPost: result.data.totalPost,
      storeFileName: result.data.storeFileName,
    };
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const EditUserNickname = async (nickname) => {
  try {
    const accessToken = localStorage.getItem('accessToken');

    const response = await instance.put(
      '/user-info',
      {
        nickname: nickname,
      },
      {
        headers: {
          Authorization: accessToken,
        },
      }
    );

    const result = response.data;
    return {
      message: result.message,
      newNickname: result.data.nickname,
    };
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const EditUserPassword = async (password) => {
  try {
    const accessToken = localStorage.getItem('accessToken');

    const response = await instance.put(
      '/user-info',
      {
        password: password,
      },
      {
        headers: {
          Authorization: accessToken,
        },
      }
    );

    const result = response.data;
    return {
      message: result.message,
    };
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const EditProfileImg = async (img) => {
  try {
    const formData = new FormData();
    formData.append('profileImg', img);

    const accessToken = localStorage.getItem('accessToken');
    const response = await instance.post('/profileImg', formData, {
      headers: {
        Authorization: accessToken,
        'Content-Type': 'multipart/form-data',
      },
    });

    const result = response.data;
    return {
      message: result.message,
      imgName: result.data,
    };
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};

export const GetProfileImg = async (imgName) => {
  try {
    const accessToken = localStorage.getItem('accessToken');

    const response = await instance.get(`/images/${imgName}`, {
      responseType: 'blob',
      headers: {
        Authorization: accessToken,
      },
    });

    const blob = new Blob([response.data], { type: 'image/jpeg' });
    const image = URL.createObjectURL(blob);

    return image;
  } catch (error) {
    const result = error.response.data;
    return result.message;
  }
};
