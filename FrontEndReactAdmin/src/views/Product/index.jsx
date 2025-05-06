import React, { useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { 
  Box, 
  Typography, 
  Button, 
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  IconButton,
  Stack
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';

const Product = () => {
  const [rows, setRows] = useState([
    { id: 1, name: 'Bánh mì', price: 15000, category: 'Bánh mặn', stock: 100 },
    { id: 2, name: 'Bánh ngọt', price: 25000, category: 'Bánh ngọt', stock: 50 },
  ]);

  const [open, setOpen] = useState(false);
  const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [productToDelete, setProductToDelete] = useState(null);
  const [formData, setFormData] = useState({
    name: '',
    price: '',
    category: '',
    stock: ''
  });

  const handleOpen = (product = null) => {
    if (product) {
      setSelectedProduct(product);
      setFormData({
        name: product.name,
        price: product.price,
        category: product.category,
        stock: product.stock
      });
    } else {
      setSelectedProduct(null);
      setFormData({
        name: '',
        price: '',
        category: '',
        stock: ''
      });
    }
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setSelectedProduct(null);
    setFormData({
      name: '',
      price: '',
      category: '',
      stock: ''
    });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = () => {
    if (selectedProduct) {
      // Update existing product
      setRows(rows.map(row => 
        row.id === selectedProduct.id 
          ? { ...row, ...formData, price: Number(formData.price), stock: Number(formData.stock) }
          : row
      ));
    } else {
      // Add new product
      const newProduct = {
        id: Math.max(...rows.map(row => row.id)) + 1,
        ...formData,
        price: Number(formData.price),
        stock: Number(formData.stock)
      };
      setRows([...rows, newProduct]);
    }
    handleClose();
  };

  const handleDeleteClick = (product) => {
    setProductToDelete(product);
    setOpenDeleteDialog(true);
  };

  const handleDeleteConfirm = () => {
    if (productToDelete) {
      setRows(rows.filter(row => row.id !== productToDelete.id));
      setOpenDeleteDialog(false);
      setProductToDelete(null);
    }
  };

  const handleDeleteCancel = () => {
    setOpenDeleteDialog(false);
    setProductToDelete(null);
  };

  const columns = [
    { field: 'id', headerName: 'ID', width: 90 },
    { field: 'name', headerName: 'Tên sản phẩm', width: 200 },
    { field: 'price', headerName: 'Giá', width: 130, 
      valueFormatter: (params) => {
        return new Intl.NumberFormat('vi-VN', {
          style: 'currency',
          currency: 'VND'
        }).format(params.value);
      }
    },
    { field: 'category', headerName: 'Danh mục', width: 150 },
    { field: 'stock', headerName: 'Tồn kho', width: 130 },
    {
      field: 'actions',
      headerName: 'Thao tác',
      width: 120,
      renderCell: (params) => (
        <Stack direction="row" spacing={1}>
          <IconButton 
            color="primary" 
            size="small"
            onClick={() => handleOpen(params.row)}
          >
            <EditIcon />
          </IconButton>
          <IconButton 
            color="error" 
            size="small"
            onClick={() => handleDeleteClick(params.row)}
          >
            <DeleteIcon />
          </IconButton>
        </Stack>
      ),
    },
  ];

  return (
    <Box sx={{ height: 400, width: '100%', p: 2 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 2 }}>
        <Typography variant="h4">
          Quản lý sản phẩm
        </Typography>
        <Button 
          variant="contained" 
          startIcon={<AddIcon />}
          onClick={() => handleOpen()}
        >
          Thêm sản phẩm
        </Button>
      </Box>

      <DataGrid
        rows={rows}
        columns={columns}
        initialState={{
          pagination: {
            paginationModel: { page: 0, pageSize: 5 },
          },
        }}
        pageSizeOptions={[5, 10, 25]}
        checkboxSelection
        disableRowSelectionOnClick
      />

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>
          {selectedProduct ? 'Sửa sản phẩm' : 'Thêm sản phẩm mới'}
        </DialogTitle>
        <DialogContent>
          <Box sx={{ pt: 2, display: 'flex', flexDirection: 'column', gap: 2 }}>
            <TextField
              name="name"
              label="Tên sản phẩm"
              value={formData.name}
              onChange={handleInputChange}
              fullWidth
            />
            <TextField
              name="price"
              label="Giá"
              type="number"
              value={formData.price}
              onChange={handleInputChange}
              fullWidth
            />
            <TextField
              name="category"
              label="Danh mục"
              value={formData.category}
              onChange={handleInputChange}
              fullWidth
            />
            <TextField
              name="stock"
              label="Tồn kho"
              type="number"
              value={formData.stock}
              onChange={handleInputChange}
              fullWidth
            />
          </Box>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Hủy</Button>
          <Button onClick={handleSubmit} variant="contained">
            {selectedProduct ? 'Cập nhật' : 'Thêm mới'}
          </Button>
        </DialogActions>
      </Dialog>

      <Dialog
        open={openDeleteDialog}
        onClose={handleDeleteCancel}
      >
        <DialogTitle>Xác nhận xóa</DialogTitle>
        <DialogContent>
          <Typography>
            Bạn có chắc chắn muốn xóa sản phẩm "{productToDelete?.name}"?
          </Typography>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDeleteCancel}>Hủy</Button>
          <Button 
            onClick={handleDeleteConfirm} 
            color="error" 
            variant="contained"
          >
            Xóa
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default Product; 