import React, { useContext, useRef } from 'react';
import { Box, Button, Typography, FormHelperText } from '@mui/material';
import { FormikContext } from 'formik';
import { CloudUpload } from '@mui/icons-material';

interface Common_InputImageProps {
  settingInput: {
    name: string;
    labelName?: string;
    accept?: string;
    maxSize?: number; // in bytes
    width?: string | number;
    height?: string | number;
    [key: string]: any;
  };
  validatesInput?: {
    required?: boolean;
  };
}

const Common_InputImage: React.FC<Common_InputImageProps> = ({
  settingInput,
  validatesInput,
  ...props
}) => {
  const formik = useContext(FormikContext);
  const fileInputRef = useRef<HTMLInputElement>(null);
  const { name, labelName, accept = 'image/*', maxSize = 5 * 1024 * 1024, width, height, ...restSettings } = settingInput;

  // Get formik values and handlers
  const value = formik?.values[name];
  const error = formik?.touched[name] && Boolean(formik?.errors[name]);
  const helperText = formik?.touched[name] && String(formik?.errors[name] ?? '');

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (!file) return;

    // Validate file size
    if (file.size > maxSize) {
      formik?.setFieldError(name, `File size must be less than ${maxSize / 1024 / 1024}MB`);
      return;
    }

    // Validate file type
    if (!file.type.startsWith('image/')) {
      formik?.setFieldError(name, 'File must be an image');
      return;
    }

    // Convert file to base64
    const reader = new FileReader();
    reader.onload = (e) => {
      const base64 = e.target?.result as string;
      formik?.setFieldValue(name, base64);
    };
    reader.readAsDataURL(file);
  };

  const handleButtonClick = () => {
    fileInputRef.current?.click();
  };

  const renderLabel = () => {
    if (!labelName) return '';

    if (typeof labelName === 'string') {
      const isRequired = validatesInput?.required;
      return isRequired ? (
        <span>
          {labelName}
          <span style={{ color: 'red' }}> *</span>
        </span>
      ) : labelName;
    }

    return labelName;
  };

  return (
    <Box sx={{ width: '100%', height: '100%', display: 'flex', flexDirection: 'column' }}>
      <input
        type="file"
        ref={fileInputRef}
        onChange={handleFileChange}
        accept={accept}
        style={{ display: 'none' }}
      />
      <Button
        variant="outlined"
        onClick={handleButtonClick}
        startIcon={<CloudUpload />}
        fullWidth
        sx={{
          height: '56px',
          borderColor: error ? 'error.main' : 'divider',
          '&:hover': {
            borderColor: error ? 'error.main' : 'primary.main'
          }
        }}
      >
        {value ? 'Change Image' : 'Upload Image'}
      </Button>
      {value && (
        <Box 
          mt={1} 
          sx={{ 
            height: '300px',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            border: '1px solid',
            borderColor: 'divider',
            borderRadius: 1,
            overflow: 'hidden'
          }}
        >
          <img
            src={value}
            alt="Preview"
            style={{
              width: '100%',
              height: '100%',
              objectFit: 'contain'
            }}
          />
        </Box>
      )}
      {error && (
        <FormHelperText error sx={{ mt: 1 }}>
          {helperText}
        </FormHelperText>
      )}
      {!error && labelName && (
        <Typography variant="caption" color="textSecondary" sx={{ mt: 1, display: 'block' }}>
          {renderLabel()}
        </Typography>
      )}
    </Box>
  );
};

export default Common_InputImage;
