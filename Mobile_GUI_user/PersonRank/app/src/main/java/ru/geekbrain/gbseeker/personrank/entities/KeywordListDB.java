package ru.geekbrain.gbseeker.personrank.entities;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import ru.geekbrain.gbseeker.personrank.DB.DBHelper;
import ru.geekbrain.gbseeker.personrank.net.iNet2SQL;

public class KeywordListDB implements iNet2SQL {
    private static final String TAG="KeywordListDB";

    Context context;
    ArrayList<String> personList = new ArrayList<>();
    String selectedPerson = "";
    String saveSelectedPerson = "";
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
        if(personList.size()>0){
            if(selectedPerson.equals("") || personList.indexOf(selectedPerson)<0){
                selectedPerson=personList.get(0);
            }
        }
        else{
            selectedPerson="";
        }
        personListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return personListAdapter;
    }

    @Override
    public void init() {
        saveSelectedPerson=selectedPerson;
    }

    @Override
    public void updateDB(String json, String param) {
        try {
          JSONObject dataJsonObj = new JSONObject(json);
            Iterator<String> iter=dataJsonObj.keys();
            if(param.contains("person")){ //persons
                while (iter.hasNext()) {
                    String k = iter.next();
                    int id=Integer.parseInt(k);
                    String person = dataJsonObj.getString(k);
                    DBHelper.getInstance().addPersonWithCheck(id,person);
                    Log.d(TAG, k + ":" + person);
                }
            }else if(param.contains("keyword")) { //keywrods
                while (iter.hasNext()) {
                    String k = iter.next();
                    String keyword= dataJsonObj.getString(k);
                    DBHelper.getInstance().addKeywordWithCheck(saveSelectedPerson,keyword);
                    Log.d(TAG, k + ":" + keyword);
                }
            }
        }
        catch(Exception e){
            Log.d(TAG,e.getMessage());
        }

    }

    @Override
    public String getInfo() {
        return TAG;
    }

    public void updateUI(){
        getPersonList();
        personListAdapter.notifyDataSetChanged();
        setSelectedPersonPosition((personList.size()>0)?personList.indexOf(selectedPerson):-1);
    }


    public void setSelectedPersonPosition(int id) {
        if (id >= 0 && id < personList.size())
            selectedPerson = personList.get(id);
        else
            selectedPerson="";
        scKeywordAdapter.swapCursor(DBHelper.getInstance().getCursorOfKeywordWithPerson(selectedPerson));
        scKeywordAdapter.notifyDataSetChanged();
    }
    public String getSelectedPerson() {
        return selectedPerson;
    }
    public SimpleCursorAdapter getAdapterWithWords(LoaderManager loaderManager) {

        String[] from = new String[]{DBHelper.DB.COLUMNS.KEYWORD.KEYWORD};
        int[] to = new int[]{android.R.id.text1};

        scKeywordAdapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, null, from, to, 0);
        loaderManager.initLoader(LOADER_IDS.LOADER_KEYWORDS.ordinal(), null,
                new KeywordCursorLoaderManager(context, scKeywordAdapter,selectedPerson));

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
