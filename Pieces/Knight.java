package com.company.Pieces;

import com.company.Logic.IObserver;
import com.company.Logic.IPosGetter;
import com.company.Logic.Position;

import java.util.HashSet;

public class Knight extends Cell {
    private static final Position[] moves = {new Position(-2, -1), new Position(-2, 1), new Position(-1, -2), new Position(-1, 2), new Position(1, -2), new Position(1, 2), new Position(2, -1), new Position(2, 1)};

    public Knight(Position position, Color color, IObserver observer){
        super(position, color, observer);
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

    /* Knights move in a very different way from the other pieces
     – going two squares in one direction, and then one more move at a 90 degree angle,
     just like the shape of an “L”. Knights are also the only pieces that can move over
     other pieces. */
    @Override
    public HashSet<Position> validMoves(IPosGetter g) {

        return checkPositions(g, moves);
    }
}
