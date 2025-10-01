import React from 'react';
import { Typography } from 'antd';

const { Title } = Typography;

const SearchResultsPage: React.FC = () => {
  return (
    <div>
      <Title level={2}>Search Results</Title>
      <p>Search results coming soon...</p>
    </div>
  );
};

export default SearchResultsPage;