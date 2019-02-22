package uem.dam.sharethebeach.sharethebeach.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.bean.Playa;

public class AdapterPlayas extends RecyclerView.Adapter<AdapterPlayas.HolderPlaya> implements View.OnClickListener {
    private ArrayList<Playa> listaPlayas;
    private View.OnClickListener listener;
    private Context context;

    public AdapterPlayas(ArrayList<Playa> listaPlayas, Context context) {
        this.listaPlayas = listaPlayas;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public HolderPlaya onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.beach_item, parent, false);
        v.setOnClickListener(this);
        HolderPlaya holder = new HolderPlaya(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPlaya holder, int position) {

        //Mapeamos la carpeta de imagenes asignando cada imagen a su playa
        Bitmap b = null;
        String url = "images/" + listaPlayas.get(position).getId() + ".jpg";
        InputStream stream = null;

        try {
            stream = context.getAssets().open(url);
            b = BitmapFactory.decodeStream(stream);
            holder.imgPlaya.setImageBitmap(b);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Asignamos el nombre y el municipio de la playa
        holder.tvNombrePlaya.setText(listaPlayas.get(position).getNombre());
        holder.tvMunicipioPlaya.setText(listaPlayas.get(position).getMunicipio());
    }

    @Override
    public int getItemCount() {
        return listaPlayas.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public static class HolderPlaya extends RecyclerView.ViewHolder {
        TextView tvNombrePlaya;
        TextView tvMunicipioPlaya;
        ImageView imgPlaya;

        public HolderPlaya(View itemView) {
            super(itemView);
            tvMunicipioPlaya = itemView.findViewById(R.id.tvMunicipioPlaya);
            tvNombrePlaya = itemView.findViewById(R.id.tvNombrePlaya);
            imgPlaya = itemView.findViewById(R.id.imgPlaya);
        }
    }

    public Playa getPlayaAtPos(int pos) {
        return listaPlayas.get(pos);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public void agregarPlayas(ArrayList<Playa> playas) {
        listaPlayas.addAll(playas);
        //notifyItemRangeChanged(0, getItemCount());
        notifyDataSetChanged();
    }

    public void eliminarTodasLasPlayas(){
        int size = listaPlayas.size();
        listaPlayas.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void ordenarAZ() {
        Collections.sort(listaPlayas, new Comparator<Playa>() {
            @Override
            public int compare(Playa playa, Playa t1) {
                return playa.getNombre().compareTo(t1.getNombre());
            }
        });
        notifyDataSetChanged();
    }

    public void ordenarZA() {
        Collections.sort(listaPlayas, new Comparator<Playa>() {
            @Override
            public int compare(Playa playa, Playa t1) {
                return (playa.getNombre().compareTo(t1.getNombre())) * -1;
            }
        });
        notifyDataSetChanged();
    }
}
