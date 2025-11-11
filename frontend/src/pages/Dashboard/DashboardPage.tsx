import React, { useEffect, useState } from 'react';
import {
  Row,
  Col,
  Card,
  Typography,
  List,
  Avatar,
  Button,
  Space,
  Select,
  message,
} from 'antd';
import {
  StarOutlined,
  TeamOutlined,
  BookOutlined,
  UserOutlined,
  LikeOutlined,
  DislikeOutlined,
  CalendarOutlined,
} from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { avaliacoesUsuarios } from '../../services/api/avaliacaoApi';
import { salvarReacao } from '../../services/api/reacaoApi';

import { Avaliacao, SampleItem } from '../../types/avaliacao';

const { Title, Text } = Typography;
const { Option } = Select;

const fieldsEvaluation = {
  PROFESSOR: {
    didatica: null,
    qualidadeAula: null,
    flexibilidade: null,
    organizacaoProfessor: null,
    comentario: null,
    avaliacaoId: null,
    nomeReferencia: null,
    likes: null,
    deslikes: null,
    tipoAvaliacao: 'PROFESSOR',
  },
  DISCIPLINA: {
    dificuldade: null,
    metodologia: null,
    conteudos: null,
    aplicabilidade: null,
    comentario: null,
    avaliacaoId: null,
    nomeReferencia: null,
    likes: null,
    deslikes: null,
    tipoAvaliacao: 'DISCIPLINA',
  },
  COORDENADOR: {
    transparencia: null,
    interacaoAluno: null,
    suporte: null,
    organizacaoCoordenador: null,
    comentario: null,
    avaliacaoId: null,
    nomeReferencia: null,
    likes: null,
    deslikes: null,
    tipoAvaliacao: 'COORDENADOR',
  },
};

const refactorData = (data: Avaliacao[]): SampleItem[] => {
  return data
    .map((item) => {
      const base = fieldsEvaluation[item.tipoAvaliacao];
      if (!base) return null;

      const campos = Object.keys(base).reduce((acc, key) => {
        (acc as any)[key] = (item as any)[key] ?? (base as any)[key];
        return acc;
      }, {} as Record<string, any>);

      return {
        ...campos,
        userReaction: item.userReaction ?? null
      };
    })
    .filter(Boolean) as SampleItem[];
};

