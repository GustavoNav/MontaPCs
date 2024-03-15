package com.example.montapcs;

public class Computador {
    private String nome;
    private Double numero;

    public Computador(String nome, Double numero) {
        this.nome = nome;
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public Double getNumero() {
        return numero;
    }
}
