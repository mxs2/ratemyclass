import React from 'react';
import { createRoot } from 'react-dom/client';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import { ConfigProvider } from 'antd';
import { store } from './store/index';
import { theme } from './styles/theme';
import { AuthProvider } from './hooks/useAuth';
import App from './App';
import './styles/globals.css';

const container = document.getElementById('root');
if (!container) {
  throw new Error('Root element not found');
}

const root = createRoot(container);

root.render(
  <React.StrictMode>
    <Provider store={store}>
      <BrowserRouter>
        <ConfigProvider theme={theme}>
          <AuthProvider>
            <App />
          </AuthProvider>
        </ConfigProvider>
      </BrowserRouter>
    </Provider>
  </React.StrictMode>
);