package com.company.Pieces;

import com.company.Logic.IObserver;
import com.company.Logic.Position;

public class Queen extends Cell {

    public Queen(Position position, Color color, IObserver observer){
        super(position, color, observer);
        checkMyvalidMoves = new CheckMoves[] {new CheckUpDownRightLeft(), new CheckDiagonals()};
    }

    @Override
    public Cell clone(IObserver observer) {
        return new Queen(this.position.clone(), this.color, observer);
    }

    @Override
    public String getName() {
        if(color == Color.BLACK)
            return "♛\t";
        else
            return "♕\t";
    }
}
