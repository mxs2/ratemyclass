import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, InputNumber, Input, Radio, Button, message, Card, Spin } from 'antd';
import { avaliacaoApiDisciplina } from '../../services/api/avaliacaoApi';
import type { AvaliacaoDisciplina } from '../../types/avaliacao';

const disciplinas = {
  1: 'Programação Orientada a Objeto',
  2: 'Algoritmos e Estrutura de Dados',
  3: 'Infraestrutura de Software',
  4: 'Estatística e Probabilidade',
  5: 'Infra de Comunicação'
};

const UpdateDisciplinaEvaluationPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [form] = Form.useForm();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [fetching, setFetching] = useState(true);

  useEffect(() => {
    const fetch = async () => {
      if (!id) return;
      try {
        setFetching(true);
        const data = await avaliacaoApiDisciplina.buscarAvaliacaoPorId(Number(id));
        form.setFieldsValue({
          disciplinaNome: disciplinas[data.disciplinaId as keyof typeof disciplinas] || '',
          dificuldade: data.dificuldade,
          metodologia: data.metodologia,
          conteudos: data.conteudos,
          aplicabilidade: data.aplicabilidade,
          comentario: data.comentario,
          visibilidade: data.visibilidade,
        } as Partial<AvaliacaoDisciplina>);
      } catch (err) {
        console.error(err);
        message.error('Erro ao carregar avaliação para edição');
      } finally {
        setFetching(false);
      }
    };

    fetch();
  }, [id, form]);

  const onFinish = async (values: Partial<AvaliacaoDisciplina>) => {
    if (!id) return;
    try {
      setLoading(true);
      await avaliacaoApiDisciplina.atualizarAvaliacao(Number(id), values);
      message.success('Avaliação atualizada com sucesso!');
      navigate('/minhas-avaliacoes/disciplina');
    } catch (err) {
      console.error(err);
      message.error('Erro ao atualizar avaliação');
    } finally {
      setLoading(false);
    }
  };

  if (fetching) {
    return (
      <div style={{ textAlign: 'center', padding: 40 }}>
        <Spin />
      </div>
    );
  }

  return (
    <div className="avaliacao-container">
      <Card title="Atualizar Avaliação de Disciplina" className="avaliacao-card">
        <Form form={form} layout="vertical" onFinish={onFinish}>
          <Form.Item name="disciplinaNome" label="Disciplina" rules={[{ required: true }]}>
            <Input disabled />
          </Form.Item>

          <Form.Item name="dificuldade" label="Dificuldade" rules={[{ required: true }]}>
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item name="metodologia" label="Metodologia" rules={[{ required: true }]}>
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item name="conteudos" label="Conteúdos" rules={[{ required: true }]}>
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item name="aplicabilidade" label="Aplicabilidade" rules={[{ required: true }]}>
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item name="comentario" label="Comentário">
            <Input.TextArea rows={4} />
          </Form.Item>

          <Form.Item name="visibilidade" label="Visibilidade" rules={[{ required: true }]}>
            <Radio.Group>
              <Radio value="publica">Pública</Radio>
              <Radio value="privada">Privada</Radio>
            </Radio.Group>
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" loading={loading} block>
              Atualizar Avaliação
            </Button>
          </Form.Item>
        </Form>
      </Card>
    </div>
  );
};

export default UpdateDisciplinaEvaluationPage;
