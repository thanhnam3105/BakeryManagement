import React from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, Typography } from '@mui/material';

const ConfirmDeleteDialog = ({
  open,
  onCancel,
  onConfirm,
  itemName = '',
  title = 'Xác nhận xóa',
  content = null,
}) => (
  <Dialog open={open} onClose={onCancel}>
    <DialogTitle>{title}</DialogTitle>
    <DialogContent>
      {content ? (
        content
      ) : (
        <Typography>
          Bạn có chắc chắn muốn xóa {itemName ? `sản phẩm "${itemName}"` : 'mục này'}?
        </Typography>
      )}
    </DialogContent>
    <DialogActions>
      <Button onClick={onCancel}>Hủy</Button>
      <Button onClick={onConfirm} color="error" variant="contained">
        Xóa
      </Button>
    </DialogActions>
  </Dialog>
);

export default ConfirmDeleteDialog;