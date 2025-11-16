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
} from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { avaliacoesUsuarios } from '../../services/api/avaliacaoApi';
import { salvarReacao } from '../../services/api/reacaoApi';
import { SampleItem } from '../../types/avaliacao';
import { useAuth } from '../../hooks/useAuth';

const { Title, Text } = Typography;
const { Option } = Select;

const FIELDS = {
  PROFESSOR: ['didatica', 'qualidadeAula', 'flexibilidade', 'organizacaoProfessor',],
  DISCIPLINA: ['dificuldade', 'metodologia', 'conteudos', 'aplicabilidade'],
  COORDENADOR: ['transparencia', 'interacaoAluno', 'suporte', 'organizacaoCoordenador',],
};

const FIELD_LABELS: Record<string, string> = {
  didatica: 'Didática',
  qualidadeAula: 'Qualidade',
  flexibilidade: 'Flexibilidade',
  organizacaoProfessor: 'Organização',

  dificuldade: 'Dificuldade',
  metodologia: 'Metodologia',
  conteudos: 'Conteúdos',
  aplicabilidade: 'Aplicabilidade',

  transparencia: 'Transparência',
  interacaoAluno: 'Interação',
  suporte: 'Suporte',
  organizacaoCoordenador: 'Organização',
};

const ICONS = {
  PROFESSOR: {
    icon: <TeamOutlined />,
    color: 'rgb(79 163 203)',
    bg: 'rgb(79 163 203 / 25%)',
  },
  DISCIPLINA: {
    icon: <BookOutlined />,
    color: 'rgb(233 174 32)',
    bg: 'rgb(255 213 120 / 30%)',
  },
  COORDENADOR: {
    icon: <StarOutlined />,
    color: 'rgb(230 86 126)',
    bg: 'rgb(242 143 174 / 25%)',
  },
};

const refactorData = (data: any[]): SampleItem[] =>
  data.map((item, index) => {
    let tipoAvaliacao = 'PROFESSOR';
    let nomeReferencia = 'Avaliação';

    if (item.disciplinaId) {
      tipoAvaliacao = 'DISCIPLINA';
      nomeReferencia = `Disciplina #${item.disciplinaId}`;
    } else if (item.coordenadorId) {
      tipoAvaliacao = 'COORDENADOR';
      nomeReferencia = `Coordenador #${item.coordenadorId}`;
    } else if (item.professorId) {
      tipoAvaliacao = 'PROFESSOR';
      nomeReferencia = `Professor #${item.professorId}`;
    }

    return {
      ...item,
      avaliacaoId: item.id || item.avaliacaoId,
      tipoAvaliacao: item.tipoAvaliacao || tipoAvaliacao,
      nomeReferencia: item.nomeReferencia || nomeReferencia,
      userReaction: item.userReaction ?? null,
      uid: `${item.id || item.avaliacaoId}-${index}`,
    };
  });

