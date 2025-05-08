// components/ConfirmServiceContext.jsx
import React, { createContext, useContext, useState } from 'react';
import PropTypes from 'prop-types';
import ConfirmDialog from '../components/common/DialogConfirm';

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

ConfirmProvider.propTypes = {
  children: PropTypes.node.isRequired
};

export const useConfirm = () => {
  const context = useContext(ConfirmContext);
  if (!context) {
    throw new Error('useConfirm must be used within a ConfirmProvider');
  }
  return context;
};
