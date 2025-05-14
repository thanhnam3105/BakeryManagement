import React, { useState, useEffect } from 'react';
import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem
} from '@mui/material';
import ApiService from '../../../services/api.services';
import { paymentMethodOptions, statusOptions } from 'config/constant';
import { ToastService } from '../../../services/toast.service';

interface OrderDialogProps {
  open: boolean;
  onClose: () => void;
  data?: any; // order có thể là bất kỳ kiểu gì từ backend, có thể thay thế bằng 'any'
  onSave: (updatedOrder: any) => void;
}

const OrderDialog: React.FC<OrderDialogProps> = ({ open, onClose, data, onSave }) => {
  const apiService = new ApiService();
  const [isSaving, setIsSaving] = useState(false);
  const [formData, setFormData] = useState<any>({});
  const urlAPI = 'https://localhost:7031/api/Orders';

  useEffect(() => {
    if (data) {
      setFormData(data); // Chỉ khi có `order` mới set giá trị form
    }
  }, [data]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev: any) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSave = () => {
    setIsSaving(true); // Đánh dấu đang lưu
    apiService.apiPut(urlAPI, formData).then((response) => {
        onSave(response);
        onClose(); // Đóng dialog sau khi lưu
        ToastService.success('Lưu dữ liệu thành công!');
      }).catch((error) => {
        ToastService.error('Lỗi khi lưu dữ liệu! '+error);
      }).finally(() => {
        setIsSaving(false);
        // loadingStore.hide();
        // toggleSidebar();
      });
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Edit Order</DialogTitle>
      <DialogContent>
        <FormControl fullWidth margin="normal">
          <InputLabel>Status</InputLabel>
          <Select name="cd_status" value={formData?.status || ''} onChange={handleChange} label="Status">
            {statusOptions.map((status) => (
              <MenuItem key={status.value} value={status.value}>
                {status.name}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <FormControl fullWidth margin="normal">
          <InputLabel>Payment Method</InputLabel>
          <Select name="cd_payment_method" value={formData?.payment_method || ''} onChange={handleChange} label="Payment Method">
            {paymentMethodOptions.map((payment_method) => (
              <MenuItem key={payment_method.value} value={payment_method.value}>
                {payment_method.name}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} color="primary">
          Cancel
        </Button>
        <Button onClick={handleSave} color="primary">
          Save
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default OrderDialog;
