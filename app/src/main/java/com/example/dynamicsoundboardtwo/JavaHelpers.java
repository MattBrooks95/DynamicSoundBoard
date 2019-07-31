package com.example.dynamicsoundboardtwo;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class JavaHelpers {
    private static final String TAG = "JavaHelpers";

    //finish later!
    public static final String get_string_from_file(File file_to_read){
        return "";
    }

    //this needs to be recursive
    public static final HashMap<String,File> build_map_of_directory(File directory){
        HashMap<String,File> map = new HashMap<>();

        if(directory == null || !directory.isDirectory()){
            Log.d(TAG,"build_map_of_directory not given a directory!");
            return map;
        }

        File[] file_list = directory.listFiles();

        for(File file_object : file_list){
            map.put(file_object.getName(), file_object);
        }

        return map;
    }

    public static final String join_string_array(ArrayList<String> string_array, Character joining_character){
        StringBuilder return_string = new StringBuilder();
        int ending_index = string_array.size();
        for(int string_index = 0; string_index < ending_index; ++string_index){
            return_string.append(string_array.get(string_index));
            if(string_index != ending_index - 1){
                return_string.append(joining_character);
            }
        }
        return return_string.toString();
    }
}
