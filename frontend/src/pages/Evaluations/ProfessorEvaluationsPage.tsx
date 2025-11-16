import React, { useEffect, useState } from 'react';
import { Card, List, Avatar, Tooltip, Popconfirm, message, Spin, Typography } from 'antd';
import { DeleteOutlined, EditOutlined, CalendarOutlined, UserOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { avaliacaoApi } from '../../services/api/avaliacaoApi';

const { Title, Text } = Typography;

interface Evaluation {
    id: number;
    professorId: number;
    didatica: number;
    qualidadeAula: number;
    flexibilidade: number;
    organizacao: number;
    comentario: string;
    visibilidade: string;
    active: boolean;
    createdAt: string;
}

const professores = [
    { id: 1, nome: 'João Victor Tinoco de Souza' },
    { id: 2, nome: 'Eduardo Nascimento de Arruda' },
    { id: 3, nome: 'José Maurício da Silva Junior' },
    { id: 4, nome: 'Maurício da Motta Braga' },
];

const ProfessorEvaluationsPage: React.FC = () => {
    const [items, setItems] = useState<Evaluation[]>([]);
    const [loading, setLoading] = useState(true);
    const [deletingId, setDeletingId] = useState<number | null>(null);

    const fetchEvaluations = async () => {
        setLoading(true);
        try {
            const data = await avaliacaoApi.listarAvaliacoes();
            const sorted = data.sort((a: Evaluation, b: Evaluation) => {
                const dateA = a.createdAt || '';
                const dateB = b.createdAt || '';
                if (dateA && dateB) {
                    return new Date(dateB).getTime() - new Date(dateA).getTime();
                }
                return (b.id || 0) - (a.id || 0);
            });
            setItems(sorted);
        } catch (err) {
            console.error(err);
            message.error('Não foi possível carregar as avaliações.');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchEvaluations();
    }, []);

    const handleDelete = async (id: number) => {
        setDeletingId(id);
        try {
            await avaliacaoApi.excluirAvaliacao(id);
            setItems((prev) => prev.filter((i) => i.id !== id));
            message.success('Avaliação excluída');
        } catch (err) {
            console.error(err);
            message.error('Erro ao excluir avaliação');
        } finally {
            setDeletingId(null);
        }
    };

    const renderItem = (item: Evaluation) => {
        const avg = Math.round(
            (item.didatica + item.qualidadeAula + item.flexibilidade + item.organizacao) / 4
        );
        const created = item.createdAt ? new Date(item.createdAt).toLocaleString('pt-BR') : '';

        const professor = professores.find((p) => p.id === item.professorId);
        const nomeProfessor = professor ? professor.nome : 'Professor desconhecido';

        return (
            <List.Item
                actions={[
                    <Tooltip key={`edit-${item.id}`} title="Editar">
                        <Link to={`/editar-avaliacao/professor/${item.id}`}>
                            <EditOutlined style={{ fontSize: 16 }} />
                        </Link>
                    </Tooltip>,
                    <Popconfirm
                        key={`del-${item.id}`}
                        title="Deseja excluir essa avaliação?"
                        onConfirm={() => handleDelete(item.id)}
                        okText="Sim"
                        cancelText="Não"
                        disabled={deletingId === item.id}
                    >
                        <Tooltip title={deletingId === item.id ? 'Excluindo...' : 'Excluir'}>
                            <DeleteOutlined
                                style={{
                                    color: deletingId === item.id ? '#ccc' : '#ff4d4f',
                                    fontSize: 16,
                                    cursor: deletingId === item.id ? 'not-allowed' : 'pointer',
                                }}
                            />
                        </Tooltip>
                    </Popconfirm>,
                ]}
            >
                <List.Item.Meta
                    avatar={
                        // <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                        //     <Rate disabled allowHalf value={avg / 2} style={{ color: '#1890ff' }} />
                        // </div>

                        <Avatar
                            style={{
                                background: '#faad14',
                            }}
                            icon={<UserOutlined />}
                        >
                            {avg}
                        </Avatar>
                    }
                    title={
                        <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
                            <Text strong>{`Avaliação - ${nomeProfessor}`}</Text>
                            <Text type="secondary">
                                {item.visibilidade === 'publica' ? 'Pública' : item.visibilidade}
                            </Text>
                        </div>
                    }
                    description={
                        <div>
                            <div style={{ color: '#444', marginBottom: 6 }}>{item.comentario}</div>
                            <div style={{ display: 'flex', gap: 16, color: '#888', alignItems: 'center', fontWeight: 650 }}>
                                <Text type="secondary">Didática: {item.didatica}</Text>
                                <Text type="secondary">Qualidade: {item.qualidadeAula}</Text>
                                <Text type="secondary">Flexibilidade: {item.flexibilidade}</Text>
                                <Text type="secondary">Organização: {item.organizacao}</Text>
                                <span style={{ marginLeft: 8 }}>
                                    <CalendarOutlined /> <Text type="secondary">{created}</Text>
                                </span>
                            </div>
                        </div>
                    }
                />
            </List.Item>
        );
    };

    return (
        <div>
            <Title level={4}>Minhas Avaliações - Professor</Title>
            <Card style={{ marginTop: 8 }}>
                {loading ? (
                    <div style={{ textAlign: 'center', padding: 40 }}>
                        <Spin />
                    </div>
                ) : (
                    <List
                        itemLayout="horizontal"
                        dataSource={items}
                        renderItem={renderItem}
                        locale={{ emptyText: 'Nenhuma avaliação encontrada' }}
                    />
                )}
            </Card>
        </div>
    );
};

export default ProfessorEvaluationsPage;
