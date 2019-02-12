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

public class AdaptadorUsuarioAlertaInfo  extends RecyclerView.Adapter<AdaptadorUsuarioAlertaInfo.UsuHolder> {

    private ArrayList<Usuario> lista;
    private Context contexto;

    public AdaptadorUsuarioAlertaInfo(ArrayList<Usuario> lista, Context contexto) {
        this.lista = lista;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public UsuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_usuario_info_alertas, parent, false);

        UsuHolder vh = new UsuHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UsuHolder holder, int position) {
        holder.nomUser.setText(lista.get(position).getNombre_completo());
        Glide.with(contexto).load(lista.get(position).getUrlFoto()).into(holder.fotoUser);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    public class UsuHolder extends RecyclerView.ViewHolder{
        TextView nomUser;
        ImageView fotoUser;

        public UsuHolder(View itemView) {
            super(itemView);
            this.nomUser = itemView.findViewById(R.id.InfoAleUsuNom);
            this.fotoUser = itemView.findViewById(R.id.fotoUsuario);
        }

    }

    public void clear(){
        lista.clear();
    }
}
