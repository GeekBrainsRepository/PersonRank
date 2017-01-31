package ru.geekbrain.gbseeker.personrank.net;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ConnectionWrapper  extends AsyncTask<String, Void, String> {
    final String TAG="ConnectionWrapper";

    public ConnectionWrapper() {
    }

    @Override
    protected String doInBackground(String... params) {
        String content;
        try {
            content = getContent(params[0]);
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception ex) {
            content = ex.getMessage();
        }
        Log.d(TAG,content);

        return content;

    }


    private String getContent(String path) throws IOException {
        BufferedReader reader = null;
        try {
            URL url = new URL(path);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setReadTimeout(100);
            c.connect();
            reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            StringBuilder buf = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buf.append(line);
            }
            return (buf.toString());
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }
}

