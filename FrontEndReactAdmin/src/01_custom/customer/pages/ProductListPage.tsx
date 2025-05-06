import React from 'react';
import { Container, Typography, Box, Grid } from '@mui/material';
import CategoryFilter from '../components/CategoryFilter.tsx';
import ProductCard from '../components/ProductCard.tsx';
import img1 from '../../../assets/images/1.jpg';
import img2 from '../../../assets/images/2.jpg';
import img3 from '../../../assets/images/3.jpg';

const ProductListPage = () => {
  // Giả lập data sản phẩm
  const products = [
    { id: 1, name: 'Mini Cupcakes', price: 120000, imageUrl: img1 },
    { id: 2, name: 'Fried Cheese Buns', price: 120000, imageUrl: img2 },
    { id: 3, name: 'Croissants', price: 120000, imageUrl: img3 },
  ];

  return (
    <Container maxWidth="lg" sx={{ py: 5 }}>
      <Typography variant="h3" align="center" gutterBottom>
        Product
      </Typography>

      <Box sx={{ my: 4 }}>
        <CategoryFilter />
      </Box>

      <Grid container spacing={4}>
        {products.map((product) => (
          <Grid size={{ xs: 12, sm:6, md: 4 }} key={product.id}>
            <ProductCard product={product} />
          </Grid>
        ))}
      </Grid>
    </Container>
  );
};

export default ProductListPage;