const DashboardPage: React.FC = () => {
  const [loading, setLoading] = useState(true);
  const [sampleItems, setSampleItems] = useState<SampleItem[]>([]);

  const fetchEvaluations = async () => {
    setLoading(true);
    try {
      const data = await avaliacoesUsuarios.listarAvaliacoes();
      const formatted = refactorData(data).map((item, index) => ({
        ...item,
        uid: `${item.avaliacaoId}-${index}`
      }));

      setSampleItems(formatted);
    } catch (err) {
      console.error(err);
      message.error('Não foi possível carregar as avaliações dos alunos.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchEvaluations();
  }, []);

  const handleReaction = async (avaliacao: object, tipo: 'LIKE' | 'DISLIKE') => {
    console.log('avaliacao', avaliacao);
    try {
      setSampleItems(prev =>
        prev.map(item => {
          if (item.uid !== avaliacao.uid) return item;

          const alreadyLiked = item.userReaction === 'LIKE';
          const alreadyDisliked = item.userReaction === 'DISLIKE';

          let newLikes = item.likes ?? 0;
          let newDislikes = item.deslikes ?? 0;
          let newReaction: 'LIKE' | 'DISLIKE' | null = null;

          if (tipo === 'LIKE') {
            if (alreadyLiked) {
              newLikes -= 1;
              newReaction = null;
            } else {
              newLikes += 1;
              if (alreadyDisliked) newDislikes -= 1;
              newReaction = 'LIKE';
            }
          } else {
            if (alreadyDisliked) {
              newDislikes -= 1;
              newReaction = null;
            } else {
              newDislikes += 1;
              if (alreadyLiked) newLikes -= 1;
              newReaction = 'DISLIKE';
            }
          }

          return {
            ...item,
            likes: newLikes,
            deslikes: newDislikes,
            userReaction: newReaction
          };
        })
      );

      console.log('chamaando');
      const payload = {
        avaliacaoId: avaliacao?.avaliacaoId,
        tipoAvaliacao: avaliacao?.tipoAvaliacao,
        tipoReacao: tipo
      }
      console.log('payload', payload);

      await salvarReacao.cadastrarReacao(payload);

    } catch {
      message.error("Não foi possível registrar sua reação.");
    }
  };

  const getButtonStyle = (item: SampleItem, tipo: 'LIKE' | 'DISLIKE') => {
    const isSelected = item.userReaction === tipo;

    if (isSelected) {
      return {
        backgroundColor: tipo === 'LIKE' ? '#7edb7e49' : '#8f2f2f38',
        color: '#fff',
        borderRadius: 6,
        padding: '2px 8px',
        transition: '0.25s',
      };
    }

    return {
      color: '#555',
      borderRadius: 6,
      padding: '2px 8px',
      transition: '0.25s',
    };
  };

  /** === Renderizador dinâmico conforme tipoAvaliacao === **/
  const renderEvaluationItem = (item: SampleItem) => {

    const commonStyle = { color: '#444', fontWeight: 500 };
    const metricsStyle = {
      display: 'flex',
      gap: 16,
      color: '#888',
      fontSize: 13,
      marginTop: 6,
      flexWrap: 'wrap',
    };

    switch (item.tipoAvaliacao) {
      case 'PROFESSOR':
        return (
          <List.Item key={item.uid}>
            <List.Item.Meta
              avatar={
                <Avatar
                  icon={<TeamOutlined />}
                  style={{
                    background: 'rgb(79 163 203 / 25%)',
                    color: 'rgb(79 163 203)',
                  }}
                />
              }
              title={<Text strong>{item.nomeReferencia}</Text>}
              description={
                <>
                  <div style={commonStyle}>{item.comentario}</div>
                  <div style={metricsStyle}>
                    <Text type="secondary">Didática: {item.didatica}</Text>
                    <Text type="secondary">
                      Qualidade: {item.qualidadeAula}
                    </Text>
                    <Text type="secondary">
                      Flexibilidade: {item.flexibilidade}
                    </Text>
                    <Text type="secondary">
                      Organização: {item.organizacaoProfessor}
                    </Text>
                  </div>
                  <div style={{ marginTop: 6 }}>
                    <Button
                      type="text"
                      icon={<LikeOutlined />}
                      style={{
                        ...getButtonStyle(item, 'LIKE'),
                        color: '#52c41a'
                      }}
                      onClick={() => handleReaction(item, 'LIKE')}
                    >
                      {item.likes}
                    </Button>
                    <Button
                      type="text"
                      icon={<DislikeOutlined />}
                      style={{
                        ...getButtonStyle(item, 'DISLIKE'),
                        color: '#f5222d'
                      }}
                      onClick={() => handleReaction(item, 'DISLIKE')}
                    >
                      {item.deslikes}
                    </Button>
                  </div>
                </>
              }
            />
          </List.Item>
        );

      case 'DISCIPLINA':
        return (
          <List.Item key={item.uid}>
            <List.Item.Meta
              avatar={
                <Avatar
                  icon={<BookOutlined />}
                  style={{
                    background: 'rgb(255 213 120 / 30%)',
                    color: 'rgb(233 174 32)',
                  }}
                />
              }
              title={<Text strong>{item.nomeReferencia}</Text>}
              description={
                <>
                  <div style={commonStyle}>{item.comentario}</div>
                  <div style={metricsStyle}>
                    <Text type="secondary">
                      Dificuldade: {item.dificuldade}
                    </Text>
                    <Text type="secondary">
                      Metodologia: {item.metodologia}
                    </Text>
                    <Text type="secondary">
                      Conteúdos: {item.conteudos}
                    </Text>
                    <Text type="secondary">
                      Aplicabilidade: {item.aplicabilidade}
                    </Text>
                  </div>
                  <div style={{ marginTop: 6 }}>
                    <Button
                      type="text"
                      icon={<LikeOutlined />}
                      style={{
                        ...getButtonStyle(item, 'LIKE'),
                        color: '#52c41a'
                      }}
                      onClick={() => handleReaction(item, 'LIKE')}
                    >
                      {item.likes}
                    </Button>
                    <Button
                      type="text"
                      icon={<DislikeOutlined />}
                      style={{
                        ...getButtonStyle(item, 'DISLIKE'),
                        color: '#f5222d'
                      }}
                      onClick={() => handleReaction(item, 'DISLIKE')}
                    >
                      {item.deslikes}
                    </Button>
                  </div>
                </>
              }
            />
          </List.Item>
        );

      case 'COORDENADOR':
        return (
          <List.Item key={item.uid}>
            <List.Item.Meta
              avatar={
                <Avatar
                  icon={<StarOutlined />}
                  style={{
                    background: 'rgb(242 143 174 / 25%)',
                    color: 'rgb(230 86 126)',
                  }}
                />
              }
              title={<Text strong>{item.nomeReferencia}</Text>}
              description={
                <>
                  <div style={commonStyle}>{item.comentario}</div>
                  <div style={metricsStyle}>
                    <Text type="secondary">
                      Transparência: {item.transparencia}
                    </Text>
                    <Text type="secondary">
                      Interação: {item.interacaoAluno}
                    </Text>
                    <Text type="secondary">Suporte: {item.suporte}</Text>
                    <Text type="secondary">
                      Organização: {item.organizacaoCoordenador}
                    </Text>
                  </div>
                  <div style={{ marginTop: 6 }}>
                    <Button
                      type="text"
                      icon={<LikeOutlined />}
                      style={{
                        ...getButtonStyle(item, 'LIKE'),
                        color: '#52c41a'
                      }}
                      onClick={() => handleReaction(item, 'LIKE')}
                    >
                      {item.likes}
                    </Button>
                    <Button
                      type="text"
                      icon={<DislikeOutlined />}
                      style={{
                        ...getButtonStyle(item, 'DISLIKE'),
                        color: '#f5222d'
                      }}
                      onClick={() => handleReaction(item, 'DISLIKE')}
                    >
                      {item.deslikes}
                    </Button>
                  </div>
                </>
              }
            />
          </List.Item>
        );

      default:
        return null;
    }
  };

  return (
    <div style={{ background: '#f5f7fa', padding: 16, minHeight: '80vh' }}>
      <Row gutter={16}>
        {/* --- Coluna lateral --- */}
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

              <List.Item>
                <List.Item.Meta
                  avatar={<Avatar icon={<StarOutlined />} />}
                  title={<Text strong>Minhas avaliações</Text>}
                />
              </List.Item>

              <div style={{ marginLeft: 48, marginTop: -8 }}>
                <List size="small">
                  <List.Item>
                    <List.Item.Meta
                      title={
                        <Link to="/minhas-avaliacoes/professor">
                          Professor
                        </Link>
                      }
                    />
                  </List.Item>
                  <List.Item>
                    <List.Item.Meta
                      title={
                        <Link to="/minhas-avaliacoes/disciplina">
                          Disciplina
                        </Link>
                      }
                    />
                  </List.Item>
                  <List.Item>
                    <List.Item.Meta
                      title={
                        <Link to="/minhas-avaliacoes/coordenador">
                          Coordenador
                        </Link>
                      }
                    />
                  </List.Item>
                </List>
              </div>
            </List>
          </Card>
        </Col>

        {/* --- Coluna central --- */}
        <Col xs={24} lg={14}>
          <Card style={{ borderRadius: 8 }}>
            {/* Header */}
            <div
              style={{
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
              }}
            >
              <div>
                <Title level={4} style={{ margin: 0 }}>
                  Seja bem-vindo (a)
                </Title>
                <Text type="secondary">
                  Use o painel abaixo para cadastrar e visualizar avaliações
                </Text>
              </div>
              <Space>
                <Button type="link">Filtros</Button>
                <Select defaultValue="todos" style={{ width: 160 }}>
                  <Option value="todos">Todos</Option>
                  <Option value="professor">Professor</Option>
                  <Option value="disciplina">Disciplina</Option>
                  <Option value="coordenador">Coordenador</Option>
                </Select>
              </Space>
            </div>

            {/* Cadastrar avaliações */}
            <div style={{ marginTop: 16 }}>
              <Card type="inner" title="Cadastrar avaliações" style={{ marginBottom: 16 }}>
                <div
                  style={{
                    display: 'grid',
                    gridTemplateColumns: 'repeat(auto-fit, minmax(220px, 1fr))',
                    gap: '16px',
                    marginTop: '8px',
                  }}
                >
                  <Link to="/avaliar-professor">
                    <Card hoverable bodyStyle={{ padding: 16 }} style={{ borderRadius: 12, backgroundColor: '#f8f9fa' }}>
                      <div style={{ display: 'flex', alignItems: 'center' }}>
                        <Avatar
                          icon={<TeamOutlined />}
                          style={{
                            background: 'rgb(79 163 203 / 25%)',
                            color: 'rgb(79 163 203)',
                            marginRight: 12,
                          }}
                        />
                        <div>
                          <Text strong>Avaliar professor</Text>
                          <div style={{ color: '#888', fontSize: 12 }}>
                            Cursos, didática e organização
                          </div>
                        </div>
                      </div>
                    </Card>
                  </Link>

                  <Link to="/avaliar-disciplina">
                    <Card hoverable bodyStyle={{ padding: 16 }} style={{ borderRadius: 12, backgroundColor: '#f8f9fa' }}>
                      <div style={{ display: 'flex', alignItems: 'center' }}>
                        <Avatar
                          icon={<BookOutlined />}
                          style={{
                            background: 'rgb(255 213 120 / 30%)',
                            color: 'rgb(233 174 32)',
                            marginRight: 12,
                          }}
                        />
                        <div>
                          <Text strong>Avaliar disciplina</Text>
                          <div style={{ color: '#888', fontSize: 12 }}>
                            Ementa, carga e avaliação
                          </div>
                        </div>
                      </div>
                    </Card>
                  </Link>

                  <Link to="/avaliar-coordenador">
                    <Card hoverable bodyStyle={{ padding: 16 }} style={{ borderRadius: 12, backgroundColor: '#f8f9fa' }}>
                      <div style={{ display: 'flex', alignItems: 'center' }}>
                        <Avatar
                          icon={<StarOutlined />}
                          style={{
                            background: 'rgb(242 143 174 / 25%)',
                            color: 'rgb(230 86 126)',
                            marginRight: 12,
                          }}
                        />
                        <div>
                          <Text strong>Avaliar coordenador</Text>
                          <div style={{ color: '#888', fontSize: 12 }}>
                            Gestão, transparência e respostas
                          </div>
                        </div>
                      </div>
                    </Card>
                  </Link>
                </div>
              </Card>

              {/* === LISTA DE AVALIAÇÕES DINÂMICA === */}
              <Card type="inner" title="Todas as Avaliações">
                <List
                  loading={loading}
                  itemLayout="vertical"
                  dataSource={sampleItems}
                  renderItem={renderEvaluationItem}
                />
              </Card>
            </div>
          </Card>
        </Col>

        {/* --- Coluna direita --- */}
        <Col xs={24} lg={6}>
          <Card style={{ borderRadius: 8 }}>
            <Title level={5}>Minhas avaliações</Title>
            <div
              style={{
                display: 'flex',
                justifyContent: 'space-between',
                marginBottom: 12,
              }}
            >
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
      </Row>
    </div>
  );
};

export default DashboardPage;
