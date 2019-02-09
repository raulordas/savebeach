package uem.dam.sharethebeach.sharethebeach.activities;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.bean.UsuarioChat;

public class chatGeneral extends AppCompatActivity {

    //RecyclerView recycler;
    EditText mensajeEnviado;
    ImageView ivEnviar;
    FirebaseUser user;
    DatabaseReference dbr;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_general);

        user  = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            uid = user.getUid();

        } else {
            //Aqui ira algo
        }

        //recycler = findViewById(R.id.recycler_mensajes);
        mensajeEnviado = findViewById(R.id.editTextMensaje);
        ivEnviar = findViewById(R.id.ivEnviar);

        dbr = FirebaseDatabase.getInstance().getReference().child("Chat");

        ivEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensaje();
            }
        });
    }

    private void enviarMensaje() {
        String mensaje = mensajeEnviado.getText().toString();
        UsuarioChat uc = new UsuarioChat(mensaje, uid);

        if (mensaje.trim().isEmpty()) {
            //No ocurre nada
        } else {
            dbr.push().setValue(uc);
        }
    }
}
