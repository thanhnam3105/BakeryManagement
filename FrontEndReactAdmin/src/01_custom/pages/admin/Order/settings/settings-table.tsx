// src/pages/OrderManagement/columns/orderColumns.ts
import { GridRenderCellParams } from '@mui/x-data-grid';
import { LABELS_ORDER, ORDER_STATUS, DataCbbOrderStatus } from '../../../../../config/constant';
import { Chip, IconButton, Stack } from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import { ExtendedGridColDef } from '../../../../components/common/Common_GridTable';

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
  { 
    field: 'cd_status', 
    headerName: LABELS_ORDER.STATUS, 
    width: 120,
    renderCell: (params: GridRenderCellParams) => {
      const status = Object.values(ORDER_STATUS).find(
        (status) => status.value === params.value
      );
      
      return (
        <Chip 
          label={status?.name || params.value} 
          color={status?.color as any || 'default'} 
          size="small" 
        />
      );
    }
  },
  { field: 'cd_order', headerName: LABELS_ORDER.ORDER_ID, width: 120 },
  { field: 'cd_customer', headerName: LABELS_ORDER.CUSTOMER_ID, width: 140 },
  { field: 'cd_staff', headerName: LABELS_ORDER.STAFF_ID, width: 140 },
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
 
  { 
    field: 'total_amount', 
    headerName: LABELS_ORDER.TOTAL_AMOUNT, 
    width: 120,
    formatType: 'currency'
  },
  { field: 'delivery_address', headerName: LABELS_ORDER.DELIVERY_ADDRESS, flex: 2 }
];

export const SettingTableDetail: ExtendedGridColDef[] = [
  { field: 'index', headerName: LABELS_ORDER.DETAIL_INDEX, flex: 0.3, formatType: 'index' },
  { field: 'image', headerName: LABELS_ORDER.DETAIL_IMAGE, formatType: 'image', flex: 1 },
  { field: 'nm_product', headerName: LABELS_ORDER.DETAIL_PRODUCT_NAME, flex: 1 },
  { field: 'nm_quatity_unit', headerName: LABELS_ORDER.DETAIL_QUANTITY, flex: 1 },
  { field: 'price', headerName: LABELS_ORDER.DETAIL_PRICE, flex: 1, formatType: 'currency' },
  { field: 'total', headerName: LABELS_ORDER.DETAIL_TOTAL, flex: 1, formatType: 'currency' },
];
