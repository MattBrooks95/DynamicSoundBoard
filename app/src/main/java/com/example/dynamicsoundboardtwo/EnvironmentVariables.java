package com.example.dynamicsoundboardtwo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class EnvironmentVariables {
    private static Context application_environment = null;
    private static ViewGroup main_view             = null;
    private static int main_view_id                = -1;

    public static Context get_app_context(){
        return application_environment;
    }

    public static void set_app_context(Context environment){
        application_environment = environment;
    }

    public static void set_main_view(ViewGroup main_view_in){
        main_view = main_view_in;
    }

    public static ViewGroup get_main_view(){
        return main_view;
    }

    public static void set_main_view_id(int main_context_id_in){
        main_view_id = main_context_id_in;
    }

    public static int get_main_view_id(){
        return main_view_id;
    }
}
