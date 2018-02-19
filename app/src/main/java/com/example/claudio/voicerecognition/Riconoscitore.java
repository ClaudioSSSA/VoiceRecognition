package com.example.claudio.voicerecognition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import weka.core.Instances;
import java.io.BufferedReader;
import java.io.FileReader;


public class Riconoscitore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riconoscitore);
    }
}
