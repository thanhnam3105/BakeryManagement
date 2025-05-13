import { GridColDef, GridRenderCellParams } from '@mui/x-data-grid';
import { LBL_PRODUCT } from '../../../../../config/constant';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { IconButton, Stack } from '@mui/material';

export const formFieldSettings = [
  { name: 'cake_id', label: 'Mã sản phẩm', type: 'text' },
  { name: 'name', label: 'Tên sản phẩm', type: 'text' },
  { name: 'price', label: 'Giá', type: 'number' },
  { name: 'category', label: 'Danh mục', type: 'text' },
  { name: 'stock', label: 'Tồn kho', type: 'number' },
  { name: 'size', label: 'Kích thước', type: 'text' },
  { name: 'image', label: 'Hình ảnh', type: 'text' },
  { name: 'description', label: 'Mô tả', type: 'text' },
  { name: 'status', label: 'Trạng thái', type: 'text' }
];

export const columns: GridColDef[] = [
  {
    field: 'actions',
    headerName: 'Actions',
    width: 120,
    align: 'center',
    headerAlign: 'center',
    renderCell: (params: GridRenderCellParams) => (
      <Stack direction="row" spacing={1}>
        <IconButton size="small" onClick={() => params.row.onEdit(params.row)}>
          <EditIcon />
        </IconButton>
        <IconButton size="small" onClick={() => params.row.onDelete(params.row)}>
          <DeleteIcon />
        </IconButton>
      </Stack>
    )
  },
  {
    field: 'status',
    headerName: 'Status',
    width: 130,
    renderCell: (params: GridRenderCellParams) => <span>{params.value}</span>
  },
  {
    field: 'image',
    headerName: 'Image',
    width: 130,
    renderCell: (params: GridRenderCellParams) => (
      <img src={params.value} alt="product" style={{ width: 50, height: 50, objectFit: 'cover' }} />
    )
  },
  {
    field: 'id',
    headerName: 'ID',
    width: 90
  },
  {
    field: 'cake_id',
    headerName: 'Product Code',
    width: 130
  },
  {
    field: 'name',
    headerName: 'Product Name',
    width: 200
  },
  {
    field: 'price',
    headerName: 'Price',
    width: 130,
    valueFormatter: (params: { value: number }) => {
      return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(params.value);
    }
  },
  {
    field: 'category',
    headerName: 'Category',
    width: 150
  },
  {
    field: 'stock',
    headerName: 'Stock',
    width: 130
  },
  {
    field: 'size',
    headerName: 'Size',
    width: 130
  }
];
