import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { Dialog, DialogTitle, DialogContent, DialogActions, TextField, Button, Box } from '@mui/material';
import { useFormik } from 'formik';
import { validationSettings } from './settings/settings-validates';
import { formFieldSettings } from './settings/settings-columns';
import ApiService from '../../../services/api.services';
import { ToastService } from '../../../services/toast.service';
import { LBL_PRODUCT_DIALOG } from '../../../../config/constant';

// ✅ Tạo initialValues từ config và data nếu có
const getInitialValues = (data) => {
  const initial = {};
  formFieldSettings.forEach((field) => {
    if (field.hidden) {
      initial[field.name] = null;
    } else {
      initial[field.name] = data?.[field.name] ?? (field.name === 'cd_status' ? '1' : '');
    }
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
      const apiMethod = data ? apiService.apiPut : apiService.apiPost;
      
      // Set hidden fields to null before saving
      const formData = { ...values };
      formFieldSettings.forEach((field) => {
        if (field.hidden) {
          formData[field.name] = null;
        }
      });
      
      apiMethod(urlAPI, formData)
        .then((response) => {
          onSave(response);
          onClose();
          ToastService.success(data ? LBL_PRODUCT_DIALOG.SUCCESS_UPDATE : LBL_PRODUCT_DIALOG.SUCCESS_ADD);
        })
        .catch((error) => {
          const message = error.response?.data ?? error.message ?? LBL_PRODUCT_DIALOG.ERROR_SAVE;
          ToastService.error(message);
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
      <DialogTitle>{data ? LBL_PRODUCT_DIALOG.TITLE_EDIT : LBL_PRODUCT_DIALOG.TITLE_ADD}</DialogTitle>
      <form onSubmit={formik.handleSubmit}>
        <DialogContent>
          <Box sx={{ pt: 3, display: 'flex', flexDirection: 'column', gap: 3 }}>
            {formFieldSettings
              .filter(field => !field.hidden)
              .map(({ name, label, type }) => (
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
            {LBL_PRODUCT_DIALOG.BUTTON_CANCEL}
          </Button>
          <Button type="submit" variant="contained" disabled={isSaving}>
            {data ? LBL_PRODUCT_DIALOG.BUTTON_UPDATE : LBL_PRODUCT_DIALOG.BUTTON_ADD}
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
