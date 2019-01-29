package uem.dam.sharethebeach.sharethebeach.bean;

import android.graphics.Bitmap;
import java.sql.Date;

public class Usuario {
    private String uid;
    private String nombre;
    private String apellido;
    private Date fechaNac;
    private String descripcion;
    private Bitmap foto;
    private String email;
    private String usuario;
    private String password;

    //Constructor Completo
    public Usuario(String uid, String nombre, String apellido, Date fechaNac, String descripcion,
                   Bitmap foto, String email, String usuario, String password) {
        this.uid = uid;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.descripcion = descripcion;
        this.foto = foto;
        this.email = email;
        this.usuario = usuario;
        this.password = password;
    }

    public Usuario(){}

    //Constructor para identificar usuario mediante usuario y password
    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //Getters
    public String getUid() {
        return uid;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public String getEmail() {
        return email;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "uid='" + uid + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", fechaNac=" + fechaNac +
                ", descripcion='" + descripcion + '\'' +
                ", foto=" + foto +
                ", email='" + email + '\'' +
                ", usuario='" + usuario + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
