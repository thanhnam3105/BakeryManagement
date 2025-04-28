import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Thay Switch bằng Routes
import { CartProvider } from './context/CartContext';
import HomePage from './pages/HomePage';
import CakeDetail from './pages/CakeDetail';
import Cart from './pages/Cart';
import Header from './components/Header';
import Footer from './components/Footer';

const App = () => {
  return (
    <CartProvider>
      <Router>
        <Header />
        <Routes> {/* Thay Switch bằng Routes */}
          <Route path="/" element={<HomePage />} /> {/* Sử dụng element thay vì component */}
          <Route path="/cake/:id" element={<CakeDetail />} />
          <Route path="/cart" element={<Cart />} />
        </Routes>
        <Footer />
      </Router>
    </CartProvider>
  );
};

export default App;
