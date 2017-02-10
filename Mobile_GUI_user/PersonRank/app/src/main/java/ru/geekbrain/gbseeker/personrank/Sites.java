package ru.geekbrain.gbseeker.personrank;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ListView;

import ru.geekbrain.gbseeker.personrank.entities.SitesDB;
import ru.geekbrain.gbseeker.personrank.net.ReloadFromNet;
import ru.geekbrain.gbseeker.personrank.net.RestAPI;

public class Sites extends Fragment implements  ReloadFromNet {
    SitesDB sitesDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sitesDB = new SitesDB(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.site_list, container, false);

        getActivity().setTitle("Список сайтов");

        ListView list = (ListView) v.findViewById(R.id.SiteList);
        SimpleCursorAdapter scSiteAdapter = sitesDB.getAdapterWithSites(getActivity().getSupportLoaderManager());
        list.setAdapter(scSiteAdapter);

        reload();

        return v;
    }

    @Override
    public void reload() {
        RestAPI.getSite(sitesDB);
    }

}



