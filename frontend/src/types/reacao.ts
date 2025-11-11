export interface cadastrarReacao {
  avaliacaoId: number;
  tipoAvaliacao: number;
  tipoReacao: 'LIKE' | 'DISLIKE';
}