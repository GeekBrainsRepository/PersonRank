package ru.geekbrain.gbseeker.personrank.DB;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;


public class CursorLoaderManager implements LoaderManager.LoaderCallbacks<Cursor> {
    final private SimpleCursorAdapter scAdapter;
    final private CursorLoader loader;

    public CursorLoaderManager(final SimpleCursorAdapter scAdapter, final CursorLoader loader) {
        this.scAdapter = scAdapter;
        this.loader = loader;
    }

    @Override
    public Loader<Cursor> onCreateLoader(final int id, final Bundle bundle) {
        return loader;
    }

    @Override
    public void onLoadFinished(final Loader<Cursor> loader, final Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(final Loader<Cursor> loader) {
    }
}