import React from 'react';
import { Link } from 'react-router-dom';

const CustomerHomePage = () => {
  return (
    <div>
      <h1>Trang Chủ Khách Hàng</h1>
      <p>Chào mừng bạn đến với Tiệm Bánh!</p>
      <Link to="/customer/products">
        <button>Xem Danh Sách Bánh</button>
      </Link>
    </div>
  );
};

export default CustomerHomePage;
