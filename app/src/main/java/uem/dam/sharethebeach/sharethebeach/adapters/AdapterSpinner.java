package uem.dam.sharethebeach.sharethebeach.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.bean.Playa;

public class AdapterSpinner extends ArrayAdapter {

    ArrayList<Playa> lista ;
    private Context context;

    public AdapterSpinner(@NonNull Context context, ArrayList<Playa> lista) {
        super(context, R.layout.spinner_playas);
        this.context = context;
        this.lista = lista;

    }

    @Override
    public int getCount() {
        return lista.size();
    }

    private static class ItemPlaya{
        ImageView img;
        TextView txt1;
        TextView txt2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ItemPlaya item = new ItemPlaya();

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner_playas, parent, false);
            item.img = convertView.findViewById(R.id.imgPlayaSpi);
            item.txt1 =  convertView.findViewById(R.id.txtTituloPlayasSpi);
            item.txt2 = convertView.findViewById(R.id.txtMuniciPlayasSpi);
            convertView.setTag(item);
        } else {
            item = (ItemPlaya) convertView.getTag();
        }


        //Mapeamos la carpeta de imagenes asignando cada imagen a su playa
        Bitmap b = null;
        String url = "images/" + lista.get(position).getId() + ".jpg";
        InputStream stream = null;

        try {
            stream = context.getAssets().open(url);
            b = BitmapFactory.decodeStream(stream);
            item.img.setImageBitmap(b);

        } catch (IOException e) {
            e.printStackTrace();
        }

        item.txt1.setText(lista.get(position).getNombre());
        item.txt2.setText(lista.get(position).getMunicipio());


        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

}
