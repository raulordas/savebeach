package uem.dam.sharethebeach.sharethebeach.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import uem.dam.sharethebeach.sharethebeach.ContextoCustom;
import uem.dam.sharethebeach.sharethebeach.R;

/*
Esta clase incorpora la barra de navegación Toolbar y asigna el layout del activity que
exienda de ella utilizando un método abstracto. Aquí incorporamos toda la lógica que otorga
funcionalidad al menu de navegación. Al hacer una clase abstracta conseguimos no tener que repetir
el código en cada una de los activities en los que queremos hacer uso del menú.
 */
public abstract class Base_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Activamos la funcionalidad de transiciones
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        /*
        Asignamos el layout del Base activity que contiene la barra de navegación y su menu
        de opciones
         */
        setContentView(R.layout.base_activity);

        /*
        Asignamos el Toolbar que hemos diseñado mediante el método setSupportActionBar().
        Este método pérmite sustituir el ActionBar por un ToolBar más avanzado.
         */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        //toolbar.setNavigationIcon(R.drawable.ic_back_scalable);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle(getLocalClassName());

        /*
        Generamos una variable de tipo View para almacenar el contenedor del XML que
        funcionará como viewport de los diferentes layout en función del activity que intente
        hacer uso de el. Para que las clases que extiendan de esta tengan acceso a suministrar
        el layout en cada momento, hacemos uso del método cargarLayout() que le asigna el layout
        al contenedor rel mediante el uso de getLayoutInflater.inflate().
         */

        View container = findViewById(R.id.contenedorLayout);
        ConstraintLayout rel = (ConstraintLayout) container;
        getLayoutInflater().inflate(cargarLayout(), rel);
        /*
        Elemento botón acción flotante
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Carga el Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //Decide si estamos en modo Menu o Modo Back en función del parámetro que recibe el método
        //desde la implementación en el activity
        toggle.setDrawerIndicatorEnabled(setDrawer());
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Añadimos un listener para que cuando se haga click en el boton back de navegación
        //invoquemos el metodo onBackpressed que nos devolvera al activity anterior.
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Carga la navegación
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //Metodo abstracto que utilizamos desde la clase que extienda para cargar el layout.
    public abstract int cargarLayout();

    //Metodo abstracto para establecer la modalidad del activity (menu o back)
    public abstract boolean setDrawer();
    
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Infla el menu y añade los items al menu
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Beaches) {

        } else if (id == R.id.nav_Beach_Map) {
            startActivity(new Intent(this, User_Profile_Activity.class));

        } else if (id == R.id.nav_Beach_Fans) {
            startActivity(new Intent(this, User_List.class));

        } else if (id == R.id.nav_Beach_Alerts) {

            Intent i = new Intent(this,Alertas_Activity.class);
            startActivity(i);

        } else if (id == R.id.nav_Login) {

            //Si el usuario hace Logout, le asigna null al usuario del contexto de la aplicación
        } else if (id == R.id.nav_Logout) {
            ((ContextoCustom) getApplicationContext()).setUser(null);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
