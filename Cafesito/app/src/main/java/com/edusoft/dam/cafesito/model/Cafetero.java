package com.edusoft.dam.cafesito.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cafetero") //nombre tabla singular
public class Cafetero implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "NombreCompleto") //PascalCase
    private String nombreCompleto;
    @ColumnInfo(name = "Mv")
    private String mv;
    @ColumnInfo(name = "TipoCafe")
    private String tipoCafe;
    @ColumnInfo(name = "NumCafe")
    private Integer numCafe;


    @Ignore
    public Cafetero() {
    }

    public Cafetero(Integer id, String nombreCompleto, String mv, String tipoCafe, Integer numCafe) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.mv = mv;
        this.tipoCafe = tipoCafe;
        this.numCafe = numCafe;
    }

    protected Cafetero(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
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
                "id=" + id +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", mv='" + mv + '\'' +
                ", tipoCafe='" + tipoCafe + '\'' +
                ", numCafe=" + numCafe +
                '}';
    }
}
