package ru.geekbrain.gbseeker.personrank.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";

    //DB decriptor
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
                String ID = "id";
                String KEYWORD = "keyword";
                String PERSON_REF = "person_ref";
            }

            interface COMMON {
                String ID = "id";
                String SITE_REF = "site_ref";
                String PERSON_REF = "person_ref";
                String STATS = "stats";
            }

            interface DAILY {
                String ID = "id";
                String SITE_REF = "site_ref";
                String PERSON_REF = "person_ref";
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
                DB.COLUMNS.KEYWORD.ID + " INTEGER," +
                DB.COLUMNS.KEYWORD.KEYWORD + " TEXT," +
                DB.COLUMNS.KEYWORD.PERSON_REF + " INTEGER " +
                ")"
        );

        db.execSQL("CREATE TABLE " + DB.TABLES.COMMON + "(" +
                "_id INTEGER PRIMARY KEY," +
                DB.COLUMNS.COMMON.ID + " INTEGER," +
                DB.COLUMNS.COMMON.STATS + " INTEGER," +
                DB.COLUMNS.COMMON.SITE_REF + " INTEGER," +
                DB.COLUMNS.COMMON.PERSON_REF + " INTEGER" +
                ")"
        );
        db.execSQL("CREATE TABLE " + DB.TABLES.DAILY + "(" +
                "_id INTEGER PRIMARY KEY," +
                DB.COLUMNS.DAILY.ID + " INTEGER," +
                DB.COLUMNS.DAILY.STATS + " INTEGER," +
                DB.COLUMNS.DAILY.DATE + " DATE," +
                DB.COLUMNS.DAILY.PERSON_REF + " INTEGER," +
                DB.COLUMNS.DAILY.SITE_REF + " INTEGER" +
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
        getDB().execSQL("DELETE FROM "+DB.TABLES.COMMON+" WHERE  "+DB.COLUMNS.COMMON.PERSON_REF+" in "+
                "(select "+DB.COLUMNS.PERSON.ID+" from "+DB.TABLES.PERSON +
                " where (" + DB.COLUMNS.PERSON.PERSON + "!='" + person + "' AND " + DB.COLUMNS.PERSON.ID + "=" + id + ") " +
                " OR " +
                "(" + DB.COLUMNS.PERSON.PERSON + "='" + person+ "' AND " + DB.COLUMNS.PERSON.ID + "!=" + id + ")"+
                ")");
        getDB().execSQL("DELETE FROM "+DB.TABLES.DAILY+" WHERE  "+DB.COLUMNS.DAILY.PERSON_REF+" in " +
                "(select "+DB.COLUMNS.PERSON.ID+" from "+DB.TABLES.PERSON +
                " where (" + DB.COLUMNS.PERSON.PERSON + "!='" + person + "' AND " + DB.COLUMNS.PERSON.ID + "=" + id + ") " +
                " OR " +
                "(" + DB.COLUMNS.PERSON.PERSON + "='" + person+ "' AND " + DB.COLUMNS.PERSON.ID + "!=" + id + ")"+
                ")");
        getDB().execSQL("DELETE FROM "+DB.TABLES.KEYWORD+" WHERE  "+DB.COLUMNS.KEYWORD.PERSON_REF+" in " +
                "(select "+DB.COLUMNS.PERSON.ID+" from "+DB.TABLES.PERSON +
                " where (" + DB.COLUMNS.PERSON.PERSON + "!='" + person + "' AND " + DB.COLUMNS.PERSON.ID + "=" + id + ") " +
                " OR " +
                "(" + DB.COLUMNS.PERSON.PERSON + "='" + person+ "' AND " + DB.COLUMNS.PERSON.ID + "!=" + id + ")"+
                ")");
        getDB().execSQL("DELETE FROM "+DB.TABLES.PERSON+" WHERE  "+
                " (" + DB.COLUMNS.PERSON.PERSON + "!='" + person + "' AND " + DB.COLUMNS.PERSON.ID + "=" + id + ") " +
                " OR " +
                "(" + DB.COLUMNS.PERSON.PERSON + "='" + person+ "' AND " + DB.COLUMNS.PERSON.ID + "!=" + id + ")");

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
                int id = cursor.getInt(indexID);
                return id;
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
    public void getPersonListOnSite(ArrayList<String> personList,String site) {
        personList.clear();
        int site_id=getSiteID(site);

        String table = DB.TABLES.PERSON+" as PS inner join "+DB.TABLES.DAILY+" as DS " +
                " on PS."+DB.COLUMNS.PERSON.ID+"=DS."+DB.COLUMNS.DAILY.PERSON_REF;
        String columns[] = {"PS."+DB.COLUMNS.PERSON.PERSON+" as "+DB.COLUMNS.PERSON.PERSON, " PS._id as _id"};
        String selection = "DS."+DB.COLUMNS.DAILY.SITE_REF+"=?";
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
    public Cursor getCursorWithPersons(){
        return DBHelper.getInstance().getDB().query(DB.TABLES.PERSON, null,null,null, null, null, null, null);
    }

    public void addSite(int id,String site) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.SITE.ID, id);
        cv.put(DB.COLUMNS.SITE.SITE, site);
        getDB().insert(DB.TABLES.SITE, null, cv);
    }
    public void addSiteWithCheck(int id,String site) {
        getDB().execSQL("DELETE FROM "+DB.TABLES.COMMON+" WHERE  "+DB.COLUMNS.COMMON.SITE_REF+" in "+
                "(select "+DB.COLUMNS.SITE.ID+" from "+DB.TABLES.SITE +
                   " where (" + DB.COLUMNS.SITE.SITE + "!='" + site + "' AND " + DB.COLUMNS.SITE.ID + "=" + id + ") " +
                         " OR " +
                          "(" + DB.COLUMNS.SITE.SITE + "='" + site + "' AND " + DB.COLUMNS.SITE.ID + "!=" + id + ")"+
                ")");
        getDB().execSQL("DELETE FROM "+DB.TABLES.DAILY+" WHERE  "+DB.COLUMNS.DAILY.SITE_REF+" in " +
                "(select "+DB.COLUMNS.SITE.ID+" from "+DB.TABLES.SITE +
                   " where (" + DB.COLUMNS.SITE.SITE + "!='" + site + "' AND " + DB.COLUMNS.SITE.ID + "=" + id + ") " +
                          "OR " +
                          "(" + DB.COLUMNS.SITE.SITE + "='" + site + "' AND " + DB.COLUMNS.SITE.ID + "!=" + id + ")" +
                ")");
        getDB().execSQL("DELETE FROM "+DB.TABLES.SITE+" WHERE  "
                +"(" + DB.COLUMNS.SITE.SITE + "!='" + site + "' AND " + DB.COLUMNS.SITE.ID + "=" + id + ") " +
                " OR " +
                 "(" + DB.COLUMNS.SITE.SITE + "='" + site + "' AND " + DB.COLUMNS.SITE.ID + "!=" + id + ")");

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
                int id = cursor.getInt(indexID);
                return id;
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

    public void addKeyword(int id,int person_id,String keyword) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.KEYWORD.ID, id);
        cv.put(DB.COLUMNS.KEYWORD.PERSON_REF, person_id);
        cv.put(DB.COLUMNS.KEYWORD.KEYWORD, keyword);
        getDB().insert(DB.TABLES.KEYWORD, null, cv);
    }
    public void addKeywordWithCheck(int id,String person,String keyword) {
        int person_id = getPersonID(person);

        getDB().execSQL("DELETE FROM " + DB.TABLES.COMMON + " WHERE  " + DB.COLUMNS.COMMON.PERSON_REF + " in " +
                "(select " + DB.COLUMNS.KEYWORD.PERSON_REF + " from " + DB.TABLES.KEYWORD
                + " where (" + DB.COLUMNS.KEYWORD.KEYWORD + "='" + keyword + "' OR " + DB.COLUMNS.KEYWORD.ID + "=" + id + " OR " + DB.COLUMNS.KEYWORD.PERSON_REF + "=" + person_id + ") "
                + " AND "
                + " NOT (" + DB.COLUMNS.KEYWORD.KEYWORD + "='" + keyword + "' AND " + DB.COLUMNS.KEYWORD.ID + "=" + id + " AND " + DB.COLUMNS.KEYWORD.PERSON_REF + "=" + person_id + ") "
                + ")");
        getDB().execSQL("DELETE FROM " + DB.TABLES.DAILY + " WHERE  " + DB.COLUMNS.DAILY.PERSON_REF + " in " +
                "(select " + DB.COLUMNS.KEYWORD.PERSON_REF + " from " + DB.TABLES.KEYWORD
                + " where (" + DB.COLUMNS.KEYWORD.KEYWORD + "='" + keyword + "' OR " + DB.COLUMNS.KEYWORD.ID + "=" + id + " OR " + DB.COLUMNS.KEYWORD.PERSON_REF + "=" + person_id + ") "
                + " AND "
                + " NOT (" + DB.COLUMNS.KEYWORD.KEYWORD + "='" + keyword + "' AND " + DB.COLUMNS.KEYWORD.ID + "=" + id + " AND " + DB.COLUMNS.KEYWORD.PERSON_REF + "=" + person_id + ") "
                + ")");

        getDB().execSQL("DELETE FROM " + DB.TABLES.PERSON + " WHERE  " + DB.COLUMNS.PERSON.ID + " in " +
                "(select " + DB.COLUMNS.KEYWORD.PERSON_REF + " from " + DB.TABLES.KEYWORD
                + " where (" + DB.COLUMNS.KEYWORD.KEYWORD + "='" + keyword + "' OR " + DB.COLUMNS.KEYWORD.ID + "=" + id + " OR " + DB.COLUMNS.KEYWORD.PERSON_REF + "=" + person_id + ") "
                + " AND "
                + " NOT (" + DB.COLUMNS.KEYWORD.KEYWORD + "='" + keyword + "' AND " + DB.COLUMNS.KEYWORD.ID + "=" + id + " AND " + DB.COLUMNS.KEYWORD.PERSON_REF + "=" + person_id + ") "
                + ")");

        getDB().execSQL("DELETE FROM " + DB.TABLES.KEYWORD + " WHERE  "
                + "(" + DB.COLUMNS.KEYWORD.KEYWORD + "='" + keyword + "' OR " + DB.COLUMNS.KEYWORD.ID + "=" + id + " OR " + DB.COLUMNS.KEYWORD.PERSON_REF + "=" + person_id + ") "
                + " AND "
                + " NOT (" + DB.COLUMNS.KEYWORD.KEYWORD + "='" + keyword + "' AND " + DB.COLUMNS.KEYWORD.ID + "=" + id + " AND " + DB.COLUMNS.KEYWORD.PERSON_REF + "=" + person_id + ") "
                + ")");


        Cursor cursor = null;
        try {
            cursor = getDB().query(DB.TABLES.KEYWORD, null,
                    DB.COLUMNS.KEYWORD.KEYWORD + "='" + keyword + "' AND " + DB.COLUMNS.KEYWORD.ID + "=" + id + " AND " + DB.COLUMNS.KEYWORD.PERSON_REF + "=" + person_id,
                    null, null, null, null, null);
            if (cursor.moveToFirst()) {
            } else {
                addKeyword(id, person_id, keyword);
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
    public Cursor getCursorOfKeywordWithPerson(String person){
        int person_id=getPersonID(person);
        return getDB().query(DBHelper.DB.TABLES.KEYWORD, null,
                DBHelper.DB.COLUMNS.KEYWORD.PERSON_REF+"="+person_id,
                null, null, null, null, null);
    }

    public void addCommonStats(int id,int site_id,int person_id,int stats) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.COMMON.ID, id);
        cv.put(DB.COLUMNS.COMMON.PERSON_REF, person_id);
        cv.put(DB.COLUMNS.COMMON.SITE_REF, site_id);
        cv.put(DB.COLUMNS.COMMON.STATS, stats);
        getDB().insert(DB.TABLES.COMMON, null, cv);
    }
    public void updateCommonStats(int _id,int id,int site_id,int person_id,int stats) {
        ContentValues cv = new ContentValues();
        cv.put(DB.COLUMNS.COMMON.ID, id);
        cv.put(DB.COLUMNS.COMMON.PERSON_REF, person_id);
        cv.put(DB.COLUMNS.COMMON.SITE_REF, site_id);
        cv.put(DB.COLUMNS.COMMON.STATS, stats);
        getDB().update(DB.TABLES.COMMON, cv,"_id="+_id,null);
    }
    public void addOrUpdateCommonStatsWithCheck(int id,String site,String person,int stats) {
 /*       int person_id = getPersonIDOrCreate(person);
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
        }*/
    }
    public Cursor getCursorOfCommonStatsWithSite(String site) {
        int site_id = DBHelper.getInstance().getSiteID(site);

        String table = DB.TABLES.PERSON+" as PS inner join "+DB.TABLES.COMMON+" as CS  on PS."+DB.COLUMNS.PERSON.ID+"=CS."+DB.COLUMNS.COMMON.PERSON_REF;
        String columns[] = {"PS."+DB.COLUMNS.PERSON.PERSON+" as "+DB.COLUMNS.PERSON.PERSON, "CS."+DB.COLUMNS.COMMON.STATS+" as "+DB.COLUMNS.COMMON.STATS, " PS._id as _id"};
        String selection = "CS."+DB.COLUMNS.COMMON.SITE_REF+"=?";
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
  /*      int person_id = getPersonIDOrCreate(person);
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
        }*/
    }
    public Cursor getCursorOfDailyStatsWithSite(String site,String person) {
        int site_id = DBHelper.getInstance().getSiteID(site);
        int person_id = DBHelper.getInstance().getPersonID(person);
        dumpTablePerson();
        dumpTableSite();
        dumpTableDailyStats();

        return DBHelper.getInstance().getDB().query(DB.TABLES.DAILY, null,
                DB.COLUMNS.DAILY.PERSON_REF+'='+person_id+" and "+DB.COLUMNS.DAILY.SITE_REF+'='+site_id,
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
                    int indexID = cursor.getColumnIndex(DB.COLUMNS.KEYWORD.ID);
                    int indexKeyword = cursor.getColumnIndex(DB.COLUMNS.KEYWORD.KEYWORD);
                    int indexRef = cursor.getColumnIndex(DB.COLUMNS.KEYWORD.PERSON_REF);
                    Log.d(TAG, "_id="+cursor.getInt(id)+":ID="+cursor.getInt(indexID) + ":word=" + cursor.getString(indexKeyword)+
                            ":person_ref=" + cursor.getInt(indexRef));
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
                    int indexID = cursor.getColumnIndex(DB.COLUMNS.COMMON.ID);
                    int indexStats = cursor.getColumnIndex(DB.COLUMNS.COMMON.STATS);
                    int indexPersonRef = cursor.getColumnIndex(DB.COLUMNS.COMMON.PERSON_REF);
                    int indexSiteRef = cursor.getColumnIndex(DB.COLUMNS.COMMON.SITE_REF);
                    Log.d(TAG, "_id="+cursor.getInt(id)+":ID="+cursor.getInt(indexID) + ":stat=" + cursor.getInt(indexStats)+
                            ":per_ref=" + cursor.getInt(indexPersonRef)+ ":site_ref=" + cursor.getInt(indexSiteRef));
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
                    int indexID = cursor.getColumnIndex(DB.COLUMNS.DAILY.ID);
                    int indexStats = cursor.getColumnIndex(DB.COLUMNS.DAILY.STATS);
                    int indexPersonRef = cursor.getColumnIndex(DB.COLUMNS.DAILY.PERSON_REF);
                    int indexSiteRef = cursor.getColumnIndex(DB.COLUMNS.DAILY.SITE_REF);
                    int indexDate = cursor.getColumnIndex(DB.COLUMNS.DAILY.DATE);
                    Log.d(TAG, "_id="+cursor.getInt(id)+":ID="+cursor.getInt(indexID) + ":date=" + cursor.getString(indexDate)+
                            ":stat=" + cursor.getInt(indexStats)+
                            ":per_ref=" + cursor.getInt(indexPersonRef)+ ":site_ref=" + cursor.getInt(indexSiteRef));

                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "empty");
            }
        } finally {
            if (cursor != null) cursor.close();
        }
    }
}
