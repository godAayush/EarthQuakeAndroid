package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word>
{
    private int colorid;
    public WordAdapter(Activity con, ArrayList<Word> lis,int collor)
    {

        super(con,0,lis);
        colorid=collor;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView =  LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        final Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.deftext);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(currentWord.getDefaultTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView numberTextView = (TextView) listItemView.findViewById(R.id.miwaktext);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        numberTextView.setText(currentWord.getMiwakTranslation());

        LinearLayout texx = (LinearLayout) listItemView.findViewById(R.id.entiretext);
        texx.setBackgroundResource(colorid);

        ImageView imm = (ImageView) listItemView.findViewById(R.id.playbutton);
        imm.setBackgroundResource(colorid);

        ImageView im = (ImageView) listItemView.findViewById(R.id.imgview);
        if (currentWord.hasImage())
        {
            im.setImageResource(currentWord.getImageResourceId());
            im.setVisibility(View.VISIBLE);
        }
        else
            im.setVisibility(View.GONE);


        return listItemView;
    }
}
