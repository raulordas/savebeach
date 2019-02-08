package uem.dam.sharethebeach.sharethebeach.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private TabLayout alertsTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_alertas_);

        //String id_usuario_creador = ((ContextoCustom) (getApplicationContext())).getUser().getUid();
        boton = findViewById(R.id.btnAniadirAlerta);

        //Si el usuario que ha entrado no esta registrado no podra añadir alertas
        /*
        if(((ContextoCustom) (getApplicationContext())).getUser() == null){

            boton.setEnabled(false);
            boton.setVisibility(View.INVISIBLE);


        }
        */


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


        //Abrir nueva activity para añadir una nueva alerta.
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Alertas_Activity.this, Nueva_Alerta.class);
                startActivity(i);

            }
        });

         alertsTabs = findViewById(R.id.alertasTabs);
         alertsTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("TAB", tab.getText().toString());

                if (tab.getText().equals(getString(R.string.tab_filtrar))) {

                } else if(tab.getText().equals(getString(R.string.tab_ordenar))) {
                    verDialogOrdenar();
                }
            }

             @Override
             public void onTabUnselected(TabLayout.Tab tab) {

             }

             @Override
             public void onTabReselected(TabLayout.Tab tab) {

                 if (tab.getText().equals(getString(R.string.tab_filtrar))) {

                 } else if(tab.getText().equals(getString(R.string.tab_ordenar))) {
                     verDialogOrdenar();
                 }
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
        return true;
    }

    public void verDialogOrdenar() {
        final CharSequence[] lista = {"Ordenar por Nombre de A-Z", "Ordenar Por nombre de Z-A"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.ELEGIR_MUNICIPIO));

        builder.setItems(lista, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if ( i == 0) {
                    adaptador.ordenarAZ();
                } else {
                    adaptador.ordenarZA();
                }
            }
        });

        builder.setNeutralButton(getString(R.string.DIALOG_CANCEL), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).create().show();
    }

}
