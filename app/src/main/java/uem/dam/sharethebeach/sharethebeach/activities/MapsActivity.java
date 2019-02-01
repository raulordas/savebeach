package uem.dam.sharethebeach.sharethebeach.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import uem.dam.sharethebeach.sharethebeach.ContextoCustom;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.bean.Playa;

public class MapsActivity extends Base_Activity implements OnMapReadyCallback {
    private ArrayList<Playa> listaPlayas;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        listaPlayas = ((ContextoCustom) (getApplicationContext())).getListadoPlayas();
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public int cargarLayout() {
        return R.layout.activity_maps;
    }

    @Override
    public boolean setDrawer() {
        return false;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Playa playa = getIntent().getParcelableExtra(getString(R.string.KEY_PLAYA_SEL));

        if (playa == null) {
            for (Playa aux: listaPlayas) {
                LatLng gms = new LatLng(aux.getLatitud(), aux.getLongitud());
                //.setIcon(generateBitmapDescriptorFromRes(this, R.drawable.ic_cloudy))
                mMap.addMarker(new MarkerOptions().position(gms).title(aux.getNombre()));
            }
            LatLng gmsAux = new LatLng(listaPlayas.get(0).getLatitud(), listaPlayas.get(0).getLongitud());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(gmsAux));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(7));
        } else {
            // Add a marker in Sydney and move the camera
            LatLng localizacionPlaya = new LatLng(playa.getLatitud(), playa.getLongitud());
            mMap.addMarker(new MarkerOptions().position(localizacionPlaya).title(playa.getNombre()));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Toast.makeText(getApplicationContext(), "Y HACEMOS LO QUE SEA AQUI", Toast.LENGTH_LONG).show();
                    return true;
                }
            });
            mMap.addMarker(new MarkerOptions().position(localizacionPlaya).title("ALERTA DE MEDUSAS"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(localizacionPlaya));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(12));
        }
    }
    public static BitmapDescriptor generateBitmapDescriptorFromRes(Context context, int resId) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        drawable.setBounds(
                0,
                0,
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    //Metodo pendiente de implementar
    public void verTodasLasPlayas(){

    }

    //Metodo pendiente de implementar
    public void cargarPlaya(){

    }

    //Metodo pendiente de implementar
    public void mostrarAlertasPlayaSeleccionada(){

    }

    //Metodo pendiente de implementar
    public void verAlertaSeleccionada(){

    }
}
