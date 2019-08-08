package com.example.dynamicsoundboardtwo;

import android.widget.LinearLayout;

import java.util.ArrayList;

public class DisplayManager {
    private LinearLayout sound_board_select_list_container = null;
    private LinearLayout sound_boards_container            = null;

    DisplayManager(){
        LinearLayout main_view            = EnvironmentVariables.get_main_view();
        sound_board_select_list_container = main_view.findViewById(get_sound_board_select_list_container_id());
        sound_boards_container            = main_view.findViewById(get_sound_boards_container_id());
    }

    private int get_sound_board_select_list_container_id(){
        return R.id.sound_board_select_list;
    }

    public LinearLayout get_sound_board_select_list_container(){
        return sound_board_select_list_container;
    }

    private int get_sound_boards_container_id(){
        return R.id.sound_boards_container;
    }

    public LinearLayout get_sound_boards_container(){
        return sound_boards_container;
    }
}
