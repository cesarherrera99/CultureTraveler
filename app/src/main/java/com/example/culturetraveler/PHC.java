package com.example.culturetraveler;

public class PHC {

    private String nome;
    private String morada;
    private String descricao;
    private int idimagem;

    public PHC(String nome, String descricao, int idimagem) {
        this.nome = nome;
        this.morada = morada;
        this.descricao = descricao;
        this.idimagem = idimagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdimagem() {
        return idimagem;
    }

    public void setIdimagem(int idimagem) {
        this.idimagem = idimagem;
    }
}