package com.example.android.quakereport;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class AdapterClass extends ArrayAdapter<QuakeData> {
    SimpleDateFormat dateFormat;
    SimpleDateFormat timeFormat;
    public AdapterClass(Activity con, List<QuakeData> lis)
    {
        super(con,0,lis);
        dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        timeFormat = new SimpleDateFormat("h:mm a");
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        return timeFormat.format(dateObject);
    }

    private int getMagnitudeColor(float magval)
    {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magval);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View listItemView = convertView;
        if(listItemView==null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.quake_item,parent,false);
        }
        QuakeData quak = getItem(position);

        TextView mag = (TextView)listItemView.findViewById(R.id.magnitude);
        mag.setText(String.format("%.1f",quak.getMagnitude()));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(quak.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        TextView near = (TextView)listItemView.findViewById(R.id.location_offset);

        TextView place = (TextView)listItemView.findViewById(R.id.primary_location);

        String s = quak.getLocation();

        if(s.contains(" of "))
        {
            String[] s1 = s.split(" of ");
            near.setText(s1[0]+" of");
            place.setText(s1[1]);
        }
        else
        {
            near.setText("Near the");
            place.setText(s);
        }

        TextView dateView = (TextView)listItemView.findViewById(R.id.date);
        Date dateObject = new Date(quak.getDate());
        // Format the date string (i.e. "Mar 3, 1984")
        String dateToDisplay = formatDate(dateObject);
        dateView.setText(dateToDisplay);

        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
        String timeToDisplay = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(timeToDisplay);

        return listItemView;
    }
}

