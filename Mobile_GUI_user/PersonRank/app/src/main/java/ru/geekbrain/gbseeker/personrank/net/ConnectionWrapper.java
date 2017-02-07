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
    protected String doInBackground(String... params) {
        String content="";
        Log.d(TAG,net2SQL.getInfo());
        try {
            for(int i=0;i<params.length;i++) {
                content = getContent(params[i]);
                net2SQL.updateDB(content, i);
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
}


