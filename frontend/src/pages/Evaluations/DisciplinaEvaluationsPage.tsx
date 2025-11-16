import React, { useEffect, useState } from 'react';
import { Card, List, Avatar, Tooltip, Popconfirm, message, Spin, Typography } from 'antd';
import { DeleteOutlined, EditOutlined, CalendarOutlined, BookOutlined } from '@ant-design/icons';
import { Link } from 'react-router-dom';
import { avaliacaoApiDisciplina } from '../../services/api/avaliacaoApi';

const { Title, Text } = Typography;

interface Evaluation {
    id: number;
    disciplinaId: number;
    dificuldade: number;
    metodologia: number;
    conteudos: number;
    aplicabilidade: number;
    comentario: string;
    visibilidade: string;
    active: boolean;
    createdAt: string;
}

const disciplinas = [
    { id: 1, nome: 'Programação Orientada a Objeto' },
    { id: 2, nome: 'Algoritmos e Estrutura de Dados' },
    { id: 3, nome: 'Infraestrutura de Software' },
    { id: 4, nome: 'Estatística e Probabilidade' },
    { id: 5, nome: 'Infra de Comunicação' },
];

const DisciplinaEvaluationsPage: React.FC = () => {
    const [items, setItems] = useState<Evaluation[]>([]);
    const [loading, setLoading] = useState(true);
    const [deletingId, setDeletingId] = useState<number | null>(null);

    const fetchEvaluations = async () => {
        setLoading(true);
        try {
            const data = await avaliacaoApiDisciplina.listarAvaliacoes();
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
            message.error('Não foi possível carregar as avaliações de disciplina.');
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
            await avaliacaoApiDisciplina.excluirAvaliacao(id);
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
            (item.dificuldade + item.metodologia + item.conteudos + item.aplicabilidade) / 4
        );
        const created = item.createdAt
            ? new Date(item.createdAt).toLocaleString('pt-BR')
            : '';

        const disciplina = disciplinas.find((p) => p.id === item.disciplinaId);
        const nomeDisciplina = disciplina ? disciplina.nome : 'Professor desconhecido';

        return (
            <List.Item
                actions={[
                    <Tooltip key={`edit-${item.id}`} title="Editar">
                        <Link to={`/editar-avaliacao/disciplina/${item.id}`}>
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
                                background: '#faad14',
                            }}
                            icon={<BookOutlined />}
                        >
                            {avg}
                        </Avatar>
                    }
                    title={
                        <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
                            <Text strong style={{ marginRight: 8 }}>{`Avaliação - ${nomeDisciplina}`}</Text>
                            <Text type="secondary">
                                {item.visibilidade === 'publica' ? 'Pública' : item.visibilidade}
                            </Text>
                        </div>
                    }
                    description={
                        <div>
                            <div style={{ color: '#444', marginBottom: 6 }}>{item.comentario}</div>
                            <div style={{ display: 'flex', gap: 16, color: '#888', alignItems: 'center', fontWeight: 650 }}>
                                <Text type="secondary">Dificuldade: {item.dificuldade}</Text>
                                <Text type="secondary">Metodologia: {item.metodologia}</Text>
                                <Text type="secondary">Conteúdos: {item.conteudos}</Text>
                                <Text type="secondary">Aplicabilidade: {item.aplicabilidade}</Text>
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
            <Title level={4}>Minhas Avaliações - Disciplina</Title>

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

export default DisciplinaEvaluationsPage;
