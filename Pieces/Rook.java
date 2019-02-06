package com.company.Pieces;

import com.company.Logic.IObserver;
import com.company.Logic.IPosGetter;
import com.company.Logic.Position;

import java.util.HashSet;

public class Rook extends Cell {

    public Rook(Position position, Color color, IObserver observer){
        super(position, color, observer);
    }

    @Override
    public Cell clone(IObserver observer) {
        return new Rook(this.position.clone(), this.color, observer);
    }

    @Override
    public String getName() {
        if(color == Color.BLACK)
            return "♜\t";
        else
            return "♖\t";
    }

    /* The rook may move as far as it wants, but only forward, backward,
       and to the sides. */
    @Override
    public HashSet<Position> validMoves(IPosGetter g) {

        return upDownRightLeft(g);
    }
}
