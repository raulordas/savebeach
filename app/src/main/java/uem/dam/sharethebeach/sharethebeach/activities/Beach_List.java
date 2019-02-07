package uem.dam.sharethebeach.sharethebeach.activities;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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
    private HashSet<String> municipios;
    private ArrayList<Playa> listaPlayas;
    private TabLayout beachTabs;
    private Dialog miniProgressBar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Solicitamos la activacion de las transiciones. Siempre antes del setcontent

        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.LEFT);
        getWindow().setExitTransition(slide);

        //Capturamos el listado de playas del contexto y generamos un context menu para filtros
        municipios = new HashSet<>();
        municipios.add(getString(R.string.TODAS_LAS_PLAYAS));

        listaPlayas = new ArrayList<Playa>();
        listaPlayas.addAll(((ContextoCustom) (getApplicationContext())).getListadoPlayas());

        for (int i = 0; i < listaPlayas.size(); i++) {
            municipios.add(listaPlayas.get(i).getMunicipio());
        }

        beachTabs = findViewById(R.id.usersTabs);
        beachTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("TAB", tab.getText().toString());

                if (tab.getText().equals(getString(R.string.tab_filtrar))) {
                    verDialogFilter();
                } else if(tab.getText().equals(getString(R.string.tab_ordenar))) {
                    verDialogOrdenar();
                } else {
                    Intent i = new Intent(Beach_List.this, MapsActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                if (tab.getText().equals(getString(R.string.tab_filtrar))) {
                    verDialogFilter();
                } else if(tab.getText().equals(getString(R.string.tab_ordenar))) {
                    verDialogOrdenar();
                } else {
                    Intent i = new Intent(Beach_List.this, MapsActivity.class);
                    startActivity(i);
                }
            }
        });

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
                mostrarMiniProgressBar();
                final int pos = recyclerView.getChildAdapterPosition(view);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //int pos = recyclerView.getChildAdapterPosition(view);
                        PersistenciaAemet perAemet = new PersistenciaAemet(Beach_List.this);
                        Playa playaSeleccionada = adapterPlayas.getPlayaAtPos(pos);
                        perAemet.obtenerPlaya(playaSeleccionada);
                    }
                }, 500);
            }
        });

          /*
        Para agregar separadores entre los tabs debemos de interpretar que el TabLayout es un Linear
        Layout y acceder a estas propiedades. No lo vamos a usar por que estéticamente no nos resulta
        interesante en este proyecto.

         ((LinearLayout) (beachTabs.getChildAt(0))).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.GRAY);
        drawable.setSize(1,1);
        ((LinearLayout) (beachTabs.getChildAt(0))).setDividerDrawable(drawable);
        ((LinearLayout) (beachTabs.getChildAt(0))).setDividerPadding(10);
         */
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void lanzarActivityPlayaCallback(Playa playa){

        if (playa != null){
            Intent i = new Intent(this, Beach_Detail.class);
            i.putExtra(getString(R.string.KEY_PLAYA_SEL),  playa);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        } else {
            errorDialog = new CustomDialog(this, R.string.VAL_ERROR_GENERICO);
            errorDialog.show();
        }

        if (miniProgressBar.isShowing()) {
            miniProgressBar.cancel();
        }
    }

    public void verDialogOrdenar() {
        final CharSequence[] lista = {"Ordenar por Nombre de A-Z", "Ordenar Por nombre de Z-A"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.ELEGIR_MUNICIPIO));

        builder.setItems(lista, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if ( i == 0) {
                    adapterPlayas.ordenarAZ();
                } else {
                    adapterPlayas.ordenarZA();
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

    public void verDialogFilter() {
        ArrayList<String> municipiosOrdenados = new ArrayList(municipios);
        Collections.sort(municipiosOrdenados);

        final CharSequence[] lista = new CharSequence[municipiosOrdenados.size()];

        for (int i = 0; i < municipiosOrdenados.size(); i++){
            lista[i] = municipiosOrdenados.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.ELEGIR_MUNICIPIO));


        builder.setNeutralButton(getString(R.string.DIALOG_CANCEL), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setItems(lista, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logicaFiltroMunicipio(lista[i].toString());
            }

        }).create().show();
    }

    public String logicaFiltroMunicipio(String muni) {
        final String municipio = muni;
        final String filtro = String.format(getString(R.string.SELECCION_MUNICIPIO), municipio);

        if (!municipio.equals(getString(R.string.TODAS_LAS_PLAYAS))) {
            Handler h = new Handler();
            h.post(new Runnable() {
                @Override
                public void run() {
                    filtrarPlayas(municipio);
                }
            });
        } else {
            adapterPlayas.eliminarTodasLasPlayas();
            adapterPlayas.agregarPlayas(((ContextoCustom) (getApplicationContext())).getListadoPlayas());
        }
        return filtro;
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void mostrarMiniProgressBar() {
        miniProgressBar = new Dialog(this);
        miniProgressBar.setContentView(R.layout.progbar_mini);
        miniProgressBar.setCancelable(false);
        miniProgressBar.show();
        //Branch Change
    }

}


