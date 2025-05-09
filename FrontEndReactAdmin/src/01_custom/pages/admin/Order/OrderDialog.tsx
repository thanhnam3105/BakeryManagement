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

interface OrderDialogProps {
  open: boolean;
  onClose: () => void;
  order?: any; // order có thể là bất kỳ kiểu gì từ backend, có thể thay thế bằng 'any'
  onSave: (updatedOrder: any) => void;
}

const OrderDialog: React.FC<OrderDialogProps> = ({ open, onClose, order, onSave }) => {
  const apiService = new ApiService();
  const [isSaving, setIsSaving] = useState(false);
  const [formData, setFormData] = useState<any>({});
  const urlAPI = 'https://localhost:7031/api/Orders';

  useEffect(() => {
    if (order) {
      setFormData(order); // Chỉ khi có `order` mới set giá trị form
    }
  }, [order]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev: any) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = () => {
    setIsSaving(true); // Đánh dấu đang lưu
    apiService.apiPut(urlAPI, formData).then((response) => {
        onSave(response);
        onClose(); // Đóng dialog sau khi lưu
      }).catch((error) => {
        console.log(error);
        // toastService.showErrorToast(error)
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
          <Select name="status" value={formData?.status || ''} onChange={handleChange} label="Status">
            {statusOptions.map((status) => (
              <MenuItem key={status.value} value={status.value}>
                {status.name}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
        <FormControl fullWidth margin="normal">
          <InputLabel>Status</InputLabel>
          <Select name="payment_method" value={formData?.payment_method || ''} onChange={handleChange} label="Payment Method">
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
        <Button onClick={handleSubmit} color="primary">
          Save
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default OrderDialog;
