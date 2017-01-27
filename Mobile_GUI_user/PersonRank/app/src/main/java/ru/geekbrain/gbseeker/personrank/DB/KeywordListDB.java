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

public class KeywordListDB implements LoaderManager.LoaderCallbacks<Cursor> {
    Context context;
    ArrayAdapter<String> personListAdapter;
    SimpleCursorAdapter scAdapter;
    private final static int LOADER_ID=2;
    ArrayList<String> personList=new ArrayList<>();
    String curPerson="";

    public KeywordListDB(Context context) {
        this.context = context;
    }

    public void setSelectedPerson(int id){
        curPerson=personList.get(id);
        scAdapter.swapCursor(new KeywordListCursorLoader(context,curPerson).loadInBackground());
        scAdapter.notifyDataSetChanged();
    }
    public ArrayList<String> getPersonList() {
        personList.clear();
        Cursor cursor = null;
        try {
            cursor = DBHelper.getInstance().getDB().query(DBHelper.DB.TABLES.PERSON, null, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(DBHelper.DB.COLUMNS.PERSON.PERSON);
                do {
                    personList.add(cursor.getString(index));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return personList;
    }
    public ArrayAdapter<String> getAdapterWithPerson() {
        personListAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getPersonList());
        personListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return personListAdapter;
    }


    public SimpleCursorAdapter getAdapterWithWords(LoaderManager loaderManager,int selectedPerson) {

        String[] from = new String[]{DBHelper.DB.COLUMNS.KEYWORD.KEYWORD};
        int[] to = new int[]{android.R.id.text1};

        curPerson=personList.get(selectedPerson);

        scAdapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, null, from, to, 0);
        loaderManager.initLoader(LOADER_ID, null, this);

        return scAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new KeywordListCursorLoader(context,curPerson);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    static class KeywordListCursorLoader extends CursorLoader {
        String person;
        public KeywordListCursorLoader(Context context,String person) {
            super(context);
            this.person=person;
        }

        @Override
        public Cursor loadInBackground() {
            int person_id=DBHelper.getInstance().getPersonID(person);
            return DBHelper.getInstance().getDB().query(DBHelper.DB.TABLES.KEYWORD, null,
                    DBHelper.DB.COLUMNS.KEYWORD.PERSON_REF+"="+person_id,
                    null, null, null, null, null);
        }
    }





}



