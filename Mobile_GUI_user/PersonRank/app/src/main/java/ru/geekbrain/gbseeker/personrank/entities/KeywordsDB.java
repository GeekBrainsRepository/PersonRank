package ru.geekbrain.gbseeker.personrank.entities;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import ru.geekbrain.gbseeker.personrank.DB.CursorLoaderManager;
import ru.geekbrain.gbseeker.personrank.DB.DBHelper;
import ru.geekbrain.gbseeker.personrank.net.iNet2SQL;

public class KeywordsDB implements iNet2SQL {
    private static final String TAG = "KeywordsDB";

    final private Context context;

    final private ArrayList<String> personList = new ArrayList<>();
    private String selectedPerson = "";
    private String saveSelectedPerson = "";
    private ArrayAdapter<String> personListAdapter;

    private SimpleCursorAdapter scKeywordAdapter;


    public KeywordsDB(Context context) {
        this.context = context;
    }

    private ArrayList<String> getPersonList() {
        DBHelper.getInstance().getPersonList(personList);
        return personList;
    }

    public String getSelectedPerson() {
        return selectedPerson;
    }

    public ArrayAdapter<String> getAdapterWithPerson() {
        personListAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getPersonList());
        if (personList.size() > 0) {
            if (selectedPerson.equals("") || personList.indexOf(selectedPerson) < 0) {
                selectedPerson = personList.get(0);
            }
        } else {
            selectedPerson = "";
        }
        personListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return personListAdapter;
    }

    @Override
    public void init() {
        saveSelectedPerson = selectedPerson;
    }

    @Override
    public void updateDB(String json, String param) {
        if (param.contains("/person")) { //persons
            PersonsDB.parseJSONforPerson(json);
        } else if (param.contains("/keyword")) { //keywords
            parseJSONforKeyword(json);
        }
    }

   
    @Override
    public void updateUI(){
        getPersonList();
        personListAdapter.notifyDataSetChanged();
        setSelectedPersonPosition(saveSelectedPerson);
    }
    @Override
    public String getInfo() {
        return TAG;
    }

   

 public synchronized void parseJSONforKeyword(String json) {
        try {
            JSONObject dataJsonObj = new JSONObject(json);
            Iterator<String> iter = dataJsonObj.keys();
            ArrayList<String> usedKeywords = new ArrayList<>();
            while (iter.hasNext()) {
                String k = iter.next();

                String keyword = dataJsonObj.getString(k);
                DBHelper.getInstance().addKeywordWithCheck(saveSelectedPerson, keyword);

                usedKeywords.add(keyword);
                Log.d(TAG, k + ":" + keyword);
            }

            DBHelper.getInstance().cleanKeywordDB(saveSelectedPerson, usedKeywords);
            DBHelper.getInstance().dumpTableKeyword();
        }
        catch (Exception e){
            Log.d(TAG, e.getMessage());
        }
    }
    public void setSelectedPersonPosition(String person) {
        if ( personList.size()>0 && personList.indexOf(person)>=0)
            selectedPerson = person;
        else
            selectedPerson="";
        scKeywordAdapter.swapCursor(DBHelper.getInstance().getCursorOfKeywordWithPerson(selectedPerson));
        scKeywordAdapter.notifyDataSetChanged();
    }

    public SimpleCursorAdapter getAdapterWithWords(LoaderManager loaderManager) {

        String[] from = new String[]{DBHelper.DB.COLUMNS.KEYWORD.KEYWORD};
        int[] to = new int[]{android.R.id.text1};

        scKeywordAdapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1, null, from, to, 0);
        loaderManager.initLoader(LOADER_IDS.LOADER_KEYWORDS.ordinal(), null,
                new CursorLoaderManager(scKeywordAdapter,new KeywordListCursorLoader(context,selectedPerson)));

        return scKeywordAdapter;
    }

}


class KeywordListCursorLoader extends CursorLoader {
    final private String person;

    KeywordListCursorLoader(Context context,String person) {
        super(context);
        this.person=person;
    }

    @Override
    public Cursor loadInBackground() {
        return DBHelper.getInstance().getCursorOfKeywordWithPerson(person);
    }
}
