package com.example.dynamicsoundboardtwo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.dynamicsoundboardtwo.JavaHelpers.join_string_array;

public class SoundBoardButton {
    private static final String TAG = "SoundBoardButton";

    private Button sound_button = null;

    private MediaPlayer sound = null;

    SoundBoardButton(File image, File audio){
        constructor_work(image,audio,"none");
    }

    SoundBoardButton(File image, File audio, String label){
        constructor_work(image,audio,label);
    }

    private void constructor_work(File image, File audio, String label){
        ArrayList<String> paths = new ArrayList<>(Arrays.asList(image.getPath(),audio.getPath(), label));
        Log.d(TAG,"Initializing sound board button:(" + join_string_array(paths,':') + ")");
        sound_button = new Button(EnvironmentVariables.get_app_context());
        setup_background(image);
        setup_audio();
        setup_onclick();
    }

    private void setup_background(File image){
        sound_button.setBackground(Drawable.createFromPath(image.getPath()));
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
