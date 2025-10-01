import React from 'react';
import { Card, Row, Col, Button, Typography, Space, Input, Rate } from 'antd';
import { SearchOutlined, StarOutlined, BookOutlined, UserOutlined } from '@ant-design/icons';
import { useNavigate } from 'react-router-dom';

const { Title, Paragraph } = Typography;
const { Search } = Input;

const HomePage: React.FC = () => {
  const navigate = useNavigate();

  const handleSearch = (value: string) => {
    if (value.trim()) {
      navigate(`/search?q=${encodeURIComponent(value.trim())}`);
    }
  };

  return (
    <div style={{ maxWidth: 1200, margin: '0 auto' }}>
      {/* Hero Section */}
      <div style={{ textAlign: 'center', marginBottom: 48 }}>
        <Title level={1} style={{ fontSize: '3rem', marginBottom: 16 }}>
          Find the Perfect Class
        </Title>
        <Paragraph style={{ fontSize: '1.2rem', color: '#666', marginBottom: 32 }}>
          Read reviews, discover professors, and make informed decisions about your education
        </Paragraph>
        
        <Search
          placeholder="Search for professors, courses, or universities..."
          allowClear
          enterButton={<SearchOutlined />}
          size="large"
          style={{ maxWidth: 600 }}
          onSearch={handleSearch}
        />
      </div>

      {/* Stats Section */}
      <Row gutter={[24, 24]} style={{ marginBottom: 48 }}>
        <Col xs={24} sm={8}>
          <Card style={{ textAlign: 'center' }}>
            <StarOutlined style={{ fontSize: '2rem', color: '#1890ff', marginBottom: 16 }} />
            <Title level={3}>10,000+</Title>
            <Paragraph>Reviews Written</Paragraph>
          </Card>
        </Col>
        <Col xs={24} sm={8}>
          <Card style={{ textAlign: 'center' }}>
            <UserOutlined style={{ fontSize: '2rem', color: '#1890ff', marginBottom: 16 }} />
            <Title level={3}>5,000+</Title>
            <Paragraph>Professors Rated</Paragraph>
          </Card>
        </Col>
        <Col xs={24} sm={8}>
          <Card style={{ textAlign: 'center' }}>
            <BookOutlined style={{ fontSize: '2rem', color: '#1890ff', marginBottom: 16 }} />
            <Title level={3}>15,000+</Title>
            <Paragraph>Courses Listed</Paragraph>
          </Card>
        </Col>
      </Row>

      {/* Featured Sections */}
      <Row gutter={[24, 24]}>
        <Col xs={24} md={12}>
          <Card
            title="Recent Reviews"
            extra={<Button type="link">View All</Button>}
          >
            <Space direction="vertical" style={{ width: '100%' }}>
              <div>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <strong>Dr. Smith - Computer Science 101</strong>
                  <Rate disabled defaultValue={4} style={{ fontSize: '14px' }} />
                </div>
                <Paragraph ellipsis={{ rows: 2 }}>
                  "Great professor! Very clear explanations and helpful office hours. The assignments were challenging but fair."
                </Paragraph>
              </div>
              <div>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <strong>Prof. Johnson - Mathematics 201</strong>
                  <Rate disabled defaultValue={5} style={{ fontSize: '14px' }} />
                </div>
                <Paragraph ellipsis={{ rows: 2 }}>
                  "Amazing teacher! Made complex topics easy to understand. Highly recommend taking any class with Prof. Johnson."
                </Paragraph>
              </div>
            </Space>
          </Card>
        </Col>

        <Col xs={24} md={12}>
          <Card
            title="Popular Professors"
            extra={<Button type="link">View All</Button>}
          >
            <Space direction="vertical" style={{ width: '100%' }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <strong>Dr. Emily Chen</strong>
                  <div style={{ color: '#666' }}>Psychology Department</div>
                </div>
                <div style={{ textAlign: 'right' }}>
                  <Rate disabled defaultValue={4.8} allowHalf style={{ fontSize: '14px' }} />
                  <div style={{ color: '#666', fontSize: '12px' }}>4.8/5 (127 reviews)</div>
                </div>
              </div>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <strong>Prof. Michael Brown</strong>
                  <div style={{ color: '#666' }}>Engineering Department</div>
                </div>
                <div style={{ textAlign: 'right' }}>
                  <Rate disabled defaultValue={4.6} allowHalf style={{ fontSize: '14px' }} />
                  <div style={{ color: '#666', fontSize: '12px' }}>4.6/5 (89 reviews)</div>
                </div>
              </div>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div>
                  <strong>Dr. Sarah Wilson</strong>
                  <div style={{ color: '#666' }}>Literature Department</div>
                </div>
                <div style={{ textAlign: 'right' }}>
                  <Rate disabled defaultValue={4.7} allowHalf style={{ fontSize: '14px' }} />
                  <div style={{ color: '#666', fontSize: '12px' }}>4.7/5 (156 reviews)</div>
                </div>
              </div>
            </Space>
          </Card>
        </Col>
      </Row>

      {/* Call to Action */}
      <div style={{ textAlign: 'center', marginTop: 48, padding: '32px 0' }}>
        <Title level={2}>Ready to Share Your Experience?</Title>
        <Paragraph style={{ fontSize: '1.1rem', marginBottom: 24 }}>
          Help other students by rating your professors and courses
        </Paragraph>
        <Space>
          <Button type="primary" size="large" onClick={() => navigate('/register')}>
            Get Started
          </Button>
          <Button size="large" onClick={() => navigate('/professors')}>
            Browse Professors
          </Button>
        </Space>
      </div>
    </div>
  );
};

export default HomePage;