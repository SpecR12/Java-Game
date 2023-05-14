package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];
    public Sound(){
        soundURL[0] = getClass().getResource("/sound/sad budi blues.wav");
        soundURL[1] = getClass().getResource("/sound/key_pickup.wav");
        soundURL[2] = getClass().getResource("/sound/sfx_step_grass_l.wav");
        soundURL[3] = getClass().getResource("/sound/sfx_step_grass_r.wav");
        soundURL[4] = getClass().getResource("/sound/Chest Creak.wav");
    }
    public void setFile(int i){
        try
        {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void play(){ //TODO Restore sound
//        clip.start();

    }
    public void loop(){
//        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop(){
        clip.stop();

    }
}
