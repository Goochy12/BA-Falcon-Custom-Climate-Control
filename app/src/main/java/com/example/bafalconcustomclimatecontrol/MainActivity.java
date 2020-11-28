package com.example.bafalconcustomclimatecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton button_frontDefrost, button_rearDefrost, button_recycle, button_fanUp,
            button_fanDown, button_tempUp, button_tempDown, button_domeLight, button_doorLock;

    private Button button_ac, button_acMax;

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

        //declare button listeners
        button_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

            }
        });

        button_fanDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button_tempUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button_tempDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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