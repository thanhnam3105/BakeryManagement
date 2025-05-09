import { Dialog, DialogTitle, DialogContent, DialogActions, Button } from '@mui/material';

const ConfirmDeleteDialog = ({ open, onCancel, onConfirm, title = 'Xác nhận xóa', content = null }) => {
  return (
    <Dialog open={open} onClose={onCancel}>
      <DialogTitle>{title}</DialogTitle>
      <DialogContent>{content}</DialogContent>
      <DialogActions>
        <Button onClick={onCancel}>Hủy</Button>
        <Button onClick={onConfirm} color="error" variant="contained">
          Xóa
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default ConfirmDeleteDialog;
