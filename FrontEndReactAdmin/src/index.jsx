import React from 'react';
import { createRoot } from 'react-dom/client';

import { ConfigProvider } from './contexts/ConfigContext';
import { ConfirmProvider } from './01_custom/services/confirm.services';
import LoadingOverlay from './01_custom/components/common/Common_LoadingOverlay';
import './index.scss';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from 'react-toastify';
import { LoadingProvider } from './01_custom/services/loading.services';
const container = document.getElementById('root');

const root = createRoot(container);
root.render(
  <ConfigProvider>
    <ConfirmProvider>
      <LoadingProvider>
        <App />
        <LoadingOverlay />
      </LoadingProvider>
      <ToastContainer />
    </ConfirmProvider>
  </ConfigProvider>
);

reportWebVitals();
