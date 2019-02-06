package com.company.Pieces;

import com.company.Logic.IObserver;
import com.company.Logic.IPosGetter;
import com.company.Logic.Position;

import java.util.HashSet;

public class Pawn extends Cell {

    private boolean moved;

    public Pawn(Position position, Color color, IObserver observer){
        super(position, color, observer);
        moved = false;
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
        moved = true;
    }

    /* Pawns are unusual because they move and capture in different
        ways: they move forward, but capture diagonally.
        Pawns can only move forward one square at a time, except for
        their very first move where they can move forward two squares.
        Pawns can only capture one square diagonally in front of them.
        They can never move or capture backwards.
        If there is another piece directly in front of a pawn he cannot
        move past or capture that piece. */

    /* The last rule about pawns is called “en passant,”
        which is French for “in passing”. If a pawn moves out two squares
        on its first move, and by doing so lands to the side of an
        opponent's pawn (effectively jumping past the other pawn's ability
        to capture it), that other pawn has the option of capturing the
        first pawn as it passes by. This special move must be done
        immediately after the first pawn has moved past, otherwise the
        option to capture it is no longer available.
         */
        //TODO: maybe implement en passante
    @Override
    public HashSet<Position> validMoves(IPosGetter g) {

        HashSet<Position> ans = new HashSet<>();
        int currLine = position.getLine();
        int currRow = position.getRow();

        //moving forward:
        int forwardLine = this.color == Color.BLACK ? currLine+1 : currLine-1;
        addForward(g, forwardLine, currRow, ans);

        //if first move than can move two squares forward
        if(!moved){
            int forwardAgain = this.color == Color.BLACK ? forwardLine+1 : forwardLine-1;
            addForward(g, forwardAgain, currRow, ans);
        }

        //eating diagonally:
        addDiagonally(g, forwardLine, currRow-1, ans);
        addDiagonally(g, forwardLine, currRow+1, ans);

        return ans;
    }

    private void addForward(IPosGetter g, int line, int row, HashSet<Position> ans){
        //if the spot is empty
        if(onBoard(line, row) && g.getCellAt(line, row).getColor() == Color.NONE){
            ans.add(new Position(line, row));
        }
    }

    private void addDiagonally(IPosGetter g, int line, int row, HashSet<Position> ans){
        //if the spot is piece of opponent
        Color opColor = Color.getOpponent(this.color);
        if(onBoard(line, row) && g.getCellAt(line, row).getColor() == opColor){
            ans.add(new Position(line, row));
        }

    }
}
