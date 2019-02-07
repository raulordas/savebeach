package uem.dam.sharethebeach.sharethebeach.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.adapters.AdaptadorUsuarios;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;

public class Todos_Usuarios extends AppCompatActivity {

    AdaptadorUsuarios adapter;
    ArrayList<Usuario> datos;
    private ChildEventListener cel;
    private DatabaseReference dbr;
    RecyclerView rVUsuarios;
    LinearLayoutManager llm;
    Usuario m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos__usuarios);

        datos = new ArrayList<Usuario>();
        adapter = new AdaptadorUsuarios(datos, this);
        rVUsuarios = findViewById(R.id.recyclerViewUsuarios);

        llm = new LinearLayoutManager(this);

        rVUsuarios.setLayoutManager(llm);
        rVUsuarios.setAdapter(adapter);
        rVUsuarios.setItemAnimator(new DefaultItemAnimator());
        dbr = FirebaseDatabase.getInstance().getReference().child("Usuario");
    }

    private void addChildEventListener() {
        if(cel == null){
            cel= new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    System.out.println("Nuevo mensaje");
                    m = (Usuario) dataSnapshot.getValue(Usuario.class);
                    datos.add(m);
                    adapter.notifyItemInserted(datos.size()-1);
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
            dbr.addChildEventListener(cel);
        }
    }

    protected void onResume(){
        super.onResume();
        addChildEventListener();
    }
}
