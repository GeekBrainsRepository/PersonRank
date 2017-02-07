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

    //DB descriptor
    public interface DB  {
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
                String ID = "id";
                String SITE = "site";
            }

            interface PERSON {
                String ID = "id";
                String PERSON = "person";
            }

            interface KEYWORD {
                String KEYWORD = "keyword";
                String PERSON = "person";
            }

            interface COMMON {
                String SITE = "site";
                String PERSON = "person";
                String STATS = "stats";
            }

            interface DAILY {
                String SITE = "site";
                String PERSON = "person";
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
        super(context, DB.DB_NAME, null, DB.DB_VERSION);
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
                "_id INTEGER PRIMARY KEY," +
                DB.COLUMNS.SITE.ID + " INTEGER," +
                DB.COLUMNS.SITE.SITE + " TEXT" +
                ")"
        );

        db.execSQL("CREATE TABLE " + DB.TABLES.PERSON + "(" +
                "_id INTEGER PRIMARY KEY," +
                DB.COLUMNS.PERSON.ID + " INTEGER," +
                DB.COLUMNS.PERSON.PERSON + " TEXT" +
                ")"
        );
        db.execSQL("CREATE TABLE " + DB.TABLES.KEYWORD + "(" +
                "_id INTEGER PRIMARY KEY," +
                DB.COLUMNS.KEYWORD.KEYWORD + " TEXT," +
                DB.COLUMNS.KEYWORD.PERSON + " TEXT " +
                ")"
        );

        db.execSQL("CREATE TABLE " + DB.TABLES.COMMON + "(" +
                "_id INTEGER PRIMARY KEY," +
                DB.COLUMNS.COMMON.STATS + " INTEGER," +
                DB.COLUMNS.COMMON.SITE + " TEXT," +
                DB.COLUMNS.COMMON.PERSON + " TEXT" +
                ")"
        );
        db.execSQL("CREATE TABLE " + DB.TABLES.DAILY + "(" +
                "_id INTEGER PRIMARY KEY," +
                DB.COLUMNS.DAILY.STATS + " INTEGER," +
                DB.COLUMNS.DAILY.DATE + " DATE," +
                DB.COLUMNS.DAILY.PERSON + " TEXT," +
                DB.COLUMNS.DAILY.SITE + " TEXT" +
                ")"
        );

    }

    public void addPerson(int id,String person) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.PERSON.ID, id);
        cv.put(DB.COLUMNS.PERSON.PERSON, person);
        getDB().insert(DB.TABLES.PERSON, null, cv);
    }
    public void addPersonWithCheck(int id,String person) {
        getDB().execSQL("DELETE FROM "+DB.TABLES.PERSON+" WHERE  "+
                " (" + DB.COLUMNS.PERSON.PERSON + "='" + person + "' OR " + DB.COLUMNS.PERSON.ID + "=" + id + ") " +
                " AND " +
                " NOT (" + DB.COLUMNS.PERSON.PERSON + "='" + person+ "' AND " + DB.COLUMNS.PERSON.ID + "=" + id + ")");

        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.PERSON, null,
                    DB.COLUMNS.PERSON.PERSON + "='" + person + "' AND " + DB.COLUMNS.PERSON.ID + "=" + id,
                    null, null, null, null, null);
            if (cursor.moveToFirst()) {
            } else {
                addPerson(id, person);
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
                return cursor.getInt(indexID);
            } else {
                return 0;
            }
        } finally {
            if (cursor != null) cursor.close();
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
    public Cursor getCursorWithPersons(){
        return DBHelper.getInstance().getDB().query(DB.TABLES.PERSON, null,null,null, null, null, null, null);
    }

    public void getPersonListOnSite(ArrayList<String> personList,String site) {
        personList.clear();
        String columns[] = {DB.COLUMNS.DAILY.PERSON};
        Cursor cursor = null;
        try {
            cursor = DBHelper.getInstance().getDB().query(true,DB.TABLES.DAILY, columns,DB.COLUMNS.DAILY.SITE+"='"+site+"'" ,null,null,null,null,null);
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(DB.COLUMNS.DAILY.PERSON);
                do {
                    personList.add(cursor.getString(index));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public void addSite(int id,String site) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.SITE.ID, id);
        cv.put(DB.COLUMNS.SITE.SITE, site);
        getDB().insert(DB.TABLES.SITE, null, cv);
    }
    public void addSiteWithCheck(int id,String site) {
        getDB().execSQL("DELETE FROM "+DB.TABLES.SITE+" WHERE  "
                +"(" + DB.COLUMNS.SITE.SITE + "='" + site + "' OR " + DB.COLUMNS.SITE.ID + "=" + id + ") " +
                " AND " +
                 " NOT (" + DB.COLUMNS.SITE.SITE + "='" + site + "' AND " + DB.COLUMNS.SITE.ID + "=" + id + ")");

        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.SITE, null,
                    DB.COLUMNS.SITE.SITE + "='" + site + "' AND " + DB.COLUMNS.SITE.ID + "=" + id,
                    null, null, null, null, null);
            if (cursor.moveToFirst()) {
            } else {
                addSite(id, site);
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
                return cursor.getInt(indexID);
            } else {
                return 0;
            }
        } finally {
            if (cursor != null) cursor.close();
        }
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
    public Cursor getCursorWithSites(){
        return DBHelper.getInstance().getDB().query(DB.TABLES.SITE, null,null,null, null, null, null, null);
    }

    public void addKeyword(String person,String keyword) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.KEYWORD.PERSON, person);
        cv.put(DB.COLUMNS.KEYWORD.KEYWORD, keyword);
        getDB().insert(DB.TABLES.KEYWORD, null, cv);
    }
    public void addKeywordWithCheck(String person,String keyword) {
        getDB().execSQL("DELETE FROM " + DB.TABLES.KEYWORD + " WHERE  "
                + "(" + DB.COLUMNS.KEYWORD.KEYWORD + "='" + keyword + "' OR " + DB.COLUMNS.KEYWORD.PERSON + "=" + person + ") "
                + " AND "
                + " NOT (" + DB.COLUMNS.KEYWORD.KEYWORD + "='" + keyword + "' AND "  + DB.COLUMNS.KEYWORD.PERSON + "=" + person + ") "
                + ")");


        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.KEYWORD, null,
                    DB.COLUMNS.KEYWORD.KEYWORD + "='" + keyword + "' AND " + DB.COLUMNS.KEYWORD.PERSON + "=" + person,
                    null, null, null, null, null);
            if (cursor.moveToFirst()) {
            } else {
                addKeyword(person, keyword);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
    public Cursor getCursorOfKeywordWithPerson(String person){
        return getDB().query(DBHelper.DB.TABLES.KEYWORD, null,
                DBHelper.DB.COLUMNS.KEYWORD.PERSON+"="+person,
                null, null, null, null, null);
    }

    public void getPersonListFromKeyword(ArrayList<String> personList) {
        personList.clear();
        Cursor cursor = null;
        try {
            cursor = DBHelper.getInstance().getDB().query(true,DB.TABLES.KEYWORD, new String[]{DB.COLUMNS.KEYWORD.PERSON}, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(DB.COLUMNS.KEYWORD.PERSON);
                do {
                    personList.add(cursor.getString(index));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public void addCommonStats(String site,String person,int stats) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.COMMON.PERSON, person);
        cv.put(DB.COLUMNS.COMMON.SITE, site);
        cv.put(DB.COLUMNS.COMMON.STATS, stats);
        getDB().insert(DB.TABLES.COMMON, null, cv);
    }
    public void updateCommonStats(int _id,String site,String person,int stats) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.COMMON.PERSON, person);
        cv.put(DB.COLUMNS.COMMON.SITE, site);
        cv.put(DB.COLUMNS.COMMON.STATS, stats);
        getDB().update(DB.TABLES.COMMON, cv,"_id="+_id,null);
    }
    public void addOrUpdateCommonStatsWithCheck(String site,String person,int stats) {
        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.COMMON, null,
                    DB.COLUMNS.COMMON.PERSON + "='" + person + "' AND " + DB.COLUMNS.COMMON.SITE + "='" + site+"'",
                    null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int indexID = cursor.getColumnIndex("_id");
                int id = cursor.getInt(indexID);
                int indexStats = cursor.getColumnIndex(DB.COLUMNS.COMMON.STATS);
                if(cursor.getInt(indexStats)!=stats){
                    updateCommonStats(id,site,person,stats);
                }
                return;
            } else {
                addCommonStats(site, person, stats);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
    public Cursor getCursorOfCommonStatsWithSite(String site) {
        String table = DB.TABLES.COMMON;
        String columns[] = {DB.COLUMNS.COMMON.PERSON, DB.COLUMNS.COMMON.STATS};
        String selection = "CS."+DB.COLUMNS.COMMON.SITE+"='"+site+"'";
        return DBHelper.getInstance().getDB().query(table, columns, selection, null, null, null, null);
    }

    public void addDailyStats(String site, String person, Date date, int stats) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.DAILY.PERSON, person);
        cv.put(DB.COLUMNS.DAILY.SITE, site);
        cv.put(DB.COLUMNS.DAILY.STATS, stats);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        cv.put(DB.COLUMNS.DAILY.DATE, format.format(date));
        getDB().insert(DB.TABLES.DAILY, null, cv);
    }
    public void updateDailyStats(int _id,String site,String person,Date date,int stats) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.DAILY.PERSON, person);
        cv.put(DB.COLUMNS.DAILY.SITE, site);
        cv.put(DB.COLUMNS.DAILY.DATE, date.toString());
        cv.put(DB.COLUMNS.DAILY.STATS, stats);
        getDB().update(DB.TABLES.DAILY, cv,"_id="+_id,null);
    }
    public void addOrUpdateDailyStatsWithCheck(String site,String person,long  date,int stats) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Cursor cursor = null;
        try {
            Date d=new Date(date);
            cursor = getDB().query(DB.TABLES.DAILY, null,
                    DB.COLUMNS.DAILY.PERSON + "='" + person + "' AND " + DB.COLUMNS.DAILY.SITE + "='" + site +"'"+
                            " AND " + DB.COLUMNS.DAILY.DATE + "='" + format.format(d)+"'",
                    null, null, null, null, null);
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndex("_id"));
                int indexStats = cursor.getColumnIndex(DB.COLUMNS.DAILY.STATS);
                if (cursor.getInt(indexStats) != stats) {
                    updateDailyStats(id, site, person, d, stats);
                }
                return;

            } else {
                addDailyStats(site, person, d, stats);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
    public Cursor getCursorOfDailyStatsWithSite(String site,String person) {
        return DBHelper.getInstance().getDB().query(DB.TABLES.DAILY, null,
                DB.COLUMNS.DAILY.PERSON+'='+person+"' AND "+DB.COLUMNS.DAILY.SITE+'='+site+"'",
                null, null, null, null);
    }

    public void fillByFakeData() {
  /*      dumpTablePerson();
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
        dumpTableDailyStats();*/
    }

    public void dumpTablePerson() {
        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.PERSON, null, null, null, null, null, null, null);
            Log.d(TAG, "PERSON: read=" + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getColumnIndex("_id");
                    int indexID = cursor.getColumnIndex(DB.COLUMNS.PERSON.ID);
                    int indexPerson = cursor.getColumnIndex(DB.COLUMNS.PERSON.PERSON);
                    Log.d(TAG, "_id="+cursor.getInt(id)+":ID="+cursor.getInt(indexID) + ":person=" + cursor.getString(indexPerson));
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
                    int id = cursor.getColumnIndex("_id");
                    int indexID = cursor.getColumnIndex(DB.COLUMNS.SITE.ID);
                    int indexSite = cursor.getColumnIndex(DB.COLUMNS.SITE.SITE);
                    Log.d(TAG, "_id="+cursor.getInt(id)+":ID="+cursor.getInt(indexID) + ":site=" + cursor.getString(indexSite));
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
                    int id = cursor.getColumnIndex("_id");
                    int indexKeyword = cursor.getColumnIndex(DB.COLUMNS.KEYWORD.KEYWORD);
                    int indexRef = cursor.getColumnIndex(DB.COLUMNS.KEYWORD.PERSON);
                    Log.d(TAG, "_id="+cursor.getInt(id)+ ":word=" + cursor.getString(indexKeyword)+
                            ":person_ref=" + cursor.getString(indexRef));
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
                    int id = cursor.getColumnIndex("_id");
                    int indexStats = cursor.getColumnIndex(DB.COLUMNS.COMMON.STATS);
                    int indexPersonRef = cursor.getColumnIndex(DB.COLUMNS.COMMON.PERSON);
                    int indexSiteRef = cursor.getColumnIndex(DB.COLUMNS.COMMON.SITE);
                    Log.d(TAG, "_id="+cursor.getInt(id)+ ":stat=" + cursor.getInt(indexStats)+
                            ":per_ref=" + cursor.getString(indexPersonRef)+ ":site_ref=" + cursor.getString(indexSiteRef));
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
                    int id = cursor.getColumnIndex("_id");
                    int indexStats = cursor.getColumnIndex(DB.COLUMNS.DAILY.STATS);
                    int indexPersonRef = cursor.getColumnIndex(DB.COLUMNS.DAILY.PERSON);
                    int indexSiteRef = cursor.getColumnIndex(DB.COLUMNS.DAILY.SITE);
                    int indexDate = cursor.getColumnIndex(DB.COLUMNS.DAILY.DATE);
                    Log.d(TAG, "_id="+cursor.getInt(id)+ ":date=" + cursor.getString(indexDate)+
                            ":stat=" + cursor.getInt(indexStats)+
                            ":per_ref=" + cursor.getString(indexPersonRef)+ ":site_ref=" + cursor.getString(indexSiteRef));

                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "empty");
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
}
