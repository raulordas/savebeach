package uem.dam.sharethebeach.sharethebeach.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;

import uem.dam.sharethebeach.sharethebeach.ContextoCustom;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.adapters.AdapterSpinner;

public class Nueva_Alerta extends AppCompatActivity {

    Spinner pSpi;
    AdapterSpinner adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva__alerta);


        pSpi = findViewById(R.id.spPlayas);
        adaptador = new AdapterSpinner(this,((ContextoCustom) (getApplicationContext())).getListadoPlayas());
        pSpi.setAdapter(adaptador);
    }
}
