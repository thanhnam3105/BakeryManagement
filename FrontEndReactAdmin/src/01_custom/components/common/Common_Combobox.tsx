import { useField } from 'formik';
import { FormControl, InputLabel, Select, MenuItem, FormHelperText } from '@mui/material';
import PropTypes from 'prop-types';

interface Common_ComboboxProps {
  settingInput: {
    name: string;
    label: string;
    options: Array<{ value: string; name: string }>;
    required?: boolean;
  };
}

function Common_Combobox({ settingInput }: Common_ComboboxProps) {
  const [field, meta] = useField(settingInput.name);

  return (
    <FormControl fullWidth error={meta.touched && Boolean(meta.error)}>
      <InputLabel id={`${settingInput.name}-label`}>{settingInput.label}</InputLabel>
      <Select
        labelId={`${settingInput.name}-label`}
        id={settingInput.name}
        {...field}
        label={settingInput.label}
      >
        {settingInput.options.map((option) => (
          <MenuItem key={option.value} value={option.value}>
            {option.name}
          </MenuItem>
        ))}
      </Select>
      {meta.touched && meta.error && (
        <FormHelperText>{meta.error}</FormHelperText>
      )}
    </FormControl>
  );
}

Common_Combobox.propTypes = {
  settingInput: PropTypes.shape({
    name: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    options: PropTypes.arrayOf(
      PropTypes.shape({
        value: PropTypes.string.isRequired,
        name: PropTypes.string.isRequired
      })
    ).isRequired
  }).isRequired,
  validatesInput: PropTypes.shape({
    required: PropTypes.bool
  })
};

export default Common_Combobox;
