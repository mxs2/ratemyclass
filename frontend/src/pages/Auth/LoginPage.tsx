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

  // Obtém URL de retorno dos parâmetros da URL
  const returnUrl = searchParams.get('returnUrl') || '/dashboard';

  const handleSubmit = async (values: { email: string; password: string; remember?: boolean }) => {
    try {
      setLoading(true);
      setError(null);
      await login(values);

      // Navega para a página de retorno ou dashboard
      navigate(returnUrl);
    } catch (err: any) {
      // Tratamento de erros de validação vindos do backend
      if (err.response?.data?.fieldErrors) {
        const backendErrors = err.response.data.fieldErrors;
        form.setFields(
          Object.entries(backendErrors).map(([field, message]) => ({
            name: field,
            errors: [message as string],
          }))
        );
      } else {
        setError(
          err.response?.data?.message ||
          err.message ||
          'Falha no login. Verifique suas credenciais.'
        );
      }
    } finally {
      setLoading(false);
    }
  };

  const fillDemoCredentials = () => {
    form.setFieldsValue({
      email: 'demo@cesar.school',
      password: 'cesar123',
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
          <Title level={2}>Bem-vindo de volta</Title>
          <p>Entre na sua conta RateMyClass</p>
        </div>

        {error && (
          <Alert
            message="Falha no login"
            description={error}
            type="error"
            showIcon
            closable
            style={{ marginBottom: 16 }}
          />
        )}

        <Alert
          message="Conta Demo"
          description={
            <Space direction="vertical" size="small" style={{ width: '100%' }}>
              <Text>Teste o app utilizando as credenciais demo:</Text>
              <Text strong>Email: demo@cesar.school</Text>
              <Text strong>Senha: cesar123</Text>
              <Button
                size="small"
                type="link"
                onClick={fillDemoCredentials}
                style={{ padding: 0 }}
              >
                Preencher automaticamente
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
              { required: true, message: 'Por favor, insira seu email!' },
              { type: 'email', message: 'Insira um email válido!' }
            ]}
          >
            <Input
              prefix={<UserOutlined />}
              placeholder="seu.email@universidade.edu"
              size="large"
            />
          </Form.Item>

          <Form.Item
            name="password"
            label="Senha"
            rules={[{ required: true, message: 'Por favor, insira sua senha!' }]}
          >
            <Input.Password
              prefix={<LockOutlined />}
              placeholder="Senha"
              size="large"
            />
          </Form.Item>

          <Form.Item name="remember" valuePropName="checked" style={{ marginBottom: 8 }}>
            <Checkbox>Lembrar-me por 30 dias</Checkbox>
          </Form.Item>

          <Form.Item>
            <Button
              type="primary"
              htmlType="submit"
              size="large"
              loading={loading}
              block
            >
              {loading ? 'Entrando...' : 'Entrar'}
            </Button>
          </Form.Item>
        </Form>

        <div style={{ textAlign: 'center' }}>
          <p>
            Não tem uma conta? <Link to="/register">Cadastre-se</Link>
          </p>
          <p>
            <Link to="/forgot-password">Esqueceu a senha?</Link>
          </p>
        </div>
      </Card>
    </div>
  );
};

export default LoginPage;
