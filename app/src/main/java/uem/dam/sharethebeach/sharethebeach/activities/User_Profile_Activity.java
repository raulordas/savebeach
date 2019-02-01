package uem.dam.sharethebeach.sharethebeach.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.media.ExifInterface;

import java.io.ByteArrayOutputStream;

import uem.dam.sharethebeach.sharethebeach.Picador;
import uem.dam.sharethebeach.sharethebeach.R;

public class User_Profile_Activity extends Base_Activity {
    private static final int COD_PICK_FOTO = 1;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    ImageView btnFoto;
    Button btnDatos;
    ImageView ivFoto;

    private ImageView ivCalendar;
    private TextView tvFecha;

    private int dia;
    private int mes;
    private int anio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__profile_);

        btnFoto = findViewById(R.id.btnEditFoto);
        btnDatos = findViewById(R.id.btnEditDatos);
        ivFoto = findViewById(R.id.ivFoto);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Hacer foto", "Galería", "Cancelar"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(User_Profile_Activity.this);
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

        btnDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(User_Profile_Activity.this);
                dialog.setContentView(R.layout.layout_dialog);
                ivCalendar = dialog.findViewById(R.id.ivCalendar);
                tvFecha = dialog.findViewById(R.id.tvFecha);
                dialog.show();

                ivCalendar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogFragment dg = new Picador();
                        ((Picador) dg).setActivityr(User_Profile_Activity.this);
                        dg.show(getSupportFragmentManager(), "Hola");
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivFoto.setImageBitmap(imageBitmap);
        } else if (requestCode == 10 && resultCode == RESULT_OK) {
            Uri miPath = data.getData();
            ivFoto.setImageURI(miPath);
        }
    }

    private void hacerFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void cargarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione la Aplicacion"), 10);
    }

    public void recibirFecha(int anio, int mes, int dia) {
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        tvFecha.setText(dia + "/" + mes + "/" + anio);
    }

    @Override
    public int cargarLayout() {
        return R.layout.activity_user__profile_;
    }

    @Override
    public boolean setDrawer() {
        return false;
    }

    public void seleccionarFoto(View view){
        Intent pickFoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickFoto , COD_PICK_FOTO);
    }

    /*@RequiresApi(api = Build.VERSION_CODES.N)
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        ExifInterface exif = new ExifInterface(selectedImage.getPath());
                        int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        int rotationInDegrees = exifToDegrees(rotation);
                        System.out.println(rotationInDegrees);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ivFoto.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    InputStream in;
                    try {
                        in = getContentResolver().openInputStream(selectedImage);
                        ExifInterface exif = new ExifInterface(in);
                        int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                        int rotationInDegrees = exifToDegrees(rotation);
                        System.out.println(rotationInDegrees);
                        ivFoto.setRotation(rotationInDegrees);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ivFoto.setImageURI(selectedImage);
                }
                break;
        }
    }*/

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }

    public void backToSplash(View view){
        startActivity(new Intent(this, Login_Activity.class));
    }
}
