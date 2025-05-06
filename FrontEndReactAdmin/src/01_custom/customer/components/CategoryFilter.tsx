import React from 'react';
import { Stack, Box, Typography } from '@mui/material';
import CookieIcon from '@mui/icons-material/Cookie'; // bạn có thể thay icon khác tùy ý

const categories = [
  { label: 'Cookies', icon: <CookieIcon /> },
  { label: 'Cakes', icon: <CookieIcon /> },
  { label: 'Pastries', icon: <CookieIcon /> },
  { label: 'Bread', icon: <CookieIcon /> },
  { label: 'Cupcakes', icon: <CookieIcon /> },
];

const CategoryFilter = () => {
  return (
    <Stack direction="row" justifyContent="center" spacing={4}>
      {categories.map((category, index) => (
        <Box key={index} sx={{ textAlign: 'center', cursor: 'pointer' }}>
          <Box sx={{ mb: 1, fontSize: 40 }}>{category.icon}</Box>
          <Typography variant="subtitle1" sx={{ color: index === 0 ? 'orange' : 'text.primary' }}>
            {category.label}
          </Typography>
        </Box>
      ))}
    </Stack>
  );
};

export default CategoryFilter;
