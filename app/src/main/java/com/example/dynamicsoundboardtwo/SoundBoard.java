package com.example.dynamicsoundboardtwo;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.dynamicsoundboardtwo.Constants.*;
import static com.example.dynamicsoundboardtwo.JavaHelpers.build_map_of_directory;

//extends list adapter so that this class can be used to populate a grid view element
public class SoundBoard extends ArrayAdapter {
    private static final String TAG = "SoundBoard";
    private GridView my_grid_view   = null;

    private static final String BACKGROUND_IMAGE_FILE_JSON_KEY = "background_image";

    private static final String SOUND_BOARD_CONFIGURATION_JSON_KEY = "sound_board_configuration";

    private int sound_board_index = -1;

    private SoundBoardSelectorButton sound_board_select_button = null;

    private File background_image_file = null;

    SoundBoardConfiguration configuration;
    ArrayList<SoundBoardButton> sound_board_buttons;

    SoundBoard(HashMap<String,File> sound_board_folder, int sound_board_index_in, SoundBoardManager sound_board_manager_reference){
        super(EnvironmentVariables.get_app_context(), EnvironmentVariables.get_main_view_id());
        configuration       = new SoundBoardConfiguration();
        sound_board_buttons = new ArrayList<>();

        create_grid_view();

        Log.d(TAG,"Processing sound board folder!");
        process_configuration_folder(sound_board_folder);

        sound_board_index = sound_board_index_in;

        set_up_selector_button(sound_board_index, sound_board_manager_reference);
    }

    //this is bad, the image to use as the selector for this sound board
    //should be specified in the json config file
    public void set_up_selector_button(int corresponding_sound_board_index, SoundBoardManager sound_board_manager_reference){
        Drawable selector_background = sound_board_buttons.get(0).get_fresh_drawable_of_image();
        sound_board_select_button = new SoundBoardSelectorButton(EnvironmentVariables.get_app_context(),corresponding_sound_board_index,sound_board_manager_reference,selector_background);
    }

    public SoundBoardButton get_button(int button_index){
        return sound_board_buttons.get(button_index);
    }

    public SoundBoardSelectorButton get_selector_button(){
        return sound_board_select_button;
    }

    private void process_configuration_folder(HashMap<String,File> sound_board_folder){
        File configuration_file = get_configuration_file_from_directory(sound_board_folder);

        JSONObject configuration_json = null;

        File audio_directory  = get_audio_directory_from_directory(sound_board_folder);
        File images_directory = get_images_directory_from_directory(sound_board_folder);

        try{
            configuration_json = JavaHelpers.get_file_as_json(configuration_file);

            process_sound_board_configurations(configuration_json, build_map_of_directory(get_background_directory_from_directory(sound_board_folder)));

            JSONArray buttons_array = configuration_json.getJSONArray("buttons");
            create_sound_board_buttons(buttons_array,build_map_of_directory(audio_directory),build_map_of_directory(images_directory));
        } catch(JSONException json_error){
            Log.d(TAG,"Some sort of JSON error");
            return;
        }
    }

    private void process_sound_board_configurations(JSONObject configuration_json,HashMap<String,File> backgrounds_directory){
        try{
            JSONObject configurations = configuration_json.getJSONObject(SOUND_BOARD_CONFIGURATION_JSON_KEY);

            String background_image_name = configurations.getString(BACKGROUND_IMAGE_FILE_JSON_KEY);

            if(background_image_name == "random"){
                setup_random_sound_board_background(backgrounds_directory);
            } else if(background_image_name != null){
                setup_sound_board_background_image(background_image_name,backgrounds_directory);
            } else {
                Log.d(TAG,"No background image specified in the config file!");
            }

        } catch(JSONException json_error){
            Log.d(TAG,"Some sort of JSON error");
            return;
        }
    }

    private void setup_random_sound_board_background(HashMap<String,File> backgrounds_directory){

    }

    private void setup_sound_board_background_image(String background_image_name, HashMap<String,File> backgrounds_directory){
        background_image_file = backgrounds_directory.get(background_image_name);
        Drawable background_drawable = Drawable.createFromPath(background_image_file.getAbsolutePath());
        my_grid_view.setBackground(background_drawable);
    }

    private String get_not_found_message(String map_key){
        return new String("File: " + map_key + " not found in the sound board directory!");
    }

    private void create_sound_board_buttons(JSONArray buttons_array,HashMap<String,File> audio_directory,HashMap<String,File> images_directory){
        try{
            for(int button_index = 0; button_index < buttons_array.length(); ++button_index){
                JSONObject button_hash = buttons_array.getJSONObject(button_index);
                String image_name = button_hash.optString("pic");
                String audio_name = button_hash.optString("audio");
                String label      = button_hash.optString("label");

                File image_file = images_directory.get(image_name);
                File audio_file = audio_directory.get(audio_name);

                boolean file_is_missing = false;

                if(image_file == null){
                    Log.d(TAG,get_not_found_message(image_name));
                    file_is_missing = true;
                }

                if(audio_file == null){
                    Log.d(TAG,get_not_found_message(audio_name));
                    file_is_missing = true;
                }

                //don't bother trying to construct the soundboard button if these crucial files are missing
                if(file_is_missing){
                    continue;
                }

                sound_board_buttons.add(new SoundBoardButton(image_file, audio_file, label));
            }
        } catch(org.json.JSONException error){
            Log.d(TAG,"Json exception while creating sound board buttons!");
        }
    }

    public int size(){
        return sound_board_buttons.size();
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
        if(my_grid_view != null){
            return get_grid_view();
        }

        my_grid_view = new GridView(EnvironmentVariables.get_app_context());
        //center, fill container
        set_grid_view_parameters_from_config_object(my_grid_view,configuration);
        set_up_grid_view_layout_parameters();
        return my_grid_view;
    }

    private void set_up_grid_view_layout_parameters(){
        ViewGroup.LayoutParams gridview_layout_parameters = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        my_grid_view.setLayoutParams(gridview_layout_parameters);
        my_grid_view.setGravity(11|77);
        my_grid_view.setNumColumns(6);
        my_grid_view.setAdapter(this);
    }

    public void add_grid_view_to_element(ViewGroup add_target){
        if(my_grid_view == null){
            create_grid_view();
        }
        add_target.addView(my_grid_view);
    }

    public void add_sound_board_selector_button_to_element(ViewGroup add_target){
        add_target.addView(sound_board_select_button);
    }

    public GridView get_grid_view(){
        return my_grid_view;
    }

    public void set_invisible(){
        my_grid_view.setVisibility(View.GONE);
    }

    public void set_visible(){
        my_grid_view.setVisibility(View.VISIBLE);
    }

    public SoundBoardSelectorButton get_sound_board_selector_button(){
        return sound_board_select_button;
    }

    private void set_visibility(int visibility){
        my_grid_view.setVisibility(visibility);
    }

    private void set_grid_view_parameters_from_config_object(GridView grid, SoundBoardConfiguration parameters){
        grid.setNumColumns(parameters.get_number_of_columns());
        grid.setColumnWidth(parameters.get_column_width());
        grid.setVerticalSpacing(parameters.get_vertical_spacing());
        grid.setHorizontalSpacing(parameters.get_horizontal_spacing());
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

    private File get_background_directory_from_directory(HashMap<String,File> sound_board_folder){
        return sound_board_folder.get(BACKGROUND_IMAGE_DIR_NAME);
    }
}
