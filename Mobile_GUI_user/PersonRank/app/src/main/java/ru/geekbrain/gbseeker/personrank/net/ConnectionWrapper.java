package ru.geekbrain.gbseeker.personrank.net;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class ConnectionWrapper  extends AsyncTask<String, Void, String> {
    final String TAG="ConnectionWrapper";
    iNet2SQL net2SQL;

    public ConnectionWrapper(iNet2SQL net2SQL) {
        this.net2SQL=net2SQL;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        net2SQL.init();
    }

    @Override
    protected String doInBackground(String... params) {
        String content="";
        Log.d(TAG,net2SQL.getInfo()+": param length="+params.length);
        try {
            for(int i=0;i<params.length;i++) {
                Log.d(TAG,net2SQL.getInfo()+": param i="+i+" "+params[i]);
                //content = getContent(params[i]);
                content = getFakeContent(params[i]);
                Log.d(TAG,net2SQL.getInfo()+": content i="+i+" "+content);
                net2SQL.updateDB(content, params[i]);
            }
        } catch (Exception ex) {
            content = ex.getMessage();
        }
        if(content==null) {
            Log.d(TAG,"NULLLLLLLLLL");
        }
        else
            Log.d(TAG,content);

        return content;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        net2SQL.updateUI();
    }

    private String getContent(String path) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(path);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(100);
            c.connect();
            int status = c.getResponseCode();
            if(status!=200){

            }
            reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buf.append(line);
            }
            return (buf.toString());
        }catch(Exception e){
            Log.d(TAG,e.getMessage());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return null;
    }

    private String getFakeContent(String path) throws IOException {
        if(path.contains("site")) {
            String s = "{\"1\":\"www.1.test\",\"2\":\"www.2.test\",\"3\":\"www.3.test\",\"4\":\"www.4.test\",\"7\":\"TEST_SITE\",\"8\":\"site1\"}";
            return s;
        }
        else if(path.contains("person")){
            String s="{\"1\":\"Путин\",\"2\":\"Медведев\",\"5\":\"TEST_PERSON\"}";
            return s;
        }
        else if(path.contains("keyword/1")){
            String s="{\"1\":\"Путин\",\"2\":\"Путиным \",\"3\":\"Путина \"}";
            return s;
        }
        else if(path.contains("common/1")) {
            String s = "{\"date\":1486515600000,\"result\":{\"Путин\":16,\"Медведев\":20}}";
            return s;
        }
        else if(path.contains("daily")) {
            String s ="{\"result\":[0,10,50,3,1,0,0,4]}";
            return s;
        }
        return "";
    }
}


