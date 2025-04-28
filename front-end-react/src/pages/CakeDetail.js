import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom'; // Thay useHistory bằng useNavigate

// Giả lập dữ liệu sản phẩm
const cakesData = [
  { id: 'SP001', name: 'Bánh Kem Dâu', price: 50000, description: 'Bánh kem dâu tươi thơm ngon', imageUrl: 'https://via.placeholder.com/150' },
  { id: 'SP002', name: 'Cheesecake Nhật', price: 100000, description: 'Cheesecake mềm mịn, hương vị đặc biệt', imageUrl: 'https://via.placeholder.com/150' },
];

const CakeDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate(); // Sử dụng useNavigate thay vì useHistory

  // Tìm kiếm bánh theo ID
  const cake = cakesData.find(cake => cake.id === id);

  const handleAddToCart = () => {
    // Giả lập thêm sản phẩm vào giỏ hàng
    alert(`Đã thêm ${cake.name} vào giỏ hàng.`);
    navigate('/cart'); // Thay thế history.push bằng navigate
  };

  return (
    <div>
      <h1>{cake.name}</h1>
      <img src={cake.imageUrl} alt={cake.name} />
      <p>{cake.description}</p>
      <p>{cake.price} VND</p>
      <button onClick={handleAddToCart}>Thêm vào giỏ hàng</button>
    </div>
  );
};

export default CakeDetail;
