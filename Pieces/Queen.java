package com.company.Pieces;

import com.company.Logic.IObserver;
import com.company.Logic.IPosGetter;
import com.company.Logic.Position;

import java.util.HashSet;

public class Queen extends Cell {

    public Queen(Position position, Color color, IObserver observer){
        super(position, color, observer);
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

    /*The queen is the most powerful piece.
    She can move in any one straight direction - forward, backward, sideways,
    or diagonally - as far as possible as long as she does not move through
    any of her own pieces. And, like with all pieces, if the queen captures
    an opponent's piece her move is over. */
    @Override
    public HashSet<Position> validMoves(IPosGetter g) {
        HashSet<Position> ans = new HashSet<>();

        ans.addAll(upDownRightLeft(g));
        ans.addAll(checkDiagonals(g));

        return ans;
    }
}
