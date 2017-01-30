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

import ru.geekbrain.gbseeker.personrank.entities.CommonStatDB;


public class CommonStatsFragment extends Fragment {
    CommonStatDB commonStatDB;
    int selectedSitePosition = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonStatDB=new CommonStatDB(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.common_stats,container,false);

        getActivity().setTitle("Общая статистика");

        Spinner spinner = (Spinner) v.findViewById(R.id.common_stats_sites);
        spinner.setAdapter(commonStatDB.getAdapterWithSite());

        ListView list= (ListView) v.findViewById(R.id.common_stats_list);
        SimpleCursorAdapter adapterStats = commonStatDB.getAdapterWithStats(getActivity().getSupportLoaderManager(), selectedSitePosition);
        list.setAdapter(adapterStats);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                commonStatDB.setSelectedSitePosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        return v;
    }
}



