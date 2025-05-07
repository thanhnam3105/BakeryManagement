// components/ConfirmServiceContext.jsx
import React, { createContext, useContext, useState } from 'react';
import ConfirmDialog from '../components/ConfirmDialog';

const ConfirmContext = createContext();

export const ConfirmProvider = ({ children }) => {
  const [open, setOpen] = useState(false);
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [onConfirm, setOnConfirm] = useState(null);

  const confirm = ({ title, content, onConfirm }) => {
    setTitle(title);
    setContent(content);
    setOnConfirm(() => onConfirm);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setTitle('');
    setContent('');
    setOnConfirm(null);
  };

  const handleConfirm = () => {
    if (onConfirm) {
      onConfirm();
    }
    handleClose();
  };

  return (
    <ConfirmContext.Provider value={{ confirm }}>
      {children}
      <ConfirmDialog open={open} title={title} content={content} onCancel={handleClose} onConfirm={handleConfirm} />
    </ConfirmContext.Provider>
  );
};

export const useConfirm = () => {
  const context = useContext(ConfirmContext);
  if (!context) {
    throw new Error('useConfirm must be used within a ConfirmProvider');
  }
  return context;
};
