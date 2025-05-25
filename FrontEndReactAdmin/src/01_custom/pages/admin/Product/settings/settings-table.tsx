import { GridRenderCellParams } from '@mui/x-data-grid';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import { IconButton, Stack, Chip } from '@mui/material';
import { PRODUCT_STATUS, DataCbbProductStatus, categoryOptions, TABLE_HEADERS } from '../../../../../config/constant';
import { ExtendedGridColDef } from '../../../../components/common/Common_GridTable';

export const columns: ExtendedGridColDef[] = [
  {
    field: 'actions',
    headerName: TABLE_HEADERS.ACTIONS,
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
    headerName: TABLE_HEADERS.STATUS,
    width: 130,
    renderCell: (params: GridRenderCellParams) => {
      const status = Object.values(DataCbbProductStatus).find((status) => status.value === params.value);

      // Get the appropriate color based on status
      const getStatusColor = (statusValue: string) => {
        const status = Object.values(PRODUCT_STATUS).find((status) => status.value === statusValue);
        return status ? status.color : 'default';
      };

      return <Chip label={status ? status.name : params.value} color={getStatusColor(params.value) as any} size="small" />;
    }
  },
  {
    field: 'image',
    headerName: TABLE_HEADERS.IMAGE,
    width: 130,
    renderCell: (params: GridRenderCellParams) => <img src={params.value} style={{ width: 60, height: 60, objectFit: 'cover' }} />
  },
  {
    field: 'cd_product',
    headerName: TABLE_HEADERS.PRODUCT_CODE,
    width: 130
  },
  {
    field: 'nm_product',
    headerName: TABLE_HEADERS.PRODUCT_NAME,
    width: 500
  },
  {
    field: 'price',
    headerName: TABLE_HEADERS.PRICE,
    width: 130,
    formatType: 'decimal'
  },
  {
    field: 'cd_category',
    headerName: TABLE_HEADERS.CATEGORY,
    width: 150,
    renderCell: (params: GridRenderCellParams) => {
      const category = categoryOptions.find((category) => category.value === params.value);

      return category ? category.name : params.value;
    }
  },
  {
    field: 'cd_size',
    headerName: TABLE_HEADERS.SIZE,
    width: 130
  }
];
