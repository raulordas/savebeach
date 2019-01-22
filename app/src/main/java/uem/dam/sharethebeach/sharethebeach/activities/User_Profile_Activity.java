package uem.dam.sharethebeach.sharethebeach.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import uem.dam.sharethebeach.sharethebeach.R;

public class User_Profile_Activity extends Base_Activity {
    private static final int COD_PICK_FOTO = 1;
    private ImageView imageUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user__profile_);
        imageUserProfile = findViewById(R.id.imgUserProfile);

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

    @RequiresApi(api = Build.VERSION_CODES.N)
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
                    imageUserProfile.setImageURI(selectedImage);
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
                        imageUserProfile.setRotation(rotationInDegrees);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageUserProfile.setImageURI(selectedImage);
                }
                break;
        }
    }
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
