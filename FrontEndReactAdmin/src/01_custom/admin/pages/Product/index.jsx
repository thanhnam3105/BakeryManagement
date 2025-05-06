import React, { useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { 
  Box, 
  Typography, 
  Button, 
  IconButton,
  Stack,
  Badge
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import ConfirmDialog from '../../components/ConfirmDialog';
import ProductDialog from './productDialog';
import img1 from '../../../../assets/images/1.jpg';
import img2 from '../../../../assets/images/2.jpg';
import img3 from '../../../../assets/images/3.jpg';

const Product = () => {
  const [rows, setRows] = useState([
    { 
      id: 1, 
      cake_id: 'CK001',
      name: 'Bánh mì', 
      price: 15000, 
      category: 'Bánh mặn', 
      stock: 100,
      size: 'Large',
      image: img1,
      description: 'Bánh mì thơm ngon',
      status: 'Available'
    },
    { 
      id: 2, 
      cake_id: 'CK002',
      name: 'Bánh ngọt', 
      price: 25000, 
      category: 'Bánh ngọt', 
      stock: 50,
      size: 'Large',
      image: img2,
      description: 'Bánh ngọt hấp dẫn',
      status: 'Available'
    },
  ]);

  const [open, setOpen] = useState(false);
  const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [productToDelete, setProductToDelete] = useState(null);
  const [formData, setFormData] = useState({
    name: '',
    cake_id: '',
    price: '',
    category: '',
    stock: '',
    size: '',
    image: '',
    description: '',
    status: 'Available'
  });

  const handleOpen = (product = null) => {
    if (product) {
      setSelectedProduct(product);
      setFormData({
        name: product.name,
        cake_id: product.cake_id,
        price: product.price,
        category: product.category,
        stock: product.stock,
        size: product.size,
        image: product.image,
        description: product.description,
        status: product.status
      });
    } else {
      setSelectedProduct(null);
      setFormData({
        name: '',
        cake_id: '',
        price: '',
        category: '',
        stock: '',
        size: '',
        image: '',
        description: '',
        status: 'Available'
      });
    }
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setSelectedProduct(null);
    setFormData({
      name: '',
      cake_id: '',
      price: '',
      category: '',
      stock: '',
      size: '',
      image: '',
      description: '',
      status: 'Available'
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
    {
      field: 'actions',
      headerName: 'Thao tác',
      width: 120,
      align: 'center',
      headerAlign: 'center',
      renderCell: (params) => (
        <Stack 
          direction="row" 
          spacing={1}
          justifyContent="center"
          alignItems="center"
          sx={{ width: '100%', height: '100%' }}
        >
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
    { 
      field: 'status', 
      headerName: 'Trạng thái', 
      width: 130,
      renderCell: (params) => {
        const getStatusColor = (status) => {
          switch(status.toLowerCase()) {
            case 'available':
              return 'success';
            case 'out of stock':
              return 'error';
            case 'pending':
              return 'warning';
            default:
              return 'default';
          }
        };

        return (
          <Box sx={{ 
            display: 'flex', 
            justifyContent: 'center',
            alignItems: 'center',
            width: '100%',
            height: '100%'
          }}>
            <Badge 
              color={getStatusColor(params.value)}
              badgeContent={params.value}
              sx={{
                '& .MuiBadge-badge': {
                  fontSize: '0.8rem',
                  padding: '4px 8px',
                  borderRadius: '4px'
                }
              }}
            />
          </Box>
        );
      }
    },
    { 
      field: 'image', 
      headerName: 'Hình ảnh', 
      width: 130,
      renderCell: (params) => (
        <img 
          src={params.value} 
          alt={params.row.name}
          style={{ width: 80, height: 80, objectFit: 'cover' }}
        />
      )
    },
    { field: 'id', headerName: 'ID', width: 90 },
    { field: 'cake_id', headerName: 'Mã sản phẩm', width: 130 },
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
    { field: 'size', headerName: 'Kích thước', width: 130 },
   
   
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
        getRowHeight={() => 100}
        sx={{
          '& .MuiDataGrid-row:hover': {
            backgroundColor: '#f0f7ff',
            cursor: 'pointer',
          }
        }}
      />

      <ProductDialog
        open={open}
        onClose={handleClose}
        onSubmit={handleSubmit}
        formData={formData}
        onInputChange={handleInputChange}
        selectedProduct={selectedProduct}
      />

      <ConfirmDialog
        open={openDeleteDialog}
        onCancel={handleDeleteCancel}
        onConfirm={handleDeleteConfirm}
        itemName={productToDelete?.name}
      />
    </Box>
  );
};

export default Product; 