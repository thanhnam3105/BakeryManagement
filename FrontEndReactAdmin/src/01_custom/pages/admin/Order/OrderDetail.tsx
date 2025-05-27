import React, { useEffect, useState } from 'react';
import Common_GridTable, { ExtendedGridColDef } from '../../../components/common/Common_GridTable';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button } from '@mui/material';
import ApiService from '../../../services/api.services';
import { useLoading } from '../../../services/loading.services';
import { ToastService } from '../../../services/toast.service';
import { LABELS_ORDER } from '../../../../config/constant';
import formatDate from '../../../utils/formatDate';

interface OrderDetailItem {
  cd_order: string;
  cd_order_detail: string;
  cd_product: string;
  quantity: number;
  price: number;
  cd_unit: string;
  id?: string;
}

interface OrderDetailProps {
  open: boolean;
  onClose: () => void;
  cdOrder?: string;
  orderData?: any;
}

const OrderDetail: React.FC<OrderDetailProps> = ({ open, onClose, cdOrder, orderData }) => {
  const apiService = new ApiService();
  const urlAPI = 'https://localhost:7031/api/Orders';
  const { showLoading, hideLoading } = useLoading();

  const [orderItems, setOrderItems] = useState<OrderDetailItem[]>([]);

  useEffect(() => {
    if (cdOrder && open) {
      handleSearch(cdOrder);
    }
  }, [cdOrder, open]);

  const handleSearch = async (orderCode: string) => {
    showLoading();
    const params = { cd_order: orderCode };

    apiService.apiGet(`${urlAPI}/GetOrderDetails/`, params)
      .then((response) => {
        const rowsWithId = response.map((item: OrderDetailItem, index: number) => ({
          ...item,
          id: `${item.cd_order_detail}-${index}`
        }));
        setOrderItems(rowsWithId);
      }).catch((error) => {
        ToastService.error(error);
      }).finally(() => {
        hideLoading();
      });

  };

  const columns: ExtendedGridColDef[] = [
    { field: 'cd_order_detail', headerName: 'Mã CT Đơn', flex: 1 },
    { field: 'cd_product', headerName: 'Mã Bánh', flex: 1 },
    { field: 'quantity', headerName: 'Số Lượng', flex: 1 },
    { field: 'price', headerName: 'Đơn gía', flex: 1, formatType: 'decimal' },
    { 
      field: 'total_amount', 
      headerName: 'Thành tiền', 
      flex: 1, 
      formatType: 'decimal',
      // valueGetter: (params: { row: OrderDetailItem }) => {
      //   return params.row.price * params.row.quantity;
      // }
    },
    { field: 'cd_unit', headerName: 'ĐV tính', flex: 1 }
  ];

  return (
    <Dialog open={open}
      onClose={function (event, reason) {
        if (reason !== 'backdropClick') {
          onClose();
        }
      }}
      fullWidth maxWidth="md" disableEscapeKeyDown>
      <DialogTitle>{LABELS_ORDER.DETAIL_TITLE} - {cdOrder}</DialogTitle>
      <DialogContent dividers>
        {orderData && (
          <div style={{ marginBottom: '20px' }}>
            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '10px' }}>
              <div>
                <strong>{LABELS_ORDER.CUSTOMER_ID}:</strong> {orderData.cd_customer}
              </div>
              <div>
                <strong>{LABELS_ORDER.STAFF_ID}:</strong> {orderData.cd_staff}
              </div>
              <div>
                <strong>{LABELS_ORDER.ORDER_DATE}:</strong> {formatDate(orderData.dt_order)}
              </div>
              <div>
                <strong>{LABELS_ORDER.DELIVERY_DATE}:</strong> {formatDate(orderData.dt_delivery)}
              </div>
              <div>
                <strong>{LABELS_ORDER.STATUS}:</strong> {orderData.cd_status}
              </div>
              <div>
                <strong>{LABELS_ORDER.TOTAL_AMOUNT}:</strong> {orderData.total_amount?.toLocaleString()}
              </div>
              <div style={{ gridColumn: '1 / -1' }}>
                <strong>{LABELS_ORDER.DELIVERY_ADDRESS}:</strong> {orderData.delivery_address}
              </div>
            </div>
          </div>
        )}

        <Common_GridTable
          title=""
          rows={orderItems}
          columns={columns}
          hideSearch
          rowHeight={60}
          tableHeight={300}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} variant="contained" color="primary">
          Đóng
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default OrderDetail;
