package uem.dam.sharethebeach.sharethebeach.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    //Atributo Firebase Auth
    private FirebaseAuth mAuth;

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

        //Inicializamos el FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //Cargamos el ArrayList con el listado de playas en el Context
        ((ContextoCustom) (getApplicationContext())).setListadoPlayas
                (new PersistenciaAemet(this).getListadoPlayas());

        //Asigna el Layout de la vista
        setContentView(R.layout.activity_login_);

        //Inicialización de los atributos de las imagenes del Logo
        logoSaveThebeach = findViewById(R.id.logo_saveTheBeach);
        isoLogo = findViewById(R.id.logoPez);

        //Animacion del Logo
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_top_down);
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
        dialogLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dialogLogin.comprobarCamposVacios()) {
                    Usuario user = dialogLogin.getDatos();
                    Log.e("USERDIALOG", user.toString());

                    mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                            .addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("EXITO", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Usuario usuario = new Usuario();
                                        usuario.setEmail(user.getEmail());
                                        usuario.setUid(user.getUid());
                                        ((ContextoCustom) (getApplicationContext())).setUser(usuario);
                                        Log.e("USUARIO", ((ContextoCustom) (getApplicationContext())).getUser().toString());

                                        crearSnackBar("Se ha autenticado en la base de datos con ÉXITO");
                                        //Toast.makeText(Login_Activity.this, "USUARIO AUTENTICADO",
                                          //      Toast.LENGTH_SHORT).show();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("FRACASO", "signInWithEmail:failure", task.getException());
                                        crearSnackBar("El usuario no se encuentra en la base de datos o ha habido un error");
                                        //Toast.makeText(Login_Activity.this, "USUARIO NO ENCONTRADO",
                                          //      Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
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
    Comenzamos la aplicación en el menu principal.
     */
    public void comenzar(View view){
        Intent i = new Intent(this, Beach_List.class);
        startActivity(i);
    }

    public void crearSnackBar(String mensaje) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.rl_splash), mensaje, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}