// src/components/common/LoadingOverlay.tsx
import React from 'react';
import { BallTriangle } from 'react-loader-spinner';
import { useLoading } from '../../services/loading.services';

const LoadingOverlay: React.FC = () => {
  const { isLoading } = useLoading();

  if (!isLoading) return null;

  return (
    <div
      style={{
        position: 'fixed',
        zIndex: 9999,
        top: 0,
        left: 0,
        height: '100vh',
        width: '100vw',
        background: 'rgba(255, 255, 255, 0.6)',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center'
      }}
    >
      <BallTriangle height="80" width="80" color="#4fa94d" />

      {/* tham khảo link https://mhnpd.github.io/react-loader-spinner/docs/components/comment */}
    </div>
  );
};

export default LoadingOverlay;
