package ru.geekbrain.gbseeker.personrank;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;

import ru.geekbrain.gbseeker.personrank.entities.DailyStatsDB;
import ru.geekbrain.gbseeker.personrank.net.ReloadFromNet;
import ru.geekbrain.gbseeker.personrank.net.RestAPI;


public class DailyStatsFragment extends Fragment implements ReloadFromNet {
    Button butFrom;
    Button butTo;

    DailyStatsDB dailyStatsDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dailyStatsDB=new DailyStatsDB(getContext());
     }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.daily_stats, container, false);

        getActivity().setTitle("Ежедневная статистика");

        Spinner spinnerSite = (Spinner) v.findViewById(R.id.daily_stats_sites);
        spinnerSite.setAdapter(dailyStatsDB.getAdapterWithSite());

        Spinner spinnerPersonOnSite = (Spinner) v.findViewById(R.id.daily_stats_persons);
        spinnerPersonOnSite.setAdapter(dailyStatsDB.getAdapterWithPersonOnSite());

        ListView list = (ListView) v.findViewById(R.id.daily_stats_list);
        SimpleCursorAdapter adapterStats = dailyStatsDB.getAdapterWithStats(getActivity().getSupportLoaderManager());
        list.setAdapter(adapterStats);

        butFrom = (Button) v.findViewById(R.id.date_from);
        butFrom.setText("c " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(dailyStatsDB.getDateFrom())));

        butFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(dailyStatsDB.getDateFrom());

                DatePickerDialog tpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                                dailyStatsDB.setDateFrom(calendar.getTimeInMillis());
                                butFrom.setText("c " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(dailyStatsDB.getDateFrom())));
                            }
                        },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)
                );
                tpd.show();
                reload();
            }
        });


        butTo = (Button) v.findViewById(R.id.date_to);
        butTo.setText("по " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(dailyStatsDB.getDateTo())));

        butTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(dailyStatsDB.getDateTo());

                DatePickerDialog tpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                                dailyStatsDB.setDateTo(calendar.getTimeInMillis());
                                butTo.setText("по " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(dailyStatsDB.getDateTo())));
                            }
                        },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)
                );
                tpd.show();
                reload();
            }
        });


        spinnerSite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                dailyStatsDB.setSelectedSitePosition((String) parent.getItemAtPosition(position));
                reload();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinnerPersonOnSite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                dailyStatsDB.setSelectedPersonPosition((String) parent.getItemAtPosition(position));
                reload();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        reload();
        return v;
    }

    @Override
    public void reload() {
        RestAPI.getDailyStats(dailyStatsDB,
                dailyStatsDB.getSiteID(dailyStatsDB.getSelectedSite()),
                dailyStatsDB.getPersonID(dailyStatsDB.getSelectedPerson()),
                dailyStatsDB.getDateFrom(),dailyStatsDB.getDateTo()
        );
    }
}





