package uem.dam.sharethebeach.sharethebeach.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import uem.dam.sharethebeach.sharethebeach.ContextoCustom;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.adapters.AdapterAlertas;
import uem.dam.sharethebeach.sharethebeach.bean.Alerta;

public class Alertas_Activity extends Base_Activity {


    private DatabaseReference dbR;
    private ChildEventListener cel;

    AdapterAlertas adaptador;
    RecyclerView recicler;
    LinearLayoutManager miLayoutManager;
    ArrayList<Alerta> lista = new ArrayList<>();
    FloatingActionButton boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_alertas_);

        //String id_usuario_creador = ((ContextoCustom) (getApplicationContext())).getUser().getUid();
        boton = findViewById(R.id.btnAniadirAlerta);

        //Si el usuario que ha entrado no esta registrado no podra añadir alertas
        if(((ContextoCustom) (getApplicationContext())).getUser() == null){

            boton.setEnabled(false);
            boton.setVisibility(View.INVISIBLE);


        }


        dbR = FirebaseDatabase.getInstance().getReference().child("Alerta");

        recicler = findViewById(R.id.recyclerViewAlertas);
        recicler.setHasFixedSize(true);

        miLayoutManager = new LinearLayoutManager(this);
        adaptador = new AdapterAlertas(lista, this);

        recicler.setAdapter(adaptador);
        recicler.setLayoutManager(miLayoutManager);
        recicler.setItemAnimator(new DefaultItemAnimator());


        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AL PULSAR UNA ALERTA
            }
        });


        addChildEvent();
    }

    private void addChildEvent() {
        if(cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Alerta alert = dataSnapshot.getValue(Alerta.class);
                    lista.add(alert);
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
        return R.layout.activity_alertas_;
    }

    @Override
    public boolean setDrawer() {
        return false;
    }

    public void creacio_Alerta_Pruebas(){


        ArrayList<String> lista = new ArrayList<>();
        lista.add("USU1");
        lista.add("USU2");
        lista.add("USU3");
        lista.add("USU4");

        String key = dbR.push().getKey();



        Alerta alert = new Alerta(key,"id usuario","descripcion","titulo",
                "id_playas","25/05/2015","09:00AM",lista);


        dbR.child(key).setValue(alert);


    }
}
