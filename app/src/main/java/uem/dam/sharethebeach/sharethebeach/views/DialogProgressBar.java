package uem.dam.sharethebeach.sharethebeach.views;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import uem.dam.sharethebeach.sharethebeach.R;

public class DialogProgressBar extends Dialog {

    public DialogProgressBar(@NonNull Context context) {
        super(context);
        setContentView(R.layout.rl_prog);
        setCancelable(false);
    }
}
