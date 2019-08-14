package com.example.dynamicsoundboardtwo;

import android.content.Context;
import android.view.View;

public class SoundBoardSelectorButton extends SquareButton {
    private int corresponding_sound_board_index;
    private SoundBoardManager sound_board_manager = null;

    SoundBoardSelectorButton(Context context_in, int corresponding_sound_board_index_in, SoundBoardManager sound_board_manager_in){
        super(context_in);
        corresponding_sound_board_index = corresponding_sound_board_index_in;
        sound_board_manager             = sound_board_manager_in;
        setOnClickListener(new View.OnClickListener(){
            public void onClick(View me){
                sound_board_manager.change_sound_board(corresponding_sound_board_index);
            }
        });

    }
}
