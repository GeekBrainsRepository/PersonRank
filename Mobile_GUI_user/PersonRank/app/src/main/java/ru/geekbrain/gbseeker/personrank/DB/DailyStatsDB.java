package ru.geekbrain.gbseeker.personrank.DB;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ru.geekbrain.gbseeker.personrank.R;

public class DailyStatsDB implements LoaderManager.LoaderCallbacks<Cursor> {
    Context context;
    ArrayList<String> siteList=new ArrayList<>();
    ArrayList<String> personList=new ArrayList<>();
    ArrayAdapter<String> personAdapter;
    SimpleCursorAdapter scAdapter;
    int selectedSite=0;
    int selectedPerson=0;

    private final static int LOADER_ID=7;

    public DailyStatsDB(Context context) {
        this.context = context;
    }


    public ArrayList<String> getSiteList() {
        DBHelper.getInstance().getSiteList(siteList);
        return siteList;
    }
    public ArrayAdapter<String> getAdapterWithSite() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getSiteList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public ArrayList<String> getPersonListOnSite() {
        DBHelper.getInstance().getPersonListOnSite(personList,(siteList.size()==0)?"":siteList.get(selectedSite));
        return personList;
    }
    public ArrayAdapter<String> getAdapterWithPersonOnSite() {
        personAdapter= new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getPersonListOnSite());
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return personAdapter;
    }


    public void setSelectedSitePosition(int id){
        selectedSite=id;
        personList=getPersonListOnSite();
        personAdapter.notifyDataSetChanged();
    }

    public SimpleCursorAdapter getAdapterWithStats(LoaderManager loaderManager,int selectedSite,int selectedPerson) {
        String[] from = new String[]{DBHelper.DB.COLUMNS.DAILY.DATE, DBHelper.DB.COLUMNS.DAILY.STATS};
        int[] to = new int[]{R.id.text1,R.id.text2};
        this.selectedSite=selectedSite;
        this.selectedPerson=selectedPerson;

        scAdapter = new SimpleCursorAdapter(context, R.layout.stats_item, null, from, to, 0);
        loaderManager.initLoader(LOADER_ID, null, this);

        return scAdapter;
    }
    public ArrayList<String> getMinMaxDate(int selectedSite,int selectedPerson) {
        ArrayList<String> dates=new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = DBHelper.getInstance().getCursorOfDailyStatsWithSite(siteList.get(selectedSite), personList.get(selectedPerson));
            if (cursor.moveToFirst()) {
                int indexID = cursor.getColumnIndex(DBHelper.DB.COLUMNS.DAILY.DATE);
                do {
                    dates.add(cursor.getString(indexID));
                } while (cursor.moveToNext());
                Collections.sort(dates);
            }

        }
        finally {
            cursor.close();
        }
        return dates;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new DailyStatsCursorLoader(context,siteList.get(selectedSite),personList.get(selectedPerson));

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class DailyStatsCursorLoader extends CursorLoader {
        String site;
        String person;
        public DailyStatsCursorLoader(Context context,String site,String person) {
            super(context);
            this.site=site;
            this.person=person;
        }

        @Override
        public Cursor loadInBackground() {
            return DBHelper.getInstance().getCursorOfDailyStatsWithSite(site,person);
        }
    }

}




