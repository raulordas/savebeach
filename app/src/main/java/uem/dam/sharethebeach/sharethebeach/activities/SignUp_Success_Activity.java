package uem.dam.sharethebeach.sharethebeach.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import uem.dam.sharethebeach.sharethebeach.R;

public class SignUp_Success_Activity extends AppCompatActivity {
    private TextView tvCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up__success);
        tvCorreo = findViewById(R.id.tvcorreo);
        tvCorreo.setText(String.format(getString(R.string.VAL_CORREO_CONFIRMACION),
                getIntent().getStringExtra(getString(R.string.KEY__SIGN_UP_SUCCESS))));
    }

    public void backToSplash(View view){
        startActivity(new Intent(this, Login_Activity.class));
    }
}
