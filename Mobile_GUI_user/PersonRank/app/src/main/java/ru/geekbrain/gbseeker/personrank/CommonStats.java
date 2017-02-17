package ru.geekbrain.gbseeker.personrank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import ru.geekbrain.gbseeker.personrank.entities.CommonStatsDB;
import ru.geekbrain.gbseeker.personrank.net.ReloadFromNet;
import ru.geekbrain.gbseeker.personrank.net.RestAPI;


public class CommonStats extends Fragment  implements ReloadFromNet {
    CommonStatsDB commonStatsDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonStatsDB = new CommonStatsDB(getContext());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.common_stats, container, false);

        getActivity().setTitle(R.string.common_stats);

        Spinner spinner = (Spinner) v.findViewById(R.id.common_stats_sites);
        spinner.setAdapter(commonStatsDB.getAdapterWithSite());

        ListView list = (ListView) v.findViewById(R.id.common_stats_list);
        SimpleCursorAdapter adapterStats = commonStatsDB.getAdapterWithStats(getActivity().getSupportLoaderManager(),
                commonStatsDB.getSelectedSite());
        list.setAdapter(adapterStats);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                commonStatsDB.setSelectedSitePosition((String) parent.getItemAtPosition(position));
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
        RestAPI.getCommonStats(commonStatsDB, commonStatsDB.getSiteID(commonStatsDB.getSelectedSite()));
    }
}



