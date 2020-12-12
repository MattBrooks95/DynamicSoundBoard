package com.example.dynamicsoundboardtwo;

import android.util.Log;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SoundBoardManager {
    private static final String TAG = "SoundBoardManager";

    private int currently_selected_sound_board_index = 0;

    private ArrayList<SoundBoard> sound_boards                = null;
    private ArrayList<SoundBoardSelectorButton> sound_board_select_list_buttons = null;

    SoundBoardManager(HashMap<String,HashMap<String,File>> sound_board_folders){
        sound_boards = new ArrayList<>();

        sound_board_select_list_buttons = new ArrayList<>();

        int count = 0;

        for(String key : sound_board_folders.keySet()){
            Log.d(TAG,"processing sound board folder:" + key);
            SoundBoard new_soundboard = new SoundBoard(sound_board_folders.get(key), count, this);
            sound_boards.add(new_soundboard);
            //use count to set the button's callback to set the currently displayed sound board
            //to the index stored within the button
            sound_board_select_list_buttons.add(new_soundboard.get_selector_button());
            ++count;
        }
    }

    public ArrayList<SoundBoard> get_sound_boards(){
        return sound_boards;
    }

    private void set_currently_selected_sound_board_index(int new_sound_board_index){
        currently_selected_sound_board_index = new_sound_board_index;
    }

    public void change_sound_board(int new_sound_board_index){
        if(new_sound_board_index < 0 || new_sound_board_index >= sound_boards.size()) {
            Log.d(TAG, "Change sound board method given illegal index!:" + new_sound_board_index);
            return;
        }

        set_currently_selected_sound_board_index(new_sound_board_index);

        for(int sound_board_index = 0; sound_board_index < sound_boards.size(); ++sound_board_index){
            SoundBoard this_sound_board = sound_boards.get(sound_board_index);
            if(sound_board_index != new_sound_board_index){
                this_sound_board.set_invisible();
            } else {
                this_sound_board.set_visible();
            }
        }
    }

    public int get_currently_selected_sound_board_index(){
        return currently_selected_sound_board_index;
    }

    public void next_sound_board(ArrayList<SoundBoard> sound_boards){
        if(currently_selected_sound_board_index < sound_boards.size()){
            change_sound_board(currently_selected_sound_board_index + 1);
        }
    }

    public void previous_sound_board(ArrayList<SoundBoard> sound_boards){
        if(currently_selected_sound_board_index > 0){
            change_sound_board(currently_selected_sound_board_index - 1);
        }

    }
}
