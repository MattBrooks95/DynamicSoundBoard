package com.example.dynamicsoundboardtwo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class SoundBoardManager {
    private ArrayList<SoundBoard> sound_boards                      = null;
    private ArrayList<SquareButton> sound_board_select_list_buttons = null;

    SoundBoardManager(HashMap<String,HashMap<String,File>> sound_board_folders){
        sound_boards = new ArrayList<>();

        sound_board_select_list_buttons = new ArrayList<>();

        int count = 0;

        for(String key : sound_board_folders.keySet()){
            SoundBoard new_soundboard = new SoundBoard(sound_board_folders.get(key));
            sound_boards.add(new_soundboard);
            SquareButton selector_button = new_soundboard.get_selector_button();
            //use count to set the button's callback to set the currently displayed sound board
            //to the index stored within the button
            sound_board_select_list_buttons.add(selector_button);
            ++count;
        }
    }

    public ArrayList<SoundBoard> get_sound_boards(){
        return sound_boards;
    }
}
