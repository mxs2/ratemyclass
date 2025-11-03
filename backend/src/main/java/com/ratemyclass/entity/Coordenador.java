package com.ratemyclass.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "coordenadores")
public class Coordenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false)
    private Boolean active = true;

    // Construtores
    public Coordenador() {}

    public Coordenador(String nome, Boolean active) {
        this.nome = nome;
        this.active = active;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    @Override
    public String toString() {
        return "Coordenador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", active=" + active +
                '}';
    }
}
