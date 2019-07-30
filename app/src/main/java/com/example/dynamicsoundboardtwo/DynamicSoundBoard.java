package com.example.dynamicsoundboardtwo;

import android.content.Context;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class DynamicSoundBoard extends AppCompatActivity {

    private static final String TAG = "DynamicSoundBoard";

    private static final String AUDIO_DIR_NAME = "audio";
    private static final String IMAGE_DIR_NAME = "pics";
    private static final String MAP_FILE_NAME  = "image_to_sound_mapping.json";

    protected Context application_context = null;

//    protected Configuration configuration           = null;
    protected DisplayManager display_manager        = null;
    protected SoundBoardManager sound_board_manager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        application_context = getApplicationContext();
        setup();
    }

    private void test_print(){
        TextView greeting = findViewById(R.id.greeting);
        greeting.append("And where does the newborn go from here? The net is vast and infinite.");
    }

    private void setup(){
        test_print();
        ArrayList<HashMap<String,File>> sound_board_folders = setup_sound_board_folders(application_context.getExternalFilesDirs(null));
        setup_sound_boards(sound_board_folders);
        setup_display();
    }

    //returns a list of maps from file name to file objects that can be used to instantiate sound boards
    private ArrayList<HashMap<String,File>> setup_sound_board_folders(File[] external_storage_files) {
        ArrayList<HashMap<String,File>> sound_board_folders = new ArrayList<>();

        for (File external_storage_file : external_storage_files) {
            process_external_storage_location(sound_board_folders, external_storage_file);
        }

        return sound_board_folders;
    }

    private void process_external_storage_location(ArrayList<HashMap<String,File>> sound_board_folders, File external_storage_file){
        Log.d(TAG,external_storage_file.getPath());
        for(File sound_board_folder : external_storage_file.listFiles()){
            String this_files_path = sound_board_folder.getPath();
            Log.d(TAG,this_files_path);

            if(!sound_board_folder.isDirectory()){
                Log.d(TAG,"Skipping folder" + this_files_path + " not a directory!");
                continue;
            }

            HashMap<String,File> this_directory_map = build_map_of_directory(sound_board_folder);
            if(!is_valid_sound_board_folder(this_directory_map)){
                continue;
            }

            sound_board_folders.add(this_directory_map);
        }
    }

    //A sound board file is valid if it has a a configuration file that maps images to sounds
    //a folder of images called 'pics'
    //a folder of audio files called 'audio'
    //returns an empty map if not given a file object that is a directory
    private boolean is_valid_sound_board_folder(HashMap<String,File> sound_board_directory){
        boolean media_folders_exist = (sound_board_directory.get(AUDIO_DIR_NAME) != null && sound_board_directory.get(IMAGE_DIR_NAME) != null);
        boolean map_file_exists     = (sound_board_directory.get(MAP_FILE_NAME) != null);
        boolean success = (!media_folders_exist || !map_file_exists);
        Log.d(TAG,"folder is valid?:" + Boolean.toString(success));
        return success;
    }

    //move outside of this class, should be a general helper
    private HashMap<String,File> build_map_of_directory(File directory){
        HashMap<String,File> map = new HashMap<>();

        if(!directory.isDirectory()){
            Log.d(TAG,"build_map_of_directory not given a directory!");
            return map;
        }

        File[] file_list = directory.listFiles();

        for(File file_object : file_list){
            map.put(file_object.getName(), file_object);
        }

        return map;
    }

    private void setup_sound_boards(ArrayList<HashMap<String,File>> sound_board_folders){
        sound_board_manager = new SoundBoardManager(sound_board_folders);
    }

//    private void setup_configuration(){
//        Log.d(TAG,"setup_configuration()");
//        configuration = new Configuration(application_context.getExternalFilesDirs(null));
//    }

    private void setup_display(){
        Log.d(TAG,"setup_display()");
    }
}