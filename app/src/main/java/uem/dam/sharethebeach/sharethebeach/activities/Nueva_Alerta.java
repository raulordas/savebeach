package uem.dam.sharethebeach.sharethebeach.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import uem.dam.sharethebeach.sharethebeach.ContextoCustom;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.adapters.AdapterSpinner;

public class Nueva_Alerta extends Base_Activity {

    Spinner pSpi;
    AdapterSpinner adaptador;
    private int ano,mes,dia,hora,minutos;
    TextView fecha;
    TextView horaTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_nueva__alerta);


        pSpi = findViewById(R.id.spPlayas);
        adaptador = new AdapterSpinner(this,((ContextoCustom) (getApplicationContext())).getListadoPlayas());
        pSpi.setAdapter(adaptador);

        fecha = findViewById(R.id.txtMuestraFecha);
        horaTxt = findViewById(R.id.txtMuestraHora);
    }

    @Override
    public int cargarLayout() {
        return R.layout.activity_nueva__alerta;
    }

    @Override
    public boolean setDrawer() {
        return false;
    }


    public void Fecha(View v){

        final Calendar c = Calendar.getInstance();
         dia = c.get(Calendar.DAY_OF_MONTH);
         mes = c.get(Calendar.MONTH);
         ano = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, dia,mes,ano);
        datePickerDialog.show();
    }

    public void Hora(View v){
        final Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horaTxt.setText(hourOfDay + ":" + minute);
            }
        },hora,minutos,true);
        timePickerDialog.show();


    }


}
