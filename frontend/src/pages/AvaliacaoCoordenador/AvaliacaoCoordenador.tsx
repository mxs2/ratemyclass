import React, { useState } from 'react';
import { Form, Select, InputNumber, Input, Radio, Button, message, Card } from 'antd';
import { useNavigate } from 'react-router-dom';
import { avaliacaoApiCoordenador } from '../../services/api/avaliacaoApi';
import type { AvaliacaoCoordenador } from '../../types/avaliacao';
import './styles.css';

const coordenadores = [
  { id: 1, nome: 'Carla Alexandre' },
  { id: 2, nome: 'Diocleciano Dantas Neto' },
  { id: 3, nome: 'Gabrielle Canalle' },
  { id: 4, nome: 'Eduardo Ariel' },
  { id: 5, nome: 'José Augusto Suruagy Monteiro' },
];

const AvaliacaoCoordenadorPage: React.FC = () => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onFinish = async (values: AvaliacaoCoordenador) => {
    const payload = {
      coordenadorId: values.coordenadorId,
      transparencia: values.transparencia,
      interacaoAluno: values.interacaoAluno,
      suporte: values.suporte,
      organizacao: values.organizacao,
      comentario: values.comentario,
      visibilidade: values.visibilidade,
    };

    try {
      setLoading(true);
      await avaliacaoApiCoordenador.cadastrarAvaliacao(payload);
      message.success('Avaliação de coordenador cadastrada com sucesso!');
      form.resetFields();
      navigate('/dashboard');
    } catch (error) {
      message.error('Erro ao cadastrar avaliação');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="avaliacao-container">
      <Card title="Avaliação de Coordenador" className="avaliacao-card">
        <Form form={form} layout="vertical" onFinish={onFinish}>
          <Form.Item
            name="coordenadorId"
            label="Coordenador"
            rules={[{ required: true, message: 'Por favor selecione um coordenador' }]}
          >
            <Select placeholder="Selecione um coordenador">
              {coordenadores.map(coord => (
                <Select.Option key={coord.id} value={coord.id}>
                  {coord.nome}
                </Select.Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item
            name="transparencia"
            label="Transparência"
            rules={[{ required: true, message: 'Por favor avalie a transparência' }]}
          >
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            name="interacaoAluno"
            label="Interação com alunos"
            rules={[{ required: true, message: 'Por favor avalie a interação com alunos' }]}
          >
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            name="suporte"
            label="Suporte"
            rules={[{ required: true, message: 'Por favor avalie o suporte' }]}
          >
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            name="organizacao"
            label="Organização"
            rules={[{ required: true, message: 'Por favor avalie a organização' }]}
          >
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item name="comentario" label="Comentário">
            <Input.TextArea rows={4} placeholder="Deixe seu comentário (opcional)" />
          </Form.Item>

          <Form.Item
            name="visibilidade"
            label="Visibilidade"
            rules={[{ required: true, message: 'Por favor selecione a visibilidade' }]}
          >
            <Radio.Group>
              <Radio value="publica">Pública</Radio>
              <Radio value="privada">Privada</Radio>
            </Radio.Group>
          </Form.Item>

          <Form.Item>
            <Button type="primary" htmlType="submit" loading={loading} block>
              Enviar Avaliação
            </Button>
          </Form.Item>
        </Form>
      </Card>
    </div>
  );
};

export default AvaliacaoCoordenadorPage;
