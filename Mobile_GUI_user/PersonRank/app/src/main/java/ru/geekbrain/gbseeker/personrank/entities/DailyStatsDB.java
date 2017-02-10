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
    String selectedSite = "";
    String selectedPerson = "";
    String saveSelectedSite = "";
    String saveSelectedPerson = "";

    long dateFrom,dateTo;
    long saveDateFrom,saveDateTo;

    private final long DAY_MILLISEC=3600*24*1000;
    SimpleCursorAdapter scAdapter;

    public void setDate(long from,long to) {
        dateFrom=from;
        dateTo=to;
    }
    public DailyStatsDB(Context context) {
        this.context = context;
    }

    public int  getSiteID(String site) {
        return DBHelper.getInstance().getSiteID(site);
    }
    public int  getPersonID(String person) {return DBHelper.getInstance().getPersonID(person); }

    @Override
    public void init() {
        saveDateFrom=dateFrom;
        saveDateTo=dateTo;
        saveSelectedPerson=selectedPerson;
        saveSelectedSite=selectedSite;
    }

    @Override
    public void updateUI() {}

    @Override
    public String getInfo() {
        return TAG;
    }

    @Override
    public void updateDB(String json,String param) {
        try {
            if(param.contains("/site")) {
                JSONObject result = new JSONObject(json);
                Iterator<String> iter = result.keys();
                while (iter.hasNext()) {
                    String k = iter.next();
                    int id=Integer.parseInt(k);
                    String site = result.getString(k);
                    DBHelper.getInstance().addSiteWithCheck(id,site);
                    Log.d(TAG, id+ ":" + site);
                }
            }else if(param.contains("/person")) {
                JSONObject result = new JSONObject(json);
                Iterator<String> iter = result.keys();
                while (iter.hasNext()) {
                    String k = iter.next();
                    int id=Integer.parseInt(k);
                    String person = result.getString(k);
                    DBHelper.getInstance().addPersonWithCheck(id,person);
                    Log.d(TAG, id+ ":" + person);
                }
            }else if(param.contains("/daily")) {
                JSONObject dataJsonObj = new JSONObject(json);
                JSONArray result = dataJsonObj.getJSONArray("result");
                for (int i = 0; i < result.length(); i++) {
                    long time=saveDateFrom+i*DAY_MILLISEC;
                    int v = result.getInt(i);
                    DBHelper.getInstance().addOrUpdateDailyStatsWithCheck(
                            saveSelectedSite,
                            saveSelectedPerson,
                            time,v);
                    Log.d(TAG, i + ":" + v);
                }
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
        if(selectedSite.equals("") && siteList.size()>0){
            selectedSite=siteList.get(0);
        }
        adapterSite.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapterSite;
    }

    public ArrayList<String> getPersonList() {
        DBHelper.getInstance().getPersonList(personList);
        return personList;
    }
    public ArrayAdapter<String> getAdapterWithPersonOnSite() {
        personAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getPersonList());
        if(selectedPerson.equals("") && personList.size()>0){
            selectedPerson=personList.get(0);
        }
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return personAdapter;
    }

    public String getSelectedSite() {
        return selectedSite;
    }

    public String getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedSitePosition(int id) {
        if (id >= 0 && id < siteList.size())
            selectedSite = siteList.get(id);
        else
            selectedSite="";

        scAdapter.swapCursor(DBHelper.getInstance().getCursorOfDailyStatsWithSite(selectedSite,selectedPerson));
        scAdapter.notifyDataSetChanged();
    }
    public void setSelectedPersonPosition(int id) {
        if (id >= 0 && id < personList.size())
            selectedPerson = personList.get(id);
        else
            selectedPerson ="";

        scAdapter.swapCursor(DBHelper.getInstance().getCursorOfDailyStatsWithSite(selectedSite,selectedPerson));
        scAdapter.notifyDataSetChanged();
    }

    public SimpleCursorAdapter getAdapterWithStats(LoaderManager loaderManager) {
        String[] from = new String[]{DBHelper.DB.COLUMNS.DAILY.DATE, DBHelper.DB.COLUMNS.DAILY.STATS,"_id"};
        int[] to = new int[]{R.id.text1, R.id.text2};

        scAdapter = new SimpleCursorAdapter(context, R.layout.stats_item, null, from, to, 0);
        loaderManager.initLoader(LOADER_IDS.LOADER_DAILY_STATS.ordinal(), null,
                new DailyStatsCursorLoaderManager(context, scAdapter,selectedSite,selectedPerson)
        );
        return scAdapter;

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


