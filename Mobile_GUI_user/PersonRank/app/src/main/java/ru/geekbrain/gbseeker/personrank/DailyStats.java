package ru.geekbrain.gbseeker.personrank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Date;

import ru.geekbrain.gbseeker.personrank.entities.DailyStatsDB;
import ru.geekbrain.gbseeker.personrank.net.ReloadFromNet;
import ru.geekbrain.gbseeker.personrank.net.RestAPI;

public class DailyStats extends Fragment implements ReloadFromNet {
    Button butFrom;
    Button butTo;

    private DailyStatsDB dailyStatsDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dailyStatsDB=new DailyStatsDB(getContext());
     }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.daily_stats, container, false);

        getActivity().setTitle(R.string.daily_stats);

        Spinner spinnerSite = (Spinner) v.findViewById(R.id.daily_stats_sites);
        spinnerSite.setAdapter(dailyStatsDB.getAdapterWithSite());

        Spinner spinnerPersonOnSite = (Spinner) v.findViewById(R.id.daily_stats_persons);
        spinnerPersonOnSite.setAdapter(dailyStatsDB.getAdapterWithPerson());

        ListView list = (ListView) v.findViewById(R.id.daily_stats_list);
        SimpleCursorAdapter adapterStats = dailyStatsDB.getAdapterWithStats(getActivity().getSupportLoaderManager());
        list.setAdapter(adapterStats);

        butFrom = (Button) v.findViewById(R.id.date_from);
        butFrom.setText(android.text.format.DateFormat.format("yyyy-MM-dd", new Date(dailyStatsDB.getDateFrom())));

        butFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog = DateDialog.getInstance(dailyStatsDB.getDateFrom(),true);
                Fragment f=getFragmentManager().findFragmentById(R.id.FrameContainer);
                dateDialog.setTargetFragment(f, 1);
                dateDialog.show(getFragmentManager(),"date");
            }
        });



        butTo = (Button) v.findViewById(R.id.date_to);
        butTo.setText(android.text.format.DateFormat.format("yyyy-MM-dd", new Date(dailyStatsDB.getDateTo())));

        butTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dateDialog = DateDialog.getInstance(dailyStatsDB.getDateTo(),false);
                Fragment f=getFragmentManager().findFragmentById(R.id.FrameContainer);
                dateDialog.setTargetFragment(f, 1);
                dateDialog.show(getFragmentManager(),"date");
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


    void setDateFrom(long date) {
        dailyStatsDB.setDateFrom(date);
        butFrom.setText(android.text.format.DateFormat.format("yyyy-MM-dd", new Date(date)));
        reload();
    }
    void setDateTo(long date) {
        dailyStatsDB.setDateTo(date);
        butTo.setText(android.text.format.DateFormat.format("yyyy-MM-dd", new Date(date)));
        reload();
    }

}





