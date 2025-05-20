import { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { Dialog, DialogTitle, DialogContent, DialogActions, Box } from '@mui/material';
import { useFormik, FormikContext } from 'formik';
import { validationSettings } from './settings/settings-validates';
import { formFieldSettings } from './settings/settings-columns';
import ApiService from '../../../services/api.services';
import { ToastService } from '../../../services/toast.service';
import { LBL_PRODUCT_DIALOG, statusOptions } from '../../../../config/constant';
import Common_Input from '../../../components/common/Common_Input';
import Common_Combobox from '../../../components/common/Common_Combobox';
import Common_Button from '../../../components/common/Common_Button';
import Common_InputImage from '../../../components/common/Common_InputImage';

// Convert getInitialValues to function declaration
function getInitialValues(data) {
  const initial = {};
  Object.entries(formFieldSettings).forEach(([key, field]) => {
    initial[key] = data?.[key] ?? (key === 'cd_status' ? '1' : '');
  });
  return initial;
}

// Add this new function before the ProductDialog component
function loadDataInit() {
  // Transform statusOptions to match the format expected by Common_Combobox
  return statusOptions.map(option => ({
    value: option.value,
    name: option.name
  }));
}

// Convert ProductDialog component to function declaration
function ProductDialog({ open, onClose, data, onSave }) {
  const apiService = new ApiService();
  const urlAPI = 'https://localhost:7031/api/Products';
  const [isSaving, setIsSaving] = useState(false);
  const [statusData, setStatusData] = useState([]);

  // Add useEffect to load status data when component mounts
  useEffect(() => {
    const statusList = loadDataInit();
    setStatusData(statusList);
  }, []);

  const formik = useFormik({
    initialValues: getInitialValues(data) as Record<string, any>,
    validationSchema: validationSettings,
    enableReinitialize: true,
    onSubmit: function (values) {
      const apiMethod = data ? apiService.apiPut : apiService.apiPost;

      const formData = { ...values };
   
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
      onClose={function (event, reason) {
        if (reason !== 'backdropClick') {
          formik.resetForm();
          onClose();
        }
      }}
      PaperProps={{
        sx: { width: '1200px', maxWidth: '100%' }
      }}
    >
      <DialogTitle>{data ? LBL_PRODUCT_DIALOG.TITLE_EDIT : LBL_PRODUCT_DIALOG.TITLE_ADD}</DialogTitle>
      <form onSubmit={formik.handleSubmit}>
        <FormikContext.Provider value={formik}>
          <DialogContent>
            <Box
              sx={{
                pt: 3,
                display: 'grid',
                gridTemplateColumns: '300px 1fr', // Cột trái cố định 300px, cột phải chiếm phần còn lại
                gap: 3
              }}
            >
              {/* Cột trái - Image */}
              <Box sx={{ height: '100%' }}>
                <Common_InputImage
                  settingInput={{
                    ...formFieldSettings.image.settingInput,
                    maxSize: 5 * 1024 * 1024,
                    accept: 'image/*'
                  }}
                  validatesInput={formFieldSettings.image.validatesInput}
                />
              </Box>

              {/* Cột phải - Form fields */}
              <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
                <Box sx={{ display: 'grid', gridTemplateColumns: 'repeat(2, 1fr)', gap: 3 }}>
                  {/* Cột 1 của form fields */}
                  <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
                    <Common_Input
                      settingInput={{
                        ...formFieldSettings.cd_product.settingInput,
                        disabled: !!data
                      }}
                      validatesInput={formFieldSettings.cd_product.validatesInput}
                    />
                    <Common_Input
                      settingInput={formFieldSettings.price.settingInput}
                      validatesInput={formFieldSettings.price.validatesInput}
                    />
                    <Common_Input
                      settingInput={formFieldSettings.cd_size.settingInput}
                      validatesInput={formFieldSettings.cd_size.validatesInput}
                    />
                  </Box>

                  {/* Cột 2 của form fields */}
                  <Box sx={{ display: 'flex', flexDirection: 'column', gap: 3 }}>
                    <Common_Input
                      settingInput={formFieldSettings.nm_product.settingInput}
                      validatesInput={formFieldSettings.nm_product.validatesInput}
                    />
                    <Common_Combobox
                      settingInput={formFieldSettings.cd_category.settingInput}
                      validatesInput={formFieldSettings.cd_category.validatesInput}
                    />
                    <Common_Combobox
                      settingInput={{
                        ...formFieldSettings.cd_status.settingInput,
                        options: statusData
                      }}
                      validatesInput={formFieldSettings.cd_status.validatesInput}
                    />
                  </Box>
                </Box>

                {/* Description field - Full width */}
                <Box sx={{ width: '100%' }}>
                  <Common_Input
                    settingInput={formFieldSettings.description.settingInput}
                    validatesInput={formFieldSettings.description.validatesInput}
                  />
                </Box>
              </Box>
            </Box>
          </DialogContent>
        </FormikContext.Provider>
        <DialogActions>
          <Common_Button
            label={LBL_PRODUCT_DIALOG.BUTTON_CANCEL}
            onClick={() => {
              formik.resetForm();
              onClose();
            }}
            variant="text"
          />
          <Common_Button
            label={data ? LBL_PRODUCT_DIALOG.BUTTON_UPDATE : LBL_PRODUCT_DIALOG.BUTTON_ADD}
            type="submit"
            variant="contained"
            isLoading={isSaving}
          />
        </DialogActions>
      </form>
    </Dialog>
  );
}

ProductDialog.propTypes = {
  open: PropTypes.bool.isRequired,
  onClose: PropTypes.func.isRequired,
  data: PropTypes.object,
  onSave: PropTypes.func.isRequired
};

export default ProductDialog;
