import React, { useState } from 'react';
import { Form, Select, InputNumber, Input, Radio, Button, message, Card } from 'antd';
import { useNavigate } from 'react-router-dom';
import { avaliacaoApiDisciplina } from '../../services/api/avaliacaoApi';
import './styles.css';

const disciplinas = [
  { id: 1, nome: 'Programação Orientada a Objeto' },
  { id: 2, nome: 'Algoritmos e Estrutura de Dados' },
  { id: 3, nome: 'Infraestrutura de Software' },
  { id: 4, nome: 'Estatística e Probabilidade' },
  { id: 5, nome: 'Infra de Comunicação' },
];

const AvaliacaoDisciplinaPage: React.FC = () => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const onFinish = async (values: any) => {
    const payload = {
      disciplinaId: values.disciplinaId,
      dificuldade: values.dificuldade,
      metodologia: values.metodologia,
      conteudos: values.conteudos,
      aplicabilidade: values.aplicabilidade,
      comentario: values.comentario,
      visibilidade: values.visibilidade,
    };

    try {
      setLoading(true);
      await avaliacaoApiDisciplina.cadastrarAvaliacao(payload);
      message.success('Avaliação de disciplina cadastrada com sucesso!');
      form.resetFields();
      navigate('/disciplinas');
    } catch (error) {
      message.error('Erro ao cadastrar avaliação');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="avaliacao-container">
      <Card title="Avaliação de Disciplina" className="avaliacao-card">
        <Form form={form} layout="vertical" onFinish={onFinish}>
          <Form.Item
            name="disciplinaId"
            label="Disciplina"
            rules={[{ required: true, message: 'Por favor selecione uma disciplina' }]}
          >
            <Select placeholder="Selecione uma disciplina">
              {disciplinas.map(d => (
                <Select.Option key={d.id} value={d.id}>
                  {d.nome}
                </Select.Option>
              ))}
            </Select>
          </Form.Item>

          <Form.Item
            name="dificuldade"
            label="Dificuldade"
            rules={[{ required: true, message: 'Por favor avalie a dificuldade' }]}
          >
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            name="metodologia"
            label="Metodologia"
            rules={[{ required: true, message: 'Por favor avalie a metodologia' }]}
          >
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            name="conteudos"
            label="Conteúdos"
            rules={[{ required: true, message: 'Por favor avalie os conteúdos' }]}
          >
            <InputNumber min={0} max={10} style={{ width: '100%' }} />
          </Form.Item>

          <Form.Item
            name="aplicabilidade"
            label="Aplicabilidade"
            rules={[{ required: true, message: 'Por favor avalie a aplicabilidade' }]}
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

export default AvaliacaoDisciplinaPage;