const DashboardPage: React.FC = () => {
  const { user } = useAuth();
  const [loading, setLoading] = useState(true);
  const [sampleItems, setSampleItems] = useState<SampleItem[]>([]);
  const [filter, setFilter] = useState<string>('todos');

  const primeiroNome = user?.firstName?.split(' ')[0] || '';

  const fetchEvaluations = async () => {
    setLoading(true);
    try {
      const data = await avaliacoesUsuarios.listarAvaliacoes();
      const refactored = refactorData(data);
      const sorted = refactored.sort((a, b) => {
        const dateA = a.dataAvaliacao || a.createdAt || a.timestamp || a.data;
        const dateB = b.dataAvaliacao || b.createdAt || b.timestamp || b.data;
        
        if (dateA && dateB) {
          return new Date(dateB).getTime() - new Date(dateA).getTime();
        }
        
        const idA = a.id || a.avaliacaoId || 0;
        const idB = b.id || b.avaliacaoId || 0;
        return idB - idA;
      });
      setSampleItems(sorted);
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

  const handleReaction = async (avaliacao: SampleItem, tipo: 'LIKE' | 'DISLIKE') => {
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
              newLikes--;
              newReaction = null;
            } else {
              newLikes++;
              if (alreadyDisliked) newDislikes--;
              newReaction = 'LIKE';
            }
          } else {
            if (alreadyDisliked) {
              newDislikes--;
              newReaction = null;
            } else {
              newDislikes++;
              if (alreadyLiked) newLikes--;
              newReaction = 'DISLIKE';
            }
          }

          return { ...item, likes: newLikes, deslikes: newDislikes, userReaction: newReaction };
        })
      );

      await salvarReacao.cadastrarReacao({
        avaliacaoId: avaliacao.avaliacaoId,
        tipoAvaliacao: avaliacao.tipoAvaliacao,
        tipoReacao: tipo,
      });
    } catch {
      message.error('Não foi possível registrar sua reação.');
    }
  };

  const getButtonStyle = (item: SampleItem, tipo: 'LIKE' | 'DISLIKE') => {
    const isActive = item.userReaction === tipo;
    const baseColor = tipo === 'LIKE' ? '#52c41a' : '#f5222d';
    const bg = tipo === 'LIKE' ? '#7edb7e49' : '#8f2f2f38';

    return {
      backgroundColor: isActive ? bg : undefined,
      color: baseColor,
      borderRadius: 6,
      padding: '2px 8px',
      transition: '0.25s',
    };
  };

  const filteredSampleItems = sampleItems.filter(item => {
    if (filter === 'todos') return true;
    return item.tipoAvaliacao.toLowerCase() === filter.toLowerCase();
  });

  const renderEvaluationItem = (item: SampleItem) => {
    const fields = FIELDS[item.tipoAvaliacao as keyof typeof FIELDS];
    const iconData = ICONS[item.tipoAvaliacao as keyof typeof ICONS];

    return (
      <List.Item key={item.uid}>
        <List.Item.Meta
          avatar={
            <Avatar
              icon={iconData.icon}
              style={{ background: iconData.bg, color: iconData.color }}
            />
          }
          title={<Text strong>{item.nomeReferencia}</Text>}
          description={
            <>
              <div style={{ color: '#444', fontWeight: 500 }}>{item.comentario}</div>

              <div
                style={{
                  display: 'flex',
                  flexWrap: 'wrap',
                  gap: 16,
                  fontSize: 13,
                  marginTop: 6
                }}
              >
                {fields.map(key => (
                  <Text type="secondary" key={key} style={{ color: '#888888ff' }}>
                    {`${FIELD_LABELS[key] ?? key}: ${item[key] ?? '-'}`}
                  </Text>
                ))}
              </div>

              <div style={{ marginTop: 6 }}>
                <Button
                  type="text"
                  icon={<LikeOutlined />}
                  style={getButtonStyle(item, 'LIKE')}
                  onClick={() => handleReaction(item, 'LIKE')}
                >
                  {item.likes}
                </Button>
                <Button
                  type="text"
                  icon={<DislikeOutlined />}
                  style={getButtonStyle(item, 'DISLIKE')}
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
  };

  return (
    <div style={{ background: '#f5f7fa', padding: 16, minHeight: '80vh' }}>
      <Row gutter={16}>
        <Col xs={24} lg={4}>
          <Card style={{ borderRadius: 8, height: '100%' }}>
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
                      title={<Link to="/minhas-avaliacoes/professor">Professor</Link>}
                    />
                  </List.Item>
                  <List.Item>
                    <List.Item.Meta
                      title={<Link to="/minhas-avaliacoes/disciplina">Disciplina</Link>}
                    />
                  </List.Item>
                  <List.Item>
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
                <Title level={4} style={{ margin: 0 }}>
                  Seja bem-vindo (a){primeiroNome && `, ${primeiroNome}`}
                </Title>
                <Text type="secondary">
                  Use o painel abaixo para cadastrar e visualizar avaliações
                </Text>
              </div>
              <Space>
                <Button type="link">Filtro</Button>
                <Select 
                  value={filter} 
                  onChange={setFilter} 
                  style={{ width: 160 }}
                >
                  <Option value="todos">Todos</Option>
                  <Option value="professor">Professor</Option>
                  <Option value="disciplina">Disciplina</Option>
                  <Option value="coordenador">Coordenador</Option>
                </Select>
              </Space>
            </div>

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
                  {[
                    {
                      link: '/avaliar-professor',
                      title: 'Avaliar professor',
                      desc: 'Cursos, didática e organização',
                      ...ICONS.PROFESSOR,
                    },
                    {
                      link: '/avaliar-disciplina',
                      title: 'Avaliar disciplina',
                      desc: 'Ementa, carga e avaliação',
                      ...ICONS.DISCIPLINA,
                    },
                    {
                      link: '/avaliar-coordenador',
                      title: 'Avaliar coordenador',
                      desc: 'Gestão e comunicação',
                      ...ICONS.COORDENADOR,
                    },
                  ].map(card => (
                    <Link to={card.link} key={card.title}>
                      <Card
                        hoverable
                        styles={{ body: { padding: 16 } }}
                        style={{
                          borderRadius: 12,
                          backgroundColor: '#f8f9fa',
                        }}
                      >
                        <div style={{ display: 'flex', alignItems: 'center' }}>
                          <Avatar icon={card.icon} style={{ background: card.bg, color: card.color, marginRight: 12 }} />
                          <div>
                            <Text strong>{card.title}</Text>
                            <div style={{ color: '#888', fontSize: 12 }}>{card.desc}</div>
                          </div>
                        </div>
                      </Card>
                    </Link>
                  ))}
                </div>
              </Card>

              <Card type="inner" title="Todas as Avaliações">
                <List
                  loading={loading}
                  itemLayout="vertical"
                  dataSource={filteredSampleItems}
                  renderItem={renderEvaluationItem}
                />
              </Card>
            </div>
          </Card>
        </Col>

        <Col xs={24} lg={6}>
          <Card style={{ borderRadius: 8 }}>
            <Title level={5}>Seus dados de rating</Title>
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: 12 }}>
              {['Professores', 'Disciplinas', 'Coordenador'].map((label, i) => {
                const tipos = ['PROFESSOR', 'DISCIPLINA', 'COORDENADOR'];
                const count = sampleItems.filter(item => item.tipoAvaliacao === tipos[i]).length;
                return (
                  <div key={label}>
                    <Text type="secondary">{label}</Text>
                    <div style={{ fontSize: 18, fontWeight: 600 }}>{count}</div>
                  </div>
                );
              })}
            </div>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default DashboardPage;
