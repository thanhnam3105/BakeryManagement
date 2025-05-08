import React from 'react';
import { createRoot } from 'react-dom/client';

import { ConfigProvider } from './contexts/ConfigContext';
import { ConfirmProvider } from './01_custom/services/confirm.services';

import './index.scss';
import App from './App';
import reportWebVitals from './reportWebVitals';

const container = document.getElementById('root');
const root = createRoot(container);
root.render(
  <ConfigProvider>
    <ConfirmProvider>
      <App />
    </ConfirmProvider>
  </ConfigProvider>
);

reportWebVitals();
