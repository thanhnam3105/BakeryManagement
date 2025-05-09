// src/pages/OrderManagement/columns/orderColumns.ts
import { GridColDef } from '@mui/x-data-grid';
import { LABELS_ORDER } from '../../../../../config/constant';

export const columns: GridColDef[] = [
  { field: 'actions', headerName: LABELS_ORDER.ACTIONS, width: 80},
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
