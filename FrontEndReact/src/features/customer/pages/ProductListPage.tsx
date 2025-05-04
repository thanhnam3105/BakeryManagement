import React, { useEffect, useState } from 'react';
import { getProducts } from '../services/productService.ts';
import { Link } from 'react-router-dom';
import { useCartContext } from '../../../contexts/CartContext.tsx';
import { Grid, Card, CardContent, CardMedia, Button, Typography, Box } from '@mui/material';
type Product = {
  id: number;
  name: string;
  price: number;
  description: string;
  imageUrl: string;
};
const ProductListPage = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const { addToCart } = useCartContext();

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    const data = await getProducts();
    setProducts(data);
  };

  const handleAddToCart = (product) => {
    addToCart(product); // Thêm vào giỏ
    alert('🎉 Đã thêm vào giỏ hàng!');
  };

  return (
    <Box sx={{ padding: '20px' }}>
      <Typography variant="h3" gutterBottom align="center">
        🎂 Danh Sách Bánh
      </Typography>

      <Grid container spacing={4}>
        {products.map((product) => (
          <Grid size={{ xs: 12, md: 2 ,sm: 6}} key={product['id']}>
            <Card sx={{ maxWidth: 345, boxShadow: 3, borderRadius: 2 }}>
              <CardMedia
                component="img"
                height="180"
                image={product['imageUrl']}
                alt={product['name']}
                sx={{ objectFit: 'cover', borderRadius: '8px' }}
              />
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  {product['name']}
                </Typography>
                <Typography variant="body2" color="text.secondary" paragraph>
                  {product['description']}
                </Typography>
                <Typography variant="h6" color="primary" gutterBottom>
                  {product['price'].toLocaleString()} VND
                </Typography>
              </CardContent>
              <Box sx={{ padding: 2, display: 'flex', justifyContent: 'space-between' }}>
                <Button
                  component={Link}
                  to={`/customer/products/${product.id}`}
                  variant="outlined"
                  color="primary"
                  size="small"
                >
                  🔎 Xem Chi Tiết
                </Button>
                <Button
                  onClick={() => handleAddToCart(product)}
                  variant="contained"
                  color="success"
                  size="small"
                >
                  ➕ Thêm Giỏ
                </Button>
              </Box>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
};

export default ProductListPage;
