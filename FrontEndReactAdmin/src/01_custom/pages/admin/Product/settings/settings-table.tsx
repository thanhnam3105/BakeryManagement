import { GridColDef, GridRenderCellParams } from '@mui/x-data-grid';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { IconButton, Stack } from '@mui/material';
import { LBL_PRODUCT, LBL_PRODUCT_FORM, statusOptions, categoryOptions } from '../../../../../config/constant';
import { Link } from 'react-router-dom';

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
    renderCell: (params: GridRenderCellParams) => {
      const status = Object.values(statusOptions).find(
        (status) => status.value === params.value
      );

      // Get the appropriate color class based on status
      const getStatusColorClass = (statusValue: string) => {
        switch (statusValue) {
          case '1': // Pending
            return 'bg-warning';
          case '2': // Processing
            return 'bg-info';
          case '3': // Shipped
            return 'theme-bg2';
          case '4': // Delivered
            return 'theme-bg';
          default:
            return 'bg-secondary';
        }
      };

      return (
        <Link
          to="#"
          className={`label ${getStatusColorClass(params.value)} text-white f-12`}
        >
          {status ? status.name : params.value}
        </Link>
      );
    }
  },
  {
    field: 'image',
    headerName: 'Image',
    width: 130,
    renderCell: (params: GridRenderCellParams) => (
      <img src={params.value} style={{ width: 60, height: 60, objectFit: 'cover' }} />
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
    valueFormatter: (params: number) => {
      if (params == null) return '';
      return params.toLocaleString('en-US');
    }
  },
  {
    field: 'cd_category',
    headerName: 'Category',
    width: 150,
    renderCell: (params: GridRenderCellParams) => {
      const category = categoryOptions.find(
        (category) => category.value === params.value
      );

      return (
        category ? category.name : params.value
      );
    }
  },
  {
    field: 'cd_size',
    headerName: 'Size',
    width: 130
  }
];
