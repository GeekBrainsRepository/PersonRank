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
    public int  getSiteID(String site) {
        return DBHelper.getInstance().getSiteID(site);
    }

    public ArrayAdapter<String> getAdapterWithSite() {
        siteListAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getSiteList());
        siteListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return siteListAdapter;
    }

    @Override
    public void updateDB(String json) {
        try {
            JSONObject dataJsonObj = new JSONObject(json);
            JSONObject result=dataJsonObj.getJSONObject("result");
            Iterator<String> iter=result.keys();
            while(iter.hasNext()){
                String k=iter.next();
                int v=result.getInt(k);
                DBHelper.getInstance().addOrUpdateCommonStatsWithCheck(,siteList.get(selectedSite),k,v);
                Log.d(TAG,k+":"+v);
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

