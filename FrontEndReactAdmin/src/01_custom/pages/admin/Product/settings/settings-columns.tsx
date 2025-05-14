import { GridColDef, GridRenderCellParams } from '@mui/x-data-grid';
import { LBL_PRODUCT, LBL_PRODUCT_FORM } from '../../../../../config/constant';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { IconButton, Stack } from '@mui/material';

export const formFieldSettings = [
  { name: 'cd_product', label: LBL_PRODUCT_FORM.PRODUCT_CODE, type: 'text' },
  { name: 'nm_product', label: LBL_PRODUCT_FORM.PRODUCT_NAME, type: 'text' },
  { name: 'price', label: LBL_PRODUCT_FORM.PRICE, type: 'number' },
  { name: 'cd_category', label: LBL_PRODUCT_FORM.CATEGORY, type: 'text' },
  { name: 'cd_size', label: LBL_PRODUCT_FORM.SIZE, type: 'text' },
  { name: 'image', label: LBL_PRODUCT_FORM.IMAGE, type: 'text' },
  { name: 'description', label: LBL_PRODUCT_FORM.DESCRIPTION, type: 'text' },
  { name: 'cd_status', label: LBL_PRODUCT_FORM.STATUS, type: 'text' },
  { name: 'cd_create', label: '', type: 'text', hidden: true },
  { name: 'cd_update', label: '', type: 'text', hidden: true },
  { name: 'dt_create', label: '', type: 'date', hidden: true },
  { name: 'dt_update', label: '', type: 'date', hidden: true }
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
    field: 'cd_status',
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
    field: 'cd_product',
    headerName: 'Product Code',
    width: 130
  },
  {
    field: 'nm_product',
    headerName: 'Product Name',
    width: 200
  },
  {
    field: 'price',
    headerName: 'Price',
    width: 130,
    // valueFormatter: (params: { value: number }) => {
    //   return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(params.value);
    // }
  },
  {
    field: 'cd_category',
    headerName: 'Category',
    width: 150
  },
  {
    field: 'cd_size',
    headerName: 'Size',
    width: 130
  }
];
