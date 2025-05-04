import React from 'react';
import { useCartContext } from '../../../contexts/CartContext.tsx';
import { Link, useNavigate } from 'react-router-dom';
import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography, Button, Paper } from '@mui/material';

const CartPage = () => {
  const { cartItems, increaseQuantity, decreaseQuantity, removeFromCart } = useCartContext();
  const navigate = useNavigate();
  const totalPrice = cartItems.reduce((total, item) => total + item.price * item.quantity, 0);

  return (
    <Box sx={{ padding: '20px' }}>
      <Typography variant="h4" gutterBottom>
        🛒 Giỏ Hàng
      </Typography>

      {cartItems.length === 0 ? (
        <Typography variant="h6">Giỏ hàng trống</Typography>
      ) : (
        <>
          <TableContainer component={Paper} sx={{ marginTop: '20px' }}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell align="center"><strong>Tên Bánh</strong></TableCell>
                  <TableCell align="center"><strong>Giá</strong></TableCell>
                  <TableCell align="center"><strong>Số Lượng</strong></TableCell>
                  <TableCell align="center"><strong>Tổng</strong></TableCell>
                  <TableCell align="center"><strong>Hành Động</strong></TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {cartItems.map((item) => (
                  <TableRow key={item.id}>
                    <TableCell align="center">{item.name}</TableCell>
                    <TableCell align="center">{item.price.toLocaleString()} VND</TableCell>
                    <TableCell align="center">
                      <Button onClick={() => decreaseQuantity(item.id)} variant="outlined" size="small" color="primary">-</Button>
                      <Typography component="span" sx={{ mx: 2 }}>{item.quantity}</Typography>
                      <Button onClick={() => increaseQuantity(item.id)} variant="outlined" size="small" color="primary">+</Button>
                    </TableCell>
                    <TableCell align="center">{(item.price * item.quantity).toLocaleString()} VND</TableCell>
                    <TableCell align="center">
                      <Button onClick={() => removeFromCart(item.id)} variant="outlined" color="error" size="small">Xóa</Button>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>

          <Typography variant="h5" sx={{ marginTop: '20px' }}>
            Tổng tiền: {totalPrice.toLocaleString()} VND
          </Typography>

          <Link to="/customer/products">
            <Button variant="contained" sx={{ mt: 2, backgroundColor: '#2ecc71' }}>
              ⬅️ Tiếp tục mua hàng
            </Button>
          </Link>

          <Button 
            onClick={() => navigate('/customer/checkout')} 
            variant="contained" 
            sx={{ mt: 2, ml: 2 }}
          >
            Thanh toán
          </Button>
        </>
      )}
    </Box>
  );
};

export default CartPage;
