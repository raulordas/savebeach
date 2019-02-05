package uem.dam.sharethebeach.sharethebeach.bean;

import java.sql.Date;

public class Usuario {
    private String uid;
    private String email;
    private String password;
    private String nombre_completo;
    private Date fechaNac;
    private String descripcion;
    private String urlFoto;

    public Usuario(){}

    public Usuario(String uid, String email, String password, String nombre_completo, Date fechaNac, String descripcion, String urlFoto) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.nombre_completo = nombre_completo;
        this.fechaNac = fechaNac;
        this.descripcion = descripcion;
        this.urlFoto = urlFoto;
    }

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
