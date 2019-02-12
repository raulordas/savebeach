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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private DatabaseReference dbRUpdate;
    private DatabaseReference dbRUser;
    private ChildEventListener cel;
    private ChildEventListener celUsu;
    RecyclerView recicler;
    LinearLayoutManager miLayoutManager;
    ArrayList<Usuario> lista = new ArrayList<>();
    ArrayList<Usuario> listaCompletaUsuarios = new ArrayList<>();
    AdaptadorUsuarioAlertaInfo adaptador;
    FirebaseUser user;
    Alerta al;



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

        user  = FirebaseAuth.getInstance().getCurrentUser();

        al = getIntent().getParcelableExtra(getString(R.string.CLAVE_ALERTA));

        dbR = FirebaseDatabase.getInstance().getReference().child("Alerta").child(al.getId()).child("usuarios_apuntados");
        dbRUser = FirebaseDatabase.getInstance().getReference().child("Usuario");
        dbRUpdate = FirebaseDatabase.getInstance().getReference().child("Alerta");



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

        addChildEventUsu();

        addChildEvent();

    }
    //Obtenemos la totalidad de los usuarios de la app
    private void addChildEventUsu() {
        if(celUsu == null) {
            celUsu = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Usuario usu =  dataSnapshot.getValue(Usuario.class);
                    listaCompletaUsuarios.add(usu);

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
            dbRUser.addChildEventListener(celUsu);

        }
    }

    private void addChildEvent() {
        if(cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String id = dataSnapshot.getValue().toString();
                    for (int i= 0; i < listaCompletaUsuarios.size();i++){

                        if(listaCompletaUsuarios.get(i).getUid().equals(id)){
                            lista.add(listaCompletaUsuarios.get(i));
                        }
                    }

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

    public void apuntarse(View v){
        if(!comprobarApuntado()){
            for (int i= 0; i < listaCompletaUsuarios.size();i++){

                if(listaCompletaUsuarios.get(i).getUid().equals(user.getUid())){

                    if(al.getUsuarios_apuntados() == null){
                        al.inicializarLista();
                    }

                    al.add_idUsu(user.getUid());

                    Map<String, Object> mapa = new HashMap<String, Object>();
                    mapa.put(al.getId(),al);
                    dbRUpdate.updateChildren(mapa);
                    Toast.makeText(this, "Enhorabuena! Ya estas registrado en este evento", Toast.LENGTH_SHORT).show();

                }
            }
        }else{
            Toast.makeText(this, "Usted ya esta apuntado a este evento", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean comprobarApuntado() {
        boolean apuntado = false;

        for (int i = 0; i < lista.size(); i++){
            if(user.getUid().equals(lista.get(i).getUid())){
                apuntado = true;
            }
        }
        return apuntado;
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
        listaCompletaUsuarios.clear();
        adaptador.clear();
    }
}
