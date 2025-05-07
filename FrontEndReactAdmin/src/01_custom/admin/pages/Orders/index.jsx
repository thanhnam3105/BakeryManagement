import React, { useState } from 'react';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';
import { Typography, Box, TextField, IconButton, Stack } from '@mui/material';
import { Delete, Visibility } from '@mui/icons-material';
import { useConfirm } from '../../services/ConfirmServiceContext';
import { LABELS_ORDER } from '../../../../config/constant';

const initialOrders = [
  {
    id: 1,
    order_id: 'ORD001',
    customer_id: 'CUS001',
    staff_id: 'STF001',
    branch_id: 'BR001',
    order_date: '2024-05-01',
    delivery_date: '2024-05-03',
    status: 'Delivered',
    total_amount: 500,
    delivery_address: '123 Main St',
    payment_method: 'Credit Card'
  },
  {
    id: 2,
    order_id: 'ORD002',
    customer_id: 'CUS002',
    staff_id: 'STF002',
    branch_id: 'BR002',
    order_date: '2024-05-02',
    delivery_date: '2024-05-04',
    status: 'Pending',
    total_amount: 300,
    delivery_address: '456 Oak Ave',
    payment_method: 'Cash'
  }
];

export default function OrderManagement() {
  const [rows, setRows] = useState(initialOrders);
  const [search, setSearch] = useState('');
  const { confirm } = useConfirm();

  const handleDeleteClick = async (order) => {
    confirm({
      title: LABELS_ORDER.CONFIRM_DELETE,
      content: `${LABELS_ORDER.CONFIRM_DELETE_MESSAGE}${order.order_id}"?`,
      onConfirm: () => {
        setRows((prev) => prev.filter((row) => row.id !== order.id));
      }
    });
  };

  const filteredRows = rows.filter((row) => row.order_id.toLowerCase().includes(search.toLowerCase()));

  const columns = [
    {
      field: 'actions',
      headerName: LABELS_ORDER.ACTIONS,
      flex: 1,
      renderCell: (params) => (
        <Stack direction="row" spacing={1}>
          <IconButton color="primary" onClick={() => alert('View details coming soon')}>
            <Visibility />
          </IconButton>
          <IconButton color="error" onClick={() => handleDeleteClick(params.row)}>
            <Delete />
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
          pageSize={5}
          rowsPerPageOptions={[5, 10]}
          disableSelectionOnClick
          components={{ Toolbar: GridToolbar }}
          sx={{
            borderRadius: 2,
            boxShadow: 2,
            backgroundColor: '#fff'
          }}
        />
      </Box>
    </Box>
  );
}
