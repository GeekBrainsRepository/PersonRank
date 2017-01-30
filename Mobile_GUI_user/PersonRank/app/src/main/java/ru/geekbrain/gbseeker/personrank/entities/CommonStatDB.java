package ru.geekbrain.gbseeker.personrank.entities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import ru.geekbrain.gbseeker.personrank.DB.DBHelper;
import ru.geekbrain.gbseeker.personrank.R;

public class CommonStatDB {
    Context context;
    ArrayList<String> siteList= new ArrayList<>();
    int selectedSite = 0;
    ArrayAdapter<String> siteListAdapter;

    SimpleCursorAdapter scCommonStatsAdapter;

    public CommonStatDB(Context context) {
        this.context = context;
    }

    public ArrayList<String> getSiteList() {
        DBHelper.getInstance().getSiteList(siteList);
        return siteList;
    }

    public ArrayAdapter<String> getAdapterWithSite() {
        siteListAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getSiteList());
        siteListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return siteListAdapter;
    }

    public void update(){
        String site=siteList.get(selectedSite);

        getSiteList();

        selectedSite=siteList.indexOf(site);
        if(selectedSite<0) selectedSite=0;

        siteListAdapter.notifyDataSetChanged();
        setSelectedSitePosition(selectedSite);
    }

    public void setSelectedSitePosition(int id) {
        selectedSite = id;
        scCommonStatsAdapter.swapCursor(DBHelper.getInstance().getCursorOfCommonStatsWithSite(siteList.get(id)));
        scCommonStatsAdapter.notifyDataSetChanged();
    }

    public SimpleCursorAdapter getAdapterWithStats(LoaderManager loaderManager, int selectedSite) {

        String[] from = new String[]{DBHelper.DB.COLUMNS.PERSON.PERSON, DBHelper.DB.COLUMNS.COMMON.STATS};
        int[] to = new int[]{R.id.text1, R.id.text2};

        this.selectedSite = selectedSite;

        scCommonStatsAdapter = new SimpleCursorAdapter(context, R.layout.stats_item, null, from, to, 0);
        loaderManager.initLoader(LOADER_IDS.LOADER_COMMON_STATS.ordinal(), null,
                new CommonStatsCursorLoaderManager(context, scCommonStatsAdapter, siteList.get(selectedSite)));

        return scCommonStatsAdapter;
    }
}


class CommonStatsCursorLoaderManager implements LoaderManager.LoaderCallbacks<Cursor> {
    Context context;
    SimpleCursorAdapter scAdapter;
    String site;

    public CommonStatsCursorLoaderManager(Context context, SimpleCursorAdapter scAdapter, String site) {
        this.context = context;
        this.scAdapter = scAdapter;
        this.site = site;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new CommonStatsCursorLoader(context,site);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }


}


class CommonStatsCursorLoader extends CursorLoader {
    String site;
    public CommonStatsCursorLoader(Context context,String site) {
        super(context);
        this.site=site;
    }

    @Override
    public Cursor loadInBackground() {
        return DBHelper.getInstance().getCursorOfCommonStatsWithSite(site);
    }
}

