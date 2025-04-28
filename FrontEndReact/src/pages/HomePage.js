import React from 'react';
import CakeCard from '../components/CakeCard';

// Giả lập dữ liệu sản phẩm
const cakesData = [
  { id: 'SP001', name: 'Bánh Kem Dâu', price: 50000, imageUrl: 'https://via.placeholder.com/150' },
  { id: 'SP002', name: 'Cheesecake Nhật', price: 100000, imageUrl: 'https://via.placeholder.com/150' },
];

const HomePage = () => {
  return (
    <div>
      <h1>Welcome</h1>
      <div className="cake-list">
        {cakesData.map((cake) => (
          <CakeCard key={cake.id} cake={cake} />
        ))}
      </div>
    </div>
  );
};

export default HomePage;
