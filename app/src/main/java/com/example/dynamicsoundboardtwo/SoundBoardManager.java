package com.example.dynamicsoundboardtwo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SoundBoardManager {
    private ArrayList<SoundBoard> sound_boards = null;

    SoundBoardManager(HashMap<String,HashMap<String,File>> sound_board_folders){
        sound_boards = new ArrayList<>();

        for(String key : sound_board_folders.keySet()){
            sound_boards.add(new SoundBoard(sound_board_folders.get(key)));
        }
    }
}
