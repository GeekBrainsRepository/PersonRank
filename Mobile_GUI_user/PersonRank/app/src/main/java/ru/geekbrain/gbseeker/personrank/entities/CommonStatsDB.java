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

import ru.geekbrain.gbseeker.personrank.DB.CursorLoaderManager;
import ru.geekbrain.gbseeker.personrank.DB.DBHelper;
import ru.geekbrain.gbseeker.personrank.R;
import ru.geekbrain.gbseeker.personrank.net.iNet2SQL;

public class CommonStatsDB implements iNet2SQL {
    private static final String TAG = "CommonStatsDB";

    Context context;
    ArrayList<String> siteList = new ArrayList<>();
    String selectedSite = "";
    String saveSelectedSite = "";
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

    public int getSiteID(String site) {
        return DBHelper.getInstance().getSiteID(site);
    }

    public ArrayAdapter<String> getAdapterWithSite() {
        siteListAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getSiteList());
        if (siteList.size() > 0) {
            if (selectedSite.equals("") || siteList.indexOf(selectedSite) < 0) {
                selectedSite = siteList.get(0);
            }
        } else {
            selectedSite = "";
        }
        siteListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return siteListAdapter;
    }

    @Override
    public void init() {
        saveSelectedSite = selectedSite;
    }

    @Override
    public void updateDB(String json, String param) {
        if (param.contains("/site")) {
            SitesDB.parseJSONforSites(json);
        } else if (param.contains("common")) {
            parseJSONforCommonStats(json);
        }
    }

    private void parseJSONforCommonStats(String json) {
        try {
            ArrayList<String> usedPerson = new ArrayList<>();
            JSONObject dataJsonObj = new JSONObject(json);
            JSONObject result = dataJsonObj.getJSONObject("result");
            Iterator<String> iter = result.keys();
            while (iter.hasNext()) {

                String person = iter.next();
                int stats = result.getInt(person);
                DBHelper.getInstance().addOrUpdateCommonStatsWithCheck(saveSelectedSite, person, stats);

                usedPerson.add(person);
                Log.d(TAG, person + ":" + stats);
            }
            DBHelper.getInstance().cleanCommonStatsDB(saveSelectedSite, usedPerson);
            DBHelper.getInstance().dumpTableCommonStats();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }
    @Override
    public String getInfo() {
        return TAG;
    }

    public void updateUI() {
        getSiteList();
        siteListAdapter.notifyDataSetChanged();
        setSelectedSitePosition(saveSelectedSite);
    }

    public void setSelectedSitePosition(String site) {
        if (siteList.size() > 0 && siteList.indexOf(site) >= 0)
            selectedSite = site;
        else
            selectedSite = "";
        scCommonStatsAdapter.swapCursor(DBHelper.getInstance().getCursorOfCommonStatsWithSite(selectedSite));
        scCommonStatsAdapter.notifyDataSetChanged();
    }

    public SimpleCursorAdapter getAdapterWithStats(LoaderManager loaderManager, String selectedSite) {

        String[] from = new String[]{DBHelper.DB.COLUMNS.COMMON.PERSON, DBHelper.DB.COLUMNS.COMMON.STATS};
        int[] to = new int[]{R.id.text1, R.id.text2};

        this.selectedSite = selectedSite;

        scCommonStatsAdapter = new SimpleCursorAdapter(context, R.layout.stats_item, null, from, to, 0);
        loaderManager.initLoader(LOADER_IDS.LOADER_COMMON_STATS.ordinal(), null,
                new CursorLoaderManager(scCommonStatsAdapter, new CommonStatsCursorLoader(context, selectedSite)));

        return scCommonStatsAdapter;
    }
}



class CommonStatsCursorLoader extends CursorLoader {
    final String site;

    CommonStatsCursorLoader(final Context context, final String site) {
        super(context);
        this.site = site;
    }

    @Override
    public Cursor loadInBackground() {
        return DBHelper.getInstance().getCursorOfCommonStatsWithSite(site);
    }
}

