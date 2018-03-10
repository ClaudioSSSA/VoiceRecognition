package classes;

import android.app.Application;

/**
 * Created by Mauro on 10/03/2018.
 */

public class VoiceFolder extends Application {

    private String folder;

    public String getFolder(){
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
