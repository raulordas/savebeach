package uem.dam.sharethebeach.sharethebeach.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Objects;

public class Alerta implements Parcelable {


    private String id;
    private String id_creador;
    private String descripcion;
    private String titulo;
    private String id_playa;
    private String fecha;
    private String hora;
    private ArrayList<String> usuarios_apuntados;
    private String urlImg;

    public Alerta() {
    }

    public Alerta(String id, String id_creador, String descripcion, String titulo, String id_playa, String fecha, String hora, ArrayList<String> usuarios_apuntados,String urlImg) {
        this.id = id;
        this.id_creador = id_creador;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.id_playa = id_playa;
        this.fecha = fecha;
        this.hora = hora;
        this.usuarios_apuntados = usuarios_apuntados;
        this.urlImg = urlImg;
    }


    protected Alerta(Parcel in) {
        id = in.readString();
        id_creador = in.readString();
        descripcion = in.readString();
        titulo = in.readString();
        id_playa = in.readString();
        fecha = in.readString();
        hora = in.readString();
        usuarios_apuntados = in.createStringArrayList();
        urlImg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(id_creador);
        dest.writeString(descripcion);
        dest.writeString(titulo);
        dest.writeString(id_playa);
        dest.writeString(fecha);
        dest.writeString(hora);
        dest.writeStringList(usuarios_apuntados);
        dest.writeString(urlImg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Alerta> CREATOR = new Creator<Alerta>() {
        @Override
        public Alerta createFromParcel(Parcel in) {
            return new Alerta(in);
        }

        @Override
        public Alerta[] newArray(int size) {
            return new Alerta[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getId_creador() {
        return id_creador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getId_playa() {
        return id_playa;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public ArrayList<String> getUsuarios_apuntados() {
        return usuarios_apuntados;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void add_idUsu(String id){
        usuarios_apuntados.add(id);
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    @Override
    public String toString() {
        return "Alerta{" +
                "id='" + id + '\'' +
                ", id_creador='" + id_creador + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", titulo='" + titulo + '\'' +
                ", id_playa='" + id_playa + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", usuarios_apuntados=" + usuarios_apuntados +
                ", urlImg='" + urlImg + '\'' +
                '}';
    }
}
