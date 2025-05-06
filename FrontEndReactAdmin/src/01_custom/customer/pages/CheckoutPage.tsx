import React from 'react';
import { useCartContext } from '../../../contexts/CartContext.tsx';
import { useNavigate } from 'react-router-dom';

const CheckoutPage = () => {
  const { cartItems, clearCart } = useCartContext();
  const navigate = useNavigate();

  const handleOrder = () => {
    if (cartItems.length === 0) {
      alert('Giỏ hàng trống!');
      return;
    }
    
    // Lưu đơn hàng vào localStorage
    const ordersFromStorage = localStorage.getItem('orders');
    const currentOrders = ordersFromStorage ? JSON.parse(ordersFromStorage) : [];
    
    const newOrder = {
      id: Date.now(),
      items: cartItems,
      date: new Date().toLocaleString(),
    };
    localStorage.setItem('orders', JSON.stringify([...currentOrders, newOrder]));

    // Xóa giỏ hàng
    clearCart();

    // Chuyển sang trang danh sách đơn hàng
    navigate('/customer/orders');
  };

  return (
    <div style={{ padding: 20 }}>
      <h2>Thanh toán</h2>
      <p>Bạn có chắc muốn đặt đơn hàng này không?</p>
      <button onClick={handleOrder}>Xác nhận đặt hàng</button>
    </div>
  );
};

export default CheckoutPage;
