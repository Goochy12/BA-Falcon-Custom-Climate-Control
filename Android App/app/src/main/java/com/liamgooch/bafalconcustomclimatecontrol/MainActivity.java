package com.liamgooch.bafalconcustomclimatecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    //TODO: refactor to have main serial declarations and management in the main activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create a new Root Main Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RootMain()).commit();
    }


}