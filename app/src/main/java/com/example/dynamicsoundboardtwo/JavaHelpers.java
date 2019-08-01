package com.example.dynamicsoundboardtwo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class JavaHelpers {
    private static final String TAG = "JavaHelpers";

    //finish later!
    public static final String get_string_from_file(File file_to_read) throws java.io.FileNotFoundException{
        String file_path = file_to_read.getPath();
        if(!file_to_read.canRead()){
            Log.d(TAG, "Couldn't read file:" + file_path);
            return null;
        }

        StringBuilder file_contents = new StringBuilder();

        FileReader input      = new FileReader(file_to_read);
        BufferedReader reader = new BufferedReader(input);

        try{
            String new_line;
            while((new_line = reader.readLine()) != null){
                file_contents.append(new_line);
            }
        } catch(java.io.IOException read_error){
            Log.d(TAG, "File:" + file_path + " was opened but could not be read properly.");
        }

        return file_contents.toString();
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

    public boolean message_if_null(Object some_object, String message){
        if(some_object == null) {
            Log.d(TAG, "Null object! Message:" + message);
            return true;
        } else {
            return false;
        }
    }
}
