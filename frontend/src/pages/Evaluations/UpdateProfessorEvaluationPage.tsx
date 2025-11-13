import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Form, InputNumber, Input, Radio, Button, message, Card, Spin } from 'antd';
import { avaliacaoApi } from '../../services/api/avaliacaoApi';
import type { AvaliacaoProfessor } from '../../types/avaliacao';

const professores = {
  1: 'João Victor Tinoco de Souza',
  2: 'Eduardo Nascimento de Arruda',
  3: 'José Maurício da Silva Junior',
  4: 'Maurício da Motta Braga',
};

const UpdateProfessorEvaluationPage: React.FC = () => {
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
        const data = await avaliacaoApi.buscarAvaliacaoPorId(Number(id));
        // map backend fields to form
        form.setFieldsValue({
          professorNome: professores[data.professorId as keyof typeof professores] || '',
          didatica: data.didatica,
          qualidadeAula: data.qualidadeAula,
          flexibilidade: data.flexibilidade,
          organizacao: data.organizacao,
          comentario: data.comentario,
          visibilidade: data.visibilidade,
        } as Partial<AvaliacaoProfessor>);
      } catch (err) {
        console.error(err);
        message.error('Erro ao carregar avaliação para edição');
      } finally {
        setFetching(false);
      }
    };

    fetch();
  }, [id, form]);

  const onFinish = async (values: Partial<AvaliacaoProfessor>) => {
    if (!id) return;
    try {
      setLoading(true);
      await avaliacaoApi.atualizarAvaliacao(Number(id), values);
      message.success('Avaliação atualizada com sucesso!');
      navigate('/minhas-avaliacoes/professor');
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
      <Card title="Atualizar Avaliação de Professor" className="avaliacao-card">
        <Form form={form} layout="vertical" onFinish={onFinish}>
          <Form.Item name="professorNome" label="Professor" rules={[{ required: true }]}>
            <Input disabled />
          </Form.Item>

          <Form.Item name="didatica" label="Didática" rules={[{ required: true }]}>
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item name="qualidadeAula" label="Qualidade da Aula" rules={[{ required: true }]}>
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item name="flexibilidade" label="Flexibilidade" rules={[{ required: true }]}>
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

export default UpdateProfessorEvaluationPage;
