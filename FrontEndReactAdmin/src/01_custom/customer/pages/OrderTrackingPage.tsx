import React, { useEffect, useState } from 'react';
import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography, Paper } from '@mui/material';

type Order = {
  id: number;
  items: CartItem[];
  date: string;
  total?: number;
  status?: string;
};
type CartItem = {
  id: number;
  name: string;
  price: number;
  quantity: number;
  imageUrl: string;
};
const OrdersPage = () => {
  const [orders, setOrders] = useState<Order[]>([]);

  useEffect(() => {
    const storedOrders = localStorage.getItem('orders');
    if (storedOrders) {
      setOrders(JSON.parse(storedOrders));
    }
  }, []);

  return (
    <Box sx={{ maxWidth: '1200px', mx: 'auto', p: 4 }}>
      <Typography variant="h4"  sx={{ mb: 4 }}>
        Lịch sử đơn hàng
      </Typography>

      {orders.length === 0 ? (
        <Typography variant="h6" color="textSecondary">
          Bạn chưa có đơn hàng nào.
        </Typography>
      ) : (
        <TableContainer component={Paper} sx={{ mt: 2 }}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell><strong>Mã đơn</strong></TableCell>
                <TableCell><strong>Ngày đặt</strong></TableCell>
                <TableCell><strong>Sản phẩm</strong></TableCell>
                <TableCell><strong>Tổng tiền</strong></TableCell>
                <TableCell><strong>Trạng thái</strong></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {orders.map((order, index) => (
                <TableRow key={index} sx={{ '&:hover': { backgroundColor: '#f1f1f1' } }}>
                  <TableCell>{order['id']}</TableCell>
                  <TableCell>{order['date']}</TableCell>
                  <TableCell>
                    {order['items'].map((item, idx) => (
                      <div key={idx}>
                        {item.name} x {item.quantity}
                      </div>
                    ))}
                  </TableCell>
                  <TableCell>{order['total'] ? order['total'].toLocaleString() + '₫' : '0₫'}</TableCell>
                  <TableCell>
                    <Typography variant="body2" sx={{
                      display: 'inline-block', 
                      px: 2, 
                      py: 1, 
                      borderRadius: 12, 
                      backgroundColor: order['status'] === 'Đã giao' ? '#d4edda' : '#f8d7da', 
                      color: order['status'] === 'Đã giao' ? '#155724' : '#721c24'
                    }}>
                      {order['status'] || 'Đã đặt'}
                    </Typography>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </Box>
  );
};

export default OrdersPage;
