package com.example.dynamicsoundboardtwo;

import android.widget.LinearLayout;

import java.util.ArrayList;

public class DisplayManager {
    private LinearLayout sound_board_select_list_container = null;
    private LinearLayout sound_boards_container            = null;

    private int currently_selected_sound_board = 0;

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

    public int get_currently_selected_sound_board_index(){
        return currently_selected_sound_board;
    }

    public void next_sound_board(ArrayList<SoundBoard> sound_boards){
        if(currently_selected_sound_board < sound_boards.size()){
            change_sound_board(currently_selected_sound_board + 1,sound_boards);
        }
    }

    public void previous_sound_board(ArrayList<SoundBoard> sound_boards){
        if(currently_selected_sound_board > 0){
            change_sound_board(currently_selected_sound_board - 1,sound_boards);
        }

    }

    public void change_sound_board(int new_sound_board_index, ArrayList<SoundBoard> sound_boards){
        currently_selected_sound_board = new_sound_board_index;

        for(int sound_board_index = 0; sound_board_index < sound_boards.size(); ++sound_board_index){
            SoundBoard this_sound_board = sound_boards.get(sound_board_index);
            if(sound_board_index != new_sound_board_index){
                this_sound_board.set_invisible();
            } else {
                this_sound_board.set_visible();
            }
        }
    }
}
