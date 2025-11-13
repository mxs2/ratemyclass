import React, { useEffect, useState } from 'react';
import { Card, List, Avatar, Tooltip, Popconfirm, message, Spin, Typography } from 'antd';
import { DeleteOutlined, EditOutlined, CalendarOutlined, TeamOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { avaliacaoApiCoordenador } from '../../services/api/avaliacaoApi';

const { Title, Text } = Typography;

interface Evaluation {
    id: number;
    coordenadorId: number;
    transparencia: number;
    interacaoAluno: number;
    suporte: number;
    organizacao: number;
    comentario: string;
    visibilidade: string;
    active: boolean;
    createdAt: string;
}

const coordenadores = [
    { id: 1, nome: 'Carla Alexandre' },
    { id: 2, nome: 'Diocleciano Dantas Neto' },
    { id: 3, nome: 'Gabrielle Canalle' },
    { id: 4, nome: 'Eduardo Ariel' },
    { id: 5, nome: 'José Augusto Suruagy Monteiro' },
];

const CoordenadorEvaluationsPage: React.FC = () => {
    const [items, setItems] = useState<Evaluation[]>([]);
    const [loading, setLoading] = useState(true);
    const [deletingId, setDeletingId] = useState<number | null>(null);

    const fetchEvaluations = async () => {
        setLoading(true);
        try {
            const data = await avaliacaoApiCoordenador.listarAvaliacoes();
            setItems(data);
        } catch (err) {
            console.error(err);
            message.error('Não foi possível carregar as avaliações de coordenador.');
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
            await avaliacaoApiCoordenador.excluirAvaliacao(id);
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
            (item.transparencia + item.interacaoAluno + item.suporte + item.organizacao) / 4
        );
        const created = item.createdAt
            ? new Date(item.createdAt).toLocaleString('pt-BR')
            : '';

        const coordenador = coordenadores.find((p) => p.id === item.coordenadorId);
        const nomeCoordenador = coordenador ? coordenador.nome : 'Professor desconhecido';

        return (
            <List.Item
                actions={[
                    <Tooltip key={`edit-${item.id}`} title="Editar">
                        <Link to={`/editar-avaliacao/coordenador/${item.id}`}>
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
                        <Avatar
                            style={{
                                background: '#722ed1', // Roxo para coordenador
                            }}
                            icon={<TeamOutlined />}
                        >
                            {avg}
                        </Avatar>
                    }
                    title={
                        <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
                            <Text strong style={{ marginRight: 8 }}>{`Avaliação - ${nomeCoordenador}`}</Text>
                            <Text type="secondary">
                                {item.visibilidade === 'publica' ? 'Pública' : item.visibilidade}
                            </Text>
                        </div>
                    }
                    description={
                        <div>
                            <div style={{ color: '#444', marginBottom: 6 }}>{item.comentario}</div>
                            <div style={{ display: 'flex', gap: 16, color: '#888', alignItems: 'center', fontWeight: 650 }}>
                                <Text type="secondary">Transparência: {item.transparencia}</Text>
                                <Text type="secondary">Interação com aluno: {item.interacaoAluno}</Text>
                                <Text type="secondary">Suporte: {item.suporte}</Text>
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
            <Title level={4}>Minhas Avaliações - Coordenador</Title>

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

export default CoordenadorEvaluationsPage;
