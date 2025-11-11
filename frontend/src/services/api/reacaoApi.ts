import { api } from './avaliacao';
import { cadastrarReacao } from '../../types/reacao';

export const salvarReacao = {
  cadastrarReacao: async (payload: cadastrarReacao) => {
    const response = await api.post('/reacoes', payload);
    return response.data;
  },
};