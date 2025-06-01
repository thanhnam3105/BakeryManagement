// src/pages/OrderManagement/columns/orderColumns.ts
import { GridRenderCellParams } from '@mui/x-data-grid';
import { LABELS_ORDER, ORDER_STATUS, DataCbbOrderStatus } from '../../../../../config/constant';
import { Chip, IconButton, Stack } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import { ExtendedGridColDef } from '../../../../components/common/Common_GridTable';
import { getStatusColor } from '../../../../services/common-funtion';

export const settingTable: ExtendedGridColDef[] = [
  { 
    field: 'actions', 
    headerName: LABELS_ORDER.ACTIONS, 
    width: 120,
    align: 'center',
    headerAlign: 'center',
    renderCell: (params: GridRenderCellParams) => (
      <Stack direction="row" spacing={1} justifyContent="center">
        <IconButton 
          size="small" 
          onClick={() => params.row.onEdit(params.row)}
          title="Cập nhật trạng thái"
        >
          <EditIcon />
        </IconButton>
      </Stack>
    )
  },
  { field: 'cd_order', headerName: LABELS_ORDER.ORDER_ID, width: 120 },
  { 
    field: 'cd_status', 
    headerName: LABELS_ORDER.STATUS, 
    width: 120,
    renderCell: (params: GridRenderCellParams) => {
      const statusColor = getStatusColor(params.row.cd_status);
      return (
        <Chip  variant="outlined" label={params.row.nm_status}  color={statusColor}  size="small"  />
      );
    }
  },
  
  { 
    field: 'dt_order', 
    headerName: LABELS_ORDER.ORDER_DATE, 
    width: 120,
    formatType: 'date'
  },
  { 
    field: 'dt_delivery', 
    headerName: LABELS_ORDER.DELIVERY_DATE, 
    width: 120,
    formatType: 'date'
  },
  { field: 'nm_payment', headerName: LABELS_ORDER.PAYMENT_METHOD, width: 190 },
  { field: 'nm_customer', headerName: LABELS_ORDER.CUSTOMER_ID, width: 140 },
 
  { 
    field: 'total_amount', 
    headerName: LABELS_ORDER.TOTAL_AMOUNT, 
    width: 120,
    formatType: 'currency'
  },
  { field: 'notes', headerName: LABELS_ORDER.DETAIL_NOTE, flex: 2 }
];

export const SettingTableDetail: ExtendedGridColDef[] = [
  { field: 'index', headerName: LABELS_ORDER.DETAIL_INDEX, flex: 0.3, formatType: 'index' },
  { field: 'image', headerName: LABELS_ORDER.DETAIL_IMAGE, formatType: 'image', flex: 1 },
  { field: 'nm_product', headerName: LABELS_ORDER.DETAIL_PRODUCT_NAME, flex: 1 },
  { field: 'nm_quatity_unit', headerName: LABELS_ORDER.DETAIL_QUANTITY, flex: 1 },
  { field: 'price', headerName: LABELS_ORDER.DETAIL_PRICE, flex: 1, formatType: 'currency' },
  { field: 'total', headerName: LABELS_ORDER.DETAIL_TOTAL, flex: 1, formatType: 'currency' },
];
