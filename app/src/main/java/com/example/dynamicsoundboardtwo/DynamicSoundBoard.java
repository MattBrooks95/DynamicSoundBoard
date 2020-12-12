package com.example.dynamicsoundboardtwo;

import android.os.Bundle;
import android.util.Log;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.dynamicsoundboardtwo.Constants.*;
import static com.example.dynamicsoundboardtwo.JavaHelpers.build_map_of_directory;

public class DynamicSoundBoard extends AppCompatActivity {

    private static final String TAG = "DynamicSoundBoard";

    protected DisplayManager display_manager        = null;
    protected SoundBoardManager sound_board_manager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        set_environment_variables();
        setup();
    }

    private void set_environment_variables(){
        EnvironmentVariables.set_app_context(getApplicationContext());
        int main_view_id = get_main_view_id();
        EnvironmentVariables.set_main_view_id(main_view_id);
        EnvironmentVariables.set_main_view(get_main_view());
    }

    private int get_main_view_id(){
        return R.id.main_context;
    }

    private LinearLayout get_main_view(){
        return findViewById(get_main_view_id());
    }

    private void setup(){
        HashMap<String,HashMap<String,File>> sound_board_folders = setup_sound_board_folders(EnvironmentVariables.get_app_context().getExternalFilesDirs(null));
        setup_sound_boards(sound_board_folders);
        setup_display();
    }

    //returns a list of maps from file name to file objects that can be used to instantiate sound boards
    private HashMap<String,HashMap<String,File>> setup_sound_board_folders(File[] external_storage_files) {
        HashMap<String,HashMap<String,File>> sound_board_folders = new HashMap<>();

        for (File external_storage_file : external_storage_files) {
            process_external_storage_location(sound_board_folders, external_storage_file);
        }

        return sound_board_folders;
    }

    private void process_external_storage_location(HashMap<String,HashMap<String,File>> sound_board_folders, File external_storage_file){
        Log.d(TAG,external_storage_file.getPath());
        for(File application_folder : external_storage_file.listFiles()){
            String this_files_path = application_folder.getPath();
            Log.d(TAG,this_files_path);

            if(!application_folder.isDirectory()){
                Log.d(TAG,"Skipping folder" + this_files_path + " not a directory!");
                continue;
            }

            for(File sound_board_folder : application_folder.listFiles()){
                HashMap<String,File> this_directory_map = build_map_of_directory(sound_board_folder);
                if(!is_valid_sound_board_folder(this_directory_map)){
                    continue;
                }

                sound_board_folders.put(sound_board_folder.getPath(),this_directory_map);
            }
        }
    }

    //A sound board file is valid if it has a a configuration file that maps images to sounds
    //a folder of images called 'pics'
    //a folder of audio files called 'audio'
    //returns an empty map if not given a file object that is a directory
    private boolean is_valid_sound_board_folder(HashMap<String,File> sound_board_directory){
        boolean media_folders_exist = (sound_board_directory.get(AUDIO_DIR_NAME) != null && sound_board_directory.get(IMAGE_DIR_NAME) != null);
        boolean map_file_exists     = (sound_board_directory.get(MAP_FILE_NAME) != null);
        boolean success = (media_folders_exist && map_file_exists);
        Log.d(TAG,"folder is valid?:" + Boolean.toString(success));
        return success;
    }

    private void setup_sound_boards(HashMap<String,HashMap<String,File>> sound_board_folders){
        sound_board_manager = new SoundBoardManager(sound_board_folders);
    }

//    private void setup_configuration(){
//        Log.d(TAG,"setup_configuration()");
//        configuration = new Configuration(application_context.getExternalFilesDirs(null));
//    }

    private void setup_display(){
        display_manager = new DisplayManager();
        Log.d(TAG,"setup_display()");
        ArrayList<SoundBoard> sound_boards = sound_board_manager.get_sound_boards();

        //I want this passed by reference, in c I could do this in a better way...
        boolean is_first_sound_board = true;

        for(SoundBoard this_sound_board : sound_boards){
            Log.d(TAG,"Processing sound board! Number of buttons:" + this_sound_board.size());
            setup_grid_view_element(this_sound_board);
            if(!is_first_sound_board){
                this_sound_board.set_invisible();
            } else {
                is_first_sound_board = false;
            }
            this_sound_board.add_sound_board_selector_button_to_element(display_manager.get_sound_board_select_list_container());
        }
    }

    private void setup_grid_view_element(SoundBoard this_sound_board){
        this_sound_board.create_grid_view();
        ViewGroup sound_boards_container = display_manager.get_sound_boards_container();
        this_sound_board.add_grid_view_to_element(sound_boards_container);
    }
}
