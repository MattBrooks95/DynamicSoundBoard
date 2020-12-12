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

    private boolean currently_playing = false;

    private File image_file = null;
    private File audio_file = null;

    SoundBoardButton(File image, File audio){
        constructor_work(image,audio,"none");
    }

    SoundBoardButton(File image, File audio, String label){
        constructor_work(image,audio,label);
    }

    private void constructor_work(File image, File audio, String label){
        if(image == null || audio == null){
            Log.d(TAG,"Attempting to construct a sound board button with a null audio or video file!");
            return;
        }
        ArrayList<String> paths = new ArrayList<>(Arrays.asList(image.getPath(),audio.getPath(), label));
        Log.d(TAG,"Initializing sound board button:(" + join_string_array(paths,':') + ")");
        image_file = image;
        audio_file = audio;
        currently_playing = false;

        sound_button = new SquareButton(EnvironmentVariables.get_app_context());

        initialize_media_player();

        setup_on_prepared_listener();
        setup_background();
        setup_onclick();
    }

    public Drawable get_button_background_drawable(){
        return button_background_drawable;
    }

    private void initialize_media_player(){
        sound = new MediaPlayer();
        set_data_source();
    }

    private void setup_on_prepared_listener(){
        sound.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            public void onPrepared(MediaPlayer media_player_reference){
                go_to_playing_state();
                media_player_reference.start();
            }
        });
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

    private void go_to_playing_state(){
        currently_playing = true;
    }

    private void go_to_not_playing_state(){
        currently_playing = false;
    }

    private boolean setup_audio(){
        Log.d(TAG,"Setting up audio for sound board button");

        boolean file_exists = audio_file.exists();
        boolean is_file     = audio_file.isFile();

        return file_exists && is_file;
    }

    private void release(){
        sound.stop();
    }

    private void setup_onclick(){
        Log.d(TAG,"setting up the onclick method of the sound board button!");
        sound_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View me){
                Log.d(TAG, "button clicked");

                if(currently_playing){
                    sound.stop();
                    go_to_not_playing_state();
                    return;
                }

                if(!setup_audio()){
                    Log.d(TAG, "Audio couldn't be prepared!");
                    return;
                }

                set_up_on_completion_listener();
                sound.prepareAsync();
            }
        });
    }

    private void set_up_on_completion_listener(){
        sound.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp){
                release();
                go_to_not_playing_state();
            }
        });

    }

    private boolean set_data_source(){
        try{
            sound.setDataSource(audio_file.getAbsolutePath());
        } catch(java.io.IOException file_exception){
            Log.d(TAG, "Set data source exception!");
            return false;
        }

        return true;
    }

    public SquareButton get_button(){
        return sound_button;
    }
}
