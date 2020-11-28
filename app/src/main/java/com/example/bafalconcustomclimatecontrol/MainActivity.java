package com.example.bafalconcustomclimatecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ImageButton button_frontDefrost, button_rearDefrost, button_recycle, button_fanUp,
            button_fanDown, button_tempUp, button_tempDown, button_domeLight, button_doorLock;

    private Button button_ac, button_acMax;

    //boolean variables for button
    private Boolean button_frontDefrost_isSelected, button_rearDefrost_isSelected, button_recycle_isSelected,
    button_domeLight_isSelected, button_doorLock_isSelected, button_ac_isSelected, button_acMax_isSelected;

    private ProgressBar fanProgressBar, tempProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declare buttons
        button_frontDefrost = this.findViewById(R.id.button_frontDefrost);
        button_rearDefrost = findViewById(R.id.button_rearDefrost);
        button_recycle = findViewById(R.id.button_recycle);
        button_fanUp = findViewById(R.id.button_fanUp);
        button_fanDown = findViewById(R.id.button_fanDown);
        button_tempUp = findViewById(R.id.button_tempUp);
        button_tempDown = findViewById(R.id.button_tempDown);
        button_domeLight = findViewById(R.id.button_domeLight);
        button_doorLock = findViewById(R.id.button_doorLock);

        button_ac = findViewById(R.id.button_ac);
        button_acMax = findViewById(R.id.button_acMax);

        //declare progress bars
        fanProgressBar = (ProgressBar) findViewById(R.id.fanProgressBar);
        tempProgressBar = (ProgressBar) findViewById(R.id.tempProgressBar);

        //set boolean variables
        button_frontDefrost_isSelected = false;
        button_rearDefrost_isSelected = false;
        button_recycle_isSelected = false;
        button_domeLight_isSelected = false;
        button_doorLock_isSelected = false;
        button_ac_isSelected = false;
        button_acMax_isSelected = false;

        //declare button listeners
        button_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!button_ac_isSelected){
                Log.i("debug_liam", "Working");
                    button_ac.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
                }else{
                    Log.i("debug_liam", "Working");
                    button_ac.setBackgroundColor(getResources().getColor(R.color.black));
                }
                    button_ac_isSelected = !button_ac_isSelected;
            }
        });

        button_acMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button_frontDefrost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button_rearDefrost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button_recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button_fanUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fanProgressBar.incrementProgressBy(10);
            }
        });

        button_fanDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fanProgressBar.incrementProgressBy(-10);
            }
        });

        button_tempUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempProgressBar.incrementProgressBy(10);
            }
        });

        button_tempDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempProgressBar.incrementProgressBy(-10);
            }
        });

        button_domeLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button_doorLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}