package com.company.Pieces;

public enum Color
{
    WHITE, BLACK, NONE;

    public static Color getOpponent(Color player){
        if(player == NONE) return NONE;
        return player == BLACK ? WHITE : BLACK;
    }


}
