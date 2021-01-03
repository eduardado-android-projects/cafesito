package com.edusoft.dam.cafesito.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cafetero implements Parcelable {

    private Integer Id; //para la base de datos
    private String nombreCompleto;
    private String mv;
    private String tipoCafe;
    private Integer numCafe;

    public Cafetero() {
    }

    public Cafetero(Integer id, String nombreCompleto, String mv, String tipoCafe, Integer numCafe) {
        Id = id;
        this.nombreCompleto = nombreCompleto;
        this.mv = mv;
        this.tipoCafe = tipoCafe;
        this.numCafe = numCafe;
    }


    protected Cafetero(Parcel in) {
        if (in.readByte() == 0) {
            Id = null;
        } else {
            Id = in.readInt();
        }
        nombreCompleto = in.readString();
        mv = in.readString();
        tipoCafe = in.readString();
        if (in.readByte() == 0) {
            numCafe = null;
        } else {
            numCafe = in.readInt();
        }
    }

    public static final Creator<Cafetero> CREATOR = new Creator<Cafetero>() {
        @Override
        public Cafetero createFromParcel(Parcel in) {
            return new Cafetero(in);
        }

        @Override
        public Cafetero[] newArray(int size) {
            return new Cafetero[size];
        }
    };

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getMv() {
        return mv;
    }

    public void setMv(String mv) {
        this.mv = mv;
    }

    public String getTipoCafe() {
        return tipoCafe;
    }

    public void setTipoCafe(String tipoCafe) {
        this.tipoCafe = tipoCafe;
    }

    public Integer getNumCafe() {
        return numCafe;
    }

    public void setNumCafe(Integer numCafe) {
        this.numCafe = numCafe;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (Id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Id);
        }
        dest.writeString(nombreCompleto);
        dest.writeString(mv);
        dest.writeString(tipoCafe);
        if (numCafe == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numCafe);
        }
    }

    @Override
    public String toString() {
        return "Cafetero{" +
                "Id=" + Id +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", mv='" + mv + '\'' +
                ", tipoCafe='" + tipoCafe + '\'' +
                ", numCafe=" + numCafe +
                '}';
    }
}
