import React from 'react';
import { Routes, Route } from 'react-router-dom';
import UsersPage from '@/pages/admin/Users';
import UserForm from '@/pages/admin/Users/UserForm';

const AdminRoutes = () => (
  <Routes>
    <Route path="/admin/users" element={<UsersPage />} />
    <Route path="/admin/users/:userId" element={<UserForm />} />
  </Routes>
);

export default AdminRoutes;
