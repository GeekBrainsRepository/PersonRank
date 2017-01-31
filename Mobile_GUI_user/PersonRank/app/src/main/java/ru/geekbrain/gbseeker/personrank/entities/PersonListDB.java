package ru.geekbrain.gbseeker.personrank.entities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

import ru.geekbrain.gbseeker.personrank.DB.DBHelper;

public class PersonListDB  {
    Context context;
    SimpleCursorAdapter scPersonAdapter;

    public PersonListDB(Context context) {
        this.context = context;
    }

    public SimpleCursorAdapter getAdapterWithPerson(LoaderManager loaderManager) {

        String[] from = new String[]{DBHelper.DB.COLUMNS.PERSON.PERSON};
        int[] to = new int[]{android.R.id.text1};

        scPersonAdapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, null, from, to, 0);
        loaderManager.initLoader(LOADER_IDS.LOADER_PERSONS.ordinal(), null,
                new PersonListCursorLoaderManager(context, scPersonAdapter));

        return scPersonAdapter;
    }

    public void update(){
        scPersonAdapter.swapCursor(DBHelper.getInstance().getCursorWithPersons());
        scPersonAdapter.notifyDataSetChanged();
    }
}

class PersonListCursorLoaderManager implements LoaderManager.LoaderCallbacks<Cursor>{
    Context context;
    SimpleCursorAdapter scAdapter;

    public PersonListCursorLoaderManager(Context context, SimpleCursorAdapter scAdapter) {
        this.context = context;
        this.scAdapter = scAdapter;
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


}


class PersonListCursorLoader extends CursorLoader {

    public PersonListCursorLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        return DBHelper.getInstance().getCursorWithPersons();
    }
}