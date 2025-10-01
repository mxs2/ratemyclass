import React from 'react';
import { Typography } from 'antd';

const { Title } = Typography;

const DashboardPage: React.FC = () => {
  return (
    <div>
      <Title level={2}>Dashboard</Title>
      <p>User dashboard coming soon...</p>
    </div>
  );
};

export default DashboardPage;