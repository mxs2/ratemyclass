import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, InputNumber, Input, Radio, Button, message, Card, Spin } from 'antd';
import { avaliacaoApiCoordenador } from '../../services/api/avaliacaoApi';
import type { AvaliacaoCoordenador } from '../../types/avaliacao';

const coordenadores = {
  1: 'Carla Alexandre',
  2: 'Diocleciano Dantas Neto',
  3: 'Gabrielle Canalle',
  4: 'Eduardo Ariel',
  5: 'José Augusto Suruagy Monteiro',
};


const UpdateCoordenadorEvaluationPage: React.FC = () => {
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
        const data = await avaliacaoApiCoordenador.buscarAvaliacaoPorId(Number(id));
        form.setFieldsValue({
          coordenadorNome: coordenadores[data.coordenadorId as keyof typeof coordenadores] || '',
          transparencia: data.transparencia,
          interacaoAluno: data.interacaoAluno,
          suporte: data.suporte,
          organizacao: data.organizacao,
          comentario: data.comentario,
          visibilidade: data.visibilidade,
        } as Partial<AvaliacaoCoordenador>);
      } catch (err) {
        console.error(err);
        message.error('Erro ao carregar avaliação para edição');
      } finally {
        setFetching(false);
      }
    };

    fetch();
  }, [id, form]);

  const onFinish = async (values: Partial<AvaliacaoCoordenador>) => {
    if (!id) return;
    try {
      setLoading(true);
      await avaliacaoApiCoordenador.atualizarAvaliacao(Number(id), values);
      message.success('Avaliação atualizada com sucesso!');
      navigate('/minhas-avaliacoes/coordenador');
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
      <Card title="Atualizar Avaliação de Coordenador" className="avaliacao-card">
        <Form form={form} layout="vertical" onFinish={onFinish}>
          <Form.Item name="coordenadorNome" label="Coordenador" rules={[{ required: true }]}>
            <Input disabled />
          </Form.Item>

          <Form.Item name="transparencia" label="Transparência" rules={[{ required: true }]}>
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item name="interacaoAluno" label="Interação com Aluno" rules={[{ required: true }]}>
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item name="suporte" label="Suporte" rules={[{ required: true }]}>
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item name="organizacao" label="Organização" rules={[{ required: true }]}>
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

export default UpdateCoordenadorEvaluationPage;
