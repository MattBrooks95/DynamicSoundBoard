package com.example.dynamicsoundboardtwo;

public class SoundBoardConfiguration {
    private static final String TAG = "CONFIGURATION";

    private static final int DEFAULT_NUMBER_OF_COLUMNS = 6;
    private static final int DEFAULT_COLUMN_WIDTH      = 50;
    private static final int DEFAULT_VERTICAL_SPACING  = 10;

    private int number_of_columns;
    private int column_width;
    private int vertical_spacing;

    SoundBoardConfiguration(){
        constructor_work(DEFAULT_NUMBER_OF_COLUMNS, DEFAULT_COLUMN_WIDTH, DEFAULT_VERTICAL_SPACING);
    }

    SoundBoardConfiguration(int number_of_columns){
        constructor_work(number_of_columns, DEFAULT_COLUMN_WIDTH, DEFAULT_VERTICAL_SPACING);
    }

    SoundBoardConfiguration(int number_of_columns, int column_spacing){
        constructor_work(number_of_columns, column_spacing, DEFAULT_VERTICAL_SPACING);
    }

    SoundBoardConfiguration(int number_of_columns, int column_spacing, int vertical_spacing){
        constructor_work(number_of_columns,column_spacing,vertical_spacing);
    }

    private void constructor_work(int number_of_columns, int column_width, int vertical_spacing){
        set_number_of_columns(number_of_columns);
        set_column_width(column_width);
        set_vertical_spacing(vertical_spacing);
    }

    private void set_number_of_columns(int number_of_columns_in){
        number_of_columns = number_of_columns_in;
    }

    private void set_column_width(int column_width_in){
        column_width = column_width_in;
    }

    private void set_vertical_spacing(int vertical_spacing_in){
        vertical_spacing = vertical_spacing_in;
    }

    public int get_number_of_columns(){
        return number_of_columns;
    }

    public int get_column_width(){
        return column_width;
    }

    public int get_vertical_spacing(){
        return vertical_spacing;
    }
}