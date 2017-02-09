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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ru.geekbrain.gbseeker.personrank.entities.DailyStatsDB;
import ru.geekbrain.gbseeker.personrank.net.RestAPI;


public class DailyStatsFragment extends Fragment {

    long dateFrom=Calendar.getInstance().getTimeInMillis();
    long dateTo=Calendar.getInstance().getTimeInMillis();

    Button butFrom;
    Button butTo;

    DailyStatsDB dailyStatsDB;
    int selectedSitePosition = 0;
    int selectedPersonPosition = 0;

    ArrayList<String> dates=new ArrayList<>();

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

        ListView list= (ListView) v.findViewById(R.id.daily_stats_list);
        SimpleCursorAdapter adapterStats = dailyStatsDB.getAdapterWithStats(getActivity().getSupportLoaderManager(), selectedSitePosition,selectedPersonPosition);
        list.setAdapter(adapterStats);

       //dates=dailyStatsDB.getMinMaxDate(selectedSitePosition,selectedPersonPosition);

        RestAPI.getDailyStats(dailyStatsDB,
                dailyStatsDB.getSiteID(dailyStatsDB.getSiteList().get(selectedSitePosition)),
                dailyStatsDB.getPersonID(dailyStatsDB.getPersonList().get(selectedPersonPosition)),
                dateFrom,dateTo
                );

        butFrom = (Button) v.findViewById(R.id.date_from);
        butFrom.setText("c " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(dateFrom)));

        butFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                c.setTimeInMillis(dateFrom);

                DatePickerDialog tpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                                dateFrom = calendar.getTimeInMillis();
                                butFrom.setText("c " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(dateFrom)));
                            }
                        },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)
                );
                tpd.show();
            }
        });


        butTo = (Button) v.findViewById(R.id.date_to);
        butTo.setText("по " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(dateTo)));

        butTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                c.setTimeInMillis(dateTo);

                DatePickerDialog tpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                                dateTo = calendar.getTimeInMillis();
                                butTo.setText("по " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(dateTo)));
                            }
                        },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)
                );
                tpd.show();
            }
        });


        spinnerSite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                dailyStatsDB.setSelectedSitePosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinnerPersonOnSite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getActivity(), "person = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        return v;
    }
}





