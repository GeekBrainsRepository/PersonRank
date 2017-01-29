package ru.geekbrain.gbseeker.personrank.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";

    //DB decriptor
    public interface DB {
        String DB_NAME = "personrank";
        int DB_VERSION = 1;

        interface TABLES {
            String SITE = "sites";
            String PERSON = "persons";
            String KEYWORD = "keywords";
            String COMMON = "common_stats";
            String DAILY = "daily_stats";
        }

        interface COLUMNS {
            interface SITE {
                String ID = "_id";
                String SITE = "site";
            }

            interface PERSON {
                String ID = "_id";
                String PERSON = "person";
            }

            interface KEYWORD {
                String ID = "_id";
                String KEYWORD = "keyword";
                String PERSON_REF = "person_id";
            }

            interface COMMON {
                String ID = "_id";
                String SITE_REF = "site_id";
                String PERSON_REF = "person_id";
                String STATS = "stats";
            }

            interface DAILY {
                String ID = "_id";
                String SITE_REF = "site_id";
                String PERSON_REF = "person_id";
                String DATE = "date";
                String STATS = "stats";
            }
        }
    }

    /// singleton for DB
    private SQLiteDatabase db = null;

    public SQLiteDatabase getDB() {
        if (db == null) db = getWritableDatabase();
        return db;
    }

    public void close() {
        super.close();
        db = null;
    }

    //singleton for DBHelper
    private static DBHelper dbhelper = null;

    public static void createDBHelper(Context context) {
        if (dbhelper == null) {
            dbhelper = new DBHelper(context);
        }
    }

    private DBHelper(Context context) {
        super(context, DB.DB_NAME, null, 1);
    }

    public static DBHelper getInstance() {
        return dbhelper;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DB.TABLES.SITE + "(" +
                DB.COLUMNS.SITE.ID + " INTEGER PRIMARY KEY," +
                DB.COLUMNS.SITE.SITE + " TEXT" +
                ")"
        );

        db.execSQL("CREATE TABLE " + DB.TABLES.PERSON + "(" +
                DB.COLUMNS.PERSON.ID + " INTEGER PRIMARY KEY," +
                DB.COLUMNS.PERSON.PERSON + " TEXT" +
                ")"
        );
        db.execSQL("CREATE TABLE " + DB.TABLES.KEYWORD + "(" +
                DB.COLUMNS.KEYWORD.ID + " INTEGER PRIMARY KEY," +
                DB.COLUMNS.KEYWORD.KEYWORD + " TEXT," +
                DB.COLUMNS.KEYWORD.PERSON_REF + " INTEGER " +
                ")"
        );

        db.execSQL("CREATE TABLE " + DB.TABLES.COMMON + "(" +
                DB.COLUMNS.COMMON.ID + " INTEGER PRIMARY KEY," +
                DB.COLUMNS.COMMON.STATS + " INTEGER," +
                DB.COLUMNS.COMMON.SITE_REF + " INTEGER," +
                DB.COLUMNS.COMMON.PERSON_REF + " INTEGER" +
                ")"
        );
        db.execSQL("CREATE TABLE " + DB.TABLES.DAILY + "(" +
                DB.COLUMNS.DAILY.ID + " INTEGER PRIMARY KEY," +
                DB.COLUMNS.DAILY.STATS + " INTEGER," +
                DB.COLUMNS.DAILY.DATE + " DATE," +
                DB.COLUMNS.DAILY.PERSON_REF + " INTEGER," +
                DB.COLUMNS.DAILY.SITE_REF + " INTEGER" +
                ")"
        );
    }

    public void addPerson(String person) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.PERSON.PERSON, person);
        getDB().insert(DB.TABLES.PERSON, null, cv);
    }
    public void addPersonWithCheck(String person) {
        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.PERSON, null, DB.COLUMNS.PERSON.PERSON + "='" + person + "'", null, null, null, null, null);
            if (cursor.moveToFirst()) {
                return;
            } else {
                addPerson(person);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
    public int getPersonID(String person) {
        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.PERSON, null, DB.COLUMNS.PERSON.PERSON + "='" + person + "'", null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int indexID = cursor.getColumnIndex(DB.COLUMNS.PERSON.ID);
                int id = cursor.getInt(indexID);
                return id;
            } else {
                return 0;
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
    public int getPersonIDOrCreate(String person) {
        int person_id = getPersonID(person);
        if (person_id == 0) {
            addPerson(person);
            return getPersonID(person);
        }else {
            return person_id;
        }
    }
    public void getPersonList(ArrayList<String> personList) {
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
    }
    public void getPersonListOnSite(ArrayList<String> personList,String site) {
        personList.clear();
        int site_id=getSiteID(site);

        dumpTablePerson();
        dumpTableSite();
        dumpTableDailyStats();

        String table = DB.TABLES.PERSON+" as PS inner join "+DB.TABLES.DAILY+" as DS " +
                " on PS."+DB.COLUMNS.PERSON.ID+"=DS."+DB.COLUMNS.DAILY.PERSON_REF;
        String columns[] = {"PS.person as person", " PS._id as _id"};
        String selection = "DS.site_id=?";
        String[] selectionArgs = {"" + site_id};

        Cursor cursor = null;
        try {
            cursor = DBHelper.getInstance().getDB().query(true,table, columns, selection, selectionArgs,null,null,null,null);
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(DBHelper.DB.COLUMNS.PERSON.PERSON);
                do {
                    personList.add(cursor.getString(index));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }



    public void addSite(String site) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.SITE.SITE, site);
        getDB().insert(DB.TABLES.SITE, null, cv);
    }
    public void addSiteWithCheck(String site) {
        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.SITE, null, DB.COLUMNS.SITE.SITE + "='" + site+"'", null, null, null, null, null);
            if (cursor.moveToFirst()) {
                return;
            } else {
                addSite(site);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
    public int getSiteID(String site) {
        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.SITE, null, DB.COLUMNS.SITE.SITE + "='" + site+"'", null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int indexID = cursor.getColumnIndex(DB.COLUMNS.SITE.ID);
                int id = cursor.getInt(indexID);
                return id;
            } else {
                return 0;
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
    public int getSiteIDOrCreate(String site) {
        int site_id= getSiteID(site);
        if (site_id == 0) {
            addSite(site);
        }
        return getSiteID(site);
    }
    public void getSiteList(ArrayList<String> siteList) {
        siteList.clear();
        Cursor cursor = null;
        try {
            cursor = DBHelper.getInstance().getDB().query(DB.TABLES.SITE, null, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(DB.COLUMNS.SITE.SITE);
                do {
                    siteList.add(cursor.getString(index));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public void addKeyword(int person_id,String keyword) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.KEYWORD.PERSON_REF, person_id);
        cv.put(DB.COLUMNS.KEYWORD.KEYWORD, keyword);
        getDB().insert(DB.TABLES.KEYWORD, null, cv);
    }
    public void addKeywordWithCheck(String person,String keyword){
        int person_id = getPersonIDOrCreate(person);

        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.KEYWORD, null,
                    DB.COLUMNS.KEYWORD.KEYWORD+ "='" + keyword +"' AND "+DB.COLUMNS.KEYWORD.PERSON_REF+"="+person_id,
                    null, null, null, null, null);
            if (cursor.moveToFirst()) {
                return;
            } else {
                addKeyword(person_id,keyword);
            }
        } finally {
            if(cursor!=null)cursor.close();
        }
    }
    public Cursor getCursorOfKeywordWithPerson(String person){
        int person_id=DBHelper.getInstance().getPersonID(person);
        return DBHelper.getInstance().getDB().query(DBHelper.DB.TABLES.KEYWORD, null,
                DBHelper.DB.COLUMNS.KEYWORD.PERSON_REF+"="+person_id,
                null, null, null, null, null);
    }

    public void addCommonStats(int site_id,int person_id,int stats) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.COMMON.PERSON_REF, person_id);
        cv.put(DB.COLUMNS.COMMON.SITE_REF, site_id);
        cv.put(DB.COLUMNS.COMMON.STATS, stats);
        getDB().insert(DB.TABLES.COMMON, null, cv);
    }
    public void updateCommonStats(int commonStatsID,int site_id,int person_id,int stats) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.COMMON.PERSON_REF, person_id);
        cv.put(DB.COLUMNS.COMMON.SITE_REF, site_id);
        cv.put(DB.COLUMNS.COMMON.STATS, stats);
        getDB().update(DB.TABLES.COMMON, cv,DB.COLUMNS.COMMON.ID+"="+commonStatsID,null);
    }
    public void addOrUpdateCommonStatsWithCheck(String site,String person,int stats) {
        int person_id = getPersonIDOrCreate(person);
        int site_id = getSiteIDOrCreate(site);

        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.COMMON, null,
                    DB.COLUMNS.COMMON.PERSON_REF + "=" + person_id + " AND " + DB.COLUMNS.COMMON.SITE_REF + "=" + site_id,
                    null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int indexID = cursor.getColumnIndex(DB.COLUMNS.COMMON.ID);
                int id = cursor.getInt(indexID);
                int indexStats = cursor.getColumnIndex(DB.COLUMNS.COMMON.STATS);
                if(cursor.getInt(indexStats)!=stats){
                    updateCommonStats(id,site_id,person_id,stats);
                }
                return;
            } else {
                addCommonStats(site_id, person_id, stats);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
    public Cursor getCursorOfCommonStatsWithSite(String site) {
        int site_id = DBHelper.getInstance().getSiteID(site);

        String table = " persons as PS inner join common_stats as CS  on PS._id=CS.person_id";
        String columns[] = {"PS.person as person", "CS.stats as stats", " PS._id as _id"};
        String selection = "CS.site_id=?";
        String[] selectionArgs = {"" + site_id};
        return DBHelper.getInstance().getDB().query(table, columns, selection, selectionArgs, null, null, null);
    }



    public void addDailyStats(int site_id, int person_id, Date date, int stats) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.DAILY.PERSON_REF, person_id);
        cv.put(DB.COLUMNS.DAILY.SITE_REF, site_id);
        cv.put(DB.COLUMNS.DAILY.STATS, stats);
        //SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        cv.put(DB.COLUMNS.DAILY.DATE, format.format(date));
        getDB().insert(DB.TABLES.DAILY, null, cv);
    }
    public void updateDailyStats(int dailyStatsID,int site_id,int person_id,Date date,int stats) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.DAILY.PERSON_REF, person_id);
        cv.put(DB.COLUMNS.DAILY.SITE_REF, site_id);
        cv.put(DB.COLUMNS.DAILY.DATE, date.toString());
        cv.put(DB.COLUMNS.DAILY.STATS, stats);
        getDB().update(DB.TABLES.DAILY, cv,DB.COLUMNS.DAILY.ID+"="+dailyStatsID,null);
    }
    public void addOrUpdateDailyStatsWithCheck(String site,String person,Date date,int stats) {
        int person_id = getPersonIDOrCreate(person);
        int site_id = getSiteIDOrCreate(site);

        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.DAILY, null,
                    DB.COLUMNS.DAILY.PERSON_REF + "=" + person_id + " AND " + DB.COLUMNS.DAILY.SITE_REF + "=" + site_id +
                            " AND " + DB.COLUMNS.DAILY.DATE + "='" + date.toString()+"'",
                    null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int indexID = cursor.getColumnIndex(DB.COLUMNS.DAILY.ID);
                int id = cursor.getInt(indexID);
                int indexStats = cursor.getColumnIndex(DB.COLUMNS.DAILY.STATS);
                if (cursor.getInt(indexStats) != stats) {
                    updateDailyStats(id, site_id, person_id, date, stats);
                }
                return;

            } else {
                addDailyStats(site_id, person_id, date, stats);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
    public Cursor getCursorOfDailyStatsWithSite(String site,String person) {
        int site_id = DBHelper.getInstance().getSiteID(site);
        int person_id = DBHelper.getInstance().getPersonID(person);

        return DBHelper.getInstance().getDB().query(DB.TABLES.DAILY, null,
                DB.COLUMNS.DAILY.PERSON_REF+'='+person_id+" and "+DB.COLUMNS.DAILY.SITE_REF+'='+site_id,
                null, null, null, null);
    }
    public void fillByFakeData() {
        dumpTablePerson();
        dumpTableSite();
        dumpTableKeyword();
        dumpTableCommonStats();
        dumpTableDailyStats();


        addPersonWithCheck("Петя");
        addPersonWithCheck("Вася");
        addPersonWithCheck("Оля");

        addSiteWithCheck("lenta.ru");
        addSiteWithCheck("mail.ru");
        addSiteWithCheck("yandex.ru");

        dumpTablePerson();
        dumpTableSite();
        dumpTableKeyword();
        dumpTableCommonStats();
        dumpTableDailyStats();

        addKeywordWithCheck("Петя", "Петр");
        addKeywordWithCheck("Петя", "Петрович");
        addKeywordWithCheck("Петя", "Петенька");
        addKeywordWithCheck("Вася", "Вася");
        addKeywordWithCheck("Оля", "Ольга");
        addKeywordWithCheck("Оля", "Оленька");

        dumpTablePerson();
        dumpTableSite();
        dumpTableKeyword();
        dumpTableCommonStats();
        dumpTableDailyStats();

        addOrUpdateCommonStatsWithCheck("lenta.ru", "Оля", 10);
        addOrUpdateCommonStatsWithCheck("mail.ru", "Оля", 20);
        addOrUpdateCommonStatsWithCheck("yandex.ru", "Оля", 30);
        addOrUpdateCommonStatsWithCheck("lenta.ru", "Петя", 100);
        addOrUpdateCommonStatsWithCheck("yandex.ru", "Петя", 1000);

        dumpTablePerson();
        dumpTableSite();
        dumpTableKeyword();
        dumpTableCommonStats();
        dumpTableDailyStats();

        addOrUpdateDailyStatsWithCheck("yandex.ru", "Петя", new Date(2017-1900,1,1),100);
        addOrUpdateDailyStatsWithCheck("yandex.ru", "Петя", new Date(2017-1900,1,2),200);
        addOrUpdateDailyStatsWithCheck("yandex.ru", "Петя", new Date(2017-1900,1,3),300);
        addOrUpdateDailyStatsWithCheck("yandex.ru", "Петя", new Date(2017-1900,1,4),250);
        addOrUpdateDailyStatsWithCheck("yandex.ru", "Петя", new Date(2017-1900,1,5),150);

        addOrUpdateDailyStatsWithCheck("lenta.ru", "Петя", new Date(2017-1900,1,1),10);
        addOrUpdateDailyStatsWithCheck("lenta.ru", "Петя", new Date(2017-1900,1,2),20);
        addOrUpdateDailyStatsWithCheck("lenta.ru", "Петя", new Date(2017-1900,1,3),30);
        addOrUpdateDailyStatsWithCheck("lenta.ru", "Петя", new Date(2017-1900,1,4),40);

        addOrUpdateDailyStatsWithCheck("yandex.ru", "Оля", new Date(2017-1900,1,11),10);
        addOrUpdateDailyStatsWithCheck("yandex.ru", "Оля", new Date(2017-1900,1,12),1);
        addOrUpdateDailyStatsWithCheck("yandex.ru", "Оля", new Date(2017-1900,1,13),0);
        addOrUpdateDailyStatsWithCheck("yandex.ru", "Оля", new Date(2017-1900,1,14),19);

        addOrUpdateDailyStatsWithCheck("mail.ru", "Оля", new Date(2017-1900,1,5),10);
        addOrUpdateDailyStatsWithCheck("mail.ru", "Оля", new Date(2017-1900,1,6),6);
        addOrUpdateDailyStatsWithCheck("mail.ru", "Оля", new Date(2017-1900,1,9),4);

        addOrUpdateDailyStatsWithCheck("lenta.ru", "Оля", new Date(2017-1900,1,1),10);

        dumpTablePerson();
        dumpTableSite();
        dumpTableKeyword();
        dumpTableCommonStats();
        dumpTableDailyStats();
    }

    public void dumpTablePerson() {
        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.PERSON, null, null, null, null, null, null, null);
            Log.d(TAG, "PERSON: read=" + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    int indexID = cursor.getColumnIndex(DB.COLUMNS.PERSON.ID);
                    int indexPerson = cursor.getColumnIndex(DB.COLUMNS.PERSON.PERSON);
                    Log.d(TAG, cursor.getInt(indexID) + ":" + cursor.getString(indexPerson));
                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "empty");
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
    public void dumpTableSite() {
        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.SITE, null, null, null, null, null, null, null);
            Log.d(TAG, "SITE: read=" + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    int indexID = cursor.getColumnIndex(DB.COLUMNS.SITE.ID);
                    int indexSite = cursor.getColumnIndex(DB.COLUMNS.SITE.SITE);
                    Log.d(TAG, cursor.getInt(indexID) + ":" + cursor.getString(indexSite));
                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "empty");
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
    public void dumpTableKeyword() {
        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.KEYWORD, null, null, null, null, null, null, null);
            Log.d(TAG, "KEYWORD: read=" + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    int indexID = cursor.getColumnIndex(DB.COLUMNS.KEYWORD.ID);
                    int indexKeyword = cursor.getColumnIndex(DB.COLUMNS.KEYWORD.KEYWORD);
                    int indexRef = cursor.getColumnIndex(DB.COLUMNS.KEYWORD.PERSON_REF);
                    Log.d(TAG, cursor.getInt(indexID) + ":" + cursor.getString(indexKeyword)+ ":" + cursor.getInt(indexRef));
                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "empty");
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
    public void dumpTableCommonStats() {
        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.COMMON, null, null, null, null, null, null, null);
            Log.d(TAG, "COMMON: read=" + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    int indexID = cursor.getColumnIndex(DB.COLUMNS.COMMON.ID);
                    int indexStats = cursor.getColumnIndex(DB.COLUMNS.COMMON.STATS);
                    int indexPersonRef = cursor.getColumnIndex(DB.COLUMNS.COMMON.PERSON_REF);
                    int indexSiteRef = cursor.getColumnIndex(DB.COLUMNS.COMMON.SITE_REF);
                    Log.d(TAG, cursor.getInt(indexID) + ":" + cursor.getInt(indexStats)+ ":" + cursor.getInt(indexPersonRef)+ ":" + cursor.getInt(indexSiteRef));
                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "empty");
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
    public void dumpTableDailyStats() {
        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.DAILY, null, null, null, null, null, null, null);
            Log.d(TAG, "DAILY: read=" + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    int indexID = cursor.getColumnIndex(DB.COLUMNS.DAILY.ID);
                    int indexStats = cursor.getColumnIndex(DB.COLUMNS.DAILY.STATS);
                    int indexPersonRef = cursor.getColumnIndex(DB.COLUMNS.DAILY.PERSON_REF);
                    int indexSiteRef = cursor.getColumnIndex(DB.COLUMNS.DAILY.SITE_REF);
                    int indexDate = cursor.getColumnIndex(DB.COLUMNS.DAILY.DATE);
                    Log.d(TAG, cursor.getInt(indexID) + ":" + cursor.getInt(indexDate) + ":" + cursor.getInt(indexStats)+ ":" + cursor.getInt(indexPersonRef)+ ":" + cursor.getInt(indexSiteRef));
                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "empty");
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
}
/*    public void deepDeleteChild(int parent_id){
        Cursor cursor = null;
        try {
            cursor = getDB().query(DBtableTask, null, DBtableTaskParent + "=" + parent_id, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int indexID = cursor.getColumnIndex(DBtableTaskID);
                do {
                    int id = cursor.getInt(indexID);
                    deepDeleteChild(id);
                } while (cursor.moveToNext());
            }
            db.delete(DBtableTask, DBtableTaskParent + "=" + parent_id, null);
        }
        finally {
            if(cursor!=null) cursor.close();
        }
    }
    public void delete(int id){
        getDB().delete(DBtableTask, DBtableTaskID+"=" + id, null);
    }
    public void delete(){
        getDB().delete(DBtableTask, null, null);
    }

    public void update(Task ts) {
        ContentValues cv = new ContentValues();
        cv.put(DBtableTaskID, ts.getId());
        cv.put(DBtableTaskName, ts.getTaskName());
        cv.put(DBtableTaskParent, ts.getParent() == null ? -1 : ts.getParent().getId());
        getDB().update(DBtableTask, cv, DBtableTaskID + "=" + ts.getId(), null);
    }

    public void insert(Task ts) {
        ContentValues cv = new ContentValues();
        cv.put(DBtableTaskID, ts.getId());
        cv.put(DBtableTaskName, ts.getTaskName());
        cv.put(DBtableTaskParent, ts.getParent() == null ? -1 : ts.getParent().getId());
        getDB().insert(DBtableTask, null, cv);
    }



    public void serializeTaskListSQL(Task ts) {
        Cursor cursor = null;
        try {
            cursor = getDB().query(DBtableTask, null, DBtableTaskID + "=" + ts.getId(), null, null, null, null, null);
            int parent_id = (ts.getParent() == null) ? -1 : ts.getParent().getId();

            if (cursor.moveToFirst()) {
                int idName = cursor.getColumnIndex(DBtableTaskName);
                int idParent = cursor.getColumnIndex(DBtableTaskParent);

                if (!cursor.getString(idName).equals(ts.getTaskName())
                        || cursor.getInt(idParent) != parent_id) { //need to update record
                    update(ts);
                } else { // record match with current
                }
            } else { //there is no such record
                insert(ts);
            }
        }
        finally {
            if(cursor!=null) cursor.close();
        }

        // check child
        int size = ts.getChild() == null ? 0 : ts.getChild().size();
        if (size == 0) {
            deepDeleteChild(ts.getId());
        } else {
            cursor=null;
            HashMap<Integer, Task> ids = new HashMap<>(size);

            try {
                cursor = getDB().query(DBtableTask, null, DBtableTaskParent + "=" + ts.getId(), null, null, null, null, null);

                for (Task t : ts.getChild()) {
                    ids.put(t.getId(), t);
                }

                if (cursor.moveToFirst()) {
                    do {
                        int id = cursor.getColumnIndex(DBtableTaskID);
                        Task t = ids.get(cursor.getInt(id));
                        if (t == null) {
                            delete(cursor.getInt(id));
                        } else {
                            serializeTaskListSQL(t);
                            ids.remove(cursor.getInt(id));
                        }
                    } while (cursor.moveToNext());
                }
            }
            finally {
                if(cursor!=null) cursor.close();
            }
            for (Map.Entry<Integer, Task> e : ids.entrySet()) {
                serializeTaskListSQL(e.getValue());
            }

        }

    }


    public void deserializeTaskListSQL(Task ts) {
        Cursor cursor =null;
        try {
            cursor = getDB().query(DBtableTask, null, DBtableTaskParent + "=" + ts.getId(), null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    int idName = cursor.getColumnIndex(DBtableTaskName);
                    int idParent = cursor.getColumnIndex(DBtableTaskParent);
                    int id = cursor.getColumnIndex(DBtableTaskID);

                    Task child = new Task(cursor.getString(idName), ts);
                    child.setId(cursor.getInt(id));
                    ts.addChildTask(-1, child);

                    if(ts.getId()>Task.getGlobalID()){ Task.setGlobalID(ts.getId()+1);}

                    deserializeTaskListSQL(child);
                } while (cursor.moveToNext());
            }
        }
        finally {
            if(cursor!=null) cursor.close();
        }
    }

    public Task deserializeRootTaskListSQL() {
        Cursor cursor = null;
        Task ts = Task.getEmptyTask();

        try {
            cursor = getDB().query(DBtableTask, null, DBtableTaskParent + "=-1", null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int idName = cursor.getColumnIndex(DBtableTaskName);
                int id = cursor.getColumnIndex(DBtableTaskID);

                ts = new Task(cursor.getString(idName), null);
                ts.setId(cursor.getInt(id));

                if (ts.getId() > Task.getGlobalID()) {
                    Task.setGlobalID(ts.getId() + 1);
                }
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return ts;
    }

    public void readSQLToConsole() {
        Cursor cursor = null;
        try {
            cursor = getDB().query(DBtableTask, null, null, null, null, null, null, null);
            System.out.println("read=" + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    int idName = cursor.getColumnIndex(DBtableTaskName);
                    int idparent = cursor.getColumnIndex(DBtableTaskParent);
                    int id = cursor.getColumnIndex(DBtableTaskID);

                    System.out.println(cursor.getInt(id) + ":" + cursor.getInt(idparent) + ":" + cursor.getString(idName));
                } while (cursor.moveToNext());
            } else {
                System.out.println("empty");
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }


}


*/