package com.example.dynamicsoundboardtwo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class SoundBoardSelectorButton extends android.support.v7.widget.AppCompatButton {
    private int corresponding_sound_board_index;
    private SoundBoardManager sound_board_manager = null;

    SoundBoardSelectorButton(Context context_in, int corresponding_sound_board_index_in, SoundBoardManager sound_board_manager_in, Drawable background_drawable){
        super(context_in);
        corresponding_sound_board_index = corresponding_sound_board_index_in;
        sound_board_manager             = sound_board_manager_in;

        setBackground(background_drawable);
        set_layout_params();
        setOnClickListener(new View.OnClickListener(){
            public void onClick(View me){
                sound_board_manager.change_sound_board(corresponding_sound_board_index);
            }
        });
    }

    private void set_layout_params(){
        ViewGroup.MarginLayoutParams layout_params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1f);
        setLayoutParams(layout_params);
    }
}
