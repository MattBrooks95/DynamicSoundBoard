package com.example.dynamicsoundboardtwo;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

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
    private GridView my_grid_view   = null;

    private int my_view_id = -1;
    private int sound_board_selector_button_id = -1;

    private Button sound_board_select_button = null;

    SoundBoardConfiguration configuration;
    ArrayList<SoundBoardButton> sound_board_buttons;

    SoundBoard(HashMap<String,File> sound_board_folder){
        super(EnvironmentVariables.get_app_context(), EnvironmentVariables.get_main_view_id());
        configuration       = new SoundBoardConfiguration();
        sound_board_buttons = new ArrayList<>();
        Log.d(TAG,"Processing sound board folder!");
        File configuration_file = get_configuration_file_from_directory(sound_board_folder);
        HashMap<String,File> audio_directory    = build_map_of_directory(get_audio_directory_from_directory(sound_board_folder));
        HashMap<String,File> images_directory   = build_map_of_directory(get_images_directory_from_directory(sound_board_folder));
        process_configuration_file(configuration_file, audio_directory, images_directory);
        set_up_selector_button();
    }

    //this is bad, the image to use as the selector for this sound board
    //should be specified in the json config file
    public void set_up_selector_button(){
        sound_board_select_button = new Button(EnvironmentVariables.get_app_context());
        sound_board_select_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View me){
                Log.d(TAG,"clicked selector button!");
                me.setVisibility(View.INVISIBLE);
            }
        });

        sound_board_select_button.setBackground(sound_board_buttons.get(0).get_fresh_drawable_of_image());
        ViewGroup.MarginLayoutParams layout_params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout_params.setMargins(0,0,5,0);
        sound_board_select_button.setLayoutParams(layout_params);
    }

    public SoundBoardButton get_button(int button_index){
        return sound_board_buttons.get(button_index);
    }

    public Button get_selector_button(){
        return sound_board_select_button;
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

    public int get_my_view_id(){
        return my_view_id;
    }

    public GridView create_grid_view(){
        if(my_grid_view != null){
            return get_grid_view();
        }

        my_grid_view = new GridView(EnvironmentVariables.get_app_context());
        //center, fill container
        set_grid_view_parameters_from_config_object(my_grid_view,configuration);
        my_grid_view.setGravity(11|77);
        my_grid_view.setNumColumns(6);
        my_grid_view.setAdapter(this);

        return my_grid_view;
    }

    public void add_grid_view_to_element(ViewGroup add_target){
        my_view_id = View.generateViewId();
        if(my_grid_view == null){
            create_grid_view();
        }
        my_grid_view.setId(my_view_id);
        add_target.addView(my_grid_view);
    }

    public void add_sound_board_selector_button_to_element(ViewGroup add_target){
        sound_board_selector_button_id = View.generateViewId();
        sound_board_select_button.setId(sound_board_selector_button_id);
        add_target.addView(sound_board_select_button);
    }

    public GridView get_grid_view(){
        return my_grid_view;
    }

    public void set_invisible(){
//        set_visibility(View.INVISIBLE);
        GridView my_grid_view = find_my_gridview();
        my_grid_view.setVisibility(View.INVISIBLE);
    }

    public GridView find_my_gridview(){
        View main_view = EnvironmentVariables.get_main_view();
        return main_view.findViewById(my_view_id);
    }

    public void set_visible(){
        GridView my_grid_view = find_my_gridview();
        my_grid_view.setVisibility(View.VISIBLE);
    }

    public Button get_sound_board_selector_button(){
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
}
