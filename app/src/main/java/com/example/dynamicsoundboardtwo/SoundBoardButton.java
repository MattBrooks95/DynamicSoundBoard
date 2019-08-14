package com.example.dynamicsoundboardtwo;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.dynamicsoundboardtwo.JavaHelpers.join_string_array;

public class SoundBoardButton {
    private static final String TAG = "SoundBoardButton";

    private SquareButton sound_button = null;

    private MediaPlayer sound = null;

    private Drawable button_background_drawable = null;

    private File image_file = null;
    private File audio_file = null;

    SoundBoardButton(File image, File audio){
        constructor_work(image,audio,"none");
    }

    SoundBoardButton(File image, File audio, String label){
        constructor_work(image,audio,label);
    }

    private void constructor_work(File image, File audio, String label){
        ArrayList<String> paths = new ArrayList<>(Arrays.asList(image.getPath(),audio.getPath(), label));
        Log.d(TAG,"Initializing sound board button:(" + join_string_array(paths,':') + ")");
        image_file = image;
        audio_file = audio;
        sound_button = new SquareButton(EnvironmentVariables.get_app_context());
        setup_background();
        setup_audio();
        setup_onclick();
    }

    public Drawable get_button_background_drawable(){
        return button_background_drawable;
    }

    private void setup_background(){
        button_background_drawable = get_fresh_drawable_of_image();
        sound_button.setBackground(button_background_drawable);
    }

    public Drawable get_fresh_drawable_of_image(){
        return Drawable.createFromPath(image_file.getPath());
    }

    public void setBackground(Drawable new_background){
        sound_button.setBackground(new_background);
    }


    private void setup_audio(){
        Log.d(TAG,"Setting up audio for sound board button");
//        Resources application_resources = application_environment.getResources();
//        int audio_resource_id = application_resources.getIdentifier(audio_name,"raw",application_environment.getPackageName());
//
//        //convenience method, will get better performance by doing prepareAsync and setDataSource
//        sound = MediaPlayer.create(application_environment, audio_resource_id);
    }

    private void setup_onclick(){
        sound_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View me){
                Log.d(TAG, "button clicked");

                //this solution needs to be done this way, else app will hang until
                //the audio is loaded
                //will want to asynchronously prep this, then set a bool when its ready
//                if(!sound_prepared){
//                    Log.d(TAG,"Media player isn't prepared yet!");
//                    return;
//                }

                // this keeps each sound that has been played once in memory forever, not ideal
//                if(sound.isPlaying()){
////                    sound.stop();
//                    sound.pause();
//                    sound.seekTo(0);
////                    sound.prepare();
//                } else {
//                    sound.start();
//                }
            }
        });
    }

    //create a button with the specified image, and an onclick/ontap listener that plays the audio
    public Button get_button(){
        return sound_button;
    }
}
