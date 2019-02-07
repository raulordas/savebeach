package uem.dam.sharethebeach.sharethebeach.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.UsuariosViewHolder> implements View.OnClickListener {

    private ArrayList<Usuario> lista;
    private View.OnClickListener listener;
    private Context contexto;

    public AdaptadorUsuarios(ArrayList<Usuario> lista, Context context) {
        this.contexto = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public UsuariosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.users_item, viewGroup, false);
        v.setOnClickListener(this);
        UsuariosViewHolder vh = new UsuariosViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UsuariosViewHolder usuariosViewHolder, int i) {
        usuariosViewHolder.bindMensaje(lista.get(i));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class UsuariosViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombre;
        TextView tvFechaNac;
        ImageView fotoUser;
        String id;

        public UsuariosViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvFechaNac = itemView.findViewById(R.id.tvFechaNac);
            fotoUser = itemView.findViewById(R.id.fotoUsuario);
        }

        public void bindMensaje(Usuario m){
            tvNombre.setText(m.getNombre_completo());
            tvFechaNac.setText(m.getFechaNac() + "");
            Glide.with(contexto).load(m.getUrlFoto()).into(fotoUser);
        }
    }

    public void clear(){
        lista.clear();
    }


}

