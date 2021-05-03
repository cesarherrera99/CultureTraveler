package com.example.culturetraveler;

import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

public class PHC {

    private String nome;
    private String morada;
    private String descricao;
    private double rating;
    private double latitud;
    private double longitud;
    private int idimagem;
    private int direcoes;

    public PHC(String nome, String morada, String descricao, Double rating, Double latitud, Double longitud, int idimagem) {
        this.nome = nome;
        this.morada = morada;
        this.descricao = descricao;
        this.rating = rating;
        this.latitud = latitud;
        this.longitud = longitud;
        this.idimagem = idimagem;
    }

    public PHC(String nome, String descricao, int idimagem, int direcoes) {
        this.nome = nome;
        this.descricao = descricao;
        this.idimagem = idimagem;
        this.direcoes = direcoes;
    }

    public PHC() {

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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getIdimagem() {
        return idimagem;
    }

    public void setIdimagem(int idimagem) {
        this.idimagem = idimagem;
    }

    public int getDirecoes() {
        return direcoes;
    }

    public void setDirecoes(int direcoes) {
        this.direcoes = direcoes;
    }
}