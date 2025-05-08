import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import AdminRoutes from './AdminRoutes';
import CustomerRoutes from './CustomerRoutes';
import './index.css';

const App = () => (
  <Router>
    <div className="App">
      <AdminRoutes />
      <CustomerRoutes />
    </div>
  </Router>
);

export default App;
