package com.company.Pieces;

import com.company.Logic.IObserver;
import com.company.Logic.Position;

public class Pawn extends Cell {

    public Pawn(Position position, Color color, IObserver observer){
        super(position, color, observer);
        checkMyvalidMoves = new CheckMoves[] {new PawnMoves()};
    }

    @Override
    public Cell clone(IObserver observer) {
        return new Pawn(this.position.clone(), this.color, observer);
    }

    @Override
    public String getName() {
        if(color == Color.BLACK)
            return "♟\t";
        else
            return "♙\t";
    }

    @Override
    public void update(Position position) {
        super.update(position);
        ((PawnMoves)checkMyvalidMoves[0]).setMoved();
    }
}
