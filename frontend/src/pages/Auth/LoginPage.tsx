import React from 'react';
import { Card, Form, Input, Button, Typography, Alert, Checkbox, Space } from 'antd';
import { UserOutlined, LockOutlined, InfoCircleOutlined } from '@ant-design/icons';
import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';

const { Title, Text } = Typography;

const LoginPage: React.FC = () => {
  const [form] = Form.useForm();
  const { login } = useAuth();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const [loading, setLoading] = React.useState(false);
  const [error, setError] = React.useState<string | null>(null);

  // Get return URL from query params
  const returnUrl = searchParams.get('returnUrl') || '/dashboard';

  const handleSubmit = async (values: { email: string; password: string; remember?: boolean }) => {
    try {
      setLoading(true);
      setError(null);
      await login(values);

      // Navigate to return URL or dashboard
      navigate(returnUrl);
    } catch (err: any) {
      // Handle validation errors from backend
      if (err.response?.data?.fieldErrors) {
        const backendErrors = err.response.data.fieldErrors;
        form.setFields(
          Object.entries(backendErrors).map(([field, message]) => ({
            name: field,
            errors: [message as string],
          }))
        );
      } else {
        setError(err.response?.data?.message || err.message || 'Login failed. Please check your credentials.');
      }
    } finally {
      setLoading(false);
    }
  };

  const fillDemoCredentials = () => {
    form.setFieldsValue({
      email: 'demo@university.edu',
      password: 'Demo123!',
    });
  };

  return (
    <div style={{
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      minHeight: '80vh',
      padding: '20px'
    }}>
      <Card style={{ width: '100%', maxWidth: 400 }}>
        <div style={{ textAlign: 'center', marginBottom: 24 }}>
          <Title level={2}>Welcome Back</Title>
          <p>Sign in to your RateMyClass account</p>
        </div>

        {error && (
          <Alert
            message="Login Failed"
            description={error}
            type="error"
            showIcon
            closable
            style={{ marginBottom: 16 }}
          />
        )}

        <Alert
          message="Demo Account"
          description={
            <Space direction="vertical" size="small" style={{ width: '100%' }}>
              <Text>Test the app with demo credentials:</Text>
              <Text strong>Email: demo@university.edu</Text>
              <Text strong>Password: Demo123!</Text>
              <Button
                size="small"
                type="link"
                onClick={fillDemoCredentials}
                style={{ padding: 0 }}
              >
                Click to auto-fill
              </Button>
            </Space>
          }
          type="info"
          icon={<InfoCircleOutlined />}
          showIcon
          style={{ marginBottom: 16 }}
        />

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
              prefix={<UserOutlined />}
              placeholder="your.email@university.edu"
              size="large"
            />
          </Form.Item>

          <Form.Item
            name="password"
            label="Password"
            rules={[{ required: true, message: 'Please enter your password!' }]}
          >
            <Input.Password
              prefix={<LockOutlined />}
              placeholder="Password"
              size="large"
            />
          </Form.Item>

          <Form.Item name="remember" valuePropName="checked" style={{ marginBottom: 8 }}>
            <Checkbox>Remember me for 30 days</Checkbox>
          </Form.Item>

          <Form.Item>
            <Button
              type="primary"
              htmlType="submit"
              size="large"
              loading={loading}
              block
            >
              {loading ? 'Signing in...' : 'Sign In'}
            </Button>
          </Form.Item>
        </Form>

        <div style={{ textAlign: 'center' }}>
          <p>
            Don&apos;t have an account? <Link to="/register">Sign up</Link>
          </p>
          <p>
            <Link to="/forgot-password">Forgot your password?</Link>
          </p>
        </div>
      </Card>
    </div>
  );
};

export default LoginPage;