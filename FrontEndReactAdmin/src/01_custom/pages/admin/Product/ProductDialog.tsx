import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { Dialog, DialogTitle, DialogContent, DialogActions, TextField, Button, Box } from '@mui/material';
import { useFormik } from 'formik';
import { validationSettings } from './settings/settings-validates';
import { formFieldSettings } from './settings/settings-columns';
import ApiService from '../../../services/api.services';
import { ToastService } from '../../../services/toast.service';

// ✅ Tạo initialValues từ config và data nếu có
const getInitialValues = (data) => {
  const initial = {};
  formFieldSettings.forEach((field) => {
    initial[field.name] = data?.[field.name] ?? (field.name === 'status' ? 'Available' : '');
  });
  return initial;
};

const ProductDialog = ({ open, onClose, data, onSave }) => {
  const apiService = new ApiService();
  const urlAPI = 'https://localhost:7031/api/Products';
  const [isSaving, setIsSaving] = useState(false);

  const formik = useFormik({
    initialValues: getInitialValues(data),
    validationSchema: validationSettings,
    enableReinitialize: true,
    onSubmit: (values) => {
      setIsSaving(true);
      const apiCall = data ? apiService.apiPut(`${urlAPI}/${data.id}`, {data :values}) : apiService.apiPost(urlAPI, {data :values});

      apiCall
        .then((response) => {
          onSave(response);
          onClose();
          ToastService.success(data ? 'Cập nhật thành công!' : 'Thêm mới thành công!');
        })
        .catch((error) => {
          ToastService.error(error);
        })
        .finally(() => {
          setIsSaving(false);
        });
    }
  });

  // Thêm useEffect để reset form khi dialog mở
  useEffect(() => {
    if (open) {
      formik.resetForm();
    }
  }, [open]);

  return (
    <Dialog
      open={open}
      onClose={(event, reason) => {
        if (reason !== 'backdropClick') {
          formik.resetForm();
          onClose();
        }
      }}
      PaperProps={{
        sx: { width: '700px', maxWidth: '90%' }
      }}
    >
      <DialogTitle>{data ? 'Sửa sản phẩm' : 'Thêm sản phẩm mới'}</DialogTitle>
      <form onSubmit={formik.handleSubmit}>
        <DialogContent>
          <Box sx={{ pt: 3, display: 'flex', flexDirection: 'column', gap: 3 }}>
            {formFieldSettings.map(({ name, label, type }) => (
              <TextField
                key={name}
                name={name}
                label={label}
                type={type}
                value={formik.values[name]}
                onChange={formik.handleChange}
                onBlur={formik.handleBlur}
                error={formik.touched[name] && Boolean(formik.errors[name])}
                helperText={formik.touched[name] && String(formik.errors[name])}
                fullWidth
              />
            ))}
          </Box>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={() => {
              formik.resetForm();
              onClose();
            }}
          >
            Hủy
          </Button>
          <Button type="submit" variant="contained" disabled={isSaving}>
            {data ? 'Cập nhật' : 'Thêm mới'}
          </Button>
        </DialogActions>
      </form>
    </Dialog>
  );
};

ProductDialog.propTypes = {
  open: PropTypes.bool.isRequired,
  onClose: PropTypes.func.isRequired,
  data: PropTypes.object,
  onSave: PropTypes.func.isRequired
};

export default ProductDialog;
