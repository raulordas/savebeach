package uem.dam.sharethebeach.sharethebeach.bean;

import java.util.ArrayList;

public class Alerta {


    private String id;
    private String id_creador;
    private String descripcion;
    private String titulo;
    private String id_playa;
    private ArrayList<String> usuarios_apuntados;

    public Alerta() {
    }

    public Alerta(String id, String id_creador, String descripcion, String titulo, String id_playa, ArrayList<String> usuarios_apuntados) {
        this.id = id;
        this.id_creador = id_creador;
        this.descripcion = descripcion;
        this.titulo = titulo;
        this.id_playa = id_playa;
        this.usuarios_apuntados = usuarios_apuntados;
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

    public ArrayList<String> getUsuarios_apuntados() {
        return usuarios_apuntados;
    }

    @Override
    public String toString() {
        return "Alerta{" +
                "id='" + id + '\'' +
                ", id_creador='" + id_creador + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", titulo='" + titulo + '\'' +
                ", id_playa='" + id_playa + '\'' +
                ", usuarios_apuntados=" + usuarios_apuntados +
                '}';
    }
}
