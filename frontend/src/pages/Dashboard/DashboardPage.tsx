import React from 'react';
import { Row, Col, Card, Typography, List, Avatar, Button, Space, Select } from 'antd';
import { StarOutlined, TeamOutlined, BookOutlined, UserOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';

const { Title, Text } = Typography;
const { Option } = Select;

const sampleItems = [
  { id: 1, title: 'Aluno avaliou a disciplina Programação Orientada a Objetos', subtitle: 'há 7 dias' },
  { id: 2, title: 'Aluno avaliou a disciplina Estruturas de Dados', subtitle: 'há 12 dias' },
  { id: 3, title: 'Aluno avaliou o professor João Silva', subtitle: 'há 20 dias' },
];

const DashboardPage: React.FC = () => {
  return (
    <div style={{ background: '#f5f7fa', padding: 16, minHeight: '80vh' }}>
      <Row gutter={16}>
        <Col xs={24} lg={4}>
          <Card bordered={false} style={{ borderRadius: 8, height: '100%' }}>
            <List itemLayout="horizontal">
              <List.Item>
                <List.Item.Meta
                  avatar={<Avatar icon={<UserOutlined />} />}
                  title={<Text strong>Perfil</Text>}
                  description="Seus dados e configurações"
                />
              </List.Item>

              {/* === Minhas Avaliações com subdivisão === */}
              <List.Item>
                <List.Item.Meta
                  avatar={<Avatar icon={<StarOutlined />} />}
                  title={<Text strong>Minhas avaliações</Text>}
                />
              </List.Item>

              <div style={{ marginLeft: 48, marginTop: -8 }}>
                <List size="small">
                  <List.Item style={{ paddingLeft: 0, paddingRight: 0, paddingTop: 20 }}>
                    <List.Item.Meta
                      title={<Link to="/minhas-avaliacoes/professor">Professor</Link>}
                    />
                  </List.Item>
                  <List.Item style={{ paddingLeft: 0, paddingRight: 0, paddingTop: 20 }}>
                    <List.Item.Meta
                      title={<Link to="/minhas-avaliacoes/disciplina">Disciplina</Link>}
                    />
                  </List.Item>
                  <List.Item style={{ paddingLeft: 0, paddingRight: 0, paddingTop: 20 }}>
                    <List.Item.Meta
                      title={<Link to="/minhas-avaliacoes/coordenador">Coordenador</Link>}
                    />
                  </List.Item>
                </List>
              </div>
            </List>
          </Card>
        </Col>

        <Col xs={24} lg={14}>
          <Card style={{ borderRadius: 8 }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
              <div>
                <Title level={4} style={{ margin: 0 }}>Seja bem-vindo (a)</Title>
                <Text type="secondary">Use o painel abaixo para cadastrar e visualizar avaliações</Text>
              </div>
              <Space>
                <Button type="link" onClick={() => { /* placeholder */ }}>Filtros</Button>
                <Select defaultValue="todos" style={{ width: 160 }}>
                  <Option value="todos">Todos</Option>
                  <Option value="professor">Professor</Option>
                  <Option value="disciplina">Disciplina</Option>
                  <Option value="coordenador">Coordenador</Option>
                </Select>
              </Space>
            </div>

            <div style={{ marginTop: 16 }}>
              <Card type="inner" title="Cadastrar avaliações" style={{ marginBottom: 16 }}>
                {/* Blocos de avaliação */}
                <div
                  style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fit, minmax(220px, 1fr))',
                    gap: '16px',
                    marginTop: '8px',
                  }}
                >
                  <Link to="/avaliar-professor">
                    <Card
                      hoverable
                      style={{
                        textAlign: 'left',
                        backgroundColor: '#f8f9fa',
                        borderRadius: 12,
                        transition: 'background-color 0.2s ease',
                      }}
                      bodyStyle={{ padding: '16px' }}
                    >
                      <div style={{ display: 'flex', alignItems: 'center' }}>
                        {/* Cor escurecida: azul professor */}
                        <Avatar icon={<TeamOutlined />} style={{ background: 'rgb(79 163 203 / 25%)', color: 'rgb(79 163 203)', marginRight: 12 }} />
                        <div>
                          <Text strong>Avaliar professor</Text>
                          <div style={{ color: '#888', fontSize: 12 }}>Cursos, didática e organização</div>
                        </div>
                      </div>
                    </Card>
                  </Link>

                  <Link to="/avaliar-disciplina">
                    <Card
                      hoverable
                      style={{
                        textAlign: 'left',
                        backgroundColor: '#f8f9fa',
                        borderRadius: 12,
                        transition: 'background-color 0.2s ease',
                      }}
                      bodyStyle={{ padding: '16px' }}
                    >
                      <div style={{ display: 'flex', alignItems: 'center' }}>
                        {/* Cor escurecida: amarelo disciplina */}
                        <Avatar icon={<BookOutlined />} style={{ background: 'rgb(255 213 120 / 30%)', color: 'rgb(233 174 32)', marginRight: 12 }} />
                        <div>
                          <Text strong>Avaliar disciplina</Text>
                          <div style={{ color: '#888', fontSize: 12 }}>Ementa, carga e avaliação</div>
                        </div>
                      </div>
                    </Card>
                  </Link>

                  <Link to="/avaliar-coordenador">
                    <Card
                      hoverable
                      style={{
                        textAlign: 'left',
                        backgroundColor: '#f8f9fa',
                        borderRadius: 12,
                        transition: 'background-color 0.2s ease',
                      }}
                      bodyStyle={{ padding: '16px' }}
                    >
                      <div style={{ display: 'flex', alignItems: 'center' }}>
                        {/* Cor escurecida: rosa coordenador */}
                        <Avatar icon={<StarOutlined />} style={{ background: 'rgb(242 143 174 / 25%)', color: 'rgb(230 86 126)', marginRight: 12 }} />
                        <div>
                          <Text strong>Avaliar coordenador</Text>
                          <div style={{ color: '#888', fontSize: 12 }}>Gestão, transparência e respostas</div>
                        </div>
                      </div>
                    </Card>
                  </Link>
                </div>
              </Card>

              <Card type="inner">
                <List
                  itemLayout="horizontal"
                  dataSource={sampleItems}
                  renderItem={item => (
                    <List.Item key={item.id}>
                      <List.Item.Meta
                        avatar={<Avatar icon={<UserOutlined />} />}
                        title={item.title}
                        description={item.subtitle}
                      />
                    </List.Item>
                  )}
                />
              </Card>
            </div>
          </Card>
        </Col>

        <Col xs={24} lg={6}>
          <Card style={{ borderRadius: 8 }}>
            <Title level={5}>Minhas avaliações</Title>
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: 12 }}>
              <div>
                <Text type="secondary">Professores</Text>
                <div style={{ fontSize: 18, fontWeight: 600 }}>10</div>
              </div>
              <div>
                <Text type="secondary">Disciplinas</Text>
                <div style={{ fontSize: 18, fontWeight: 600 }}>15</div>
              </div>
              <div>
                <Text type="secondary">Coordenador</Text>
                <div style={{ fontSize: 18, fontWeight: 600 }}>3</div>
              </div>
            </div>
            <Button type="link">Ver histórico</Button>
          </Card>
        </Col>
      </Row >
    </div >
  );
};

export default DashboardPage;
