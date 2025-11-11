export interface AvaliacaoProfessor {
  professorId: number;
  didatica: number;
  qualidadeAula: number;
  flexibilidade: number;
  organizacao: number;
  comentario?: string;
  visibilidade: 'publica' | 'privada';
}

export interface Professor {
  id: number;
  nome: string;
}

export interface AvaliacaoDisciplina {
  disciplinaId: number;
  dificuldade: number;
  metodologia: number;
  conteudos: number;
  aplicabilidade: number;
  comentario?: string;
  visibilidade: 'publica' | 'privada';
}

export interface Disciplina {
  id: number;
  nome: string;
}

export interface AvaliacaoCoordenador {
  coordenadorId: number;
  transparencia: number;
  interacaoAluno: number;
  suporte: number;
  organizacao: number;
  comentario?: string;
  visibilidade: 'publica' | 'privada';
}

export interface Coordenador {
  id: number;
  nome: string;
}

export interface Avaliacao {
  avaliacaoId: number;
  tipoAvaliacao: 'PROFESSOR' | 'DISCIPLINA' | 'COORDENADOR';
  nomeReferencia: string;
  comentario?: string;
  likes?: number;
  deslikes?: number;
  didatica?: number;
  qualidadeAula?: number;
  flexibilidade?: number;
  organizacaoProfessor?: number;
  dificuldade?: number;
  metodologia?: number;
  conteudos?: number;
  aplicabilidade?: number;
  transparencia?: number;
  interacaoAluno?: number;
  suporte?: number;
  organizacaoCoordenador?: number;
  userReaction?: string;
}

export interface SampleItem {
  [key: string]: any;
}