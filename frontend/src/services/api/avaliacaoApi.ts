import { api } from './avaliacao';
import { AvaliacaoProfessor, AvaliacaoDisciplina, AvaliacaoCoordenador } from '../../types/avaliacao';

export const avaliacaoApi = {
  cadastrarAvaliacao: async (avaliacao: AvaliacaoProfessor) => {
    const response = await api.post('/avaliacoes/professor', avaliacao);
    return response.data;
  },

  listarAvaliacoes: async () => {
    const response = await api.get('/avaliacoes/professor');
    return response.data;
  },

  buscarAvaliacaoPorId: async (id: number) => {
    const response = await api.get(`/avaliacoes/professor/${id}`);
    return response.data;
  },

  atualizarAvaliacao: async (id: number, avaliacao: Partial<AvaliacaoProfessor>) => {
    const response = await api.put(`/avaliacoes/professor/${id}`, avaliacao);
    return response.data;
  },

  excluirAvaliacao: async (id: number) => {
    const response = await api.delete(`/avaliacoes/professor/${id}`);
    return response.data;
  },
};

export const avaliacaoApiDisciplina = {
  cadastrarAvaliacao: async (avaliacao: AvaliacaoDisciplina) => {
    const response = await api.post('/avaliacoes/disciplina', avaliacao);
    return response.data;
  },

  listarAvaliacoes: async () => {
    const response = await api.get('/avaliacoes/disciplina');
    return response.data;
  },

  buscarAvaliacaoPorId: async (id: number) => {
    const response = await api.get(`/avaliacoes/disciplina/${id}`);
    return response.data;
  },

  atualizarAvaliacao: async (id: number, avaliacao: Partial<AvaliacaoDisciplina>) => {
    const response = await api.put(`/avaliacoes/disciplina/${id}`, avaliacao);
    return response.data;
  },

  excluirAvaliacao: async (id: number) => {
    const response = await api.delete(`/avaliacoes/disciplina/${id}`);
    return response.data;
  },
};

export const avaliacaoApiCoordenador = {
  cadastrarAvaliacao: async (avaliacao: AvaliacaoCoordenador) => {
    const response = await api.post('/avaliacoes/coordenador', avaliacao);
    return response.data;
  },

  listarAvaliacoes: async () => {
    const response = await api.get('/avaliacoes/coordenador');
    return response.data;
  },

  buscarAvaliacaoPorId: async (id: number) => {
    const response = await api.get(`/avaliacoes/coordenador/${id}`);
    return response.data;
  },

  atualizarAvaliacao: async (id: number, avaliacao: Partial<AvaliacaoCoordenador>) => {
    const response = await api.put(`/avaliacoes/coordenador/${id}`, avaliacao);
    return response.data;
  },

  excluirAvaliacao: async (id: number) => {
    const response = await api.delete(`/avaliacoes/coordenador/${id}`);
    return response.data;
  },
};

export const avaliacoesUsuarios = {
  listarAvaliacoes: async () => {
    const response = await api.get('/avaliacoes');
    return response.data;
  },
};
