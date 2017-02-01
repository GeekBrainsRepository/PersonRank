package ru.geekbrain.gbseeker.personrank;

import android.content.ContentValues;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ListView;

import ru.geekbrain.gbseeker.personrank.DB.DBHelper;
import ru.geekbrain.gbseeker.personrank.entities.SiteListDB;
import ru.geekbrain.gbseeker.personrank.net.RestAPI;

public class SiteList extends Fragment {
    SimpleCursorAdapter scSiteAdapter;
    SiteListDB siteListDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        siteListDB = new SiteListDB(getContext());

        DBHelper.getInstance().addSite(1,"qqq");
        DBHelper.getInstance().addSite(2,"qqq");
        DBHelper.getInstance().addSite(3,"qqq");
        DBHelper.getInstance().addSite(1,"aaa");
        DBHelper.getInstance().addSite(1,"222");
        DBHelper.getInstance().addSite(2,"222");
        DBHelper.getInstance().addSite(5,"5555");

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.DB.COLUMNS.COMMON.ID, 1);
        cv.put(DBHelper.DB.COLUMNS.COMMON.SITE_REF, 1);
        cv.put(DBHelper.DB.COLUMNS.COMMON.PERSON_REF,2);
        cv.put(DBHelper.DB.COLUMNS.COMMON.STATS,10);
        DBHelper.getInstance().getDB().insert(DBHelper.DB.TABLES.COMMON, null, cv);

        DBHelper.getInstance().dumpTableSite();
        DBHelper.getInstance().dumpTableCommonStats();

        RestAPI.getSite(siteListDB);
        DBHelper.getInstance().dumpTableSite();
        DBHelper.getInstance().dumpTableCommonStats();
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



