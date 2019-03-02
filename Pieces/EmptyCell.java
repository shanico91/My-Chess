package com.company.Pieces;

import com.company.Logic.IObserver;
import com.company.Logic.Position;

public class EmptyCell extends Cell  {

    public EmptyCell(Position position, IObserver observer){
        super(position, observer);
        checkMyvalidMoves = new CheckMoves[]{new EmptyMoves()};
    }

    @Override
    public Cell clone(IObserver observer) {
        return new EmptyCell(this.position.clone(), observer);
    }

    @Override
    public String getName() {
        return " \t";
    }

}
