package com.example.claudio.voicerecognition;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;

import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Date;

public class RegistraVoce extends AppCompatActivity{

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_AUDIO_AND_STORAGE = 200;
    /*private static final int WRITE_EXTERNAL_STORAGE = 201;
    private static final int READ_EXTERNAL_STORAGE = 202;*/
    private String outputPath = null;
    private String outputFile = null;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};



    ImageButton btn_rec;
    Chronometer crn_rec;
    Button btn_stop_rec;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra_voce);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_AUDIO_AND_STORAGE);
        /*ActivityCompat.requestPermissions(this, permissions, WRITE_EXTERNAL_STORAGE);
        ActivityCompat.requestPermissions(this, permissions, READ_EXTERNAL_STORAGE);*/


        btn_rec = (ImageButton)findViewById(R.id.recbutton);
        crn_rec = (Chronometer)findViewById(R.id.chrrec);
        btn_stop_rec = (Button)findViewById(R.id.btn_stop_rec);

        btn_stop_rec.setEnabled(false);
        btn_stop_rec.setBackgroundColor(Color.GRAY);

        String aboz = "stri";

        String main_folder = "VoiceRecorder";

        File f = new File(Environment.getExternalStorageDirectory(), main_folder);
        if (!f.exists()) {
            f.mkdirs();
        }

        outputPath = Environment.getExternalStorageDirectory() + "/" + main_folder + "/";


        //outputFile = getExternalCacheDir().getAbsolutePath() + "/rec1.3gp";
        //Date createdTime = new Date();
        //outputFile = getExternalCacheDir().getAbsolutePath() + "/" + createdTime + "_rec.3gp";

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_AUDIO_AND_STORAGE:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
            /*case WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Write to the storage (ex: call appendByteBuffer(byte[] data) here)

                } else {
                    Toast.makeText(getApplicationContext(), "Please grant permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }*/
        }
    }

    public void onClickStartRec(View v){

        try{
            File f = new File(outputPath);
            File[] files = f.listFiles();
            int count_files = files.length;

            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            String str = "";
            outputFile = Integer.toString(count_files) + "_rec.3gp";
            mRecorder.setOutputFile(outputPath+outputFile);

            mRecorder.prepare();
            mRecorder.start();
        }
        catch (IllegalStateException ise){}
        catch (IOException ioe){}

        btn_stop_rec.setEnabled(true);
        btn_stop_rec.setBackgroundColor(Color.RED);
        crn_rec.setBase(SystemClock.elapsedRealtime());
        crn_rec.start();

    }

    public void onClickStopRec(View v){
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        crn_rec.stop();
        Toast.makeText(getApplicationContext(), outputPath+outputFile, Toast.LENGTH_LONG).show();
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
