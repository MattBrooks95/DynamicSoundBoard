package com.example.dynamicsoundboardtwo;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

public class Configuration {
    private static final String TAG = "CONFIGURATION";
//    private static final String ROOT_DIRECTORY_NAME = "DynamicSoundBoard";
//    private static final String SAMSUNG_SDCARD_ROOT_ONE = "mnt/extSdCard/";
//    private static final String SAMSUNG_SDCARD_ROOT_TWO = "mnt/external_sd/";
//    private File root_media_directory;
    private String root_media_directory_path;
    private String absolute_path;
    private Context application_context;

//    Configuration(File external_storage_root){
    Configuration(File[] external_storage_files){
        for(File external_storage_file : external_storage_files){
            process_external_storage_location(external_storage_file);
        }
        return;
    }

    private void process_external_storage_location(File external_storage_file){
        Log.d(TAG,external_storage_file.getPath());
        for(File sound_board_folder : external_storage_file.listFiles()){
            Log.d(TAG,sound_board_folder.getPath());
            if(!sound_board_folder.isDirectory()){
                continue;
            }


        }
    }
//    private void initialize_root_media_directory(){
//        root_media_directory = new File(root_media_directory_path);

//        File[] external_storage_directories = getExternalFilesDirs();
        //        Log.d(TAG,"External storage state:"+ Environment.getExternalStorageState());
//        if(!root_media_directory.isDirectory()){
//            Log.d(TAG, "Not a directory! Trying Samsung path:" + SAMSUNG_SDCARD_ROOT_ONE);
//            root_media_directory = new File(SAMSUNG_SDCARD_ROOT_ONE);
//            if(!root_media_directory.isDirectory()){
//                Log.d(TAG, "Trying:" + SAMSUNG_SDCARD_ROOT_TWO);
//                if(!root_media_directory.isDirectory()){
//                    Log.d(TAG, "Ummm");
//                }
//            }
//        }

//        File[] files_in_media_directory = root_media_directory.listFiles();
//        if(files_in_media_directory == null){
//            Log.d(TAG,"Media directory was NULL!");
//            return;
//        }
//
//        for(File this_file : files_in_media_directory){
//            Log.d(TAG, this_file.getPath());
//        }
//    }

    private void initialize_media_folders(){

    }

    //copypasta from android docs - slightly modified
//    private String readTextFromUri(Uri uri) throws IOException {
//        InputStream fileInputStream = application_context.getContentResolver().openInputStream(uri);
//        BufferedReader reader       = new BufferedReader(new InputStreamReader(fileInputStream));
//        StringBuilder stringBuilder = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            stringBuilder.append(line);
//        }
//        fileInputStream.close();
//        return stringBuilder.toString();
//    }
}