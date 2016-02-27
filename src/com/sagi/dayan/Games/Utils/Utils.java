package com.sagi.dayan.Games.Utils;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by sagi on 2/24/16.
 */
public  class Utils {

    public static InputStream getSoundResourceAsStream(String soundFileName){
        return Utils.class.getResourceAsStream("/com/sagi/dayan/Games/Sounds/" + soundFileName);
    }

    public static URL getSoundResourceAsURL(String soundFileName){
        return Utils.class.getResource("/com/sagi/dayan/Games/Sounds/" + soundFileName);
    }

    public static InputStream getImageResourceAsStream(String soundFileName){
        return Utils.class.getResourceAsStream("/com/sagi/dayan/Games/Images/" + soundFileName);
    }

    public static URL getImageResourceAsURL(String soundFileName){
        return Utils.class.getResource("/com/sagi/dayan/Games/Images/" + soundFileName);
    }

    public static void playSound(String soundFileName){
        try {
            AudioPlayer.player.start(new AudioStream(Utils.getSoundResourceAsStream(soundFileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFontPath(String fontFileName) {
        return Utils.class.getResource("/com/sagi/dayan/Games/Fonts/" + fontFileName).toString();
    }

    public static InputStream getFontStream(String fontFileName) {
        return Utils.class.getResourceAsStream("/com/sagi/dayan/Games/Fonts/" + fontFileName);
    }
}
