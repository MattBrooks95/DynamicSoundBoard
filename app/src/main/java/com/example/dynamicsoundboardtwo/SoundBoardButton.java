package com.example.dynamicsoundboardtwo;

import android.util.Log;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static com.example.dynamicsoundboardtwo.JavaHelpers.join_string_array;

public class SoundBoardButton {
    private static final String TAG = "SoundBoardButton";
    SoundBoardButton(File image, File audio){
        constructor_work(image,audio,"none");
    }

    SoundBoardButton(File image, File audio, String label){
        constructor_work(image,audio,label);
    }

    private void constructor_work(File image, File audio, String label){
        ArrayList<String> paths = new ArrayList<>(Arrays.asList(image.getPath(),audio.getPath(), label));
        Log.d(TAG,"Initializing sound board button:(" + join_string_array(paths,':') + ")");
    }

    //create a button with the specified image, and an onclick/ontap listener that plays the audio
    public Button get_button(){
        return null;
    }
}
