package com.company.Pieces;

import com.company.Logic.IObserver;
import com.company.Logic.IPosGetter;
import com.company.Logic.Position;

import java.util.HashSet;

public class EmptyCell extends Cell  {

    public EmptyCell(Position position, IObserver observer){
        super(position, observer);
    }

    @Override
    public Cell clone(IObserver observer) {
        return new EmptyCell(this.position.clone(), observer);
    }

    @Override
    public String getName() {
        return " \t";
    }

    @Override
    public HashSet<Position> validMoves(IPosGetter g) {
        return null;
    }

}
