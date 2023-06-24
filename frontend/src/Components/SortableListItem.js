import React from 'react';
import { useSortable } from '@dnd-kit/sortable';
import { ListItemButton, ListItemText } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { CSS } from '@dnd-kit/utilities';
import { useRecoilState } from 'recoil';
import { selectedCategoryIdState } from '../atoms';

export const SortableListItem = ({
  category,
  editingCategoryId,
  handleEditIconClick,
  handleSaveEdit,
  handleKeyPress,
  inputRef,
  handleDeleteCategory,
  root,
  handleCategoryClick,
  selectedCategoryId,
}) => {
  const { attributes, listeners, setNodeRef, transform } = useSortable({
    id: category.id,
  });
  const style = { transform: CSS.Transform.toString(transform) };

  const [selectedCategoryIdNow] = useRecoilState(selectedCategoryIdState);

  return (
    <ListItemButton
      onClick={() => handleCategoryClick(category.categoryId, category.name)}
      ref={setNodeRef}
      style={style}
      {...attributes}
      {...listeners}
      sx={{
        padding: '20px',
        backgroundColor:
          selectedCategoryIdNow === category.categoryId ? '#E7F5FF' : 'inherit', // 선택된 카테고리 배경색 설정
        '&:hover': {
          backgroundColor:
            editingCategoryId === category.categoryId ? 'inherit' : '#E7F5FF', // 마우스 올라간 카테고리 배경색 설정
        },
        '&:hover .editIcon': {
          opacity: editingCategoryId === category.categoryId ? 0 : 1,
        },
        '&:hover .deleteIcon': {
          opacity: editingCategoryId === category.categoryId ? 0 : 1,
        },
      }}
    >
      {editingCategoryId === category.categoryId ? ( // 카테고리 수정 활성화인 경우
        <ListItemText
          primary={category.name}
          contentEditable
          suppressContentEditableWarning
          onBlur={(
            event // 포커스를 잃었을 때 동작. handleKeyPress내에서 blur를 실행시키고 있음.
          ) => handleSaveEdit(event, root.id, category.categoryId)}
          ref={inputRef}
          onKeyPress={handleKeyPress}
          style={{
            width: '100%',
            padding: '5px',
            border: '1px solid black',
            marginRight: '10px',
          }}
        />
      ) : (
        // 카테고리 수정 비활성화인 경우(디폴트)
        <ListItemText primary={category.name} />
      )}

      <EditIcon
        // 카테고리 수정 버튼
        className="editIcon"
        onClick={(event) => {
          handleEditIconClick(event, category.categoryId);
        }}
        sx={{
          display: { xs: 'none', sm: 'block' },
          marginLeft: 'auto',
          opacity: 0,
          paddingLeft: '5px',
          transition: 'opacity 0.2s',
          pointerEvents: 'all',
          '&:hover': {
            color: '#69db7c',
          },
        }}
      />
      <DeleteIcon
        // 카테고리 삭제 버튼
        className="deleteIcon"
        onClick={(event) => {
          handleDeleteCategory(event, root.id, category.categoryId);
        }}
        sx={{
          display: { xs: 'none', sm: 'block' },
          marginLeft: 'auto',
          paddingLeft: '5px',
          opacity: 0,
          transition: 'opacity 0.2s',
          pointerEvents: 'all',
          '&:hover': {
            color: '#fa5252',
          },
        }}
      />
    </ListItemButton>
  );
};

export default SortableListItem;
