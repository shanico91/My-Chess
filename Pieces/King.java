package com.company.Pieces;

import com.company.Logic.IObserver;
import com.company.Logic.IPosGetter;
import com.company.Logic.Position;

import java.util.HashSet;

public class King extends Cell {

    //private static final Position[] moves = {new Position(-1, -1), new Position(-1, 0), new Position(-1, 1), new Position(0, -1), new Position(0, 1), new Position(1, -1), new Position(1, 0), new Position(1, 1)};

    public King(Position position, Color color, IObserver observer) {
        super(position, color, observer);
        checkMyvalidMoves = new CheckMoves[] {new KingMoves()};
    }

    @Override
    public Cell clone(IObserver observer) {
        return new King(this.position.clone(), this.color, observer);
    }

    @Override
    public String getName() {
        if(color == Color.BLACK)
            return "♚\t";
        else
            return "♔\t";
    }
}