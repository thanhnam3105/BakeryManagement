import React, { useState } from 'react';
import CartItem from '../components/CartItem';

// Giả lập dữ liệu giỏ hàng
const cartData = [
  { id: 'SP001', name: 'Bánh Kem Dâu', quantity: 2, price: 50000, imageUrl: 'https://via.placeholder.com/150' },
  { id: 'SP002', name: 'Cheesecake Nhật', quantity: 1, price: 100000, imageUrl: 'https://via.placeholder.com/150' },
];

const Cart = () => {
  const [cartItems, setCartItems] = useState(cartData);

  const handleQuantityChange = (id, quantity) => {
    setCartItems(prevItems =>
      prevItems.map(item => 
        item.id === id ? { ...item, quantity } : item
      )
    );
  };

  const handleRemoveItem = (id) => {
    setCartItems(prevItems => prevItems.filter(item => item.id !== id));
  };

  const calculateTotal = () => {
    return cartItems.reduce((total, item) => total + item.quantity * item.price, 0);
  };

  return (
    <div>
      <h1>Giỏ hàng</h1>
      {cartItems.map(item => (
        <CartItem 
          key={item.id} 
          item={item} 
          onRemove={handleRemoveItem} 
          onQuantityChange={handleQuantityChange} 
        />
      ))}
      <h3>Tổng cộng: {calculateTotal()} VND</h3>
      <button>Thanh toán</button>
    </div>
  );
};

export default Cart;
