package com.example.dynamicsoundboardtwo;

import android.content.Context;
import android.widget.GridView;

public class EnvironmentVariables {
    private static Context application_environment = null;
    private static GridView main_context           = null;
    private static int main_context_id             = -1;

    public static Context get_app_context(){
        return application_environment;
    }

    public static void set_app_context(Context environment){
        application_environment = environment;
    }

    public static void set_main_context(GridView main_context_in){
        main_context = main_context_in;
    }

    public static GridView get_main_context(){
        return main_context;
    }

    public static void set_main_context_id(int main_context_id_in){
        main_context_id = main_context_id_in;
    }

    public static int get_main_context_id(){
        return main_context_id;
    }
}
