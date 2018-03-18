package com.example.claudio.voicerecognition;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;

import android.widget.Toast;

import java.io.File;
import java.nio.file.Paths;

import classes.VoiceRecognition;
import classes.WavRecorder;

public class RegistraVoce extends AppCompatActivity{

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_AUDIO_AND_STORAGE = 200;

    private String outputPath = null;
    private String outputFile = "prova.wav";
    int numRec = 0;

    private MediaPlayer mPlayer = null;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    private WavRecorder rec = null;

    ImageButton btn_rec;
    Chronometer crn_rec;
    Button btn_stop_rec;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_voce);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_AUDIO_AND_STORAGE);

        btn_rec = (ImageButton)findViewById(R.id.recbutton);
        crn_rec = (Chronometer)findViewById(R.id.chrrec);
        btn_stop_rec = (Button)findViewById(R.id.btn_stop_rec);

        btn_stop_rec.setEnabled(false);
        btn_stop_rec.setBackgroundResource(R.color.colorLogoLight);

        /*** salvataggio voce nella cartella dell'utente ***/

        String main_folder = "VoiceRecorder";
        String voiceFolder = ((VoiceRecognition) this.getApplication()).getFolder();

        File f = new File(Environment.getExternalStorageDirectory(), main_folder + "/" + voiceFolder);
        if (!f.exists()) {
            f.mkdirs();
        }

        numRec = f.list().length;

        outputPath = Environment.getExternalStorageDirectory() + "/" + main_folder + "/" + voiceFolder +"/";
        outputFile = "rec" + Integer.toString(numRec) + ".wav";

        /***************************************************/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_AUDIO_AND_STORAGE:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
    }

    public void onClickStartRec(View v){

        try{
            String user = ((VoiceRecognition) this.getApplication()).getFolder();
            if(!user.equals("")) {
                rec = new WavRecorder(outputPath + outputFile);
                rec.startRecording();
            }
            else {
                Toast.makeText(getApplicationContext(), "Inserisci username", Toast.LENGTH_LONG).show();
                super.onBackPressed();
            }
        }
        catch (IllegalStateException ise){}


        btn_stop_rec.setEnabled(true);
        btn_stop_rec.setBackgroundResource(R.color.colorLogo);
        crn_rec.setBase(SystemClock.elapsedRealtime());
        crn_rec.start();

    }

    public void onClickStopRec(View v){

        rec.stopRecording();
        crn_rec.stop();
        Toast.makeText(getApplicationContext(), outputPath+outputFile, Toast.LENGTH_LONG).show();

        /***** prova lista cartelle *****/
        File f = new File(Environment.getExternalStorageDirectory(), "VoiceRecorder");
        String[] children = f.list();
        if (children == null) {
            // Either dir does not exist or is not a directory
        } else {
            for (int i=0; i < children.length; i++) {
                String filename="";
                filename = filename+" "+children[i];
                Toast.makeText(getApplicationContext(), "Lista cartelle: "+filename, Toast.LENGTH_LONG).show();
            }
        }

        /********************************/
    }

    public void onClickPlayRec(View v){
        mPlayer = new MediaPlayer();

        try{
            mPlayer.setDataSource(outputPath+outputFile);
            mPlayer.prepare();
            mPlayer.start();
        }
        catch(Exception e){}

    }

}
