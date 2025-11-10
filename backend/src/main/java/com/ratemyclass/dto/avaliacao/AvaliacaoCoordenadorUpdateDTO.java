package com.ratemyclass.dto.avaliacao;

public class AvaliacaoCoordenadorUpdateDTO {
    private Integer transparencia;
    private Integer interacaoAluno;
    private Integer suporte;
    private Integer organizacao;
    private String comentario;

    // Getters e Setters
    public Integer getTransparencia() {
        return transparencia;
    }

    public void setTransparencia(Integer transparencia) {
        this.transparencia = transparencia;
    }

    public Integer getInteracaoAluno() {
        return interacaoAluno;
    }

    public void setInteracaoAluno(Integer interacaoAluno) {
        this.interacaoAluno = interacaoAluno;
    }

    public Integer getSuporte() {
        return suporte;
    }

    public void setSuporte(Integer suporte) {
        this.suporte = suporte;
    }

    public Integer getOrganizacao() {
        return organizacao;
    }

    public void setOrganizacao(Integer organizacao) {
        this.organizacao = organizacao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}