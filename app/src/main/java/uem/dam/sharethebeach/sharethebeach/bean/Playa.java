package uem.dam.sharethebeach.sharethebeach.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class Playa implements Parcelable {
    private String id;
    private double latitud;
    private double longitud;
    private String nombre;
    private String municipio;
    private Date fecha_actualizacion;
    private String estadoCielo;
    private String viento;
    private String oleaje;
    private int temperatura;
    private int temperaturaAgua;
    private int indiceUV;
    private String web;

    //Constructor Vacio
    public Playa() {
    }

    protected Playa(Parcel in) {
        id = in.readString();
        latitud = in.readDouble();
        longitud = in.readDouble();
        nombre = in.readString();
        municipio = in.readString();
        estadoCielo = in.readString();
        viento = in.readString();
        oleaje = in.readString();
        temperatura = in.readInt();
        temperaturaAgua = in.readInt();
        indiceUV = in.readInt();
        web = in.readString();
        fecha_actualizacion = (java.util.Date) in.readSerializable();
    }

    public static final Creator<Playa> CREATOR = new Creator<Playa>() {
        @Override
        public Playa createFromParcel(Parcel in) {
            return new Playa(in);
        }

        @Override
        public Playa[] newArray(int size) {
            return new Playa[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Date getFecha_actualizacion() {
        return fecha_actualizacion;
    }

    public void setFecha_actualizacion(Date fecha_actualizacion) {
        this.fecha_actualizacion = fecha_actualizacion;
    }

    public String getEstadoCielo() {
        return estadoCielo;
    }

    public void setEstadoCielo(String estadoCielo) {
        this.estadoCielo = estadoCielo;
    }

    public String getViento() {
        return viento;
    }

    public void setViento(String viento) {
        this.viento = viento;
    }

    public String getOleaje() {
        return oleaje;
    }

    public void setOleaje(String oleaje) {
        this.oleaje = oleaje;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getTemperaturaAgua() {
        return temperaturaAgua;
    }

    public void setTemperaturaAgua(int temperaturaAgua) {
        this.temperaturaAgua = temperaturaAgua;
    }

    public int getIndiceUV() {
        return indiceUV;
    }

    public void setIndiceUV(int indiceUV) {
        this.indiceUV = indiceUV;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @Override
    public String toString() {
        return "Playa{" +
                "id='" + id + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", nombre='" + nombre + '\'' +
                ", municipio='" + municipio + '\'' +
                ", fecha_actualizacion=" + fecha_actualizacion +
                ", estadoCielo='" + estadoCielo + '\'' +
                ", viento='" + viento + '\'' +
                ", oleaje='" + oleaje + '\'' +
                ", temperatura=" + temperatura +
                ", temperaturaAgua=" + temperaturaAgua +
                ", indiceUV=" + indiceUV +
                ", web='" + web + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeDouble(latitud);
        parcel.writeDouble(longitud);
        parcel.writeString(nombre);
        parcel.writeString(municipio);
        parcel.writeString(estadoCielo);
        parcel.writeString(viento);
        parcel.writeString(oleaje);
        parcel.writeInt(temperatura);
        parcel.writeInt(temperaturaAgua);
        parcel.writeInt(indiceUV);
        parcel.writeString(web);
        parcel.writeSerializable(fecha_actualizacion);
    }
}