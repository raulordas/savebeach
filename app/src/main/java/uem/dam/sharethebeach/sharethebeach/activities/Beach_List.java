package uem.dam.sharethebeach.sharethebeach.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import uem.dam.sharethebeach.sharethebeach.ContextoCustom;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.adapters.AdapterPlayas;
import uem.dam.sharethebeach.sharethebeach.bean.Playa;
import uem.dam.sharethebeach.sharethebeach.persistencia.PersistenciaAemet;
import uem.dam.sharethebeach.sharethebeach.views.CustomDialog;

public class Beach_List extends Base_Activity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager lm;
    private AdapterPlayas adapterPlayas;
    private Dialog errorDialog;
    private ImageView imgFiltroPlayas;
    private HashSet<String> municipios;
    private TextView tvFiltroMunicipio;
    private ArrayList<Playa> listaPlayas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvFiltroMunicipio = findViewById(R.id.tvFiltroMunicipio);
        tvFiltroMunicipio.setText(getString(R.string.TODAS_LAS_PLAYAS));

        //Capturamos el listado de playas del contexto y generamos un context menu para filtros
        municipios = new HashSet<>();
        municipios.add(getString(R.string.TODAS_LAS_PLAYAS));

        listaPlayas = new ArrayList<Playa>();
        listaPlayas.addAll(((ContextoCustom) (getApplicationContext())).getListadoPlayas());

        for (Playa aux : listaPlayas) {
            System.out.println(aux.getNombre());
            municipios.add(aux.getMunicipio());
        }

        imgFiltroPlayas = findViewById(R.id.imgFiltroPlayas);
        imgFiltroPlayas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performLongClick();
            }
        });
        registerForContextMenu(imgFiltroPlayas);

        //Cargamos el recyclerView con las playas
        recyclerView = findViewById(R.id.recyclerViewPlayas);
        lm = new LinearLayoutManager(this);
        adapterPlayas = new AdapterPlayas(listaPlayas, this);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapterPlayas);
        recyclerView.setHasFixedSize(true);

        adapterPlayas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = recyclerView.getChildAdapterPosition(view);
                PersistenciaAemet perAemet = new PersistenciaAemet(Beach_List.this);
                Playa playaSeleccionada = adapterPlayas.getPlayaAtPos(pos);
                //perAemet.getListadoPlayas().get(pos);
                System.out.println(playaSeleccionada.getId());
                perAemet.obtenerPlaya(playaSeleccionada);
            }
        });
    }

    @Override
    public int cargarLayout() {
        return R.layout.activity_beach_list;
    }

    @Override
    public boolean setDrawer() {
        return true;
    }

    /*
    La persistencia nos devuelve la información a través del callback
     */
    public void lanzarActivityPlayaCallback(Playa playa){

        if (playa != null){
            Intent i = new Intent(this, Beach_Detail.class);
            i.putExtra(getString(R.string.KEY_PLAYA_SEL),  playa);
            startActivity(i);

        } else {
            errorDialog = new CustomDialog(this, R.string.VAL_ERROR_GENERICO);
            errorDialog.show();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);

        ArrayList<String> municipiosOrdenados = new ArrayList(municipios);
        Collections.sort(municipiosOrdenados);

        for (String nombre : municipiosOrdenados) {
            menu.add(nombre);
        }
        menu.setHeaderTitle(getString(R.string.CONTEXT_MUNICIPIOS));
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final String municipio = item.getTitle().toString();
        final String filtro = String.format(getString(R.string.SELECCION_MUNICIPIO), municipio);
        tvFiltroMunicipio.setText(filtro);

        if (!municipio.equals(getString(R.string.TODAS_LAS_PLAYAS))) {
            Handler h = new Handler();
            h.post(new Runnable() {
                @Override
                public void run() {
                    filtrarPlayas(municipio);
                }
            });
        } else {
            adapterPlayas.agregarPlayas(listaPlayas);
        }
        return true;
    }

    public void filtrarPlayas(String municipio) {
        ArrayList<Playa> listaFiltrada = new ArrayList<>();
        ArrayList<Playa> listaPlayas = new ArrayList<>();
        listaPlayas.addAll(((ContextoCustom) (getApplicationContext())).getListadoPlayas());

        //Elimina el contenido de la lista del adaptador y notifica los elementos eliminados

        adapterPlayas.eliminarTodasLasPlayas();

        //Comprueba cuantos elementos coinciden con el del filtro en la lista original
        for (int i = 0; i < listaPlayas.size(); i++) {
            if (listaPlayas.get(i).getMunicipio().equals(municipio)) {
                listaFiltrada.add(listaPlayas.get(i));
            }
        }
        //Agrega los nuevos elementos filtrados al adaptador
        adapterPlayas.agregarPlayas(listaFiltrada);
    }
}
