package ru.geekbrain.gbseeker.personrank.entities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;

import org.json.JSONObject;

import java.util.Iterator;

import ru.geekbrain.gbseeker.personrank.DB.DBHelper;
import ru.geekbrain.gbseeker.personrank.net.iNet2SQL;

public class PersonListDB  implements iNet2SQL {
    Context context;
    SimpleCursorAdapter scPersonAdapter;
    private static final String TAG="SPersonListDB";

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

    @Override
    public void init() {

    }

    @Override
    public String getInfo() {
        return TAG;
    }

    public void updateDB(String json,String param) {
        try {
            JSONObject dataJsonObj = new JSONObject(json);
            Iterator<String> iter=dataJsonObj.keys();
            while(iter.hasNext()){
                String k=iter.next();
                int id =Integer.parseInt(k);
                String person=dataJsonObj.getString(k);
                DBHelper.getInstance().addPersonWithCheck(id,person);
                Log.d(TAG,id+":"+person);
            }
        }
        catch(Exception e){
            Log.d(TAG,e.getMessage());
        }

    }

    public void updateUI(){
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
