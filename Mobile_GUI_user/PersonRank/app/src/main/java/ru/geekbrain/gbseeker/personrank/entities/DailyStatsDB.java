package ru.geekbrain.gbseeker.personrank.entities;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import ru.geekbrain.gbseeker.personrank.DB.CursorLoaderManager;
import ru.geekbrain.gbseeker.personrank.DB.DBHelper;
import ru.geekbrain.gbseeker.personrank.R;
import ru.geekbrain.gbseeker.personrank.net.iNet2SQL;

public class DailyStatsDB  implements iNet2SQL {
    private final String TAG = "DailyStatsDB";
    Context context;
    ArrayList<String> siteList = new ArrayList<>();
    ArrayList<String> personList = new ArrayList<>();
    ArrayAdapter<String> personAdapter;
    ArrayAdapter<String> siteAdapter;
    String selectedSite = "";
    String selectedPerson = "";
    String saveSelectedSite = "";
    String saveSelectedPerson = "";

    long dateFrom, dateTo;
    long saveDateFrom, saveDateTo;

    private final static long DAY_MILLISEC = 3600 * 24 * 1000;
    SimpleCursorAdapter scAdapter;

    public long getDateFrom() {
        return dateFrom;
    }

    public long getDateTo() {
        return dateTo;
    }

    public void setDateFrom(long dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(long dateTo) {
        this.dateTo = dateTo;
    }

    public void setDate(long from, long to) {
        dateFrom = from;
        dateTo = to;
    }

    public DailyStatsDB(Context context) {
        this.context = context;
        dateTo = dateFrom = Calendar.getInstance().getTimeInMillis();
    }

    public int getSiteID(String site) {
        return DBHelper.getInstance().getSiteID(site);
    }

    public int getPersonID(String person) {
        return DBHelper.getInstance().getPersonID(person);
    }

    @Override
    public void init() {
        saveDateFrom = dateFrom;
        saveDateTo = dateTo;
        saveSelectedPerson = selectedPerson;
        saveSelectedSite = selectedSite;
    }

    @Override
    public void updateUI() {
        getSiteList();
        getPersonList();
        siteAdapter.notifyDataSetChanged();
        personAdapter.notifyDataSetChanged();
        setSelectedSitePosition(saveSelectedSite);
        setSelectedPersonPosition(saveSelectedPerson);
    }

    @Override
    public String getInfo() {
        return TAG;
    }

    @Override
    public void updateDB(String json, String param) {
        if (param.contains("/site")) {
            SitesDB.parseJSONforSites(json);

        } else if (param.contains("/person")) {
            PersonsDB.parseJSONforPerson(json);

        } else if (param.contains("/daily")) {
            parseJSONforDailyStats(json);
        }
    }

    public synchronized void parseJSONforDailyStats(String json) {
        try {
            JSONObject dataJsonObj = new JSONObject(json);
            JSONArray result = dataJsonObj.getJSONArray("result");
            for (int i = 0; i < result.length(); i++) {
                long time = saveDateFrom + i * DAY_MILLISEC;
                int v = result.getInt(i);
                DBHelper.getInstance().addOrUpdateDailyStatsWithCheck(
                        saveSelectedSite,
                        saveSelectedPerson,
                        time, v);
                Log.d(TAG, i + ":" + v);
            }
            DBHelper.getInstance().dumpTableDailyStats();
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    public ArrayList<String> getSiteList() {
        DBHelper.getInstance().getSiteList(siteList);
        return siteList;
    }

    public ArrayAdapter<String> getAdapterWithSite() {
        siteAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getSiteList());
        if (siteList.size() > 0) {
            if (selectedSite.equals("") || siteList.indexOf(selectedSite) < 0) {
                selectedSite = siteList.get(0);
            }
        } else {
            selectedSite = "";
        }

        siteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return siteAdapter;
    }

    public ArrayList<String> getPersonList() {
        DBHelper.getInstance().getPersonList(personList);
        return personList;
    }

    public ArrayAdapter<String> getAdapterWithPerson() {
        personAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getPersonList());
        if (personList.size() > 0) {
            if (selectedPerson.equals("") || personList.indexOf(selectedPerson) < 0) {
                selectedPerson = personList.get(0);
            }
        } else {
            selectedPerson = "";
        }
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return personAdapter;
    }

    public String getSelectedSite() {
        return selectedSite;
    }

    public String getSelectedPerson() {
        return selectedPerson;
    }

    public void setSelectedSitePosition(String site) {
        if (siteList.size() > 0 && siteList.indexOf(site) >= 0)
            selectedSite = site;
        else
            selectedSite = "";

        scAdapter.swapCursor(DBHelper.getInstance().getCursorOfDailyStatsWithSite(selectedSite, selectedPerson, dateFrom, dateTo));
        scAdapter.notifyDataSetChanged();
    }

    public void setSelectedPersonPosition(String person) {
        if (personList.size() > 0 && personList.indexOf(person) >= 0)
            selectedPerson = person;
        else
            selectedPerson = "";

        scAdapter.swapCursor(DBHelper.getInstance().getCursorOfDailyStatsWithSite(selectedSite, selectedPerson, dateFrom, dateTo));
        scAdapter.notifyDataSetChanged();
    }

    public SimpleCursorAdapter getAdapterWithStats(LoaderManager loaderManager) {
        String[] from = new String[]{DBHelper.DB.COLUMNS.DAILY.DATE, DBHelper.DB.COLUMNS.DAILY.STATS, "_id"};
        int[] to = new int[]{R.id.text1, R.id.text2};

        scAdapter = new SimpleCursorAdapter(context, R.layout.stats_item, null, from, to, 0);
        loaderManager.initLoader(LOADER_IDS.LOADER_DAILY_STATS.ordinal(), null,
                new CursorLoaderManager(scAdapter, new DailyStatsCursorLoader(context, selectedSite, selectedPerson, dateFrom, dateTo))
        );
        return scAdapter;

    }


}


class DailyStatsCursorLoader extends CursorLoader {
    String site;
    String person;
    long from,to;
    public DailyStatsCursorLoader(Context context,String site,String person,long from,long to) {
        super(context);
        this.site=site;
        this.person=person;
        this.from=from;
        this.to=to;
    }

    @Override
    public Cursor loadInBackground() {
        return DBHelper.getInstance().getCursorOfDailyStatsWithSite(site,person,from,to);
    }
}


