package uem.dam.sharethebeach.sharethebeach;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.util.Calendar;

import uem.dam.sharethebeach.sharethebeach.activities.User_Profile_Activity;

public class Picador extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialog.OnDateSetListener listener;
    private Activity activity;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /*
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
                */
        return new DatePickerDialog(getActivity(), R.style.DialogTheme, this,2000, 12, 3);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int anio, int mes, int dia) {
        ((User_Profile_Activity) (activity)).recibirFecha(anio, mes, dia);

    }

    public void setOnClickListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    public void setActivityr(Activity activity) {
        this.activity = activity;
    }

}
