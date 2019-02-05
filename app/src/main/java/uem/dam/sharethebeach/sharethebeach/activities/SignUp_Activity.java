package uem.dam.sharethebeach.sharethebeach.activities;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.views.IProgressBar;
import static uem.dam.sharethebeach.sharethebeach.R.*;

public class SignUp_Activity extends AppCompatActivity implements IProgressBar {
    //Atributos del Activity
    private TextInputEditText etxCorreo;
    private TextInputEditText etxPass;
    private TextInputEditText etxPassRep;

    //Atributos del XML Dialog_Error
    private Dialog errorDialog;
    private Button btnErrorRegresar;
    private TextView tvErrorGenerico;

    //Atributo Barra de Progreso
    private Dialog progressBar;

    //Atributo FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(anim.anim_slide_left, anim.anim_slide_right);
        setContentView(layout.activity_sign_up);

        //Inicialización FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //Inicialización de atributos del Dialog_Error
        errorDialog = new Dialog(this);
        errorDialog.setContentView(layout.dialog_error);
        btnErrorRegresar = errorDialog.findViewById(id.btnErrorRegresar);
        tvErrorGenerico = errorDialog.findViewById(id.tvErrorGenerico);

        //Inicialización de atributos del activity
        etxCorreo = findViewById(id.etxCorreo);
        etxPass = findViewById(id.etxPass);
        etxPassRep = findViewById(id.etxPassRep);


        etxCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()) {
                    etxCorreo.setCompoundDrawablesWithIntrinsicBounds(getApplicationContext().getDrawable(R.drawable.ic_person_black_24dp), null, getApplicationContext().getDrawable(R.drawable.ic_check_black_24dp), null);
                } else {
                    etxCorreo.setCompoundDrawablesWithIntrinsicBounds(getApplicationContext().getDrawable(R.drawable.ic_person_black_24dp), null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //Cierra el cuadro de diálogo de errores en caso de que este suceda
        btnErrorRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorDialog.dismiss();
            }
        });


        etxPass.setTypeface( Typeface.DEFAULT );
        etxPass.setTransformationMethod(new PasswordTransformationMethod());
        etxPassRep.setTypeface( Typeface.DEFAULT );
        etxPassRep.setTransformationMethod(new PasswordTransformationMethod());
    }

    public void regresarSplashScreen(View view){
        onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void registrarUsuario(View view) {
        int res = 0;

        //Condición que verifica que las contraseñas coincidan
        if (!etxPass.getText().toString().equals(etxPassRep.getText().toString())) {
            mostrarDialogError(string.VAL_ERROR_PASSWORD_DIFERENTE);

        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etxCorreo.getText().toString()).matches()) {
            mostrarDialogError(string.VAL_ERROR_EMAIL);

        //Condición que verifica que los campos no estén vacíos
        } else if (etxCorreo.getText().toString().isEmpty() || etxPass.getText().toString().isEmpty()
                || etxPassRep.getText().toString().isEmpty()){
            mostrarDialogError(string.VAL_ERROR_EMPTY_FIELDS);

            //Condicion que comprueba que el email tenga un formato adecuado
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etxCorreo.getText().toString()).matches()) {
            mostrarDialogError(string.VAL_ERROR_EMAIL);

            //Condición que permite dar de alta al usuario pasándole un objeto con los datos almacenados
        } else {
            /*
            PersistenciaUsuarios per = new PersistenciaUsuarios(this);
            per.insertarUsuario(new Usuario(etxCorreo.getText().toString(),
                    etxPass.getText().toString(), etxPassRep.getText().toString()));*/
            mostrarProgressBar();

            mAuth.createUserWithEmailAndPassword(etxCorreo.getText().toString(), etxPass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                cerrarProgressBar();
                                // Exito en el alta, recuperamos el usuario actual y le enviamos
                                //un correo de verificación
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification();
                                mAuth = null;

                                //Lanzamos el activity del éxito en la operación
                                Intent success = new Intent(SignUp_Activity.this, SignUp_Success_Activity.class);
                                success.putExtra(getString(string.KEY__SIGN_UP_SUCCESS),etxCorreo.getText().toString());
                                startActivity(success);

                            } else {
                                // Si ocurre un error en el intento de registro.
                                cerrarProgressBar();
                                conexionCancelada();
                            }
                        }
                    });
        }
    }

    public void conexionCancelada(){
        mostrarDialogError(string.VAL_ERROR_GENERICO);
    }


    //Muestra una barra de progreso durante la conexión al servidor
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void mostrarProgressBar(){
        progressBar = new Dialog(this);
        progressBar.setContentView(layout.rl_prog);
        progressBar.setCancelable(false);
        progressBar.show();
    }

    //Cierra la barra de progreso de conexión al servidor
    @Override
    public void cerrarProgressBar(){
        progressBar.dismiss();
    }

    //Metodo que personaliza el XML con el error oportuno para cada situación y lo muestra al usuario.
    @Override
    public void mostrarDialogError(int idError) {
        tvErrorGenerico.setText(String.format(getString(string.STRING_FILL), getString(idError)));
        errorDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(anim.anim_slide_left,anim.anim_slide_right);
    }
}
