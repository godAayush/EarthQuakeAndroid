package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView num = (TextView)findViewById(R.id.numbers);
        num.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent numIntent = new Intent(MainActivity.this,NumbersActivity.class);
                startActivity(numIntent);
            }
        });
        TextView fam = (TextView)findViewById(R.id.family);
        fam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent numIntent = new Intent(MainActivity.this,FamilyActivity.class);
                startActivity(numIntent);
            }
        });
        TextView col = (TextView)findViewById(R.id.colors);
        col.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent numIntent = new Intent(MainActivity.this,ColorsActivity.class);
                startActivity(numIntent);
            }
        });
        TextView phra = (TextView)findViewById(R.id.phrases);
        phra.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent numIntent = new Intent(MainActivity.this,PhrasesActivity.class);
                startActivity(numIntent);
            }
        });
    }

    public void openNumbersList(View view)
    {
        Intent ind = new Intent(this,NumbersActivity.class);
        startActivity(ind);
    }
}