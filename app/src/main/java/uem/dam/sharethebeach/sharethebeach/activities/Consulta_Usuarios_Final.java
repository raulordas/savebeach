package uem.dam.sharethebeach.sharethebeach.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    TextView email;
    TextView fechaNac;
    TextView descripcion;
    ImageView fotoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new AdaptadorUsuarios(datos, this);
        u = getIntent().getParcelableExtra("CLAVE");

        nombre = findViewById(R.id.tvNombreF);
        email = findViewById(R.id.tvEmailF);
        fechaNac = findViewById(R.id.tvFechaNacF);
        descripcion = findViewById(R.id.tvDescripcionF);
        fotoUsuario = findViewById(R.id.foto_UserF);

        Glide.with(this).load(u.getUrlFoto()).into(fotoUsuario);
        nombre.setText(u.getNombre_completo());
        email.setText(u.getEmail());
        fechaNac.setText("Fecha Nacimiento: " + u.getFechaNac());
        descripcion.setText(u.getDescripcion());

        dbr = FirebaseDatabase.getInstance().getReference().child("Usuario");
    }

    public void enviarMail(View view) {

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