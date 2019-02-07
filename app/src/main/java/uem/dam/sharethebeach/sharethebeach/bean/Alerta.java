package uem.dam.sharethebeach.sharethebeach.bean;

import java.util.ArrayList;
import java.util.Objects;

public class Alerta {


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
