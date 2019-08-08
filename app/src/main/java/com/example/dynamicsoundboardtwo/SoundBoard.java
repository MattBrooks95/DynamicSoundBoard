package com.example.dynamicsoundboardtwo;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.dynamicsoundboardtwo.Constants.*;
import static com.example.dynamicsoundboardtwo.JavaHelpers.get_string_from_file;
import static com.example.dynamicsoundboardtwo.JavaHelpers.build_map_of_directory;

//extends list adapter so that this class can be used to populate a grid view element
public class SoundBoard extends ArrayAdapter {
    private static final String TAG = "SoundBoard";
    SoundBoardConfiguration configuration;
    ArrayList<SoundBoardButton> sound_board_buttons;
    SoundBoard(HashMap<String,File> sound_board_folder){
        super(EnvironmentVariables.get_app_context(), EnvironmentVariables.get_main_view_id());
        configuration = new SoundBoardConfiguration();
        sound_board_buttons = new ArrayList<>();
        Log.d(TAG,"Processing sound board folder!");
        File configuration_file = get_configuration_file_from_directory(sound_board_folder);
        HashMap<String,File> audio_directory    = build_map_of_directory(get_audio_directory_from_directory(sound_board_folder));
        HashMap<String,File> images_directory   = build_map_of_directory(get_images_directory_from_directory(sound_board_folder));
        process_configuration_file(configuration_file, audio_directory, images_directory);
    }

    private void process_configuration_file(File configuration_file,HashMap<String,File> audio_directory,HashMap<String,File> images_directory){
        JSONObject configuration_json = null;

        try{
            String file_contents = get_string_from_file(configuration_file);
            Log.d(TAG,"File contents:" + file_contents);
            configuration_json = new JSONObject(file_contents);

            JSONArray buttons_array = configuration_json.getJSONArray("buttons");

            for(int button_index = 0; button_index < buttons_array.length(); ++button_index){
                JSONObject button_hash = buttons_array.getJSONObject(button_index);
                String image_name = button_hash.optString("pic");
                String audio_name = button_hash.optString("audio");
                String label      = button_hash.optString("label");

                File image_file = images_directory.get(image_name);
                File audio_file = audio_directory.get(audio_name);
                sound_board_buttons.add(new SoundBoardButton(image_file, audio_file, label));
            }

        } catch(JSONException json_error){
            Log.d(TAG,"Some sort of JSON error, file contents were:");
            return;
        } catch(java.io.FileNotFoundException file_not_found){
            Log.d(TAG,"File:" + configuration_file.getPath() + " was not found!");
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        return sound_board_buttons.get(position).get_button();
    }

    @Override
    public int getCount(){
        return sound_board_buttons.size();
    }

    public GridView create_grid_view(){
        GridView return_me = new GridView(EnvironmentVariables.get_app_context());
        return_me.setNumColumns(configuration.get_number_of_columns());
        return_me.setColumnWidth(configuration.get_column_width());
        return_me.setVerticalSpacing(configuration.get_vertical_spacing());
        return_me.setHorizontalSpacing(configuration.get_horizontal_spacing());
        //center, fill container
        return_me.setGravity(11|77);
        return return_me;
    }

    private File get_configuration_file_from_directory(HashMap<String,File> sound_board_folder){
        return sound_board_folder.get(MAP_FILE_NAME);
    }

    private File get_audio_directory_from_directory(HashMap<String,File> sound_board_folder){
        return sound_board_folder.get(AUDIO_DIR_NAME);
    }

    private File get_images_directory_from_directory(HashMap<String,File> sound_board_folder){
        return sound_board_folder.get(IMAGE_DIR_NAME);
    }
}
