package uem.dam.sharethebeach.sharethebeach.bean;

public class UsuarioChat {
    private String mensaje;
    private String uid;

    public UsuarioChat() {
    }

    public UsuarioChat(String mensaje, String uid) {
        this.mensaje = mensaje;
        this.uid = uid;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getUid() {
        return uid;
    }
}
