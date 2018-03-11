package classes;

import android.app.Application;

/**
 * Created by Mauro on 10/03/2018.
 */

/*** Classe per le variabili globali ***/

public class VoiceRecognition extends Application {

    private String folder;

    public String getFolder(){

        return folder;
    }

    public void setFolder(String folder) {

        this.folder = folder;
    }
}
