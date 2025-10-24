import React, { useState } from 'react';
import { Form, Select, InputNumber, Input, Radio, Button, message, Card } from 'antd';
import { useNavigate } from 'react-router-dom';
import { avaliacaoApi } from '../../services/api/avaliacaoApi';
import type { AvaliacaoProfessor } from '../../types/avaliacao';
import './styles.css';

const professores = [
  { id: 1, nome: 'João Victor Tinoco de Souza' },
  { id: 2, nome: 'Eduardo Nascimento de Arruda' },
  { id: 3, nome: 'José Maurício da Silva Junior' },
  { id: 4, nome: 'Maurício da Motta Braga' },
];

const AvaliacaoProfessorPage: React.FC = () => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onFinish = async (values: AvaliacaoProfessor) => {
    try {
      setLoading(true);
      await avaliacaoApi.cadastrarAvaliacao(values);
      message.success('Avaliação cadastrada com sucesso!');
      form.resetFields();
      navigate('/professors');
    } catch (error) {
      message.error('Erro ao cadastrar avaliação');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="avaliacao-container">
      <Card title="Avaliação de Professor" className="avaliacao-card">
        <Form
          form={form}
          layout="vertical"
          onFinish={onFinish}
        >
          <Form.Item
            name="professorId"
            label="Professor"
            rules={[{ required: true, message: 'Por favor selecione um professor' }]}
          >
            <Select placeholder="Selecione um professor">
              {professores.map(prof => (
                <Select.Option key={prof.id} value={prof.id}>
                  {prof.nome}
                </Select.Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item
            name="didatica"
            label="Didática"
            rules={[{ required: true, message: 'Por favor avalie a didática' }]}
          >
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            name="qualidadeAula"
            label="Qualidade da Aula"
            rules={[{ required: true, message: 'Por favor avalie a qualidade da aula' }]}
          >
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            name="flexibilidade"
            label="Flexibilidade"
            rules={[{ required: true, message: 'Por favor avalie a flexibilidade' }]}
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

          <Form.Item
            name="comentario"
            label="Comentário"
          >
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

export default AvaliacaoProfessorPage;