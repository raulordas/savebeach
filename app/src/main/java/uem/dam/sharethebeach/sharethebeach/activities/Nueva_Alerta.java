package uem.dam.sharethebeach.sharethebeach.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import uem.dam.sharethebeach.sharethebeach.ContextoCustom;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.adapters.AdapterSpinner;

public class Nueva_Alerta extends Base_Activity {

    Spinner pSpi;
    AdapterSpinner adaptador;
    private int ano,mes,dia,hora,minutos;
    TextView fecha;
    TextView horaTxt;
    TextView titulo;
    TextView descripcion;
    ImageView imgBoton;
    ImageView imgUsuario;
    private static final int GALERIA = 1;
    private static final int FOTO = 2;
    Uri foto;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_nueva__alerta);


        pSpi = findViewById(R.id.spPlayas);
        adaptador = new AdapterSpinner(this,((ContextoCustom) (getApplicationContext())).getListadoPlayas());
        pSpi.setAdapter(adaptador);

        fecha = findViewById(R.id.txtMuestraFecha);
        horaTxt = findViewById(R.id.txtMuestraHora);
        titulo = findViewById(R.id.txtTituloNA);
        descripcion = findViewById(R.id.txtDescripcionNA);

        imgUsuario = findViewById(R.id.imgAlerta);
        imgBoton = findViewById(R.id.imgBotonAniadir);
        imgBoton.setClickable(true);

        imgBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] options = {"Hacer foto", "Galería", "Cancelar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(Nueva_Alerta.this);
                builder.setTitle("Elige una opción");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (options[which].equals("Hacer foto")) {
                            hacerFoto();
                        } else if (options[which].equals("Galería")) {
                            cargarImagen();
                        } else if (options[which].equals("Cancelar")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();


            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            System.out.println(imageBitmap);
            imgUsuario.setImageURI(getImageUri(this,imageBitmap));



        } else if (requestCode == GALERIA && resultCode == RESULT_OK) {
            foto = data.getData();
            imgUsuario.setImageURI(foto);

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void hacerFoto() {
        if (checkPermissionWRITE_EXTERNAL_STORAGE(this)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, FOTO);
            }
        }
    }

    public void cargarImagen(){
        Intent i =new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, GALERIA);
    }

    @Override
    public int cargarLayout() {
        return R.layout.activity_nueva__alerta;
    }

    @Override
    public boolean setDrawer() {
        return false;
    }


    public void Fecha(View v){

        final Calendar c = Calendar.getInstance();
         dia = c.get(Calendar.DAY_OF_MONTH);
         mes = c.get(Calendar.MONTH);
         ano = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, dia,mes,ano);
        datePickerDialog.show();
    }

    public void Hora(View v){
        final Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horaTxt.setText(hourOfDay + ":" + minute);
            }
        },hora,minutos,true);
        timePickerDialog.show();

    }

    public void subir_Alerta(View v){


        if(titulo.getText().toString().trim().isEmpty() || descripcion.getText().toString().trim().isEmpty()
                || fecha.getText().toString().trim().isEmpty() || horaTxt.getText().toString().trim().isEmpty()){
            Toast toast1 = Toast.makeText(getApplicationContext(), getString(R.string.toast_nueva_alerta), Toast.LENGTH_SHORT);
            toast1.show();

        }else{





        }



    }

    ///PRUEBAS TERRIBLES
    public boolean checkPermissionWRITE_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }

        }

    public void showDialog(final String msg, final Context context, final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }


}
