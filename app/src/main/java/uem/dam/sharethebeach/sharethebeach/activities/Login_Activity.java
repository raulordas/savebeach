package uem.dam.sharethebeach.sharethebeach.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import uem.dam.sharethebeach.sharethebeach.ContextoCustom;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;
import uem.dam.sharethebeach.sharethebeach.persistencia.PersistenciaAemet;
import uem.dam.sharethebeach.sharethebeach.views.CustomDialog;
import uem.dam.sharethebeach.sharethebeach.views.DialogLogin;
import uem.dam.sharethebeach.sharethebeach.views.DialogProgressBar;
import uem.dam.sharethebeach.sharethebeach.views.IProgressBar;

public class Login_Activity extends AppCompatActivity implements IProgressBar {
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

    private Dialog miniProgressBar;

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

        //Inicialización de la vista Correspondiente al ProgressBar
        progressBar = new DialogProgressBar(this);

        //Inicialización del Atributo correspondiente a la clase de la vista de Dialogo Login
        dialogLogin = new DialogLogin(this);
        dialogLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dialogLogin.comprobarCamposVacios()) {
                    Usuario user = dialogLogin.getDatos();

                    mAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                            .addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("EXITO", "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        dialogLogin.cancel();
                                        crearSnackBar("Bienvenid@ " + FirebaseAuth.getInstance().getCurrentUser().getEmail());
                                        mostrarMiniProgressBar();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(Login_Activity.this, Beach_List.class));
                                                finish();
                                            }
                                        }, 1500);



                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("FRACASO", "signInWithEmail:failure", task.getException());
                                        crearSnackBar(task.getException().toString());
                                    }
                                }
                            });
                }
            }
        });
    }

    //Metodo que carga el activity Sign Up para dar de alta un usuario al pulsar en el botón de alta
    public void cargarActivitySignUp(View view) {
        startActivity(new Intent(this, SignUp_Activity.class));
    }

    //Metodo que abre la ventana de dialogo Login
    public void cargarDialogLogin(View view) {
        dialogLogin.clearDialog();
        dialogLogin.show();
    }

    //Metodo que cierra la ventana de dialogo Login
    public void cerrarDialogLogin() {
        dialogLogin.dismiss();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void mostrarMiniProgressBar() {
        miniProgressBar = new Dialog(this);
        miniProgressBar.setContentView(R.layout.progbar_mini);
        miniProgressBar.setCancelable(false);
        miniProgressBar.show();
        //Branch Change
    }
}