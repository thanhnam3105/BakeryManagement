import React from 'react';
import { Card, CardMedia, CardContent, Typography, Button, Box, Stack } from '@mui/material';
import { useCartContext } from '../../../contexts/CartContext.tsx'; // Đường dẫn CartContext
import { Link } from 'react-router-dom';

type Product = {
    id: number;
    name: string;
    price: number;
    description?: string;
    imageUrl: string;
  };
  

type ProductCardProps = {
  product: Product;
};

const ProductCard: React.FC<ProductCardProps> = ({ product }) => {
  const { addToCart } = useCartContext();

  const handleAddToCart = () => {
    alert('Thêm vào giỏ hàng thành công!');
    addToCart({
      ...product,
      quantity: 1, // Thêm số lượng khi add
    });
  };

  return (
    <Card sx={{ borderRadius: 3, overflow: 'hidden', position: 'relative', boxShadow: 3 }}>
      <Box
        sx={{
          position: 'relative',
          '&:hover .hover-buttons': {
            opacity: 1,
            pointerEvents: 'auto',
          },
        }}
      >
        <CardMedia
          component="img"
          height="220"
          image={product.imageUrl}
          alt={product.name}
          sx={{ objectFit: 'cover' }}
        />
        
        {/* Các nút nổi trên ảnh */}
        <Stack
          direction="column"
          spacing={1}
          className="hover-buttons"
          sx={{
            position: 'absolute',
            top: 10,
            left: '50%',
            transform: 'translateX(-50%)',
            backgroundColor: 'rgba(255,255,255,0.9)',
            borderRadius: 2,
            p: 1,
            opacity: 0,
            pointerEvents: 'none',
            transition: 'opacity 0.3s',
            alignItems: 'center',
          }}
        >
          <Button
            variant="outlined"
            size="small"
            onClick={handleAddToCart}
          >
            Add to Cart
          </Button>

          <Button
            component={Link}
            to={`/customer/products/${product.id}`}
            variant="outlined"
            size="small"
          >
            Visit Product
          </Button>
        </Stack>
      </Box>

      <CardContent sx={{ textAlign: 'center' }}>
        <Typography variant="h6" gutterBottom>
          {product.name}
        </Typography>
        <Typography variant="subtitle1" color="primary">
          {product.price.toLocaleString()} VND
        </Typography>
      </CardContent>
    </Card>
  );
};

export default ProductCard;
