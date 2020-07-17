package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;



/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Sample JSON response for a USGS query */

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link QuakeData} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<QuakeData> extractEarthquakes(String jsonstring) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<QuakeData> earthquakes = new ArrayList<>();


        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject jo = new JSONObject(jsonstring);
            JSONArray ar = jo.getJSONArray("features");
            for (int i=0;i<ar.length();i++)
            {
                JSONObject ob = ar.getJSONObject(i);
                JSONObject prop = ob.getJSONObject("properties");
                float magg = (float)prop.getDouble("mag");
                String place = prop.getString("place");
                long tim = prop.getLong("time");
                String url = prop.getString("url");
                QuakeData quak = new QuakeData(magg,place,tim,url);
                earthquakes.add(quak);
            }
            return earthquakes;

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    private static URL formURL(String s)
    {
        URL u1 = null;
        if(s==null || s=="")
        {
            Log.i("QueryUtils","url passed is null or empty");
            return u1;
        }
        try {
            u1 = new URL(s);
            return  u1;
        }
        catch (MalformedURLException e) {
            Log.i("QueryUtils","malformed URL");
            return null;
        }
    }

    private static String readInputStream(InputStream i) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        if(i!=null)
        {
            InputStreamReader isr = new InputStreamReader(i, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while(line!=null)
            {
                sb.append(line);
                line = br.readLine();
            }
        }
        return sb.toString();
    }

    public static String makeHTTPConnection(String inpurl)
    {
        URL ur = formURL(inpurl);
        String jsRes = "";
        if(ur==null)
        {
            return jsRes;
        }
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            con = (HttpURLConnection) ur.openConnection();
            con.setRequestMethod("GET");
            con.setReadTimeout(15*1000);
            con.setConnectTimeout(10*1000);
            con.connect();
            if(con.getResponseCode()==200)
            {
                is = con.getInputStream();
                jsRes = readInputStream(is);
            }
            else
            {
                Log.e("QueryUtils","Error response code: "+con.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("QueryUtils" , "Problem retrieving the earthquake JSON results.", e);
        }
        finally {
            if(con!=null)
            {
                con.disconnect();
            }
            if(is!=null)
            {
                try {
                    is.close();
                }
                catch (IOException e)
                {
                    Log.e("QueryUtils","Problem in closing inputstream. ",e);
                }
            }
        }
        return jsRes;
    }

}