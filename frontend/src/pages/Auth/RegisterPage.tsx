import React from 'react';
import { Card, Form, Input, Button, Typography, Alert, InputNumber } from 'antd';
import { UserOutlined, LockOutlined, MailOutlined } from '@ant-design/icons';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';

const { Title } = Typography;

const RegisterPage: React.FC = () => {
  const [form] = Form.useForm();
  const { register } = useAuth();
  const navigate = useNavigate();
  const [loading, setLoading] = React.useState(false);
  const [error, setError] = React.useState<string | null>(null);

  const handleSubmit = async (values: any) => {
    try {
      setLoading(true);
      setError(null);
      await register(values);
      navigate('/dashboard');
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Registration failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ 
      display: 'flex', 
      justifyContent: 'center', 
      alignItems: 'center', 
      minHeight: '80vh',
      padding: '20px'
    }}>
      <Card style={{ width: '100%', maxWidth: 500 }}>
        <div style={{ textAlign: 'center', marginBottom: 24 }}>
          <Title level={2}>Join RateMyClass</Title>
          <p>Create your account to start rating professors and courses</p>
        </div>

        {error && (
          <Alert
            message={error}
            type="error"
            showIcon
            style={{ marginBottom: 16 }}
          />
        )}

        <Form
          form={form}
          layout="vertical"
          onFinish={handleSubmit}
          autoComplete="off"
        >
          <Form.Item
            name="email"
            label="Email"
            rules={[
              { required: true, message: 'Please enter your email!' },
              { type: 'email', message: 'Please enter a valid email!' }
            ]}
          >
            <Input 
              prefix={<MailOutlined />} 
              placeholder="your.email@university.edu"
              size="large"
            />
          </Form.Item>

          <div style={{ display: 'flex', gap: '12px' }}>
            <Form.Item
              name="firstName"
              label="First Name"
              style={{ flex: 1 }}
              rules={[{ required: true, message: 'Please enter your first name!' }]}
            >
              <Input 
                prefix={<UserOutlined />} 
                placeholder="John"
                size="large"
              />
            </Form.Item>

            <Form.Item
              name="lastName"
              label="Last Name"
              style={{ flex: 1 }}
              rules={[{ required: true, message: 'Please enter your last name!' }]}
            >
              <Input 
                placeholder="Doe"
                size="large"
              />
            </Form.Item>
          </div>

          <Form.Item
            name="password"
            label="Password"
            rules={[
              { required: true, message: 'Please enter a password!' },
              { min: 6, message: 'Password must be at least 6 characters!' }
            ]}
          >
            <Input.Password 
              prefix={<LockOutlined />} 
              placeholder="Password (min 6 characters)"
              size="large"
            />
          </Form.Item>

          <Form.Item
            name="confirmPassword"
            label="Confirm Password"
            dependencies={['password']}
            rules={[
              { required: true, message: 'Please confirm your password!' },
              ({ getFieldValue }) => ({
                validator(_, value) {
                  if (!value || getFieldValue('password') === value) {
                    return Promise.resolve();
                  }
                  return Promise.reject(new Error('Passwords do not match!'));
                },
              }),
            ]}
          >
            <Input.Password 
              prefix={<LockOutlined />} 
              placeholder="Confirm Password"
              size="large"
            />
          </Form.Item>

          <Form.Item
            name="university"
            label="University (Optional)"
          >
            <Input 
              placeholder="e.g., University of California, Berkeley"
              size="large"
            />
          </Form.Item>

          <div style={{ display: 'flex', gap: '12px' }}>
            <Form.Item
              name="major"
              label="Major (Optional)"
              style={{ flex: 1 }}
            >
              <Input 
                placeholder="e.g., Computer Science"
                size="large"
              />
            </Form.Item>

            <Form.Item
              name="graduationYear"
              label="Graduation Year (Optional)"
              style={{ flex: 1 }}
            >
              <InputNumber 
                placeholder="2025"
                size="large"
                style={{ width: '100%' }}
                min={2020}
                max={2030}
              />
            </Form.Item>
          </div>

          <Form.Item>
            <Button 
              type="primary" 
              htmlType="submit" 
              size="large"
              loading={loading}
              style={{ width: '100%' }}
            >
              Create Account
            </Button>
          </Form.Item>
        </Form>

        <div style={{ textAlign: 'center' }}>
          <p>
            Already have an account? <Link to="/login">Sign in</Link>
          </p>
        </div>
      </Card>
    </div>
  );
};

export default RegisterPage;