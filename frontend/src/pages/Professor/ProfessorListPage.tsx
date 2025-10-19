import React, { useState, useEffect, useCallback } from 'react';
import {
  Typography,
  Card,
  List,
  Input,
  Select,
  Space,
  Avatar,
  Rate,
  Tag,
  Spin,
  Alert,
  Pagination,
  Row,
  Col,
  Button
} from 'antd';
import {
  UserOutlined,
  SearchOutlined,
  BookOutlined,
  FilterOutlined
} from '@ant-design/icons';
import { professorApi } from '../../services/api/professorApi';
import { Professor, PaginatedResponse, PaginationParams, ProfessorSearchFilters } from '../../types/professor';

const { Title, Text, Paragraph } = Typography;
const { Search } = Input;
const { Option } = Select;

const ProfessorListPage: React.FC = () => {
  const [professors, setProfessors] = useState<Professor[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [filters, setFilters] = useState<ProfessorSearchFilters>({});
  const [pagination, setPagination] = useState({
    current: 1,
    pageSize: 12,
    total: 0,
    totalPages: 0
  });
  const [sortBy, setSortBy] = useState<string>('lastName');
  const [sortDir, setSortDir] = useState<'asc' | 'desc'>('asc');

  // Fetch professors function
  const fetchProfessors = useCallback(async (
    page: number = 1,
    searchTerm?: string,
    currentFilters?: ProfessorSearchFilters
  ) => {
    try {
      setLoading(true);
      setError(null);

      const paginationParams: PaginationParams = {
        page: page - 1, // Backend uses 0-based pagination
        size: pagination.pageSize,
        sortBy,
        sortDir
      };

      let response: PaginatedResponse<Professor>;

      if (searchTerm) {
        response = await professorApi.searchProfessors(searchTerm, paginationParams);
      } else if (currentFilters && Object.keys(currentFilters).length > 0) {
        response = await professorApi.searchWithFilters(currentFilters, paginationParams);
      } else {
        response = await professorApi.getAllProfessors(paginationParams);
      }

      setProfessors(response.content);
      setPagination(prev => ({
        ...prev,
        current: response.number + 1, // Convert back to 1-based
        total: response.totalElements,
        totalPages: response.totalPages
      }));
    } catch (err) {
      console.error('Error fetching professors:', err);
      setError('Failed to load professors. Please try again.');
    } finally {
      setLoading(false);
    }
  }, [pagination.pageSize, sortBy, sortDir]);

  // Initial load
  useEffect(() => {
    fetchProfessors();
  }, [fetchProfessors]);

  // Handle search
  const handleSearch = (value: string) => {
    setSearchQuery(value);
    setPagination(prev => ({ ...prev, current: 1 }));
    fetchProfessors(1, value.trim() || undefined);
  };

  // Handle filter changes
  const handleFilterChange = (key: keyof ProfessorSearchFilters, value: any) => {
    const newFilters = { ...filters, [key]: value };
    if (value === undefined || value === '') {
      delete newFilters[key];
    }
    setFilters(newFilters);
    setPagination(prev => ({ ...prev, current: 1 }));
    fetchProfessors(1, searchQuery.trim() || undefined, newFilters);
  };

  // Handle pagination change
  const handlePaginationChange = (page: number, pageSize?: number) => {
    if (pageSize && pageSize !== pagination.pageSize) {
      setPagination(prev => ({ ...prev, pageSize, current: 1 }));
      fetchProfessors(1, searchQuery.trim() || undefined, filters);
    } else {
      setPagination(prev => ({ ...prev, current: page }));
      fetchProfessors(page, searchQuery.trim() || undefined, filters);
    }
  };

  // Handle sort change
  const handleSortChange = (value: string) => {
    const [newSortBy, newSortDir] = value.split('-');
    setSortBy(newSortBy);
    setSortDir(newSortDir as 'asc' | 'desc');
    fetchProfessors(1, searchQuery.trim() || undefined, filters);
  };

  // Clear all filters
  const clearFilters = () => {
    setSearchQuery('');
    setFilters({});
    setPagination(prev => ({ ...prev, current: 1 }));
    fetchProfessors(1);
  };

  // Get professor's average rating display
  const getRatingDisplay = (professor: Professor) => {
    if (!professor.averageRating || professor.ratingCount === 0) {
      return <Text type="secondary">No ratings yet</Text>;
    }
    return (
      <Space>
        <Rate disabled allowHalf value={professor.averageRating} style={{ fontSize: 14 }} />
        <Text>{professor.averageRating.toFixed(1)}</Text>
        <Text type="secondary">({professor.ratingCount} reviews)</Text>
      </Space>
    );
  };

  return (
    <div style={{ padding: '24px', backgroundColor: '#f5f5f5', minHeight: '100vh' }}>
      <div style={{ maxWidth: '1200px', margin: '0 auto' }}>
        {/* Header */}
        <div style={{ marginBottom: '24px', textAlign: 'center' }}>
          <Title level={1}>Professors</Title>
          <Paragraph style={{ fontSize: '16px', color: '#666' }}>
            Browse and search university professors, read reviews, and find the best instructors for your courses.
          </Paragraph>
        </div>

        {/* Search and Filter Section */}
        <Card style={{ marginBottom: '24px' }}>
          <Row gutter={[16, 16]} align="middle">
            <Col xs={24} sm={24} md={8} lg={10}>
              <Search
                placeholder="Search professors by name..."
                allowClear
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                onSearch={handleSearch}
                prefix={<SearchOutlined />}
                size="large"
              />
            </Col>

            <Col xs={12} sm={8} md={4} lg={3}>
              <Select
                placeholder="Min Rating"
                allowClear
                value={filters.minRating}
                onChange={(value) => handleFilterChange('minRating', value)}
                style={{ width: '100%' }}
                size="large"
              >
                <Option value={4.5}>4.5+ Stars</Option>
                <Option value={4.0}>4.0+ Stars</Option>
                <Option value={3.5}>3.5+ Stars</Option>
                <Option value={3.0}>3.0+ Stars</Option>
              </Select>
            </Col>

            <Col xs={12} sm={8} md={4} lg={3}>
              <Select
                placeholder="Sort By"
                value={`${sortBy}-${sortDir}`}
                onChange={handleSortChange}
                style={{ width: '100%' }}
                size="large"
              >
                <Option value="lastName-asc">Name A-Z</Option>
                <Option value="lastName-desc">Name Z-A</Option>
                <Option value="averageRating-desc">Highest Rated</Option>
                <Option value="averageRating-asc">Lowest Rated</Option>
                <Option value="createdAt-desc">Newest</Option>
              </Select>
            </Col>

            <Col xs={24} sm={8} md={4} lg={2}>
              <Button
                icon={<FilterOutlined />}
                onClick={clearFilters}
                size="large"
                style={{ width: '100%' }}
              >
                Clear
              </Button>
            </Col>
          </Row>
        </Card>

        {/* Results Info */}
        <div style={{ marginBottom: '16px' }}>
          <Text>
            Showing {professors.length} of {pagination.total} professors
          </Text>
        </div>

        {/* Error Display */}
        {error && (
          <Alert
            message="Error"
            description={error}
            type="error"
            showIcon
            style={{ marginBottom: '16px' }}
          />
        )}

        {/* Loading State */}
        {loading ? (
          <div style={{ textAlign: 'center', padding: '50px' }}>
            <Spin size="large" />
            <div style={{ marginTop: '16px' }}>
              <Text>Loading professors...</Text>
            </div>
          </div>
        ) : (
          <>
            {/* Professor List */}
            <List
              grid={{
                gutter: 16,
                xs: 1,
                sm: 2,
                md: 2,
                lg: 3,
                xl: 3,
                xxl: 4,
              }}
              dataSource={professors}
              renderItem={(professor) => (
                <List.Item>
                  <Card
                    hoverable
                    style={{ height: '100%' }}
                    actions={[
                      <Space key="courses">
                        <BookOutlined />
                        <Text>{professor.courseCount || 0} courses</Text>
                      </Space>,
                      <Space key="department">
                        <Tag color="blue">{professor.departmentCode}</Tag>
                      </Space>
                    ]}
                  >
                    <Card.Meta
                      avatar={
                        <Avatar
                          size={64}
                          src={professor.photoUrl}
                          icon={!professor.photoUrl && <UserOutlined />}
                        />
                      }
                      title={
                        <div>
                          <Text strong style={{ fontSize: '16px' }}>
                            {professor.title ? `${professor.title} ` : ''}
                            {professor.firstName} {professor.lastName}
                          </Text>
                        </div>
                      }
                      description={
                        <Space direction="vertical" style={{ width: '100%' }}>
                          <Text type="secondary">{professor.departmentName}</Text>

                          {/* Rating */}
                          <div>
                            {getRatingDisplay(professor)}
                          </div>

                          {/* Bio */}
                          {professor.bio && (
                            <Paragraph
                              ellipsis={{ rows: 2, expandable: false }}
                              style={{ margin: 0, fontSize: '12px' }}
                            >
                              {professor.bio}
                            </Paragraph>
                          )}
                        </Space>
                      }
                    />
                  </Card>
                </List.Item>
              )}
              locale={{
                emptyText: searchQuery || Object.keys(filters).length > 0
                  ? 'No professors found matching your criteria'
                  : 'No professors available'
              }}
            />

            {/* Pagination */}
            {pagination.total > pagination.pageSize && (
              <div style={{ textAlign: 'center', marginTop: '32px' }}>
                <Pagination
                  current={pagination.current}
                  total={pagination.total}
                  pageSize={pagination.pageSize}
                  showSizeChanger
                  showQuickJumper
                  showTotal={(total, range) =>
                    `${range[0]}-${range[1]} of ${total} professors`
                  }
                  onChange={handlePaginationChange}
                  pageSizeOptions={['12', '24', '36', '48']}
                />
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default ProfessorListPage;