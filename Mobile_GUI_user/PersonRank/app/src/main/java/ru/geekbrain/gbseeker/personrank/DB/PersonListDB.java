package ru.geekbrain.gbseeker.personrank.DB;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

public class PersonListDB implements LoaderManager.LoaderCallbacks<Cursor> {
    Context context;
    SimpleCursorAdapter scAdapter;
    private final static int LOADER_ID=0;

    public PersonListDB(Context context) {
        this.context = context;
    }

    public SimpleCursorAdapter getAdapterWithPerson(LoaderManager loaderManager) {

        String[] from = new String[]{DBHelper.DB.COLUMNS.PERSON.PERSON};
        int[] to = new int[]{android.R.id.text1};

        scAdapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, null, from, to, 0);
        loaderManager.initLoader(LOADER_ID, null, this);

        return scAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new PersonListCursorLoader(context);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class PersonListCursorLoader extends CursorLoader {

        public PersonListCursorLoader(Context context) {
            super(context);
        }

        @Override
        public Cursor loadInBackground() {
            return DBHelper.getInstance().getDB().query(DBHelper.DB.TABLES.PERSON, null, null, null, null, null, null, null);
        }
    }





}



