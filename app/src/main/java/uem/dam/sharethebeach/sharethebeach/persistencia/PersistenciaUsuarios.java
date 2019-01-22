package uem.dam.sharethebeach.sharethebeach.persistencia;

import android.app.Activity;
import android.os.AsyncTask;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;

public class PersistenciaUsuarios extends AsyncTask implements IConnection {
    private ConexionDB conexion;
    private Usuario usuario;
    private Connection con;
    private String opcion;
    private Activity activity;

    /*
    Constructor de la persitencia que recibe un activity y realiza la conexión a la base de datos.
    Para ello, invoca el método execute() de la instancia que contiene la conexion a la base.
    (ConexionDB).
     */
    public PersistenciaUsuarios(Activity activity){
        this.activity = activity;
        conexion = new ConexionDB(activity, this);
        conexion.execute();
    }

    //Metodo público para invocar desde un activity la inserción (Sign-Up) de un usuario.
    public void insertarUsuario(Usuario usuario){
        this.opcion = "insertarUsuario";
        this.usuario = usuario;
    }

    //Metodo público para invocar desde un activity la identificación (Log-In) de un usuario.
    public void identificarUsuario(Usuario usuario){
        this.opcion = "identificarUsuario";
        this.usuario = usuario;
    }

    /*
    Hilo doInBackground() que comprueba mediante un switch el tipo de solicitud que ha realizado
    el usuario. En función de esta, ejecuta el método oportuno de la persistencia. Retorna un resultado
    para poder recuperarlo en postExecute() si fuera necesario.
     */
    @Override
    protected Integer doInBackground(Object[] objects) {
        int res = 0;

            switch (opcion){
                case "insertarUsuario": persistirUsuario();
                    break;

                case "identificarUsuario": persistirIdentificacionUsuario(usuario);
                    break;
            }
        return res;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }

    /*
    Método protected que ejecuta en un hilo doInBackground() la identificación de un usuario conteni
    do en la base de datos. Finalmente, notifica al activity mediante el metodo resultadoPersistencia
    pasandole el resultado.
     */
    protected void persistirIdentificacionUsuario(Usuario usuario){
        PreparedStatement ps = null;
        ResultSet rs = null;
        int res = 0;
        String query = "SELECT * FROM USUARIOS WHERE USUARIO = ? AND PASSWORD = ?";

        //La primera vez, el nombre de usuario corresponderá al email facilitado para el alta
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getPassword());
            rs = ps.executeQuery();

            if (rs.next()){
                res = 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {

                if (rs != null){
                    rs.close();
                }

                if(ps != null){
                    ps.close();
                }

                if (con != null){
                    con.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(activity instanceof IPersistencia){
                ((IPersistencia) activity).resultadoPersistencia(res, usuario);
            }
        }
    }

    /*
    Método protected que ejecuta en un hilo doInBackground() la inserción (Sign-In)
    de un usuario contenido en la base de datos. Finalmente, notifica al activity
    mediante el método resultadoPersistencia pasandole el resultado.
     */
    protected void persistirUsuario() {
        PreparedStatement ps = null;
        int res = 0;
        String query = "INSERT INTO USUARIOS(USUARIO, EMAIL, PASSWORD) VALUES(?,?,?)";

        //La primera vez, el nombre de usuario corresponderá al email facilitado para el alta
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getPassword());
            res = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null){
                    ps.close();
                }

                if (con != null){
                    con.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(activity instanceof IPersistencia){
                ((IPersistencia) activity).resultadoPersistencia(res, usuario);
            }
        }
    }

    @Override
    public void callback(Connection con) {
        this.con = con;
        this.execute();
    }
}