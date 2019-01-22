package uem.dam.sharethebeach.sharethebeach.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import uem.dam.sharethebeach.sharethebeach.ContextoCustom;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;
import uem.dam.sharethebeach.sharethebeach.persistencia.IPersistencia;
import uem.dam.sharethebeach.sharethebeach.persistencia.PersistenciaAemet;
import uem.dam.sharethebeach.sharethebeach.persistencia.PersistenciaUsuarios;
import uem.dam.sharethebeach.sharethebeach.views.CustomDialog;
import uem.dam.sharethebeach.sharethebeach.views.DialogLogin;
import uem.dam.sharethebeach.sharethebeach.views.DialogProgressBar;
import uem.dam.sharethebeach.sharethebeach.views.IProgressBar;

public class Login_Activity extends AppCompatActivity implements IPersistencia, IProgressBar {
    //Atributos Logica Negocio
    private Usuario user;

    //Atributos del XML Dialog Modal Login
    private DialogLogin dialogLogin;

    //Atributos Imagenes Logo
    private ImageView logoSaveThebeach;
    private ImageView isoLogo;

    //Atributo Barra de Progreso
    private DialogProgressBar progressBar;

    //Atributo CustomDialog_Error
    private CustomDialog errorDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Cargamos el ArrayList con el listado de playas en el Context
        ((ContextoCustom) (getApplicationContext())).setListadoPlayas
                (new PersistenciaAemet(this).getListadoPlayas());

        //Asigna el Layout de la vista
        setContentView(R.layout.activity_login_);

        //Inicialización de los atributos de las imagenes del Logo
        logoSaveThebeach = findViewById(R.id.logo_saveTheBeach);
        isoLogo = findViewById(R.id.logoPez);

        //Animacion del Logo
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_translate_logo);
        Animation animAlpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_fade_logo);
        animAlpha.setStartOffset(1500);
        logoSaveThebeach.startAnimation(animAlpha);
        isoLogo.startAnimation(anim);

        /*Comprobamos si existe un usuario almacenado en el archivo sharedpreferences. En caso afirmativo
        saltamos el splashscreen para cargar directamente el activity principal. Por esta razón asignamos
        la vista tras invocar a este método.
         */
        //comprobarUsuario();

        //Inicialización de la vista Correspondiente al ProgressBar
        progressBar = new DialogProgressBar(this);

        //Inicialización del Atributo correspondiente a la clase de la vista de Dialogo Login
        dialogLogin = new DialogLogin(this);
        //DialogFragment newFragment = new Picador();
        //newFragment.show(getSupportFragmentManager(),"t");

    }

    //Metodo que carga el activity Sign Up para dar de alta un usuario al pulsar en el botón de alta
    public void cargarActivitySignUp(View view) {
        startActivity(new Intent(this, SignUp_Activity.class));
    }

    //Metodo que abre la ventana de dialogo Login
    public void cargarDialogLogin(View view) {
        dialogLogin.show();
    }

    //Metodo que cierra la ventana de dialogo Login
    public void cerrarDialogLogin() {
        dialogLogin.dismiss();
    }

    /*
    Método que comprueba al principio de la aplicación si hay algun usuario almacenado en el archivo
    sharedpreferences. En caso afirmativo, contrasta dicha información con la persistencia.
     */
    public void comprobarUsuario() {
        String username = this.getSharedPreferences("PREFERENCIAS_USUARIO", Context.MODE_PRIVATE).getString("usuario", "");
        String password = this.getSharedPreferences("PREFERENCIAS_USUARIO", Context.MODE_PRIVATE).getString("pass", "");
        user = new Usuario(username, password);
        System.out.println(password);

        if (!user.getUsuario().equals("")) {
            //Comprobar credenciales. Si son correctas intent a la ventana principal, sino mensaje
            //y eliminar lo que hubiera en el sharedpreferences.
            PersistenciaUsuarios per = new PersistenciaUsuarios(this);
            per.identificarUsuario(user);
        }
    }

    /*
    Método que procesa el resultado que devuelve la persistencia mediante callback
    En caso de que el resultado sea distinto de 0, almacena los datos del usuario en
    un archivo sharedpreferences y lanza el activity principal de la aplicación
     */
    @Override
    public void resultadoPersistencia(int res, Object obj) {
        Usuario user = (Usuario) obj;

        if (res != 0){
            SharedPreferences pref = this.getSharedPreferences("PREFERENCIAS_USUARIO", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.putString("usuario", user.getUsuario().toString());
            editor.putString("pass", user.getPassword().toString());
            editor.commit();

            startActivity(new Intent(this, User_Profile_Activity.class));
        }
    }

    //Muestra la barra de progreso de conexión al servidor
    @Override
    public void mostrarProgressBar(){
        progressBar.show();
    }

    //Cierra la barra de progreso de conexión al servidor
    @Override
    public void cerrarProgressBar(){
        if(progressBar.isShowing()){
            progressBar.dismiss();
        }
    }

    //Metodo que muestra un Dialogo de error si al intentar hacer Login no hay conexión con el servidor
    @Override
    public void mostrarDialogError(int idError) {
        if(dialogLogin.isShowing()){
            errorDialog = new CustomDialog(this, idError);
            errorDialog.show();
        }
    }

    /*
    Solicitamos a la persistencia información sobre una playa determinada
     */
    public void comenzar(View view){
        Intent i = new Intent(this, Beach_List.class);
        startActivity(i);
    }
}