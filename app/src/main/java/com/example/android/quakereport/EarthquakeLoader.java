package com.example.android.quakereport;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<QuakeData>>
{
    private String url;
    public EarthquakeLoader(Context c,String urll)
    {
        super(c);
        //Log.i("aayush","const: "+urll);
        url=urll;
    }

    @Override
    protected void onStartLoading() {
        //Log.i("aayush","onstartloading forceload kiya");
        forceLoad();
    }

    @Nullable
    @Override
    public List<QuakeData> loadInBackground() {
        //Log.i("aayush","background chala");
        if(url==null||url=="")
            return null;
        //Log.i("aayush","background chala2");
        String inputJSON = QueryUtils.makeHTTPConnection(url);
        //Log.i("aayush","background chala3");
        return QueryUtils.extractEarthquakes(inputJSON);
    }
}
