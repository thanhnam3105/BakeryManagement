import React, { useContext } from 'react';
import { TextField, TextFieldProps } from '@mui/material';
import { FormikProps, FormikContext } from 'formik';

interface Common_InputProps extends Omit<TextFieldProps, 'variant'> {
  settingInput: {
    name: string;
    labelName?: string;
    type?: string;
    multiline?: boolean;
    rows?: number;
    [key: string]: any;
    required?: boolean;
  };
}

const Common_Input: React.FC<Common_InputProps> = ({
  settingInput,
  ...props
}) => {
  const formik = useContext(FormikContext);
  const { name, labelName, type = 'text', multiline, rows, ...restSettings } = settingInput;
  
  // Get formik values and handlers
  const value = formik?.values[name] ?? '';
  const error = formik?.touched[name] && Boolean(formik?.errors[name]);
  const helperText = formik?.touched[name] && String(formik?.errors[name] ?? '');

  return (
    <TextField
      name={name}
      label={labelName}
      type={type}
      value={value}
      onChange={formik?.handleChange}
      onBlur={formik?.handleBlur}
      error={error}
      helperText={helperText}
      fullWidth={true}
      multiline={multiline}
      rows={rows}
      variant="outlined"
      {...restSettings}
      {...props}
    />
  );
};

export default Common_Input;
