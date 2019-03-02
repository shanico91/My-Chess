package com.company.Pieces;

import com.company.Logic.IObserver;
import com.company.Logic.Position;

public class Knight extends Cell {
    //private static final Position[] moves = {new Position(-2, -1), new Position(-2, 1), new Position(-1, -2), new Position(-1, 2), new Position(1, -2), new Position(1, 2), new Position(2, -1), new Position(2, 1)};

    public Knight(Position position, Color color, IObserver observer){
        super(position, color, observer);
        checkMyvalidMoves = new CheckMoves[] {new KnightMoves()};
    }

    @Override
    public Cell clone(IObserver observer) {
        return new Knight(this.position.clone(), this.color, observer);
    }

    @Override
    public String getName() {
        if(color == Color.BLACK)
            return "♞\t";
        else
            return "♘\t";
    }
}
