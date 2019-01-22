package uem.dam.sharethebeach.sharethebeach.bean;

import android.graphics.Bitmap;
import java.sql.Date;

public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private Date fechaNac;
    private String descripcion;
    private Bitmap foto;
    private String email;
    private String usuario;
    private String password;

    //Constructor Completo
    public Usuario(int id, String nombre, String apellido, Date fechaNac, String descripcion,
                   Bitmap foto, String email, String usuario, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.descripcion = descripcion;
        this.foto = foto;
        this.email = email;
        this.usuario = usuario;
        this.password = password;
    }

    //Constructor para guardar los datos al hacer Sign Up por primera vez
    public Usuario(String email, String usuario, String password) {
        this.email = email;
        this.usuario = usuario;
        this.password = password;
    }

    //Constructor para identificar usuario mediante usuario y password
    public Usuario(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    //Getters
    public int getId() {
        return id;
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
}
