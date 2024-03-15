package com.example.montapcs;

public class Componente {
    private String nome;
    private Double custo;

    public Componente(String nome, Double custo) {
        this.nome = nome;
        this.custo = custo;
    }
    public String getNome() {
        return nome;
    }
    public Double getCusto() {
        return custo;
    }
}

