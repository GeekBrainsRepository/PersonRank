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
    ArrayList<String> personList=new ArrayList<>();

    SimpleCursorAdapter scAdapter;
    int selectedPerson=0;
    private final static int LOADER_ID=2;

    public KeywordListDB(Context context) {
        this.context = context;
    }

    public ArrayList<String> getPersonList() {
        DBHelper.getInstance().getPersonList(personList);
        return personList;
    }
    public ArrayAdapter<String> getAdapterWithPerson() {
        ArrayAdapter<String> personListAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, getPersonList());
        personListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return personListAdapter;
    }



    public void setSelectedPersonPosition(int id){
        selectedPerson=id;
        String curPerson=personList.get(id);
        scAdapter.swapCursor(DBHelper.getInstance().getCursorOfKeywordWithPerson(curPerson));
        scAdapter.notifyDataSetChanged();
    }

    public SimpleCursorAdapter getAdapterWithWords(LoaderManager loaderManager,int selectedPerson) {

        String[] from = new String[]{DBHelper.DB.COLUMNS.KEYWORD.KEYWORD};
        int[] to = new int[]{android.R.id.text1};

        this.selectedPerson=selectedPerson;

        scAdapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, null, from, to, 0);
        loaderManager.initLoader(LOADER_ID, null, this);

        return scAdapter;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new KeywordListCursorLoader(context,personList.get(selectedPerson));

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
            return DBHelper.getInstance().getCursorOfKeywordWithPerson(person);
        }
    }





}



