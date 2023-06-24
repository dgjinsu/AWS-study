import { ListItemButton } from '@mui/material';
import React, { useState, useRef, useEffect } from 'react';
import CheckIcon from '@mui/icons-material/Check';
import DeleteIcon from '@mui/icons-material/Delete';

const NewCategoryInput = ({ onCreate, onCancel }) => {
  const [inputValue, setInputValue] = useState('');
  const inputRef = useRef(null);

  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, []);

  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleCheckIconClick = () => {
    onCreate(inputValue);
    setInputValue('');
  };

  const handleCancelIconClick = () => {
    onCancel();
    setInputValue('');
  };

  return (
    <ListItemButton sx={{ padding: '20px', background: '#E9ECEF' }}>
      <input
        ref={inputRef}
        value={inputValue}
        onChange={handleInputChange}
        style={{
          width: '100%',
          padding: '5px',
          border: '1px solid black',
          marginRight: '10px',
        }}
      />
      <CheckIcon
        onClick={handleCheckIconClick}
        sx={{
          cursor: 'pointer',
          '&:hover': { color: '#69db7c' },
        }}
      />
      <DeleteIcon
        onClick={handleCancelIconClick}
        sx={{
          cursor: 'pointer',
          '&:hover': { color: '#fa5252' },
        }}
      />
    </ListItemButton>
  );
};

export default NewCategoryInput;
