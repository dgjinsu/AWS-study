import React, { useState, useEffect } from 'react';
import instance from './Instance';

export const AsyncTest2 = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    instance
      .get('/test2')
      .then((response) => setData(response.data))
      .catch((error) => console.log(error));
  }, []);

  return <div>{data}</div>;
};
export default AsyncTest2;
