import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getProductById } from '../services/productService.ts';
import { useCartContext } from '../../../contexts/CartContext.tsx';
import {
  Container,
  Typography,
  Button,
  Card,
  CardContent,
  CardMedia,
  Grid,
  Box
} from '@mui/material';

type Product = {
  id: number;
  name: string;
  price: number;
  description: string;
  imageUrl: string;
};

const ProductDetailPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { addToCart } = useCartContext();
  const [product, setProduct] = useState<Product | null>(null);

  useEffect(() => {
    async function fetchData() {
      const data = await getProductById(id);
      if (data) {
        setProduct(data);
      } else {
        setProduct(null);
      }
    }
    fetchData();
  }, [id]);

  if (!product) return (
    <Typography variant="h5" align="center" mt={5}>
      Loading...
    </Typography>
  );

  const handleAddToCart = () => {
    addToCart({ ...product, quantity: 1 });
    navigate('/customer/cart');
  };

  return (
    <Container maxWidth="md" sx={{ mt: 4 }}>
      <Box mb={2}>
        <Button component={Link} to="/customer/products" variant="outlined">
          ← Quay lại danh sách sản phẩm
        </Button>
      </Box>

      <Card>
        <Grid container spacing={2}>
          <Grid size={{ xs: 12, md: 6 }}>
            <CardMedia
              component="img"
              image={product.imageUrl}
              alt={product.name}
              sx={{ objectFit: 'cover', height: '100%', borderRadius: '8px 0 0 8px' }}
            />
          </Grid>

          <Grid size={{ xs: 12, md: 6 }}>
            <CardContent>
              <Typography variant="h4" gutterBottom>{product.name}</Typography>
              <Typography variant="h6" color="text.secondary" gutterBottom>
                Giá: {product.price.toLocaleString()} VND
              </Typography>
              <Typography variant="body1" paragraph>
                {product.description}
              </Typography>
              <Button variant="contained" color="primary" onClick={handleAddToCart}>
                Thêm vào giỏ
              </Button>
            </CardContent>
          </Grid>
        </Grid>
      </Card>
    </Container>
  );
};

export default ProductDetailPage;
