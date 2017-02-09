package ru.geekbrain.gbseeker.personrank.entities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import ru.geekbrain.gbseeker.personrank.DB.DBHelper;
import ru.geekbrain.gbseeker.personrank.R;
import ru.geekbrain.gbseeker.personrank.net.iNet2SQL;

public class DailyStatsDB  implements iNet2SQL {
    private final String TAG="DailyStatsDB";
    Context context;
    ArrayList<String> siteList = new ArrayList<>();
    ArrayList<String> personList = new ArrayList<>();
    ArrayAdapter<String> personAdapter;
    ArrayAdapter<String> adapterSite;
    int selectedSite = 0;
    int selectedPerson = 0;

    long dateFrom,dateTo;
    private final long DAY_MILLISEC=3600*24*1000;

    SimpleCursorAdapter scAdapter;

    public DailyStatsDB(Context context) {
        this.context = context;
    }

    public int  getSiteID(String site) {
        return DBHelper.getInstance().getSiteID(site);
    }
    public int  getPersonID(String person) {return DBHelper.getInstance().getPersonID(person); }

    @Override
    public void init() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void updateDB(String json,String param) {
        try {
            JSONObject dataJsonObj = new JSONObject(json);
            JSONArray result=dataJsonObj.getJSONArray("result");
            for(int i=0;i<result.length();i++){
                int v=result.getInt(i);
                DBHelper.getInstance().addOrUpdateDailyStatsWithCheck(
                        siteList.get(selectedSite),
                        personList.get(selectedPerson),
                        dateFrom+i*DAY_MILLISEC,v);
                Log.d(TAG,i+":"+v);
            }
        }
        catch(Exception e){
            Log.d(TAG,e.getMessage());
        }

    }


    public ArrayList<String> getSiteList() {
        DBHelper.getInstance().getSiteList(siteList);
        return siteList;
    }
    public ArrayAdapter<String> getAdapterWithSite() {
        adapterSite = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getSiteList());
        adapterSite.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapterSite;
    }

    public ArrayList<String> getPersonListOnSite() {
        DBHelper.getInstance().getPersonListOnSite(personList, (siteList.size() == 0) ? "" : siteList.get(selectedSite));
        return personList;
    }
    public ArrayAdapter<String> getAdapterWithPersonOnSite() {
        personAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getPersonListOnSite());
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return personAdapter;
    }


    public void setSelectedSitePosition(int id) {
        selectedSite = id;
        personList = getPersonListOnSite();
        personAdapter.notifyDataSetChanged();
    }

    public SimpleCursorAdapter getAdapterWithStats(LoaderManager loaderManager, int selectedSite, int selectedPerson) {
        String[] from = new String[]{DBHelper.DB.COLUMNS.DAILY.DATE, DBHelper.DB.COLUMNS.DAILY.STATS};
        int[] to = new int[]{R.id.text1, R.id.text2};
        this.selectedSite = selectedSite;
        this.selectedPerson = selectedPerson;

        scAdapter = new SimpleCursorAdapter(context, R.layout.stats_item, null, from, to, 0);
        loaderManager.initLoader(LOADER_IDS.LOADER_DAILY_STATS.ordinal(), null,
                new DailyStatsCursorLoaderManager(context, scAdapter,
                        siteList.get(selectedSite),
                        personList.get(selectedPerson))
        );
        return scAdapter;
    }

    public ArrayList<String> getMinMaxDate(int selectedSite, int selectedPerson) {
        ArrayList<String> dates = new ArrayList<>();
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

        } finally {
            cursor.close();
        }
        return dates;
    }
}

class DailyStatsCursorLoaderManager implements LoaderManager.LoaderCallbacks<Cursor> {
    Context context;
    SimpleCursorAdapter scAdapter;
    String person;
    String site;

    public DailyStatsCursorLoaderManager(Context context, SimpleCursorAdapter scAdapter, String site, String person) {
        this.context = context;
        this.scAdapter = scAdapter;
        this.person = person;
        this.site = site;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new DailyStatsCursorLoader(context,site,person);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }


}


class DailyStatsCursorLoader extends CursorLoader {
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


