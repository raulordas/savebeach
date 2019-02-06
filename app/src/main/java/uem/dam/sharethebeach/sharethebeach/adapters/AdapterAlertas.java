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
import uem.dam.sharethebeach.sharethebeach.bean.Alerta;

public class AdapterAlertas extends RecyclerView.Adapter<AdapterAlertas.HolderAlerta> implements View.OnClickListener {

    private ArrayList<Alerta> listaAlertas;
    private View.OnClickListener listener;
    private Context context;

    public AdapterAlertas(ArrayList<Alerta> listaAlertas, Context context) {
        this.listaAlertas = listaAlertas;
        this.context = context;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public HolderAlerta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alerta_layout, parent, false);
        v.setOnClickListener(this);
        HolderAlerta holder = new HolderAlerta(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAlerta holder, int position) {

        //Demomento solo meto una imagen aleatoria.
        Glide.with(context).load(listaAlertas.get(position).getUrlImg()).into(holder.imgAlerta);
        //Resto atributos
        holder.txtTituloAlerta.setText(listaAlertas.get(position).getTitulo());
        holder.txtFecha.setText(listaAlertas.get(position).getFecha());
        holder.txtUsuCreador.setText(listaAlertas.get(position).getId_creador());
        holder.txtHora.setText(listaAlertas.get(position).getHora());
    }

    @Override
    public int getItemCount() {
        return listaAlertas.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public static class HolderAlerta extends RecyclerView.ViewHolder {
        TextView txtTituloAlerta;
        TextView txtUsuCreador;
        TextView txtFecha;
        TextView txtHora;
        ImageView imgAlerta;

        public HolderAlerta(View itemView) {
            super(itemView);
            txtTituloAlerta = itemView.findViewById(R.id.txtTitulo);
            txtUsuCreador = itemView.findViewById(R.id.txtCreador);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtHora = itemView.findViewById(R.id.txtHora);
            imgAlerta = itemView.findViewById(R.id.imgAlerta);

        }
    }




}
