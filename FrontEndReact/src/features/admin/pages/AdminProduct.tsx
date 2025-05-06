import React, { useState } from "react";
import { DataGrid, GridToolbar } from "@mui/x-data-grid";
import {
  Button,
  TextField,
  Typography,
  Box,
  Stack,
} from "@mui/material";
import { Delete, Edit, Add } from "@mui/icons-material";
import ProductFormDialog from "../components/AddEditProductDialog.tsx";
import ConfirmDeleteDialog from "../../../components/ConfirmDeleteDialog.tsx";

const initialRows = [
  {
    id: 1,
    cake_id: "CK001",
    cake_name: "Chocolate Cake",
    category_id: 1,
    size: "Medium",
    price: 200,
    image: "chocolate.jpg",
    description: "Rich chocolate flavor",
    status: "Available",
  },
  {
    id: 2,
    cake_id: "CK002",
    cake_name: "Vanilla Cake",
    category_id: 2,
    size: "Large",
    price: 180,
    image: "vanilla.jpg",
    description: "Classic vanilla taste",
    status: "Out of stock",
  },
];

export default function CakeManagement() {
  const [rows, setRows] = useState(initialRows);
  const [selectedRow, setSelectedRow] = useState(null);
  const [openDelete, setOpenDelete] = useState(false);
  const [search, setSearch] = useState("");
  const [openForm, setOpenForm] = useState(false);
  const [formData, setFormData] = useState({
    id: null,
    cake_id: "",
    cake_name: "",
    category_id: "",
    size: "",
    price: "",
    image: "",
    description: "",
    status: "Available",
  });

  const handleDelete = (id) => {
    setRows(rows.filter((row) => row.id !== id));
    setOpenDelete(false);
  };

  const handleOpenForm = (row = null) => {
    if (row) {
      setFormData(row);
    } else {
      setFormData({
        id: null,
        cake_id: "",
        cake_name: "",
        category_id: "",
        size: "",
        price: "",
        image: "",
        description: "",
        status: "Available",
      });
    }
    setOpenForm(true);
  };

  const handleSave = () => {
    if (formData.id) {
      setRows(rows.map((row) => (row.id === formData.id ? formData : row)));
    } else {
      const newId = rows.length ? Math.max(...rows.map((r) => r.id)) + 1 : 1;
      setRows([...rows, { ...formData, id: newId }]);
    }
    setOpenForm(false);
  };

  const filteredRows = rows.filter((row) =>
    row.cake_name.toLowerCase().includes(search.toLowerCase())
  );

  const columns = [
    {
      field: "actions",
      headerName: "Actions",
      flex: 1.5,
      renderCell: (params) => (
        <Stack direction="row" spacing={1}>
          <Button
            variant="outlined"
            color="primary"
            size="small"
            startIcon={<Edit />}
            onClick={() => handleOpenForm(params.row)}
          >
          </Button>
          <Button
            variant="outlined"
            color="error"
            size="small"
            startIcon={<Delete />}
            onClick={() => {
              setSelectedRow(params.row);
              setOpenDelete(true);
            }}
          >
          </Button>
        </Stack>
      ),
    },
    { field: "cake_id", headerName: "Cake ID", flex: 1 },
    { field: "cake_name", headerName: "Name", flex: 1.5 },
    { field: "category_id", headerName: "Category ID", flex: 1 },
    { field: "size", headerName: "Size", flex: 1 },
    { field: "price", headerName: "Price", flex: 1 },
    { field: "image", headerName: "Image", flex: 1 },
    { field: "description", headerName: "Description", flex: 2 },
    { field: "status", headerName: "Status", flex: 1 },
   
  ];

  return (
    <Box p={3}>
      <Typography variant="h4" gutterBottom>
        🍰 Quản lý sản phẩm
      </Typography>

      <Box
        mb={2}
        display="flex"
        justifyContent="space-between"
        alignItems="center"
      >
        <TextField
          label="Tìm kiếm sản phẩm"
          variant="outlined"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          sx={{ width: 300 }}
        />
        <Button
          variant="contained"
          startIcon={<Add />}
          onClick={() => handleOpenForm()}
        >
          Thêm sản phẩm
        </Button>
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
            backgroundColor: "#fff",
          }}
        />
      </Box>

      {/* Dialog xác nhận xóa */}
      <ConfirmDeleteDialog
        open={openDelete}
        onClose={() => setOpenDelete(false)}
        onConfirm={() => handleDelete(selectedRow.id)}
        itemName={selectedRow?.cake_name}
      />

      {/* Dialog form thêm/sửa */}
      <ProductFormDialog
        open={openForm}
        onClose={() => setOpenForm(false)}
        onSave={handleSave}
        formData={formData}
        setFormData={setFormData}
      />
    </Box>
  );
}
