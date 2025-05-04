import { Outlet, Link } from 'react-router-dom'
import React from 'react'

export default function CustomerLayout() {
  return (
    <div>
      <header className="p-4 bg-blue-500 text-white flex justify-between">
        <Link to="/">Tiệm Bánh</Link>
        <nav className="space-x-4">
          <Link to="/products">Sản phẩm</Link>
          <Link to="/cart">Giỏ hàng</Link>
          <Link to="/orders">Đơn hàng</Link>
        </nav>
      </header>
      <main className="p-4">
        <Outlet />
      </main>
    </div>
  )
}
