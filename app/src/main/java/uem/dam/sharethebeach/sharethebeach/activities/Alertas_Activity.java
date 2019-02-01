package uem.dam.sharethebeach.sharethebeach.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import uem.dam.sharethebeach.sharethebeach.ContextoCustom;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.bean.Alerta;

public class Alertas_Activity extends Base_Activity {


    private DatabaseReference dbR;
    private ChildEventListener cel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_alertas_);

        dbR = FirebaseDatabase.getInstance().getReference().child("Alerta");

        ArrayList<String> lista = new ArrayList<>();
        lista.add("USU1");
        lista.add("USU2");
        lista.add("USU3");
        lista.add("USU4");

        String key = dbR.push().getKey();

        //String id_usuario_creador = ((ContextoCustom) (getApplicationContext())).getUser().getUid();


        Alerta alert = new Alerta(key,"id usuario","descripcion","titulo",
                "id_playas",lista);


        dbR.child(key).setValue(alert);


      cel = new ChildEventListener() {
          @Override
          public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


              Alerta alertita = dataSnapshot.getValue(Alerta.class);

              System.out.println(alertita);

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

    @Override
    public int cargarLayout() {
        return R.layout.activity_alertas_;
    }

    @Override
    public boolean setDrawer() {
        return false;
    }
}
