package com.company.Pieces;

import com.company.Logic.IObserver;
import com.company.Logic.Position;

public class Bishop extends Cell {

    public Bishop(Position position, Color color, IObserver observer){
        super(position, color, observer);
        checkMyvalidMoves = new CheckMoves[] {new CheckDiagonals()};
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

}
