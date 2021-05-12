package com.example.culturetraveler;

import android.os.Parcel;
import android.os.Parcelable;

public class    PHC implements Parcelable {

    private String nome;
    private String morada;
    private String descricao;
    private double rating;
    private Double latitud;
    private Double longitud;
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


    public PHC(String nome, String morada, String descricao, int idimagem, int direcoes, Double rating, Double latitud, Double longitud) {
        this.nome = nome;
        this.morada = morada;
        this.descricao = descricao;
        this.idimagem = idimagem;
        this.direcoes = direcoes;
        this.rating = rating;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public PHC() {

    }



    protected PHC(Parcel in) {
        nome = in.readString();
        morada = in.readString();
        descricao = in.readString();
        rating = in.readDouble();
        if (in.readByte() == 0) {
            latitud = null;
        } else {
            latitud = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitud = null;
        } else {
            longitud = in.readDouble();
        }
        idimagem = in.readInt();
        direcoes = in.readInt();
    }

    public static final Creator<PHC> CREATOR = new Creator<PHC>() {
        @Override
        public PHC createFromParcel(Parcel in) {
            return new PHC(in);
        }

        @Override
        public PHC[] newArray(int size) {
            return new PHC[size];
        }
    };

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

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nome);
        parcel.writeString(morada);
        parcel.writeString(descricao);
        parcel.writeDouble(rating);
        if (latitud == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(latitud);
        }
        if (longitud == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(longitud);
        }
        parcel.writeInt(idimagem);
        parcel.writeInt(direcoes);
    }
}