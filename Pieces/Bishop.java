package com.company.Pieces;

import com.company.Logic.IObserver;
import com.company.Logic.IPosGetter;
import com.company.Logic.Position;

import java.util.HashSet;

public class Bishop extends Cell {

    public Bishop(Position position, Color color, IObserver observer){
        super(position, color, observer);
    }

    @Override
    public Cell clone(IObserver observer) {
        return new Bishop(this.position.clone(), this.color, observer);
    }

    @Override
    public String getName() {
        if(color == Color.BLACK)
            return "♝\t";
        else
            return "♗\t";
    }

    /* The bishop may move as far as it wants, but only diagonally.
        Each bishop starts on one color (light or dark) and must always stay on
        that color.*/
    @Override
    public HashSet<Position> validMoves(IPosGetter g) {

        return checkDiagonals(g);
    }
}
