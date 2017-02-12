package ru.geekbrain.gbseeker.personrank;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    final static String KEY_DATE="DATE";
    final static String KEY_FROM_TO="DATE_FROM_TO";

    public static final DateDialog getInstance(long date,boolean isDateFrom) {
        Bundle data=new Bundle();
        data.putLong(KEY_DATE,date);
        data.putBoolean(KEY_FROM_TO,isDateFrom);
        DateDialog fragment = new DateDialog();
        fragment.setArguments(data);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        long date=getArguments().getLong(KEY_DATE);
        Date d=new Date(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        int year = cal.get(Calendar.YEAR);

        return new DatePickerDialog(getActivity(), this, year,month,day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);

        if(getArguments().getBoolean(KEY_FROM_TO)){
            ((DailyStatsFragment)getTargetFragment()).setDateFrom(calendar.getTimeInMillis());
        }
        else{
            ((DailyStatsFragment)getTargetFragment()).setDateTo(calendar.getTimeInMillis());
        }


    }
}
