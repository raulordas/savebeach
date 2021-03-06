package uem.dam.sharethebeach.sharethebeach.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;

public class DialogLogin extends Dialog implements View.OnClickListener {
    private Button btnCerrarLogin;
    private TextInputEditText etxLoginNombre;
    private TextInputEditText etxLoginPassword;
    private Button btnLogin;
    private Context context;
    private View.OnClickListener listener;

    public DialogLogin(@NonNull final Context context) {
        super(context);
        this.context = context;
        //Asignamos los componentes del Dialog Login a los atributos
        setContentView(R.layout.dialog_login);
        etxLoginNombre = findViewById(R.id.etxCorreo);
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
        btnLogin.setOnClickListener(this);
        etxLoginNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()) {
                    etxLoginNombre.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.ic_person_black_24dp), null, context.getDrawable(R.drawable.ic_check_black_24dp), null);
                } else {
                    etxLoginNombre.setCompoundDrawablesWithIntrinsicBounds(context.getDrawable(R.drawable.ic_person_black_24dp), null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etxLoginPassword.setTypeface( Typeface.DEFAULT );
        etxLoginPassword.setTransformationMethod(new PasswordTransformationMethod());
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

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public Usuario getDatos() {
       return new Usuario(etxLoginNombre.getText().toString(), etxLoginPassword.getText().toString());
    }

    public void clearDialog() {
        etxLoginNombre.setText("");
        etxLoginPassword.setText("");
    }
}
