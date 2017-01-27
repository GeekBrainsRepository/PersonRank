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

import ru.geekbrain.gbseeker.personrank.R;

public class CommonStatDB implements LoaderManager.LoaderCallbacks<Cursor> {
    Context context;
    ArrayList<String> siteList=new ArrayList<>();

    SimpleCursorAdapter scAdapter;
    int selectedSite=0;
    private final static int LOADER_ID=5;

    public CommonStatDB(Context context) {
        this.context = context;
    }

    public ArrayList<String> getSiteList() {
        DBHelper.getInstance().getSiteList(siteList);
        return siteList;
    }
    public ArrayAdapter<String> getAdapterWithSite() {
        ArrayAdapter<String> siteListAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getSiteList());
        siteListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return siteListAdapter;
    }



    public void setSelectedSitePosition(int id){
        selectedSite=id;
        String curSite=siteList.get(id);
        scAdapter.swapCursor(DBHelper.getInstance().getCursorOfCommonStatsWithSite(curSite));
        scAdapter.notifyDataSetChanged();
    }

    public SimpleCursorAdapter getAdapterWithStats(LoaderManager loaderManager,int selectedSite) {

        String[] from = new String[]{DBHelper.DB.COLUMNS.PERSON.PERSON, DBHelper.DB.COLUMNS.COMMON.STATS};
        int[] to = new int[]{R.id.text1,R.id.text2};

        this.selectedSite=selectedSite;


        scAdapter = new SimpleCursorAdapter(context, R.layout.common_stats_item, null, from, to, 0);
        loaderManager.initLoader(LOADER_ID, null, this);

        return scAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new CommonStatsCursorLoader(context,siteList.get(selectedSite));

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class CommonStatsCursorLoader extends CursorLoader {
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

}



