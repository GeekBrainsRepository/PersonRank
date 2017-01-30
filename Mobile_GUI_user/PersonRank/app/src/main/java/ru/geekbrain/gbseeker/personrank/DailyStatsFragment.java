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
import java.util.Date;

import ru.geekbrain.gbseeker.personrank.entities.DailyStatsDB;


public class DailyStatsFragment extends Fragment {
    int myYear = 2011;
    int myMonth = 2;
    int myDay = 3;
    int myYear1 = 2011;
    int myMonth1 = 2;
    int myDay1 = 3;
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

        dates=dailyStatsDB.getMinMaxDate(selectedSitePosition,selectedPersonPosition);

        butFrom = (Button) v.findViewById(R.id.date_from);
        butFrom.setText("c " + android.text.format.DateFormat.format("dd.MM.yyyy", new Date(myYear-1900,myMonth,myDay)));

        butFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog tpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                myYear = year;
                                myMonth = monthOfYear;
                                myDay = dayOfMonth;
                                butFrom.setText("c " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(myYear-1900,myMonth,myDay)));                            }
                        },
                        myYear, myMonth, myDay);
                String t=dates.get(0);
                t=dates.get(dates.size()-1);
                Date minDay=new Date(dates.get(0));
                Date maxDay=new Date(dates.get(dates.size()-1));
                long q=minDay.getTime();
                q=maxDay.getTime();
                tpd.getDatePicker().setMinDate(minDay.getTime());
                tpd.getDatePicker().setMaxDate(maxDay.getTime());
                tpd.show();
            }
        });
        butTo = (Button) v.findViewById(R.id.date_to);
        butTo.setText("по " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(myYear1-1900,myMonth1,myDay1)));

        butTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog tpd = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                myYear1 = year;
                                myMonth1 = monthOfYear;
                                myDay1 = dayOfMonth;

                                butTo.setText("по " + android.text.format.DateFormat.format("yyyy-MM-dd", new Date(myYear1-1900,myMonth1,myDay1)));
                            }
                        },
                        myYear1, myMonth1, myDay1);
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





