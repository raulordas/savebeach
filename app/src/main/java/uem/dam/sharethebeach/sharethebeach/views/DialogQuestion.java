package uem.dam.sharethebeach.sharethebeach.views;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uem.dam.sharethebeach.sharethebeach.R;

public class DialogQuestion extends Dialog implements View.OnClickListener {
    private String res;
    private Button btnDialogAceptar;
    private Button btnDialogCancel;
    private TextView tvErrorGenerico;
    private View.OnClickListener listener;

    public DialogQuestion(@NonNull Context context, String res) {
        super(context);
        this.res = res;
        setContentView(R.layout.dialog_pregunta);
        btnDialogAceptar = findViewById(R.id.btnDialogAceptar);
        btnDialogCancel = findViewById(R.id.btnDialogCancel);
        tvErrorGenerico = findViewById(R.id.tvErrorGenerico);
        setCancelable(false);
        mostrarDialogoError();

        btnDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnDialogAceptar.setOnClickListener(this);

    }

    //Metodo que personaliza el XML con el error oportuno para cada situación y lo muestra al usuario.
    public void mostrarDialogoError(){
        tvErrorGenerico.setText(res);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null ) {
            listener.onClick(view);
        }
    }
}
