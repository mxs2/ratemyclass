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

      // Remove confirmPassword antes de enviar ao backend
      // eslint-disable-next-line @typescript-eslint/no-unused-vars, no-unused-vars
      const { confirmPassword, ...registerData } = values;

      // Registra o usuário (com login automático após o registro)
      await register(registerData);

      // Redireciona para o dashboard após registro bem-sucedido
      navigate('/dashboard', {
        state: { message: 'Bem-vindo ao RateMyClass! Sua conta foi criada com sucesso.' }
      });
    } catch (err: any) {
      // Lida com erros de validação vindos do backend
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
          'Falha no registro. Por favor, tente novamente.'
        );
      }
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
          <Title level={2}>Junte-se ao RateMyClass</Title>
          <p>Crie sua conta para começar a avaliar professores e disciplinas</p>
        </div>

        {error && (
          <Alert
            message="Falha no registro"
            description={error}
            type="error"
            showIcon
            closable
            onClose={() => setError(null)}
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
              { required: true, message: 'Por favor, insira seu email!' },
              { type: 'email', message: 'Insira um email válido!' }
            ]}
          >
            <Input
              prefix={<MailOutlined />}
              placeholder="seu.email@universidade.edu"
              size="large"
            />
          </Form.Item>

          <Form.Item
            name="studentId"
            label="Matrícula"
            rules={[
              { required: true, message: 'Por favor, insira sua matrícula!' },
              { max: 20, message: 'A matrícula não pode exceder 20 caracteres!' }
            ]}
          >
            <Input
              placeholder="ex: ST12345678"
              size="large"
            />
          </Form.Item>

          <div style={{ display: 'flex', gap: '12px' }}>
            <Form.Item
              name="firstName"
              label="Nome"
              style={{ flex: 1 }}
              rules={[
                { required: true, message: 'Por favor, insira seu nome!' },
                { max: 100, message: 'Seu nome não pode exceder 100 caracteres!' }
              ]}
            >
              <Input
                prefix={<UserOutlined />}
                placeholder="João"
                size="large"
              />
            </Form.Item>

            <Form.Item
              name="lastName"
              label="Sobrenome"
              style={{ flex: 1 }}
              rules={[
                { required: true, message: 'Por favor, insira seu sobrenome!' },
                { max: 100, message: 'O sobrenome não pode exceder 100 caracteres!' }
              ]}
            >
              <Input
                placeholder="Silva"
                size="large"
              />
            </Form.Item>
          </div>

          <Form.Item
            name="password"
            label="Senha"
            rules={[
              { required: true, message: 'Por favor, insira uma senha!' },
              { min: 8, message: 'A senha deve ter pelo menos 8 caracteres!' },
              { max: 255, message: 'A senha não pode exceder 255 caracteres!' }
            ]}
          >
            <Input.Password
              prefix={<LockOutlined />}
              placeholder="Senha (mínimo 8 caracteres)"
              size="large"
            />
          </Form.Item>

          <Form.Item
            name="confirmPassword"
            label="Confirmar senha"
            dependencies={['password']}
            rules={[
              { required: true, message: 'Por favor, confirme sua senha!' },
              ({ getFieldValue }) => ({
                validator(_, value) {
                  if (!value || getFieldValue('password') === value) {
                    return Promise.resolve();
                  }
                  return Promise.reject(new Error('As senhas não coincidem!'));
                },
              }),
            ]}
          >
            <Input.Password
              prefix={<LockOutlined />}
              placeholder="Confirme sua senha"
              size="large"
            />
          </Form.Item>

          <div style={{ display: 'flex', gap: '12px' }}>
            <Form.Item
              name="major"
              label="Curso (Opcional)"
              style={{ flex: 1 }}
              rules={[
                { max: 100, message: 'O curso não pode exceder 100 caracteres!' }
              ]}
            >
              <Input
                placeholder="ex: Ciência da Computação"
                size="large"
              />
            </Form.Item>

            <Form.Item
              name="graduationYear"
              label="Ano de conclusão (Opcional)"
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
              block
            >
              {loading ? 'Criando conta...' : 'Criar conta'}
            </Button>
          </Form.Item>
        </Form>

        <div style={{ textAlign: 'center' }}>
          <p>
            Já tem uma conta? <Link to="/login">Entrar</Link>
          </p>
        </div>
      </Card>
    </div>
  );
};

export default RegisterPage;
