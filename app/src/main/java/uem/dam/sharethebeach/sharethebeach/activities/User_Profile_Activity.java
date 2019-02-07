package uem.dam.sharethebeach.sharethebeach.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.media.ExifInterface;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

import uem.dam.sharethebeach.sharethebeach.Picador;
import uem.dam.sharethebeach.sharethebeach.R;
import uem.dam.sharethebeach.sharethebeach.bean.Usuario;

public class User_Profile_Activity extends Base_Activity {
    private static final int COD_PICK_FOTO = 151;

    private static final int REQUEST_IMAGE_CAPTURE = 150;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 123;

    ImageView btnFoto;
    ImageView ivFoto;
    TextView fechaNacP;
    TextView email;
    TextView tvNombre;
    TextView tvDescripcion;

    private TextView tvFecha;
    FirebaseUser user;
    String emailF;
    String uid;
    String nombre;
    String fecha;
    String descripcion;
    String URL;

    StorageReference destino;
    StorageReference storage;
    DatabaseReference dbr;
    ChildEventListener cel;


    private int dia;
    private int mes;
    private int anio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user__profile_);

        user  = FirebaseAuth.getInstance().getCurrentUser();
        //emailF = "juan.notario20@gmail.com";
        //uid = "123456789";

        if (user != null) {
            emailF = user.getEmail();
            uid = user.getUid();
        } else {
            //Aqui ira algo
        }

        storage = FirebaseStorage.getInstance().getReference();


        btnFoto = findViewById(R.id.btnEditFoto);
        ivFoto = findViewById(R.id.ivFoto);
        fechaNacP = findViewById(R.id.tvFechaNc);
        tvFecha = findViewById(R.id.tvFechaNc);
        tvNombre = findViewById(R.id.tvNombre);
        email = findViewById(R.id.tvEmailReal);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        email.setText(emailF);

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

        dbr = FirebaseDatabase.getInstance().getReference().child("Usuario");

    }

    public void aceptar(View view) {
        nombre = tvNombre.getText().toString();
        fecha = tvFecha.getText().toString();
        descripcion = tvDescripcion.getText().toString();

        Usuario usuario = new Usuario(uid,emailF, nombre, fecha, descripcion, URL);

        if (nombre.trim().isEmpty() || fecha.trim().isEmpty() || descripcion.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();

        } else {

            dbr.child(uid).setValue(usuario);
            Toast.makeText(getApplicationContext(), "Los datos se han guardado correctamente", Toast.LENGTH_SHORT).show();
        }
    }

    public void calendario(View view) {
        final DialogFragment dg = new Picador();
        ((Picador) dg).setActivityr(User_Profile_Activity.this);
        dg.show(getSupportFragmentManager(), "Hola");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            subirImagen(getImageUri(this, imageBitmap));


        } else if (requestCode == 10 && resultCode == RESULT_OK) {
            Uri miPath = data.getData();

            subirImagen(miPath);

            System.out.println(URL);
        }
    }

    private void hacerFoto() {
        if (checkPermissionWRITE_EXTERNAL_STORAGE(this)) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
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
        mes = mes + 1;
        tvFecha.setText("Fecha Nacimiento: " + dia + "/" + mes + "/" + anio);
    }

    @Override
    public int cargarLayout() {
        return R.layout.activity_user__profile_;
    }

    @Override
    public boolean setDrawer() {
        return false;
    }

    public void backToSplash(View view){
        startActivity(new Intent(this, Login_Activity.class));
    }

    public void subirImagen(Uri uri) {
        destino = storage.child("fotoUsuario").child(String.valueOf(uri.getLastPathSegment()));


        UploadTask uploadTask = destino.putFile(uri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }


                return destino.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    URL = downloadUri.toString();  //URL DE DESCARGA
                    System.out.println(URL);
                    Glide.with(User_Profile_Activity.this).load(URL).into(ivFoto); //Mostramos imagen en imageview

                } else {
                    //Posibles errores
                }
            }
        });
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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
