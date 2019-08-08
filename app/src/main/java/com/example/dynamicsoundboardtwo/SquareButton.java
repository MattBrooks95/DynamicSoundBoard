package com.example.dynamicsoundboardtwo;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

//this class reports it's height to be it's width, so that the column width
//from a grid view is used to set the height to be the same, so the buttons look square
//this seems really hacky and not proper
public class SquareButton extends  android.support.v7.widget.AppCompatButton {
    private int height = 0;
    SquareButton(Context context_in){
        super(context_in);
    }

    SquareButton(Context context_in, int height_in){
        super(context_in);
        height = height_in;
    }

    @Override
    protected void onMeasure(int width, int height_in){
        setMeasuredDimension(width,width);
    }
}
