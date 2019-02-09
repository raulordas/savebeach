package uem.dam.sharethebeach.sharethebeach.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uem.dam.sharethebeach.sharethebeach.R;

public class info_UserProfile extends Dialog {

    Button btnRegresar;

    public info_UserProfile(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info__user_profile);

        btnRegresar = findViewById(R.id.btnRegresar);
        setCancelable(false);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
