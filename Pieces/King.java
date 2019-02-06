package com.company.Pieces;

import com.company.Logic.IObserver;
import com.company.Logic.IPosGetter;
import com.company.Logic.Position;

import java.util.HashSet;

public class King extends Cell {

    private static final Position[] moves = {new Position(-1, -1), new Position(-1, 0), new Position(-1, 1), new Position(0, -1), new Position(0, 1), new Position(1, -1), new Position(1, 0), new Position(1, 1)};

    public King(Position position, Color color, IObserver observer) {
        super(position, color, observer);
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

    /* The king is the most important piece, but is one of the weakest.
     The king can only move one square in any direction - up, down, to the sides,
     and diagonally. The king may never move himself into check
     (where he could be captured). When the king is attacked by another piece
     this is called "check". */
    @Override
    public HashSet<Position> validMoves(IPosGetter g) {

        return checkPositions(g, moves);

    }
}