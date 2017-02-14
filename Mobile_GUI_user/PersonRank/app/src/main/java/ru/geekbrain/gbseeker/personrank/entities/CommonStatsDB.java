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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import ru.geekbrain.gbseeker.personrank.DB.DBHelper;
import ru.geekbrain.gbseeker.personrank.R;
import ru.geekbrain.gbseeker.personrank.net.iNet2SQL;

public class CommonStatsDB implements iNet2SQL {
    private static final String TAG="CommonStatsDB";

    Context context;
    ArrayList<String> siteList= new ArrayList<>();
    String selectedSite = "";
    String saveSelectedSite="";
    ArrayAdapter<String> siteListAdapter;

    SimpleCursorAdapter scCommonStatsAdapter;

    public CommonStatsDB(Context context) {
        this.context = context;
    }

    public String getSelectedSite() {
        return selectedSite;
    }

    public ArrayList<String> getCurrentSiteList() {
        return siteList;
    }
    public ArrayList<String> getSiteList() {
        DBHelper.getInstance().getSiteList(siteList);
        return siteList;
    }
    public int  getSiteID(String site) {
        return DBHelper.getInstance().getSiteID(site);
    }

    public ArrayAdapter<String> getAdapterWithSite() {
        siteListAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getSiteList());
        if(siteList.size()>0) {
            if (selectedSite.equals("") || siteList.indexOf(selectedSite) < 0) {
                selectedSite = siteList.get(0);
            }
        }else{
            selectedSite="";
        }
        siteListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return siteListAdapter;
    }

    @Override
    public void init() {
        saveSelectedSite=selectedSite;
    }

    @Override
    public void updateDB(String json,String param) {
        try {
            if(param.contains("/site")) {
                ArrayList<String> usedSite=new ArrayList<>();
                JSONObject result = new JSONObject(json);
                Iterator<String> iter = result.keys();
                while (iter.hasNext()) {
                    String k = iter.next();

                    int id=Integer.parseInt(k);
                    String site = result.getString(k);
                    DBHelper.getInstance().addSiteDBWithCheck(id,site);

                    usedSite.add(site);
                    Log.d(TAG, id+ ":" + site);
                }
                DBHelper.getInstance().cleanCommonStatsDB(usedSite);
                DBHelper.getInstance().dumpTableSite();
                DBHelper.getInstance().dumpTableCommonStats();
            }
            else if(param.contains("common")){
                ArrayList<String> usedPerson=new ArrayList<>();
                JSONObject dataJsonObj = new JSONObject(json);
                JSONObject result = dataJsonObj.getJSONObject("result");
                Iterator<String> iter = result.keys();
                while (iter.hasNext()) {
                    String k = iter.next();

                    String person = k;
                    int stats = result.getInt(k);
                    DBHelper.getInstance().addOrUpdateCommonStatsWithCheck(saveSelectedSite, person, stats);

                    usedPerson.add(person);
                    Log.d(TAG, person + ":" + stats);
                }
                DBHelper.getInstance().cleanCommonStatsDB(saveSelectedSite,usedPerson);
                DBHelper.getInstance().dumpTableCommonStats();
            }
        }
        catch(Exception e){
            Log.d(TAG,e.getMessage());
        }

    }

    @Override
    public String getInfo() {
        return TAG;
    }

    public void updateUI(){
        getSiteList();
        siteListAdapter.notifyDataSetChanged();
        setSelectedSitePosition( saveSelectedSite );
    }

    public void setSelectedSitePosition(String site) {
        if (siteList.size()>0 && siteList.indexOf(site)>=0)
            selectedSite = site;
        else
            selectedSite="";
        scCommonStatsAdapter.swapCursor(DBHelper.getInstance().getCursorOfCommonStatsWithSite(selectedSite));
        scCommonStatsAdapter.notifyDataSetChanged();
    }

    public SimpleCursorAdapter getAdapterWithStats(LoaderManager loaderManager, String selectedSite) {

        String[] from = new String[]{DBHelper.DB.COLUMNS.COMMON.PERSON, DBHelper.DB.COLUMNS.COMMON.STATS};
        int[] to = new int[]{R.id.text1, R.id.text2};

        this.selectedSite = selectedSite;

        scCommonStatsAdapter = new SimpleCursorAdapter(context, R.layout.stats_item, null, from, to, 0);
        loaderManager.initLoader(LOADER_IDS.LOADER_COMMON_STATS.ordinal(), null,
                new CommonStatsCursorLoaderManager(context, scCommonStatsAdapter, selectedSite));

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
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
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

