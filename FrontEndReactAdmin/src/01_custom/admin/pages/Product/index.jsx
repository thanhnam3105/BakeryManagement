import React, { useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { Box, Typography, Button, IconButton, Stack, Badge, TextField } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import ProductDialog from './productDialog';
import img1 from '../../../../assets/images/1.jpg';
import img2 from '../../../../assets/images/2.jpg';
import { useConfirm } from '../../services/ConfirmServiceContext';
import { PRODUCT_STATUS, LBL_PRODUCT } from '../../../../config/constant';

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
    }
  ]);

  const [open, setOpen] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);
  const initialFormData = {
    name: '',
    cake_id: '',
    price: '',
    category: '',
    stock: '',
    size: '',
    image: '',
    description: '',
    status: 'Available'
  };

  const [formData, setFormData] = useState(initialFormData);
  const [search, setSearch] = useState('');

  const { confirm } = useConfirm();

  const handleOpen = (product = null) => {
    setSelectedProduct(product);
    setFormData(product ? { ...product } : initialFormData);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setSelectedProduct(null);
    setFormData(initialFormData);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = () => {
    if (selectedProduct) {
      // Update existing product
      setRows(
        rows.map((row) =>
          row.id === selectedProduct.id ? { ...row, ...formData, price: Number(formData.price), stock: Number(formData.stock) } : row
        )
      );
    } else {
      // Add new product
      const newProduct = {
        id: Math.max(...rows.map((row) => row.id)) + 1,
        ...formData,
        price: Number(formData.price),
        stock: Number(formData.stock)
      };
      setRows([...rows, newProduct]);
    }
    handleClose();
  };

  const handleDeleteClick = async (product) => {
    confirm({
      title: LBL_PRODUCT.CONFIRM_DELETE,
      content: `${LBL_PRODUCT.CONFIRM_DELETE_MESSAGE}${product.name}"?`,
      onConfirm: () => {
        setRows(rows.filter((row) => row.id !== product.id));
      }
    });
  };

  const columns = [
    {
      field: 'actions',
      headerName: LBL_PRODUCT.ACTIONS,
      width: 120,
      align: 'center',
      headerAlign: 'center',
      renderCell: (params) => (
        <Stack direction="row" spacing={1} justifyContent="center" alignItems="center" sx={{ width: '100%', height: '100%' }}>
          <IconButton color="primary" size="small" onClick={() => handleOpen(params.row)}>
            <EditIcon />
          </IconButton>
          <IconButton color="error" size="small" onClick={() => handleDeleteClick(params.row)}>
            <DeleteIcon />
          </IconButton>
        </Stack>
      )
    },
    {
      field: 'status',
      headerName: LBL_PRODUCT.STATUS,
      width: 130,
      renderCell: (params) => {
        const getStatusColor = (status) => {
          const statusObj = Object.values(PRODUCT_STATUS).find((s) => s.value === status.toLowerCase());
          return statusObj ? statusObj.color : PRODUCT_STATUS.DEFAULT.color;
        };

        return (
          <Box
            sx={{
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
              width: '100%',
              height: '100%'
            }}
          >
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
      headerName: LBL_PRODUCT.IMAGE,
      width: 130,
      renderCell: (params) => <img src={params.value} alt={params.row.name} style={{ width: 80, height: 80, objectFit: 'cover' }} />
    },
    { field: 'id', headerName: LBL_PRODUCT.ID, width: 90 },
    { field: 'cake_id', headerName: LBL_PRODUCT.PRODUCT_CODE, width: 130 },
    { field: 'name', headerName: LBL_PRODUCT.PRODUCT_NAME, width: 200 },
    {
      field: 'price',
      headerName: LBL_PRODUCT.PRICE,
      width: 130,
      valueFormatter: (params) => {
        return new Intl.NumberFormat('vi-VN', {
          style: 'currency',
          currency: 'VND'
        }).format(params.value);
      }
    },
    { field: 'category', headerName: LBL_PRODUCT.CATEGORY, width: 150 },
    { field: 'stock', headerName: LBL_PRODUCT.STOCK, width: 130 },
    { field: 'size', headerName: LBL_PRODUCT.SIZE, width: 130 }
  ];

  const filteredData = rows.filter((item) => item.name.toLowerCase().includes(search.toLowerCase()));

  return (
    <Box sx={{ height: 400, width: '100%', p: 2 }}>
      <Box sx={{ display: 'flex', justifyContent: 'space-between', mb: 2 }}>
        <Typography variant="h4">{LBL_PRODUCT.TITLE}</Typography>
        <Button variant="contained" startIcon={<AddIcon />} onClick={() => handleOpen()}>
          {LBL_PRODUCT.ADD_PRODUCT}
        </Button>
      </Box>

      <Box mb={2} display="flex" justifyContent="space-between" alignItems="center">
        <TextField
          label={LBL_PRODUCT.SEARCH}
          variant="outlined"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          sx={{ width: 300 }}
        />
      </Box>

      <DataGrid
        rows={filteredData}
        columns={columns}
        initialState={{
          pagination: {
            paginationModel: { page: 0, pageSize: 5 }
          }
        }}
        pageSizeOptions={[5, 10, 25]}
        checkboxSelection
        disableRowSelectionOnClick
        getRowHeight={() => 100}
        sx={{
          '& .MuiDataGrid-row:hover': {
            backgroundColor: '#f0f7ff',
            cursor: 'pointer'
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
    </Box>
  );
};

export default Product;
