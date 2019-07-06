package com.tokopedia.testasync;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hendry on 2019-06-28.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new FireTopAdsActionAsyncTask().execute("https://google.com");

    }

    public static class FireTopAdsActionAsyncTask extends AsyncTask<String, Void, Void> {

        @NonNull
        @Override
        protected Void doInBackground(@NonNull String... strings) {
            URL url;
            HttpURLConnection urlConnection;

            try {
                String stringBuilder = strings[0] +
                        "?device=android&os_type=1&appversion=1";
                url = new URL(stringBuilder);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    readStream(urlConnection.getInputStream());
                }

            } catch (Throwable e) {
                e.printStackTrace();
            }
            return null;
        }

        private void readStream(InputStream in) {
            BufferedReader reader = null;
            StringBuilder response = new StringBuilder();
            try {
                reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                Log.i("TESTHENDRY", response.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
