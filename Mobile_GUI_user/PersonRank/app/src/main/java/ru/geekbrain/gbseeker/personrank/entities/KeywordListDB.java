package ru.geekbrain.gbseeker.personrank.entities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Collection;

import ru.geekbrain.gbseeker.personrank.DB.DBHelper;

public class KeywordListDB {
    Context context;
    ArrayList<String> personList = new ArrayList<>();
    int selectedPerson = 0;
    ArrayAdapter<String> personListAdapter;

    SimpleCursorAdapter scKeywordAdapter;

    public KeywordListDB(Context context) {
        this.context = context;
    }

    public ArrayList<String> getPersonList() {
        DBHelper.getInstance().getPersonList(personList);
        return personList;
    }

    public ArrayAdapter<String> getAdapterWithPerson() {
        personListAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getPersonList());
        personListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return personListAdapter;
    }

    public void update(){
        String person=personList.get(selectedPerson);

        getPersonList();

        selectedPerson=personList.indexOf(person);
        if(selectedPerson<0) selectedPerson=0;

        personListAdapter.notifyDataSetChanged();
        setSelectedPersonPosition(selectedPerson);
    }


    public void setSelectedPersonPosition(int id) {
        selectedPerson = id;
        scKeywordAdapter.swapCursor(DBHelper.getInstance().getCursorOfKeywordWithPerson(personList.get(id)));
        scKeywordAdapter.notifyDataSetChanged();
    }

    public SimpleCursorAdapter getAdapterWithWords(LoaderManager loaderManager, int selectedPerson) {

        String[] from = new String[]{DBHelper.DB.COLUMNS.KEYWORD.KEYWORD};
        int[] to = new int[]{android.R.id.text1};

        this.selectedPerson = selectedPerson;

        scKeywordAdapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, null, from, to, 0);
        loaderManager.initLoader(LOADER_IDS.LOADER_KEYWORDS.ordinal(), null,
                new KeywordCursorLoaderManager(context, scKeywordAdapter,personList.get(selectedPerson)));

        return scKeywordAdapter;
    }

}

class KeywordCursorLoaderManager implements LoaderManager.LoaderCallbacks<Cursor>{
    Context context;
    SimpleCursorAdapter scAdapter;
    String person;

    public KeywordCursorLoaderManager(Context context, SimpleCursorAdapter scAdapter,String person) {
        this.context = context;
        this.scAdapter = scAdapter;
        this.person=person;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bndl) {
        return new KeywordListCursorLoader(context,person);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        scAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }


}


class KeywordListCursorLoader extends CursorLoader {
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
