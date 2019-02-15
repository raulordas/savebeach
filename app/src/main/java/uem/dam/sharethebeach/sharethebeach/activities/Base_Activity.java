package uem.dam.sharethebeach.sharethebeach.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;
import uem.dam.sharethebeach.sharethebeach.views.DialogLogin;
import uem.dam.sharethebeach.sharethebeach.views.DialogQuestion;

/*
Esta clase incorpora la barra de navegación Toolbar y asigna el layout del activity que
exienda de ella utilizando un método abstracto. Aquí incorporamos toda la lógica que otorga
funcionalidad al menu de navegación. Al hacer una clase abstracta conseguimos no tener que repetir
el código en cada una de los activities en los que queremos hacer uso del menú.
 */
public abstract class Base_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CircleImageView imgPerfil;
    private DatabaseReference dbr;
    private ChildEventListener cel;
    private ArrayList<Usuario> listaUsuarios;
    private Menu menuv;
    private DialogLogin dialogLogin;
    private NavigationView navigationView;
    private static boolean activityRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Activamos la funcionalidad de transiciones
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        activityRefresh = false;

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
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            navigationView.getMenu().findItem(R.id.nav_Logout).setVisible(false);
        } else {
            navigationView.getMenu().findItem(R.id.nav_Login).setVisible(false);
        }


        //Carga la foto de perfil en el bar header si el usuario esta loggeado
        imgPerfil = hView.findViewById(R.id.imgPerfil);
        listaUsuarios = new ArrayList<>();
        dbr = FirebaseDatabase.getInstance().getReference().child("Usuario");

        if (cel == null) {
            cel = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Usuario user = dataSnapshot.getValue(Usuario.class);
                    listaUsuarios.add(user);

                    FirebaseUser userFirebase = FirebaseAuth.getInstance().getCurrentUser();

                    if (userFirebase != null) {
                        for (Usuario aux : listaUsuarios) {
                            Log.e("usuari", aux.getUid());
                            if (aux.getUid().equals(userFirebase.getUid())) {
                                Glide.with(Base_Activity.this).load(aux.getUrlFoto()).into(imgPerfil);
                                dbr.removeEventListener(cel);
                            }
                        }
                    }

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
        this.menuv = menu;

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            menu.getItem(0).setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cuenta) {
            startActivity(new Intent(this, User_Profile_Activity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Beaches) {
            startActivity(new Intent(this, Beach_List.class));

        } else if (id == R.id.nav_Beach_Map) {
            //Aquí va algo

        } else if (id == R.id.nav_Beach_Alerts) {

            Intent i = new Intent(this,Alertas_Activity.class);
            startActivity(i);

        } else if (id == R.id.nav_Login) {
            cargarDialogLogin();

            //Si el usuario hace Logout, le asigna null al usuario del contexto de la aplicación
        } else if (id == R.id.nav_Logout) {

            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String desconectar = String.format(getString(R.string.DIALOG_SEGURO_LOG_OUT), email);
                String res = String.format(getString(R.string.STRING_FILL), desconectar);

                final DialogQuestion question = new DialogQuestion(this, res);

                question.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseAuth.getInstance().signOut();
                        menuv.getItem(0).setVisible(false);
                        Glide.with(Base_Activity.this).load(R.mipmap.ic_saveicosave_round).into(imgPerfil);
                        question.cancel();

                        navigationView.getMenu().findItem(R.id.nav_Logout).setVisible(false);
                        navigationView.getMenu().findItem(R.id.nav_Login).setVisible(true);

                        if (activityRefresh) {
                            Intent i = new Intent(Base_Activity.this, Alertas_Activity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            finish();
                        }

                    }
                });

                question.show();
            }

        } else if (id == R.id.nav_Usuarios) {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                Toast.makeText(this, "Debes iniciar sesión", Toast.LENGTH_SHORT).show();

            } else {
                Intent i = new Intent(this,Todos_Usuarios.class);
                startActivity(i);
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Metodo que abre la ventana de dialogo Login
    public void cargarDialogLogin() {
        dialogLogin = new DialogLogin(this);
        dialogLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dialogLogin.comprobarCamposVacios()) {
                    Usuario user = dialogLogin.getDatos();

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                            .addOnCompleteListener(Base_Activity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        navigationView.getMenu().findItem(R.id.nav_Logout).setVisible(true);
                                        navigationView.getMenu().findItem(R.id.nav_Login).setVisible(false);
                                        menuv.getItem(0).setVisible(true);

                                        FirebaseUser userFirebase = FirebaseAuth.getInstance().getCurrentUser();

                                        if (userFirebase != null) {
                                            for (Usuario aux : listaUsuarios) {
                                                Log.e("usuari", aux.getUid());
                                                if (aux.getUid().equals(userFirebase.getUid())) {
                                                    Glide.with(Base_Activity.this).load(aux.getUrlFoto()).into(imgPerfil);
                                                    dbr.removeEventListener(cel);
                                                }
                                            }
                                        }

                                        dialogLogin.cancel();

                                    } else {
                                        // Si falla mostramos un dialog que diga que no se ha podido conectar
                                        //con dicha informacion. Pendiente de implementar

                                    }
                                }
                            });
                }
            }
        });
        dialogLogin.show();

    }

    protected void setActivityAlertas() {
        this.activityRefresh = true;
    }
}
