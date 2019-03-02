package com.company.Pieces;
import com.company.Logic.IPosGetter;
import com.company.Logic.Position;
import static com.company.Logic.movesFuncs.*;

import java.util.HashSet;

public interface CheckMoves {

    HashSet<Position> validMoves(IPosGetter g, Position position, Color color);
}

abstract class CheckPositions implements CheckMoves{

    public static HashSet<Position> checkPositions(IPosGetter g, Position position, Color color,  Position[] moves){

        HashSet<Position> ans = new HashSet<>();
        int currLine = position.getLine();
        int currColumn = position.getColumn();

        for (Position pos: moves){
            int line = currLine+pos.getLine();
            int column = currColumn+pos.getColumn();
            if (onBoard(line, column)){
                //checks if I can step there (color is different than mine)
                continueAdding(g, line, column, ans, color);
            }
        }

        return ans;
    }
}

class CheckDiagonals implements CheckMoves{

    @Override
    public HashSet<Position> validMoves(IPosGetter g, Position position, Color color) {
        HashSet<Position> ans = new HashSet<>();

        int currLine = position.getLine();
        int currColumn = position.getColumn();

        int columnUpRight = currColumn + 1;
        int columnUpLeft = currColumn -1;
        for(int i = currLine-1; i>=0; i--){
            if(columnUpRight<8) { //diagonally up-right
                if (!continueAdding(g, i, columnUpRight, ans, color)) break;
                columnUpRight++;
            }
        }

        for(int j = currLine -1; j>=0; j--){
            if(columnUpLeft >= 0){ //diagonally up-left
                if (!continueAdding(g, j, j, ans, color)) break;
                columnUpLeft--;
            }
        }

        int columnDownRight = currColumn + 1;
        int columnDownLeft = currColumn -1;

        for(int i = currLine+1; i<8; i++){
            if(columnDownRight<8) { //diagonally down-right
                if (!continueAdding(g, i, columnDownRight, ans, color)) break;
                columnDownRight++;
            }
        }

        for(int j = currLine+1; j<8; j++){
            if(columnDownLeft >= 0){ //diagonally down-left
                if (!continueAdding(g, j, columnDownLeft, ans, color)) break;
                columnDownLeft--;
            }
        }
        return ans;
    }
}

class CheckUpDownRightLeft implements CheckMoves{

    @Override
    public HashSet<Position> validMoves(IPosGetter g, Position position, Color color) {
        HashSet<Position> ans = new HashSet<>();

        int currLine = position.getLine();
        int currColumn = position.getColumn();

        for(int lineUp = currLine-1; lineUp >= 0; lineUp--){ //up
            if(!continueAdding(g, lineUp, currColumn, ans, color)) break;
        }

        for(int lineDown = currLine+1; lineDown < 8; lineDown++){ //down
            if(!continueAdding(g, lineDown, currColumn, ans, color)) break;
        }

        for(int columnRight = currColumn+1; columnRight < 8; columnRight++){ //right
            if(!continueAdding(g, currLine, columnRight, ans, color)) break;
        }


        for (int columnLeft = currColumn - 1; columnLeft >= 0; columnLeft--) { //left
            if (!continueAdding(g, currLine, columnLeft, ans, color)) break;
        }

        return ans;
    }
}

class EmptyMoves implements CheckMoves{

    @Override
    public HashSet<Position> validMoves(IPosGetter g, Position position, Color color) {
        return null;
    }
}

class KingMoves extends CheckPositions{
    private static final Position[] moves = {new Position(-1, -1), new Position(-1, 0), new Position(-1, 1), new Position(0, -1), new Position(0, 1), new Position(1, -1), new Position(1, 0), new Position(1, 1)};

    /* The king is the most important piece, but is one of the weakest.
     The king can only move one square in any direction - up, down, to the sides,
     and diagonally. The king may never move himself into check
     (where he could be captured). When the king is attacked by another piece
     this is called "check". */
    @Override
    public HashSet<Position> validMoves(IPosGetter g, Position position, Color color) {
        return checkPositions(g, position, color, moves);
    }
}

class KnightMoves extends CheckPositions{
    private static final Position[] moves = {new Position(-2, -1), new Position(-2, 1), new Position(-1, -2), new Position(-1, 2), new Position(1, -2), new Position(1, 2), new Position(2, -1), new Position(2, 1)};

    /* Knights move in a very different way from the other pieces
     – going two squares in one direction, and then one more move at a 90 degree angle,
     just like the shape of an “L”. Knights are also the only pieces that can move over
     other pieces. */
    @Override
    public HashSet<Position> validMoves(IPosGetter g, Position position, Color color) {
        return checkPositions(g, position, color, moves);
    }
}

class PawnMoves implements CheckMoves{
    private boolean moved;

    PawnMoves(){
        moved = false;
    }

    public void setMoved(){
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
    public HashSet<Position> validMoves(IPosGetter g, Position position, Color color) {
        HashSet<Position> ans = new HashSet<>();
        int currLine = position.getLine();
        int currColumn = position.getColumn();

        //moving forward:
        int forwardLine = color == Color.BLACK ? currLine+1 : currLine-1;
        addForward(g, forwardLine, currColumn, ans);

        //if first move than can move two squares forward
        if(!moved){
            int forwardAgain = color == Color.BLACK ? forwardLine+1 : forwardLine-1;
            addForward(g, forwardAgain, currColumn, ans);
        }

        //eating diagonally:
        addDiagonally(g, forwardLine, currColumn-1, ans, color);
        addDiagonally(g, forwardLine, currColumn+1, ans, color);

        return ans;
    }

    private static void addForward(IPosGetter g, int line, int column, HashSet<Position> ans){
        //if the spot is empty
        if(onBoard(line, column) && g.getCellAt(line, column).getColor() == Color.NONE){
            ans.add(new Position(line, column));
        }
    }

    private static void addDiagonally(IPosGetter g, int line, int column, HashSet<Position> ans, Color color){
        //if the spot is piece of opponent
        Color opColor = Color.getOpponent(color);
        if(onBoard(line, column) && g.getCellAt(line, column).getColor() == opColor){
            ans.add(new Position(line, column));
        }
    }
}