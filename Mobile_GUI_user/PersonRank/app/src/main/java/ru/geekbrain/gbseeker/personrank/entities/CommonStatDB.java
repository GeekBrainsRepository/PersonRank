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

public class CommonStatDB implements iNet2SQL {
    private static final String TAG="CommonStatDB";

    Context context;
    ArrayList<String> siteList= new ArrayList<>();
    String selectedSite = "";
    String saveSelectedSite="";
    ArrayAdapter<String> siteListAdapter;

    SimpleCursorAdapter scCommonStatsAdapter;

    public CommonStatDB(Context context) {
        this.context = context;
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
            if(param.contains("site")) {
                JSONObject result = new JSONObject(json);
                Iterator<String> iter = result.keys();
                while (iter.hasNext()) {
                    String k = iter.next();
                    int id=Integer.parseInt(k);
                    String site = result.getString(k);
                    DBHelper.getInstance().addSiteWithCheck(id,site);
                    Log.d(TAG, id+ ":" + site);
                }
            }
            else if(param.contains("common")){
                JSONObject dataJsonObj = new JSONObject(json);
                JSONObject result = dataJsonObj.getJSONObject("result");
                Iterator<String> iter = result.keys();
                while (iter.hasNext()) {
                    String k = iter.next();
                    String person = k;
                    int stats = result.getInt(k);
                    DBHelper.getInstance().addOrUpdateCommonStatsWithCheck(saveSelectedSite, person, stats);
                    Log.d(TAG, person + ":" + stats);
                }

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
        setSelectedSitePosition( (siteList.size()>0) ? siteList.indexOf(selectedSite): -1);
    }

    public void setSelectedSitePosition(int id) {
        if (id >= 0 && id < siteList.size())
            selectedSite = siteList.get(id);
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

