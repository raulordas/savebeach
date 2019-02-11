package uem.dam.sharethebeach.sharethebeach.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import uem.dam.sharethebeach.sharethebeach.ContextoCustom;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.bean.Alerta;
import uem.dam.sharethebeach.sharethebeach.bean.Playa;

public class Informacion_alertas  extends Base_Activity {

    TextView titulo;
    TextView descripcion;
    ImageView imgAlerta;
    TextView fecha;
    TextView hora;
    ImageView imgPlaya;
    TextView nomPlaya;
    TextView muniPlaya;



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


    }

    @Override
    public int cargarLayout() {
        return R.layout.activity_informacion_alertas;
    }

    @Override
    public boolean setDrawer() {
        return false;
    }
}
