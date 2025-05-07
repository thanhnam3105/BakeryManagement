import React from 'react';
import { Dialog, DialogTitle, DialogContent, DialogActions, TextField, Button, Box } from '@mui/material';

const ProductDialog = ({ open, onClose, onSubmit, formData, onInputChange, selectedProduct }) => (
  <Dialog
    open={open}
    onClose={onClose}
    PaperProps={{
      sx: { width: '700px', maxWidth: '90%' }
    }}
  >
    <DialogTitle>{selectedProduct ? 'Sửa sản phẩm' : 'Thêm sản phẩm mới'}</DialogTitle>
    <DialogContent>
      <Box sx={{ pt: 3, display: 'flex', flexDirection: 'column', gap: 3 }}>
        <TextField name="cake_id" label="Mã sản phẩm" value={formData.cake_id} onChange={onInputChange} fullWidth />
        <TextField name="name" label="Tên sản phẩm" value={formData.name} onChange={onInputChange} fullWidth />
        <TextField name="price" label="Giá" type="number" value={formData.price} onChange={onInputChange} fullWidth />
        <TextField name="category" label="Danh mục" value={formData.category} onChange={onInputChange} fullWidth />
        <TextField name="stock" label="Tồn kho" type="number" value={formData.stock} onChange={onInputChange} fullWidth />
        <TextField name="size" label="Kích thước" value={formData.size} onChange={onInputChange} fullWidth />
        <TextField name="image" label="Hình ảnh" value={formData.image} onChange={onInputChange} fullWidth />
        <TextField name="status" label="Trạng thái" value={formData.status} onChange={onInputChange} fullWidth />
      </Box>
    </DialogContent>
    <DialogActions>
      <Button onClick={onClose}>Hủy</Button>
      <Button onClick={onSubmit} variant="contained">
        {selectedProduct ? 'Cập nhật' : 'Thêm mới'}
      </Button>
    </DialogActions>
  </Dialog>
);

export default ProductDialog;
