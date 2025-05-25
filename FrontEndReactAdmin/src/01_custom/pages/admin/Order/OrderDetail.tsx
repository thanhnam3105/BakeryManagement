import React, { useEffect, useState } from 'react';
import Common_GridTable, { ExtendedGridColDef } from '../../../components/common/Common_GridTable';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button } from '@mui/material';
import ApiService from '../../../services/api.services';
import { useLoading } from '../../../services/loading.services';
import { ToastService } from '../../../services/toast.service';
import { LABELS_ORDER } from '../../../../config/constant';

interface OrderDetailItem {
  cd_order: string;
  cd_order_detail: string;
  cd_product: string;
  quantity: number;
  cd_unit: string;
  id?: string;
}

interface OrderDetailProps {
  open: boolean;
  onClose: () => void;
  cdOrder?: string;
}

const OrderDetail: React.FC<OrderDetailProps> = ({ open, onClose, cdOrder }) => {
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
    { field: 'quantity', headerName: 'Số Lượng', flex: 1, formatType: 'decimal' },
    { field: 'cd_unit', headerName: 'ĐVT', flex: 1 }
  ];

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm" disableEscapeKeyDown>
      <DialogTitle>{LABELS_ORDER.DETAIL_TITLE} - {cdOrder}</DialogTitle>
      <DialogContent dividers>
        <Common_GridTable
          title=""
          rows={orderItems}
          columns={columns}
          hideSearch
          rowHeight={60} // nhỏ hơn mặc định 70
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
