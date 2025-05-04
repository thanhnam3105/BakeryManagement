import { Routes, Route } from 'react-router-dom'
import CustomerRoutes from '../features/customer/customerRoutes'
import React from 'react'


export default function AppRoutes() {
  return (
    <Routes>
      {CustomerRoutes}
    </Routes>
  )
}
