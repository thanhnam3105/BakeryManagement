import React from 'react';
import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <header>
      <nav>
        <Link to="/">Trang chủ</Link>
        <Link to="/cart">Giỏ hàng</Link>
      </nav>
    </header>
  );
};

export default Header;
