package uem.dam.sharethebeach.sharethebeach.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import uem.dam.sharethebeach.sharethebeach.ContextoCustom;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.adapters.AdaptadorUsuarioAlertaInfo;
import uem.dam.sharethebeach.sharethebeach.adapters.AdapterAlertas;
import uem.dam.sharethebeach.sharethebeach.bean.Alerta;
import uem.dam.sharethebeach.sharethebeach.bean.Playa;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;

public class Informacion_alertas  extends Base_Activity {

    TextView titulo;
    TextView descripcion;
    ImageView imgAlerta;
    TextView fecha;
    TextView hora;
    ImageView imgPlaya;
    TextView nomPlaya;
    TextView muniPlaya;
    private DatabaseReference dbR;
    private ChildEventListener cel;
    RecyclerView recicler;
    LinearLayoutManager miLayoutManager;
    ArrayList<Usuario> lista = new ArrayList<>();
    AdaptadorUsuarioAlertaInfo adaptador;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_informacion_alertas);
        getSupportActionBar().setTitle("Informacion");

        titulo = findViewById(R.id.txtInfTitulo);
        descripcion = findViewById(R.id.txtInfDescrip);
        fecha = findViewById(R.id.txtInfoFecho);
        hora = findViewById(R.id.txtInfoHora);
        nomPlaya = findViewById(R.id.txtNombreLocal);
        muniPlaya = findViewById(R.id.txtNombreMuni);
        imgAlerta = findViewById(R.id.imgAlertInfo);
        imgPlaya = findViewById(R.id.imgPlayaInfoAlert);

        Alerta al = getIntent().getParcelableExtra(getString(R.string.CLAVE_ALERTA));

        dbR = FirebaseDatabase.getInstance().getReference().child("Alerta").child(al.getId()).child("usuarios_apuntados");

        recicler = findViewById(R.id.reciUsuInfo);
        recicler.setHasFixedSize(true);

        miLayoutManager = new LinearLayoutManager(this);
        adaptador = new AdaptadorUsuarioAlertaInfo(lista, this);

        recicler.setAdapter(adaptador);
        recicler.setLayoutManager(miLayoutManager);
        recicler.setItemAnimator(new DefaultItemAnimator());

        titulo.setText(al.getTitulo());
        descripcion.setText(al.getDescripcion());
        fecha.setText( al.getFecha());
        hora.setText( al.getHora());

        if(al.getUrlImg().equals("DEFAULT")){
            imgAlerta.setImageDrawable(getDrawable(R.drawable.beach_sample));
        }else{
            Glide.with(this).load(al.getUrlImg()).into(imgAlerta);
        }



        ArrayList<Playa> listaP = ((ContextoCustom) (getApplicationContext())).getListadoPlayas();

        for (int i = 0; i < listaP.size(); i++){

            if(listaP.get(i).getId().equals(al.getId_playa())){

                nomPlaya.setText(listaP.get(i).getNombre());
                muniPlaya.setText(listaP.get(i).getMunicipio());
                Bitmap b = null;

                String url = "images/" + listaP.get(i).getId() + ".jpg";
                InputStream stream = null;

                try {
                    stream = this.getAssets().open(url);
                    b = BitmapFactory.decodeStream(stream);
                    imgPlaya.setImageBitmap(b);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        addChildEvent();
    }

    private void addChildEvent() {
        if(cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Usuario usu = dataSnapshot.getValue(Usuario.class);
                    System.out.println(usu);
                    lista.add(usu);
                    adaptador.notifyItemInserted(lista.size() - 1);

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            dbR.addChildEventListener(cel);
        }
    }

    @Override
    public int cargarLayout() {
        return R.layout.activity_informacion_alertas;
    }

    @Override
    public boolean setDrawer() {
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        lista.clear();
        adaptador.clear();
    }
}
