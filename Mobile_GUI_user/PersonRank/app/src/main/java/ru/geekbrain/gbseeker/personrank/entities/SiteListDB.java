package ru.geekbrain.gbseeker.personrank.entities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

import ru.geekbrain.gbseeker.personrank.DB.DBHelper;

public class SiteListDB {
    Context context;
    SimpleCursorAdapter scSiteAdapter;

    public SiteListDB(Context context) {
        this.context = context;
    }

    public SimpleCursorAdapter getAdapterWithSite(LoaderManager loaderManager) {

        String[] from = new String[]{DBHelper.DB.COLUMNS.SITE.SITE};
        int[] to = new int[]{android.R.id.text1};

        scSiteAdapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, null, from, to, 0);
        loaderManager.initLoader(LOADER_IDS.LOADER_SITES.ordinal(), null,
                new SiteListCursorLoaderManager(context, scSiteAdapter));

        return scSiteAdapter;
    }

    public void update(){
        scSiteAdapter.swapCursor(DBHelper.getInstance().getCursorWithSites());
        scSiteAdapter.notifyDataSetChanged();
    }
}


class SiteListCursorLoaderManager implements LoaderManager.LoaderCallbacks<Cursor> {
    Context context;
    SimpleCursorAdapter scAdapter;

    SiteListCursorLoaderManager(Context context, SimpleCursorAdapter scAdapter){
        this.context=context;
        this.scAdapter=scAdapter;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new SiteListCursorLoader(context);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

}


class SiteListCursorLoader extends CursorLoader {

    public SiteListCursorLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        return DBHelper.getInstance().getCursorWithSites();
    }
}






