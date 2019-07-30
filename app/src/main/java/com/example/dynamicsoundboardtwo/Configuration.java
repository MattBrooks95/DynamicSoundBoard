package com.example.dynamicsoundboardtwo;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class Configuration {
    private static final String TAG = "CONFIGURATION";
    private static final String configuration_file_name = "image_to_sound_mapping.json";
    private String configuration_file_content;
    private Context application_context;
    private JSONObject configuration_json;

    Configuration(Uri folder_uri, Context application_context_in){
        application_context = application_context_in;
        Log.d(TAG,"Message from configuration class");
        Log.d(TAG, folder_uri.getPath());
        Uri compounded_uri = get_compounded_uri(folder_uri,configuration_file_name);
        Log.d(TAG, compounded_uri.getPath());
        initialize_configuration_object_from_uri(compounded_uri);
    }

    private Uri get_compounded_uri(Uri base_uri, String uri_offset){
        return base_uri;
//        Uri.Builder compounded_uri_builder = base_uri.buildUpon().appendPath(uri_offset);
//        Uri compounded_uri                 = compounded_uri_builder.build();
//        return compounded_uri;
    }

    private void initialize_configuration_object_from_uri(Uri file_uri){
        String file_as_string;
//        file_as_string = readTextFromPath(file_uri);
        Log.d(TAG,"initializing configuration object");
        try {
            file_as_string = readTextFromUri(file_uri);
        } catch(java.io.IOException io_error){
            Log.d(TAG,"IO error");
            return;
        }

        Log.d(TAG, file_as_string);

        try{
            configuration_json = new JSONObject(file_as_string);
        } catch(org.json.JSONException json_parsing_error){
            Log.d(TAG,"JSON parsing error");
        }
    }

//    private String readTextFromPath(String path){
//        File configuration_file = new File(path);
//        try {
//            FileReader configuration_file_reader = new FileReader(configuration_file);
//            BufferedReader read_buffer  = new BufferedReader(configuration_file_reader);
//            StringBuilder file_contents = new StringBuilder();
//            String line;
//            while ((line = read_buffer.readLine()) != null) {
//                file_contents.append(line);
//            }
//            configuration_file_reader.close();
//            return file_contents.toString();
//        } catch(java.io.FileNotFoundException not_found){
//            Log.d(TAG,"file not found exception!");
//            return "";
//        } catch(java.io.IOException input_output_error){
//            Log.d(TAG, "IO error!");
//        }
//
//        return "";
//    }

    //copypasta from android docs - slightly modified
    private String readTextFromUri(Uri uri) throws IOException {
        InputStream fileInputStream = application_context.getContentResolver().openInputStream(uri);
        BufferedReader reader       = new BufferedReader(new InputStreamReader(fileInputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        fileInputStream.close();
        return stringBuilder.toString();
    }
}