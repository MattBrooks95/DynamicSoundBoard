package com.example.dynamicsoundboardtwo;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class Configuration {
    private static final String TAG = "Configuration";
    private static final String configuration_file_name = "image_to_sound_mapping.json";
    private String configuration_file_content;
    Configuration(Uri folder_uri){
        Uri.Builder compounded_uri_builder = folder_uri.buildUpon().appendPath(configuration_file_name);
        Uri compounded_uri   = compounded_uri_builder.build();
        initialize_configuration_object_from_uri(compounded_uri);
    }

    private void initialize_configuration_object_from_uri(Uri file_uri){
        String uri_as_string = file_uri.getPath();
        Log.d(TAG,"appended uri:" + uri_as_string);
    }
}
