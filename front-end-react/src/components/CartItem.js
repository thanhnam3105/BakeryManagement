import React from 'react';

const CartItem = ({ item, onRemove, onQuantityChange }) => {
  return (
    <div className="cart-item">
      <img src={item.imageUrl} alt={item.name} />
      <h3>{item.name}</h3>
      <input 
        type="number" 
        value={item.quantity} 
        onChange={(e) => onQuantityChange(item.id, parseInt(e.target.value))} 
        min="1" 
      />
      <p>{item.price} VND</p>
      <button onClick={() => onRemove(item.id)}>Xóa</button>
    </div>
  );
};

export default CartItem;
