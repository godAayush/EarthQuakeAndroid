package com.example.android.quakereport;

public class QuakeData
{
    private float mMagnitude;
    private String mPlace;
    private long mTime;
    private String mURL;
    public QuakeData(float mag,String place,long time,String url)
    {
        mMagnitude=mag;
        mPlace=place;
        mTime=time;
        mURL=url;
    }
    public float getMagnitude()
    {
        return mMagnitude;
    }
    public String getLocation()
    {
        return mPlace;
    }
    public long getDate()
    {
        return mTime;
    }
    public String getURL()
    {
        return mURL;
    }
}
