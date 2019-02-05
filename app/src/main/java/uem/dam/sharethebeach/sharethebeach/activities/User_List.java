package uem.dam.sharethebeach.sharethebeach.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.adapters.AdapterUsers;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;

public class User_List extends Base_Activity {
    private RecyclerView recyclerUserList;
    private LinearLayoutManager lm;
    private AdapterUsers adapter;
    private ArrayList<Usuario> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_user__list);
        listaUsuarios = new ArrayList();
        Usuario u = new Usuario();
        u.setNombre_completo("Raul Perez");
        listaUsuarios.add(u);

        recyclerUserList = findViewById(R.id.recycler_user_list);
        lm = new LinearLayoutManager(this);
        recyclerUserList.setLayoutManager(lm);
        adapter = new AdapterUsers(listaUsuarios, Glide.with(this));
        recyclerUserList.setAdapter(adapter);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int cargarLayout() {
        return R.layout.activity_user__list;
    }

    @Override
    public boolean setDrawer() {
        return false;
    }
}
