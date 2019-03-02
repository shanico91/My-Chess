package com.company.Logic;

import com.company.Pieces.Color;

import java.util.HashSet;

import static com.company.Logic.Global.LENGTH;

public class movesFuncs {

    public static boolean continueAdding(IPosGetter g, int line, int column, HashSet<Position> ans, Color color) {
        Color currColor = g.getCellAt(line, column).getColor();
        if (currColor != color) {
            ans.add(new Position(line, column));
        }
        return currColor == Color.NONE;
    }


    public static boolean onBoard(int line, int column) {
        if (line < LENGTH && column < LENGTH && line >= 0 && column >= 0) {
            return true;
        }

        return false;
    }

}