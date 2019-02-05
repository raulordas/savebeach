package uem.dam.sharethebeach.sharethebeach.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.RequestManager;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.HolderUsers> implements View.OnClickListener {
    private ArrayList<Usuario> listaUsuarios;
    private View.OnClickListener listener;
    private RequestManager glide;

    public AdapterUsers(ArrayList<Usuario> listaUsuarios, RequestManager glide) {
        this.listaUsuarios = listaUsuarios;
        this.glide = glide;
    }

    @NonNull
    @Override
    public HolderUsers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        v.setOnClickListener(this);
        HolderUsers holder = new HolderUsers(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUsers holder, int position) {
        holder.userName.setText(listaUsuarios.get(position).getNombre_completo());

        if (listaUsuarios.get(position).getUrlFoto() != null) {
            glide.load(listaUsuarios.get(position).getUrlFoto()).into(holder.imgUser);
        }
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public static class HolderUsers extends RecyclerView.ViewHolder {
        private CircleImageView imgUser;
        private TextView userName;

        public HolderUsers(View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user);
            userName = itemView.findViewById(R.id.tvUserName);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
