package ru.geekbrain.gbseeker.personrank;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ListView;

import ru.geekbrain.gbseeker.personrank.entities.SiteListDB;

public class SiteList extends Fragment {
    SimpleCursorAdapter scSiteAdapter;
    SiteListDB siteListDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        siteListDB = new SiteListDB(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.site_list, container, false);

        getActivity().setTitle("Список сайтов");

        ListView list = (ListView) v.findViewById(R.id.SiteList);
        scSiteAdapter = siteListDB.getAdapterWithSite(getActivity().getSupportLoaderManager());
        list.setAdapter(scSiteAdapter);

        return v;
    }
}



