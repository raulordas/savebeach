package uem.dam.sharethebeach.sharethebeach.bean;

import android.graphics.Bitmap;
import java.sql.Date;

public class Usuario {
    private String uid;
    private String nombre;
    private String fechaNac;
    private String descripcion;
    private String email;
    private String usuario;
    private String password;

    //Constructor Completo
    public Usuario(String uid, String nombre, String fechaNac, String descripcion,
                   String email, String ususario, String password) {
        this.uid = uid;
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        this.descripcion = descripcion;
        this.email = email;
        this.usuario = usuario;
        this.password = password;
    }

    //Constructor Semicompleto
    public Usuario(String uid, String nombre, String fechaNac, String descripcion,
                   String email) {
        this.uid = uid;
        this.nombre = nombre;
        this.fechaNac = fechaNac;
        this.descripcion = descripcion;
        this.email = email;
        this.usuario = usuario;
        this.password = password;
    }

    public Usuario(){}

    //Constructor para identificar usuario mediante usuario y password
    public Usuario(String email, String password) {
        this.email = email;
        //this.password = password;
    }

    //Getters
    public String getUid() {
        return uid;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "uid='" + uid + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fechaNac=" + fechaNac +
                ", descripcion='" + descripcion + '\'' +
                ", email='" + email + '\'' +
                //", password='" + password + '\'' +
                '}';
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
