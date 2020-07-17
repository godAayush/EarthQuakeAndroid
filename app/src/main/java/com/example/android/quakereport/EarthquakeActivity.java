/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<QuakeData>> {
public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<QuakeData>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private AdapterClass adapter;
    private final String MY_KEY = "urlstr";
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private TextView mEmptyStateTextView;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new AdapterClass(this, new ArrayList<QuakeData>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        loading = (ProgressBar)findViewById(R.id.loading_spinner);



        if(!isConnected)
        {
            loading.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet);
            return;
        }



        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                QuakeData clicked = adapter.getItem(i);
                Uri webpage = Uri.parse(clicked.getURL());
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        String urlstr = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";

        Bundle bun = new Bundle();
        bun.putString(MY_KEY,urlstr);
        //Log.i("aayush","yaha aaya");
        LoaderManager.getInstance(this).initLoader(EARTHQUAKE_LOADER_ID, bun, this);
        //Log.i("aayush","initloader ke baad bhi aaya");

        //EarthquakeAsyncClass task = new EarthquakeAsyncClass();
        //task.execute(urlstr);

        // Create a fake list of earthquake locations.
        //ArrayList<QuakeData> earthquakes = QueryUtils.extractEarthquakes();

        // Find a reference to the {@link ListView} in the layout

    }

    @NonNull
    @Override
    public Loader<List<QuakeData>> onCreateLoader(int id, @Nullable Bundle args) {
        //Log.i("aayush","1st time");
        String urlstr = args.getString(MY_KEY);
        return new EarthquakeLoader(this,urlstr);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<QuakeData>> loader, List<QuakeData> data) {
        //Log.i("aayush","dude");
        adapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            //Log.i("aayush","change ui");
            adapter.addAll(data);
        }
        loading.setVisibility(View.GONE);
        mEmptyStateTextView.setText(R.string.no_earthquakes);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<QuakeData>> loader) {
        adapter.clear();
        //Log.i("aayush","onloaderreset clear kar diya");
    }


    /**
    private class EarthquakeAsyncClass extends AsyncTask<String,Void, List<QuakeData>>
    {
        @Override
        protected List<QuakeData> doInBackground(String... strings) {
            if(strings==null||strings.length==0||strings[0]==null)
                return null;
            String inputJSON = QueryUtils.makeHTTPConnection(strings[0]);
            return QueryUtils.extractEarthquakes(inputJSON);
        }

        @Override
        protected void onPostExecute(List<QuakeData> quakeData) {
            // Clear the adapter of previous earthquake data
            adapter.clear();

            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (quakeData != null && !quakeData.isEmpty()) {
                adapter.addAll(quakeData);
            }
        }
    }
     */

}
