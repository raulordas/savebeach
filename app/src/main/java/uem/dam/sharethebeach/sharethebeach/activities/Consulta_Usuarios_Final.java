package uem.dam.sharethebeach.sharethebeach.activities;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.adapters.AdaptadorUsuarios;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;

public class Consulta_Usuarios_Final extends Base_Activity {

    Usuario u;
    private DatabaseReference dbr;
    AdaptadorUsuarios adapter;
    ArrayList<Usuario> datos;

    TextView nombre;
    //TextView email;
    TextView fechaNac;
    TextView descripcion;
    ImageView fotoUsuario;
    String emailEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new AdaptadorUsuarios(datos, this);
        u = getIntent().getParcelableExtra("CLAVE");

        nombre = findViewById(R.id.tvNombreF);
        //email = findViewById(R.id.tvEmailF);
        fechaNac = findViewById(R.id.tvFechaNacF);
        descripcion = findViewById(R.id.tvDescripcionF);
        fotoUsuario = findViewById(R.id.foto_UserF);

        Glide.with(this).load(u.getUrlFoto()).into(fotoUsuario);
        nombre.setText(u.getNombre_completo());
        //email.setText(u.getEmail());
        fechaNac.setText(u.getFechaNac());
        descripcion.setText(u.getDescripcion());

        emailEnviar = u.getEmail();

        dbr = FirebaseDatabase.getInstance().getReference().child("Usuario");
    }

    public void enviarMail(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_send_mail);
        dialog.show();

        final EditText asunto;
        final EditText mensaje;
        Button btnEnviar;

        asunto = dialog.findViewById(R.id.etAsunto);
        mensaje = dialog.findViewById(R.id.etMensaje);
        btnEnviar = dialog.findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtAsunto = asunto.getText().toString();
                String txtMensaje = mensaje.getText().toString();

                String[] TO = {emailEnviar}; //aquí pon tu correo
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
                //Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, txtAsunto);
                emailIntent.putExtra(Intent.EXTRA_TEXT, txtMensaje);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Consulta_Usuarios_Final.this, "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int cargarLayout() {
        return R.layout.activity_consulta__usuarios__final;
    }

    @Override
    public boolean setDrawer() {
        return true;
    }
}