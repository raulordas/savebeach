package uem.dam.sharethebeach.sharethebeach.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.activities.Login_Activity;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;
import uem.dam.sharethebeach.sharethebeach.persistencia.PersistenciaUsuarios;

public class DialogLogin extends Dialog {
    private Button btnCerrarLogin;
    private EditText etxLoginNombre;
    private EditText etxLoginPassword;
    private Button btnLogin;
    private Context context;

    public DialogLogin(@NonNull final Context context) {
        super(context);
        this.context = context;
        //Asignamos los componentes del Dialog Login a los atributos
        setContentView(R.layout.dialog_login);
        etxLoginNombre = findViewById(R.id.etxLoginNombre);
        etxLoginPassword = findViewById(R.id.etxLoginPassword);
        btnCerrarLogin = findViewById(R.id.btnCerrarLogin);
        btnLogin = findViewById(R.id.btnLogin);


        //Listener de la ventana de Dialogo Login (Boton Cerrar)
        btnCerrarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //Listener de la ventana de Dialogo Login (Boton Log-In)
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!comprobarCamposVacios()) {

                    if(context instanceof Login_Activity){
                        ((Login_Activity) (context)).mostrarProgressBar();
                    }

                    PersistenciaUsuarios per = new PersistenciaUsuarios((Activity) context);
                    per.identificarUsuario(new Usuario(etxLoginNombre.getText().toString(), etxLoginPassword.getText().toString()));
                }
            }
        });
    }
    //Método que comprueba si los campos del Dialogo Login estan vacios
    public boolean comprobarCamposVacios(){
        if (etxLoginNombre.getText().toString().isEmpty() || etxLoginPassword.getText().toString().isEmpty()){
            new CustomDialog(context, R.string.VAL_ERROR_EMPTY_FIELDS).show();

            return true;
        } else {
            return false;
        }
    }
}
