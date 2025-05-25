import React, { useState, useEffect } from 'react';
import {
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Button
} from '@mui/material';
import ApiService from '../../../services/api.services';
import { DataCbbProductStatus, LABELS_ORDER, DataCbbOrderStatus } from '../../../../config/constant';
import { ToastService } from '../../../services/toast.service';

interface OrderDialogProps {
  open: boolean;
  onClose: () => void;
  data?: any;
  onSave: (updatedOrder: any) => void;
}

const OrderDialog: React.FC<OrderDialogProps> = ({ open, onClose, data, onSave }) => {
  const apiService = new ApiService();
  const [isSaving, setIsSaving] = useState(false);
  const [formData, setFormData] = useState<any>({});
  const urlAPI = 'https://localhost:7031/api/Orders';

  useEffect(() => {
    if (data) {
      setFormData(data);
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
    setIsSaving(true);
    apiService
      .apiPut(urlAPI, formData)
      .then((response) => {
        onSave(response);
        onClose();
        ToastService.success(LABELS_ORDER.SUCCESS_UPDATE);
      })
      .catch((error) => {
        ToastService.error(LABELS_ORDER.ERROR_UPDATE + error);
      })
      .finally(() => {
        setIsSaving(false);
      });
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>{LABELS_ORDER.DIALOG_TITLE}</DialogTitle>
      <DialogContent>
        <FormControl fullWidth margin="normal">
          <InputLabel>{LABELS_ORDER.DIALOG_STATUS}</InputLabel>
          <Select
            name="cd_status"
            value={formData?.cd_status || ''}
            onChange={handleChange}
            label={LABELS_ORDER.DIALOG_STATUS}
          >
            {DataCbbOrderStatus.map((status) => (
              <MenuItem key={status.value} value={status.value}>
                {status.name}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} color="primary">
          {LABELS_ORDER.BUTTON_CANCEL}
        </Button>
        <Button 
          onClick={handleSave} 
          color="primary" 
          disabled={isSaving}
        >
          {isSaving ? LABELS_ORDER.BUTTON_SAVING : LABELS_ORDER.BUTTON_SAVE}
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default OrderDialog;
