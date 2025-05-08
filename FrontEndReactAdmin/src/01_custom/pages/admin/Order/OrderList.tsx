import React, { useState, useEffect } from 'react';
import { DataGrid, GridColDef, GridToolbar, GridPaginationModel } from '@mui/x-data-grid';
import { Typography, Box, TextField, IconButton, Stack } from '@mui/material';
import { Delete, Visibility, Edit } from '@mui/icons-material';
import { useConfirm } from '../../../services/confirm.services';
import { LABELS_ORDER } from '../../../../config/constant';
import ApiService from '../../../services/api.services';
import OrderDialog from './OrderDialog'; // Import Dialog

export default function OrderManagement(): JSX.Element {
  const apiService = new ApiService();
  const urlAPI = 'https://localhost:7031/api/Orders';

  const [rows, setRows] = useState<any[]>([]);
  const [search, setSearch] = useState('');
  const [paginationModel, setPaginationModel] = useState<GridPaginationModel>({
    page: 0,
    pageSize: 5
  });
  const [openDialog, setOpenDialog] = useState(false);
  const [selectedOrder, setSelectedOrder] = useState<any>(null); // Lưu trữ dòng đã chọn

  const { confirm } = useConfirm();

  useEffect(() => {
    loadOrders();
  }, []);

  const loadOrders = () => {
    apiService
      .apiGet(urlAPI)
      .then((response) => {
        setRows(response);
      })
      .catch((error) => {
        console.error('API Error:', error);
      });
  };

  const handleDeleteClick = (order: any) => {
    confirm({
      title: LABELS_ORDER.CONFIRM_DELETE,
      content: `${LABELS_ORDER.CONFIRM_DELETE_MESSAGE}${order.order_id}"?`,
      onConfirm: () => {
        setRows((prev) => prev.filter((row) => row.id !== order.id));
      }
    });
  };

  const handleEditClick = (order: any) => {
    setSelectedOrder(order);
    setOpenDialog(true); // Mở dialog khi nhấn nút chỉnh sửa
  };

  const handleSaveOrder = (updatedOrder: any) => {
    // Thực hiện update dữ liệu, có thể gọi API nếu cần.
    setRows((prev) => prev.map((order) => (order.id === updatedOrder.id ? updatedOrder : order)));
  };

  const filteredRows = rows.filter((row) => row.order_id?.toLowerCase().includes(search.toLowerCase()));

  const columns: GridColDef[] = [
    {
      field: 'actions',
      headerName: LABELS_ORDER.ACTIONS,
      width: 70,
      sortable: false,
      filterable: false,
      renderCell: (params) => (
        <Stack direction="row" spacing={1}>
          {/* <IconButton color="primary" onClick={() => alert('View details')}>
            <Visibility />
          </IconButton> */}
          <IconButton color="warning" onClick={() => handleEditClick(params.row)}>
            <Edit />
          </IconButton>
        </Stack>
      )
    },
    { field: 'order_id', headerName: LABELS_ORDER.ORDER_ID, flex: 1 },
    { field: 'customer_id', headerName: LABELS_ORDER.CUSTOMER_ID, flex: 1 },
    { field: 'staff_id', headerName: LABELS_ORDER.STAFF_ID, flex: 1 },
    { field: 'branch_id', headerName: LABELS_ORDER.BRANCH_ID, flex: 1 },
    { field: 'order_date', headerName: LABELS_ORDER.ORDER_DATE, flex: 1 },
    { field: 'delivery_date', headerName: LABELS_ORDER.DELIVERY_DATE, flex: 1 },
    { field: 'status', headerName: LABELS_ORDER.STATUS, flex: 1 },
    { field: 'total_amount', headerName: LABELS_ORDER.TOTAL_AMOUNT, flex: 1 },
    { field: 'delivery_address', headerName: LABELS_ORDER.DELIVERY_ADDRESS, flex: 2 },
    { field: 'payment_method', headerName: LABELS_ORDER.PAYMENT_METHOD, flex: 1 }
  ];

  return (
    <Box p={3}>
      <Typography variant="h4" gutterBottom>
        {LABELS_ORDER.TITLE}
      </Typography>

      <Box mb={2} display="flex" justifyContent="space-between" alignItems="center">
        <TextField
          label={LABELS_ORDER.SEARCH}
          variant="outlined"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          sx={{ width: 300 }}
        />
      </Box>

      <Box height={500}>
        <DataGrid
          rows={filteredRows}
          columns={columns}
          paginationModel={paginationModel}
          onPaginationModelChange={setPaginationModel}
          pageSizeOptions={[5, 10]}
          disableRowSelectionOnClick
          slots={{ toolbar: GridToolbar }}
          sx={{
            borderRadius: 2,
            boxShadow: 2,
            backgroundColor: '#fff'
          }}
        />
      </Box>

      {/* Dialog chỉnh sửa Order */}
      <OrderDialog open={openDialog} onClose={() => setOpenDialog(false)} order={selectedOrder} onSave={handleSaveOrder} />
    </Box>
  );
}
