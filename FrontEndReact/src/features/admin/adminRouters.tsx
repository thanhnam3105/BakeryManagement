import { Routes, Route, Navigate } from 'react-router-dom'
import AdminProductPage from './pages/AdminProduct.tsx'

import React from 'react'

const AdminRoutes = () => {
  return (
    <Routes>
      <Route path="home" element={<AdminProductPage />} />
      <Route path="*" element={<Navigate to="/admin/home" />} />
    </Routes>
  )
}

export default AdminRoutes
