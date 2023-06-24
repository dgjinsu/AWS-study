import React, { useState, useEffect } from 'react';
import instance from './Instance';

export const AsyncTest = () => {
  const [data, setData] = useState([]);

  useEffect(() => {
    instance
      .get('/test')
      .then((response) => setData(response.data))
      .catch((error) => console.log(error));
  }, []);

  return <div>{data}</div>;
};
export default AsyncTest;
