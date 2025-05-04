import { Routes, Route, Navigate } from 'react-router-dom'
import CustomerHomePage from './pages/CustomerHomePage.tsx'
import ProductListPage from './pages/ProductListPage.tsx'
import ProductDetailPage from './pages/ProductDetailPage.tsx'
import CartPage from './pages/CartPage.tsx'
import CheckoutPage from './pages/CheckoutPage.tsx'
import OrderTrackingPage from './pages/OrderTrackingPage.tsx'
import React from 'react'

const CustomerRoutes = () => {
  return (
    <Routes>
      <Route path="home" element={<CustomerHomePage />} />
      <Route path="products" element={<ProductListPage />} />
      <Route path="products/:id" element={<ProductDetailPage />} />
      <Route path="cart" element={<CartPage />} />
      <Route path="checkout" element={<CheckoutPage />} />
      <Route path="orders" element={<OrderTrackingPage />} />
      <Route path="*" element={<Navigate to="/customer/home" />} />
    </Routes>
  )
}

export default CustomerRoutes
