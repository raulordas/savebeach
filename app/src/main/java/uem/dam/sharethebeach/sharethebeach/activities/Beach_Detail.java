package uem.dam.sharethebeach.sharethebeach.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.bean.Playa;

public class Beach_Detail extends Base_Activity {
    //Constantes que representan los estados de AEMET
    private static final String[] ESTADOS_LLUVIOSOS = {"Intervalos nubosos con lluvia",
            "Nuboso con lluvia", "Muy nuboso con lluvia", "Cubierto con lluvia", "Chubascos"};

    private static final String[] ESTADOS_DESPEJADOS = {"Despejado", "Poco nuboso"};

    private static final String[] ESTADOS_CUBIERTOS = {"Intervalos nubosos",
            "Nuboso", "Muy nuboso", "Cubierto", "Nubes altas"};

    private static final String[] ESTADOS_NIEVE = {"Intervalos nubosos con nieve", "Nuboso con nieve"
            ,"Muy nuboso con nieve", "Cubierto con nieve", "Granizo"};

    private static final String[] ESTADOS_NIEBLAS = {"Bruma", "Niebla", "Calima"};

    private static final String[] TORMENTAS = {"Tormenta"};

    private TextView tvBeachNombre;
    private TextView tvBeachGrados;
    private TextView tvBeachEstadoCielo2;
    private TextView tvBeachViento;
    private TextView tvBeachOleaje;
    private TextView tvBeachTempAgua;
    private TextView tvBeachUV;
    private TextView tvBeachFecha;
    private ImageView imgBeachEstadoCielo;
    private ImageView imgBeachEstadoCielo2;
    private Playa playa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Recibimos la playa a mostrar mediante un intent
        playa  = (Playa) getIntent().getParcelableExtra(getString(R.string.KEY_PLAYA_SEL));

        //Nombre de la playa
        tvBeachNombre = findViewById(R.id.tvBeachNombre);
        tvBeachNombre.setText(String.format(getString(R.string.VAL_BEACH_NOMBRE), playa.getNombre()));

        //Temperatura
        tvBeachGrados = findViewById(R.id.tvBeachGrados);
        tvBeachGrados.setText(String.format(getString(R.string.VAL_BEACH_GRADOS), playa.getTemperatura()));

        //Atributo que representa mediante una imagen el estado del cielo
        imgBeachEstadoCielo = findViewById(R.id.imgBeachEstadoCielo);
        imgBeachEstadoCielo2 = findViewById(R.id.imgBeachEstadoCielo2);

        //Comprobamos mediante un metodo el estado del cielo y le asignamos un icono
        if (playa != null) {

            if (playa.getEstadoCielo() != null) {
                comprobarEstado(playa.getEstadoCielo(), ESTADOS_LLUVIOSOS, R.drawable.ic_raindrops2, R.color.azulViento);
                comprobarEstado(playa.getEstadoCielo(), ESTADOS_DESPEJADOS, R.drawable.ic_sun94, R.color.amarilloSoleado);
                comprobarEstado(playa.getEstadoCielo(), ESTADOS_CUBIERTOS, R.drawable.ic_cloudy, R.color.azulNubes);
                comprobarEstado(playa.getEstadoCielo(), ESTADOS_NIEBLAS, R.drawable.ic_fog, R.color.azulViento);
                comprobarEstado(playa.getEstadoCielo(), ESTADOS_NIEVE, R.drawable.ic_snowing, R.color.blancoNieve);
                comprobarEstado(playa.getEstadoCielo(), TORMENTAS, R.drawable.ic_weather, R.color.azulViento);

                //Estado del cielo
                tvBeachEstadoCielo2 = findViewById(R.id.tvBeachEstadoCielo2);
                tvBeachEstadoCielo2.setText(String.format(getString(R.string.VAL_BEACH_ESTADO_CIELO), playa.getEstadoCielo()));
            }

            if (playa.getViento() != null) {
                //Viento
                tvBeachViento = findViewById(R.id.tvBeachViento);
                tvBeachViento.setText(String.format(getString(R.string.VAL_VIENTO), playa.getViento()));
            }

            if (playa.getOleaje() != null) {
                //Oleaje
                tvBeachOleaje = findViewById(R.id.tvBeachOleaje);
                tvBeachOleaje.setText(String.format(getString(R.string.VAL_OLEAJE), playa.getOleaje()));
            }

            //Temperatura Agua
            tvBeachTempAgua = findViewById(R.id.tvBeachTempAgua);
            tvBeachTempAgua.setText(String.format(getString(R.string.VAL_TEMP_AGUA), playa.getTemperaturaAgua()));

            //Indice Ultravioleta
            tvBeachUV = findViewById(R.id.tvBeachUV);
            tvBeachUV.setText(String.format(getString(R.string.VAL_UV), playa.getIndiceUV()));

            if (playa.getFecha_actualizacion() != null) {
                //Fecha
                tvBeachFecha = findViewById(R.id.tvBeachFecha);
                System.out.println(playa.getFecha_actualizacion());

                //Convertimos la fecha a formato String desde Date
                SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
                String fechaFinal = sdf.format(playa.getFecha_actualizacion());
                tvBeachFecha.setText(String.format(getString(R.string.VAL_FECHA_ACT), fechaFinal));
            }
        }
    }

    @Override
    public int cargarLayout() {
        return R.layout.beach_layout;
    }

    @Override
    public boolean setDrawer() {
        return false;
    }

    private void comprobarEstado(String estadoConsulta, String[] estadosAemet, int idDrawable, int color){
        boolean stop = false;

        for (int i = 0; i < estadosAemet.length && !stop; i++) {
            if (estadoConsulta.equalsIgnoreCase(estadosAemet[i])) {
                stop = true;
            }
        }

        if(stop) {
            imgBeachEstadoCielo.setImageResource(idDrawable);
            imgBeachEstadoCielo.setColorFilter(ContextCompat.getColor(getBaseContext(), color),
                    android.graphics.PorterDuff.Mode.SRC_IN);
            imgBeachEstadoCielo2.setImageResource(idDrawable);
            imgBeachEstadoCielo2.setColorFilter(ContextCompat.getColor(getBaseContext(), color),
                    android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }

    public void verPlayaEnMapa(View view){
        Intent i = new Intent(this, MapsActivity.class);
        i.putExtra(getString(R.string.KEY_PLAYA_SEL), playa);
        startActivity(i);
    }
}
