import React, { useState } from 'react';
import {
  Container, Typography, Table, TableBody, TableCell,
  TableContainer, TableHead, TableRow, Paper, Button,
  Dialog, DialogActions, DialogContent, DialogTitle,
  TextField, IconButton
} from '@mui/material';
import { Edit, Delete } from '@mui/icons-material';

const ProductManagement = () => {
  const [products, setProducts] = useState([
    { id: 1, name: 'Sản phẩm 1', price: 100 },
    { id: 2, name: 'Sản phẩm 2', price: 200 },
    { id: 3, name: 'Sản phẩm 3', price: 300 }
  ]);

  const [openDialog, setOpenDialog] = useState(false);
  const [isEdit, setIsEdit] = useState(false);
  const [currentProduct, setCurrentProduct] = useState<{ id?: number; name: string; price: string }>({ name: '', price: '' });

  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [productToDelete, setProductToDelete] = useState<number | null>(null);

  const handleOpenDialog = (product?: { id: number; name: string; price: number } | null) => {
    if (product) {
      setIsEdit(true);
      setCurrentProduct({ ...product, price: String(product.price) });
    } else {
      setIsEdit(false);
      setCurrentProduct({ name: '', price: '' });
    }
    setOpenDialog(true);
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
    setCurrentProduct({ name: '', price: '' });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCurrentProduct({ ...currentProduct, [name]: value });
  };

  const handleSaveProduct = () => {
    if (isEdit && currentProduct.id !== undefined) {
      const productToSave = { ...currentProduct, price: Number(currentProduct.price), id: currentProduct.id };
      setProducts(products.map(p => p.id === currentProduct.id ? productToSave : p));
    } else {
      const newId = products.length > 0 ? Math.max(...products.map(p => p.id)) + 1 : 1;
      const productToSave = { ...currentProduct, price: Number(currentProduct.price), id: newId };
      setProducts([...products, productToSave]);
    }
    handleCloseDialog();
  };

  const handleOpenDeleteDialog = (id: number) => {
    setProductToDelete(id);
    setDeleteDialogOpen(true);
  };

  const handleCloseDeleteDialog = () => {
    setDeleteDialogOpen(false);
    setProductToDelete(null);
  };

  const handleConfirmDelete = () => {
    if (productToDelete !== null) {
      setProducts(products.filter(p => p.id !== productToDelete));
    }
    handleCloseDeleteDialog();
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Quản Lý Danh Sách Sản Phẩm
      </Typography>

      <Button variant="contained" color="primary" onClick={() => handleOpenDialog()}>
        Thêm Sản Phẩm
      </Button>

      <TableContainer component={Paper} sx={{ marginTop: 2 }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Tên Sản Phẩm</TableCell>
              <TableCell>Giá</TableCell>
              <TableCell>Hành Động</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {products.map((product) => (
              <TableRow key={product.id}>
                <TableCell>{product.id}</TableCell>
                <TableCell>{product.name}</TableCell>
                <TableCell>{product.price} VND</TableCell>
                <TableCell>
                  <IconButton color="primary" onClick={() => handleOpenDialog(product)}>
                    <Edit />
                  </IconButton>
                  <IconButton color="error" onClick={() => handleOpenDeleteDialog(product.id)}>
                    <Delete />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={openDialog} onClose={handleCloseDialog}>
        <DialogTitle>{isEdit ? 'Chỉnh Sửa Sản Phẩm' : 'Thêm Sản Phẩm Mới'}</DialogTitle>
        <DialogContent>
          <TextField
            label="Tên Sản Phẩm"
            fullWidth
            margin="normal"
            name="name"
            value={currentProduct.name}
            onChange={handleChange}
          />
          <TextField
            label="Giá"
            fullWidth
            margin="normal"
            name="price"
            type="number"
            value={currentProduct.price}
            onChange={handleChange}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDialog} color="primary">
            Hủy
          </Button>
          <Button onClick={handleSaveProduct} color="primary">
            {isEdit ? 'Cập Nhật' : 'Thêm'}
          </Button>
        </DialogActions>
      </Dialog>

      <Dialog open={deleteDialogOpen} onClose={handleCloseDeleteDialog}>
        <DialogTitle>Xác nhận xóa</DialogTitle>
        <DialogContent>
          <Typography>Bạn có chắc chắn muốn xóa sản phẩm này?</Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseDeleteDialog} color="primary">
            Hủy
          </Button>
          <Button onClick={handleConfirmDelete} color="error">
            Xóa
          </Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default ProductManagement;
