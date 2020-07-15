package com.example.android.miwok;

public class Word
{
    private String miwtrans, deftrans;
    private int imgid=NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int songid;
    public Word(String def, String miw , int songidd)
    {
        miwtrans=miw;
        deftrans=def;
        songid = songidd;
    }
    public Word(String def,String miw,int imgidd, int songidd)
    {
        miwtrans=miw;
        deftrans=def;
        imgid=imgidd;
        songid = songidd;
    }
    public String getMiwakTranslation()
    {
        return miwtrans;
    }
    public String getDefaultTranslation()
    {
        return deftrans;
    }
    public int getImageResourceId()
    {
        return imgid;
    }
    public boolean hasImage()
    {
        return imgid!=NO_IMAGE_PROVIDED;
    }
    public int getMusicResourceId()
    {
        return songid;
    }
}