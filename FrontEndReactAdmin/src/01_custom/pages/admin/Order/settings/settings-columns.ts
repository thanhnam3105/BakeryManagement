// src/pages/OrderManagement/columns/orderColumns.ts
import { GridColDef } from '@mui/x-data-grid';
import { LABELS_ORDER } from '../../../../../config/constant';

export const columns: GridColDef[] = [
  { field: 'actions', headerName: LABELS_ORDER.ACTIONS, width: 80},
  { field: 'cd_order', headerName: LABELS_ORDER.ORDER_ID, flex: 1 },
  { field: 'cd_customer', headerName: LABELS_ORDER.CUSTOMER_ID, flex: 1 },
  { field: 'cd_staff', headerName: LABELS_ORDER.STAFF_ID, flex: 1 },
  { field: 'cd_branch', headerName: LABELS_ORDER.BRANCH_ID, flex: 1 },
  { field: 'dt_order', headerName: LABELS_ORDER.ORDER_DATE, flex: 1 },
  { field: 'dt_delivery', headerName: LABELS_ORDER.DELIVERY_DATE, flex: 1 },
  { field: 'cd_status', headerName: LABELS_ORDER.STATUS, flex: 1 },
  { field: 'total_amount', headerName: LABELS_ORDER.TOTAL_AMOUNT, flex: 1 },
  { field: 'delivery_address', headerName: LABELS_ORDER.DELIVERY_ADDRESS, flex: 2 },
  { field: 'cd_payment_method', headerName: LABELS_ORDER.PAYMENT_METHOD, flex: 1 }
];
