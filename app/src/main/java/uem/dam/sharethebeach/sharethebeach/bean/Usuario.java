package uem.dam.sharethebeach.sharethebeach.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Date;

public class Usuario implements Parcelable {
    private String uid;
    private String email;
    private String password;
    private String nombre_completo;
    private String fechaNac;
    private String descripcion;
    private String urlFoto;

    public Usuario(){}

    public Usuario(String uid, String email, String password, String nombre_completo, String fechaNac, String descripcion, String urlFoto) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.nombre_completo = nombre_completo;
        this.fechaNac = fechaNac;
        this.descripcion = descripcion;
        this.urlFoto = urlFoto;
    }

    public Usuario(String uid, String email, String nombre_completo, String fechaNac, String descripcion, String urlFoto) {
        this.uid = uid;
        this.email = email;
        this.nombre_completo = nombre_completo;
        this.fechaNac = fechaNac;
        this.descripcion = descripcion;
        this.urlFoto = urlFoto;
    }

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected Usuario(Parcel in) {
        uid = in.readString();
        email = in.readString();
        password = in.readString();
        nombre_completo = in.readString();
        fechaNac = in.readString();
        descripcion = in.readString();
        urlFoto = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

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

    public String getFechaNac() {
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

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(nombre_completo);
        dest.writeString(fechaNac);
        dest.writeString(descripcion);
        dest.writeString(urlFoto);
    }
}
