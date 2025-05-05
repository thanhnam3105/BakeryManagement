import { BrowserRouter as Router, Routes, Route,BrowserRouter  } from 'react-router-dom';
import { Navigate } from 'react-router-dom'
import CustomerRoutes from './features/customer/customerRoutes.tsx'
import AdminRoutes from './features/admin/adminRouters.tsx'
import { CartProvider } from './contexts/CartContext.tsx';

function App() {
  return (
      <CartProvider> 
        <BrowserRouter>
          <Routes>
            <Route path="/customer/*" element={<CustomerRoutes />} />
            <Route path="/admin/*" element={<AdminRoutes />} />
            <Route path="*" element={<Navigate to="/customer/home" />} />
          </Routes>
        </BrowserRouter>
      </CartProvider>
  );
}

export default App;
