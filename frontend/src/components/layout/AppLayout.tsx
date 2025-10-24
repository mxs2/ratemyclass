import { Layout, Menu, Avatar, Dropdown, Button, Space } from 'antd';
import { UserOutlined, LogoutOutlined, HomeOutlined } from '@ant-design/icons';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import type { MenuProps } from 'antd';
import type { ReactNode } from 'react';

const { Header, Content, Footer } = Layout;

interface AppLayoutProps {
  children: ReactNode;
}

// Main menu items - defined outside component for better performance
const mainMenuItems: MenuProps['items'] = [
  {
    key: '/',
    icon: <HomeOutlined />,
    label: <Link to="/dashboard">Dashboard</Link>,
  },
  // {
  //   key: '/professors',
  //   icon: <UserOutlined />,
  //   label: <Link to="/professors">Professors</Link>,
  // }
];

const AppLayout = ({ children }: AppLayoutProps) => {
  const { user, logout, isAuthenticated } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  // User menu items - needs to be inside component to access navigate and handleLogout
  const userMenuItems: MenuProps['items'] = [
    {
      key: 'profile',
      icon: <UserOutlined />,
      label: 'Profile',
      onClick: () => navigate('/dashboard'),
    },
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: 'Logout',
      onClick: handleLogout,
    },
  ];

  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Header style={{ padding: '0 24px', background: '#fff', boxShadow: '0 1px 4px rgba(0,21,41,.08)' }}>
        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', height: '100%' }}>
          <div style={{ display: 'flex', alignItems: 'center' }}>
            <Link to="/" style={{ textDecoration: 'none' }}>
              <h1 style={{ color: '#1890ff', margin: 0, fontSize: '24px', fontWeight: 'bold' }}>
                RateMyClass
              </h1>
            </Link>
          </div>

          <Menu
            mode="horizontal"
            selectedKeys={[location.pathname]}
            items={mainMenuItems}
            style={{
              flex: 1,
              marginLeft: 48,
              border: 'none',
              background: 'transparent'
            }}
          />

          <div>
            {isAuthenticated ? (
              <Space>
                {/* <Button type="primary" onClick={() => navigate('/dashboard')}>
                  Dashboard
                </Button> */}
                <Dropdown menu={{ items: userMenuItems }} placement="bottomRight">
                  <Avatar
                    style={{ backgroundColor: '#1890ff', cursor: 'pointer' }}
                    icon={<UserOutlined />}
                  >
                    {user?.firstName?.[0]}{user?.lastName?.[0]}
                  </Avatar>
                </Dropdown>
              </Space>
            ) : (
              <Space>
                <Button onClick={() => navigate('/login')}>
                  Login
                </Button>
                <Button type="primary" onClick={() => navigate('/register')}>
                  Sign Up
                </Button>
              </Space>
            )}
          </div>
        </div>
      </Header>

      <Content style={{ padding: '24px', flex: 1 }}>
        {children}
      </Content>

      <Footer style={{ textAlign: 'center', background: '#f0f2f5' }}>
        RateMyClass ©{new Date().getFullYear()} - Helping students make informed decisions
      </Footer>
    </Layout>
  );
};

export default AppLayout;