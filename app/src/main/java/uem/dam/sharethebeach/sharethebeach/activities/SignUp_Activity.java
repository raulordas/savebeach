package uem.dam.sharethebeach.sharethebeach.activities;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;
import uem.dam.sharethebeach.sharethebeach.persistencia.IPersistencia;
import uem.dam.sharethebeach.sharethebeach.persistencia.PersistenciaUsuarios;
import uem.dam.sharethebeach.sharethebeach.views.IProgressBar;
import static uem.dam.sharethebeach.sharethebeach.R.*;

public class SignUp_Activity extends AppCompatActivity implements IProgressBar, IPersistencia {
    //Atributos del Activity
    private EditText etxCorreo;
    private EditText etxPass;
    private EditText etxPassRep;

    //Atributos del XML Dialog_Error
    private Dialog errorDialog;
    private Button btnErrorRegresar;
    private TextView tvErrorGenerico;

    //Atributo Barra de Progreso
    private Dialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_sign_up);

        //Inicialización de atributos del Dialog_Error
        errorDialog = new Dialog(this);
        errorDialog.setContentView(layout.dialog_error);
        btnErrorRegresar = errorDialog.findViewById(id.btnErrorRegresar);
        tvErrorGenerico = errorDialog.findViewById(id.tvErrorGenerico);

        //Inicialización de atributos del activity
        etxCorreo = findViewById(id.etxCorreo);
        etxPass = findViewById(id.etxPass);
        etxPassRep = findViewById(id.etxPassRep);

        //Cierra el cuadro de diálogo de errores en caso de que este suceda
        btnErrorRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorDialog.dismiss();
            }
        });
    }

    public void regresarSplashScreen(View view){
        startActivity(new Intent(this, Login_Activity.class));
    }

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
            PersistenciaUsuarios per = new PersistenciaUsuarios(this);
            per.insertarUsuario(new Usuario(etxCorreo.getText().toString(),
                    etxPass.getText().toString(), etxPassRep.getText().toString()));
        }
    }

    //Metodo que comprueba el resultado que devuelve la persistencia
    public void resultadoPersistencia(int res){


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
    public void resultadoPersistencia(int res, Object obj) {
        //Si el resultado del alta es satisfactorio, se lanza un intent hacia un activity que muestra el exito

        cerrarProgressBar();
        if (res != 0){
            Intent success = new Intent(this, SignUp_Success_Activity.class);
            success.putExtra(getString(string.KEY__SIGN_UP_SUCCESS),etxCorreo.getText().toString());
            startActivity(success);

            //En caso contrario se muestra un error.
        } else {
            mostrarDialogError(string.VAL_ERROR_GENERICO);
        }
    }
}
