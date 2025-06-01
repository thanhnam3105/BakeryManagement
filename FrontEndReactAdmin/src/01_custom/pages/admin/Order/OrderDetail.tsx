import React, { useEffect, useState } from 'react';
import Common_GridTable from '../../../components/common/Common_GridTable';
import { Dialog, DialogTitle, DialogContent, DialogActions, Button, Chip, Box, Typography } from '@mui/material';
import ApiService from '../../../services/api.services';
import { useLoading } from '../../../services/loading.services';
import { ToastService } from '../../../services/toast.service';
import { LABELS_ORDER } from '../../../../config/constant';
import { formatDate, formatCurrency } from '../../../utils/Common_format';
import { SettingTableDetail } from './settings/settings-table';
import { getStatusColor } from '../../../../01_custom/services/common-funtion'

interface OrderDetailParams {
  open: boolean;
  onClose: () => void;
  cdOrder?: string;
}

const OrderDetail: React.FC<OrderDetailParams> = ({ open, onClose, cdOrder }) => {
  const apiService = new ApiService();
  const urlAPI = 'https://localhost:7031/api/Orders';
  const { showLoading, hideLoading } = useLoading();

  const [orderItems, setOrderItems] = useState<any[]>([]);
  const [orderHeader, setOrderHeader] = useState<any | null>(null);

  useEffect(() => {
    if (cdOrder && open) {
      handleSearch(cdOrder);
    }
  }, [cdOrder, open]);

  const handleSearch = async (orderCode: string) => {
    // showLoading();
    const params = { cd_order: orderCode };

    apiService.apiGet(`${urlAPI}/GetOrderDetails/`, params)
      .then((response) => {
        const rowsWithId = response.map((item: any, index: number) => ({
          ...item,
          id: `${item.cd_order_detail}-${index}`
        }));
        setOrderItems(rowsWithId);
        if (rowsWithId.length > 0) {
          setOrderHeader(rowsWithId[0]);
        }
      }).catch((error) => {
        ToastService.error(error);
      }).finally(() => {
        // hideLoading();
      });
  };

  const handleClose = () => {
    setOrderItems([]);
    setOrderHeader(null);
    onClose();
  };

  return (
    <Dialog open={open}
      onClose={function (event, reason) {
        if (reason !== 'backdropClick') {
          handleClose();
        }
      }}
      fullWidth maxWidth="md" disableEscapeKeyDown>
      <DialogTitle>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
          <span>{LABELS_ORDER.DETAIL_TITLE} - {cdOrder}</span>
          <Chip
            label={orderHeader?.nm_status}
            color={getStatusColor(orderHeader?.cd_status)}
            // variant="filled"
            variant="outlined"
            sx={{
              fontSize: '1rem',
              fontWeight: 'bold',
              px: 2,
              py: 1
            }}
          />
        </div>
      </DialogTitle>

      <DialogContent dividers>
        {orderHeader && (
          <Box>
            <Box
              display="grid"
              gridTemplateColumns="repeat(2, 1fr)"
              gap={2}
              position="relative"
            >
              <Box>
                <strong>{LABELS_ORDER.CUSTOMER_ID}:</strong> {orderHeader.nm_customer}
              </Box>
              <Box>
                <strong>{LABELS_ORDER.STAFF_ID}:</strong> {orderHeader.nm_staff}
              </Box>
              <Box>
                <strong>{LABELS_ORDER.ORDER_DATE}:</strong> {formatDate(orderHeader.dt_order)}
              </Box>
              <Box>
                <strong>{LABELS_ORDER.PAYMENT_METHOD}:</strong> {orderHeader.nm_payment}
              </Box>
              <Box>
                <strong>{LABELS_ORDER.DELIVERY_DATE}:</strong> {formatDate(orderHeader.dt_delivery)}
              </Box>
              <Box>
                <strong>{LABELS_ORDER.DETAIL_NOTE}:</strong> {orderHeader.notes}
              </Box>
              <Box gridColumn="1 / -1">
                <strong>{LABELS_ORDER.DELIVERY_ADDRESS}:</strong> {orderHeader.delivery_address}
              </Box>
            </Box>
            <Box display="flex" justifyContent="flex-end">
              <Typography variant="h6" fontWeight="bold">
                {LABELS_ORDER.TOTAL_AMOUNT}: {formatCurrency(orderHeader.total_amount)}
              </Typography>
            </Box>
          </Box>
        )}
        <Common_GridTable
          rows={orderItems}
          columns={SettingTableDetail}
          hideSearch
          rowHeight={60}
          tableHeight={300}
        />

      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} variant="contained" color="primary">
          Đóng
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default OrderDetail;
