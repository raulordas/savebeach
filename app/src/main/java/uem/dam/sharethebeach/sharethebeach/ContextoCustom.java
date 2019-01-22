package uem.dam.sharethebeach.sharethebeach;

import android.app.Application;

import java.util.ArrayList;

import uem.dam.sharethebeach.sharethebeach.bean.Playa;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;

public class ContextoCustom extends Application {
    private Usuario user;
    private ArrayList<Playa> listadoPlayas;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public ArrayList<Playa> getListadoPlayas() {
        return listadoPlayas;
    }

    public void setListadoPlayas(ArrayList<Playa> listadoPlayas) {
        this.listadoPlayas = listadoPlayas;
    }
}
