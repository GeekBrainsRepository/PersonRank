package ru.geekbrain.gbseeker.personrank.entities;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import ru.geekbrain.gbseeker.personrank.DB.CursorLoaderManager;
import ru.geekbrain.gbseeker.personrank.DB.DBHelper;
import ru.geekbrain.gbseeker.personrank.net.iNet2SQL;

public class SitesDB implements iNet2SQL {
    private static final String TAG = "SitesDB";

    final Context context;
    SimpleCursorAdapter scSitesAdapter;

    public SitesDB(Context context) {
        this.context = context;
    }

    public SimpleCursorAdapter getAdapterWithSites(LoaderManager loaderManager) {

        String[] from = new String[]{DBHelper.DB.COLUMNS.SITE.SITE};
        int[] to = new int[]{android.R.id.text1};

        scSitesAdapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, null, from, to, 0);
        loaderManager.initLoader(LOADER_IDS.LOADER_SITES.ordinal(),
                null,
                new CursorLoaderManager(scSitesAdapter, new SiteListCursorLoader(context)));

        return scSitesAdapter;
    }


    @Override
    public void init() {
    }

    @Override
    public String getInfo() {
        return TAG;
    }

    public void updateDB(String json, String param) {
        parseJSONforSites(json);
    }

    public void updateUI() {
        scSitesAdapter.swapCursor(DBHelper.getInstance().getCursorWithSites());
        scSitesAdapter.notifyDataSetChanged();
    }

    static public synchronized void parseJSONforSites(String json) {
        ArrayList<Integer> usedIds = new ArrayList<>();
        try {
            JSONObject dataJsonObj = new JSONObject(json);
            Iterator<String> iter = dataJsonObj.keys();
            while (iter.hasNext()) {
                String k = iter.next();

                int id = Integer.parseInt(k);
                String site = dataJsonObj.getString(k);
                DBHelper.getInstance().addSiteDBWithCheck(id, site);

                usedIds.add(id);
                Log.d(TAG, k + ":" + site);
            }
            DBHelper.getInstance().cleanSiteDB(usedIds);
            DBHelper.getInstance().dumpTableSite();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

}



class SiteListCursorLoader extends CursorLoader {

    SiteListCursorLoader(final Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        return DBHelper.getInstance().getCursorWithSites();
    }
}


