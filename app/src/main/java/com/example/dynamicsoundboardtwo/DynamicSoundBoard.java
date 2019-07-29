package com.example.dynamicsoundboardtwo;

import android.app.Activity;
import android.content.Intent;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;

public class DynamicSoundBoard extends AppCompatActivity {

    private static final String TAG = "DynamicSoundBoard";

    public static final int GET_FOLDER_REQUEST_CODE = 1776;

    public Uri user_selected_media_directory_uri;

    protected Configuration configuration    = null;
    protected DisplayManager display_manager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup_root_media_directory_uri();
    }

    private void setup(){
        test_print();

        setup_configuration(user_selected_media_directory_uri);
        setup_display(user_selected_media_directory_uri);
    }

    private void test_print(){
        TextView greeting = findViewById(R.id.greeting);
        greeting.append(user_selected_media_directory_uri.toString());
    }

    private void setup_configuration(Uri folder_uri){
        Log.d(TAG,"setup_configuration()");
        configuration = new Configuration(folder_uri);
    }

    private void setup_display(Uri folder_uri){
        Log.d(TAG,"setup_display()");
    }

    private void setup_root_media_directory_uri(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, GET_FOLDER_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData){
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == GET_FOLDER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            if (resultData != null){
                user_selected_media_directory_uri = resultData.getData();

                Log.d(TAG, "Uri: " + user_selected_media_directory_uri.toString());
                setup();
            }
        }
    }
}
