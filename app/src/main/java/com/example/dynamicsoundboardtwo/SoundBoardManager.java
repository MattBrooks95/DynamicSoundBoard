package com.example.dynamicsoundboardtwo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SoundBoardManager {
    private ArrayList<SoundBoard> sound_boards = null;

    SoundBoardManager(ArrayList<HashMap<String,File>> sound_board_folders){
        sound_boards = new ArrayList<>();

        for(HashMap<String,File> sound_board_folder : sound_board_folders){
            sound_boards.add(new SoundBoard(sound_board_folder));
        }
    }
}
