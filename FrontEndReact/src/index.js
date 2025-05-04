import React from 'react';
import ReactDOM from 'react-dom/client'; // Chỉnh sửa import từ 'react-dom' thành 'react-dom/client'
import App from './App';
import './index.css';

// Sử dụng createRoot thay vì render
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <App />
  </React.StrictMode>
);
