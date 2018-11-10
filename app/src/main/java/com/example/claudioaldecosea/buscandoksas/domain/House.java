package com.example.claudioaldecosea.buscandoksas.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class House implements Parcelable {
    private String InmuebleTienePatio;

    private ArrayList<Habitaciones> Habitaciones;

    private String InmuebleCantDormitorio;

    private String InmuebleTieneParrillero;

    private String InmuebleTieneBalcon;

    private String InmuebleTieneGarage;

    private ArrayList<Fotos> Fotos;

    private String InmuebleId;

    private String InmueblePrecio;

    private String InmuebleMetrosCuadrados;

    private String InmuebleBarrio;

    private String Favorito;

    private String InmuebleTitulo;

    protected House(Parcel in) {
        InmuebleTienePatio = in.readString();
        if (in.readByte() == 0x01) {
            Habitaciones = new ArrayList<Habitaciones>();
            in.readList(Habitaciones, Habitaciones.class.getClassLoader());
        } else {
            Habitaciones = null;
        }
        InmuebleCantDormitorio = in.readString();
        InmuebleTieneParrillero = in.readString();
        InmuebleTieneBalcon = in.readString();
        InmuebleTieneGarage = in.readString();
        if (in.readByte() == 0x01) {
            Fotos = new ArrayList<Fotos>();
            in.readList(Fotos, Fotos.class.getClassLoader());
        } else {
            Fotos = null;
        }
        InmuebleId = in.readString();
        InmueblePrecio = in.readString();
        InmuebleMetrosCuadrados = in.readString();
        InmuebleBarrio = in.readString();
        Favorito = in.readString();
        InmuebleTitulo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(InmuebleTienePatio);
        if (Habitaciones == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Habitaciones);
        }
        dest.writeString(InmuebleCantDormitorio);
        dest.writeString(InmuebleTieneParrillero);
        dest.writeString(InmuebleTieneBalcon);
        dest.writeString(InmuebleTieneGarage);
        if (Fotos == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(Fotos);
        }
        dest.writeString(InmuebleId);
        dest.writeString(InmueblePrecio);
        dest.writeString(InmuebleMetrosCuadrados);
        dest.writeString(InmuebleBarrio);
        dest.writeString(Favorito);
        dest.writeString(InmuebleTitulo);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<House> CREATOR = new Parcelable.Creator<House>() {
        @Override
        public House createFromParcel(Parcel in) {
            return new House(in);
        }

        @Override
        public House[] newArray(int size) {
            return new House[size];
        }
    };

    public String getInmuebleTienePatio() {
        return InmuebleTienePatio;
    }

    public void setInmuebleTienePatio(String InmuebleTienePatio) {
        this.InmuebleTienePatio = InmuebleTienePatio;
    }

    public ArrayList<Habitaciones> getHabitaciones() {
        return Habitaciones;
    }

    public void setHabitaciones(ArrayList<Habitaciones> Habitaciones) {
        this.Habitaciones = Habitaciones;
    }

    public String getInmuebleCantDormitorio() {
        return InmuebleCantDormitorio;
    }

    public void setInmuebleCantDormitorio(String InmuebleCantDormitorio) {
        this.InmuebleCantDormitorio = InmuebleCantDormitorio;
    }

    public String getInmuebleTieneParrillero() {
        return InmuebleTieneParrillero;
    }

    public void setInmuebleTieneParrillero(String InmuebleTieneParrillero) {
        this.InmuebleTieneParrillero = InmuebleTieneParrillero;
    }

    public String getInmuebleTieneBalcon() {
        return InmuebleTieneBalcon;
    }

    public void setInmuebleTieneBalcon(String InmuebleTieneBalcon) {
        this.InmuebleTieneBalcon = InmuebleTieneBalcon;
    }

    public String getInmuebleTieneGarage() {
        return InmuebleTieneGarage;
    }

    public void setInmuebleTieneGarage(String InmuebleTieneGarage) {
        this.InmuebleTieneGarage = InmuebleTieneGarage;
    }

    public ArrayList<Fotos> getFotos() {
        return Fotos;
    }

    public void setFotos(ArrayList<Fotos> Fotos) {
        this.Fotos = Fotos;
    }

    public String getInmuebleId() {
        return InmuebleId;
    }

    public void setInmuebleId(String InmuebleId) {
        this.InmuebleId = InmuebleId;
    }

    public String getInmueblePrecio() {
        return InmueblePrecio;
    }

    public void setInmueblePrecio(String InmueblePrecio) {
        this.InmueblePrecio = InmueblePrecio;
    }

    public String getInmuebleMetrosCuadrados() {
        return InmuebleMetrosCuadrados;
    }

    public void setInmuebleMetrosCuadrados(String InmuebleMetrosCuadrados) {
        this.InmuebleMetrosCuadrados = InmuebleMetrosCuadrados;
    }

    public String getInmuebleBarrio() {
        return InmuebleBarrio;
    }

    public void setInmuebleBarrio(String InmuebleBarrio) {
        this.InmuebleBarrio = InmuebleBarrio;
    }

    public String getFavorito() {
        return Favorito;
    }

    public void setFavorito(String Favorito) {
        this.Favorito = Favorito;
    }

    public String getInmuebleTitulo() {
        return InmuebleTitulo;
    }

    public void setInmuebleTitulo(String InmuebleTitulo) {
        this.InmuebleTitulo = InmuebleTitulo;
    }
}

