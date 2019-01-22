package uem.dam.sharethebeach.sharethebeach.persistencia;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.views.IProgressBar;

/**
 * Para poder realizar una conexión JDBC MySQL, debemos de realizar una clase que extienda de
 * AsyncTask. Esta clase ejecuta un hilo en el metodo doInBackground y devuelve el resultado
 * en postExecute. El método doInBackground comienza al invocar execute(). También podemos utilizar
 * el metodo onPreExecute() para ejecutar tareas antes de que comience el hilo. Es obligatorio
 * utilizar AsyncTask en JDBC para no congelar el hilo UI.
 *
 */
public class ConexionDB extends AsyncTask {
    //Constantes de la clase
    private static final String FICHERO_PROPERTIES = "database.properties";
    private static final String PROPERTY_DRIVER = "mysqldriver";
    private static final String PROPERTY_URL = "urlsharethebeachmysql";
    private static final String PROPERTY_USUARIO = "usuariomysql";
    private static final String PROPERTY_PASSWORD = "passwordmysql";

    //Atributos para la conexión
    private String driver;
    private String url;
    private String usuario;
    private String password;
    private IConnection callback;
    private Activity activity;

    //Constructor que recibe por parametro el Activity desde el que es invocado y lo asigna al atributo.
    //También recibe un Interfaz callback para devolver a los metodos de acceso de persistencia un resultado.
    public ConexionDB(Activity activity, IConnection callback){
        this.activity = activity;
        this.callback = callback;

        //Generamos un objeto Properties y AssetManager para cargar el contenido del fichero database.properties
        Properties properties = new Properties();
        AssetManager asset = activity.getAssets();
        try {
            properties.load(new InputStreamReader(asset.open(FICHERO_PROPERTIES)));
            driver = properties.getProperty(PROPERTY_DRIVER);
            url = properties.getProperty(PROPERTY_URL);
            usuario = properties.getProperty(PROPERTY_USUARIO);
            password = properties.getProperty(PROPERTY_PASSWORD);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Metodo que realiza la conexión a la persistencia.
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        con = DriverManager.getConnection(url, usuario, password);

        return con;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    //Durante la comprobación de los datos mostramos una barra de progreso en el UI
    protected void onPreExecute() {

        //Comprobamos la instancia del Activity para mostrar la barra de progreso en función
        //de la procedencia.
        if (activity instanceof IProgressBar) {
            ((IProgressBar) activity).mostrarProgressBar();
        }
    }

    /*
    Debemos sobrescribir este metodo para ejecutar la conexión en un hilo independiente de la UI.
    Invocamos desde el hilo el método que realiza la conexión a la base de datos.
     */
    @Override
    protected Connection doInBackground(Object... objects) {
        Connection con = null;

        try {
            con =  getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    /*
    Al finalizar el hilo doInBackground() eliminamos la barra de progreso en la UI.
    En caso de conexión fallida mostramos un mensaje de error en la UI utilizando
    el método callback(). Controlamos también la instancia de procedencia del activity.
     */
    @Override
    protected void onPostExecute(Object o) {

        if (o != null){
            callback.callback((Connection) o);

        } else{

            if (activity instanceof IProgressBar) {
                ((IProgressBar) activity).cerrarProgressBar();
                ((IProgressBar) activity).mostrarDialogError(R.string.VAL_ERROR_GENERICO);
            }
        }
    }
}
