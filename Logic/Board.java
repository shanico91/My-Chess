package com.company.Logic;

import com.company.Pieces.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Scanner;

import static com.company.Logic.Global.LENGTH;

public class Board implements IPosGetter, IObserver{

    private HashSet<Cell> whitePieces;
    private HashSet<Cell> blackPieces;
    private Scanner scanner;

    private Cell[][] cells;
    private King whiteKing;
    private King blackKing;

    public Board(){
        this.cells = new Cell[LENGTH][LENGTH];
        this.whitePieces = new HashSet<>();
        this.blackPieces = new HashSet<>();
        this.whiteKing = null;
        this.blackKing = null;
        this.scanner = new Scanner(System.in);
    }

    boolean isGameOver(Color currPlayer){
        HashSet<Cell> currPieces = currPlayer == Color.BLACK ? blackPieces : whitePieces;

        for(Cell piece : currPieces) {
            for (Position pos : piece.validMoves(this)) {
                if (!checkAfterMove(piece.position, pos, currPlayer == Color.BLACK)) {
                    return false;
                }
            }
        }
        System.out.println("Checkmate! " + Color.getOpponent(currPlayer).name() + " has won!");
        return true;
    }

    //init the board
    void init(){
        //TODO: use reflections to init

        for(int i=2; i<6; i++){ //empty cells:
            for(int j=0; j<LENGTH; j++){
                cells[i][j]= new EmptyCell(new Position(i,j), this);
            }
        }

        for(int i=0; i<LENGTH; i++){ //set Pawns:
            cells[1][i] = new Pawn(new Position(1,i), Color.BLACK, this);
            cells[6][i] = new Pawn(new Position(6,i), Color.WHITE, this);
        }

        // Rooks:
        cells[0][0] = new Rook(new Position(0,0), Color.BLACK, this);
        cells[0][7] = new Rook(new Position(0,7), Color.BLACK, this);
        cells[7][0] = new Rook(new Position(7,0), Color.WHITE, this);
        cells[7][7] = new Rook(new Position(7,0), Color.WHITE, this);

        //Knights:
        cells[0][1] = new Knight(new Position(0,1), Color.BLACK, this);
        cells[0][6] = new Knight(new Position(0,6), Color.BLACK, this);
        cells[7][1] = new Knight(new Position(7,1), Color.WHITE, this);
        cells[7][6] = new Knight(new Position(7,6), Color.WHITE, this);

        //Bishops:
        cells[0][2] = new Bishop(new Position(0,2), Color.BLACK, this);
        cells[0][5] = new Bishop(new Position(0,5), Color.BLACK, this);
        cells[7][2] = new Bishop(new Position(7,2), Color.WHITE, this);
        cells[7][5] = new Bishop(new Position(7,5), Color.WHITE, this);

        //Queens:
        cells[0][3] = new Queen(new Position(0, 3), Color.BLACK, this);
        cells[7][3] = new Queen(new Position(7, 3), Color.WHITE, this);

        //Kings:
        cells[0][4] = new King(new Position(0,4), Color.BLACK, this);
        cells[7][4] = new King(new Position(7,4), Color.WHITE, this);

    }

    //will return true if moved and false otherwise
    boolean movePiece(Position from, Position to, boolean blackTurn) {
        Cell fromCell = getCellAt(from);
        Cell toCell = getCellAt(to);

        // check if the player moves his own piece:
        Color playedColor = fromCell.getColor();
        if ((blackTurn && playedColor != Color.BLACK) || (!blackTurn && playedColor != Color.WHITE)) {
            System.out.println(playedColor == Color.NONE ? "there is no piece at this cell" : "this is not your piece!");
            return false;
        }

        if (!fromCell.validMoves(this).contains(to)) {
            return false;
        }

        //check if there is check (the king is threatened)
        // after the move - if not it's not valid!
        if (checkAfterMove(from, to, blackTurn)) {
            System.out.println("your king cannot be in check");
            return false;
        }

        toCell.remove();

        setCell(to, fromCell); //move fromCell to position to
        setCell(from, new EmptyCell(from,this)); //create new EmptyCell at the from position
        if (fromCell instanceof Pawn) {
            attemptPromote(fromCell);
        }

        return true;
    }

    private void attemptPromote(Cell fromCell){
        int oppositeLine = fromCell.color == Color.BLACK ? 7 : 0;
        //did we get to the opposite line?
        if(!(fromCell.position.getLine() == oppositeLine)){
            return;
        }

        System.out.println("you got to the opposite line," +
                " what type of piece do you want this pawn to turn to?\n" +
                "insert new type (B/R/Kn/Q)");
        String type = scanner.nextLine();

        try {
            fromCell.remove();
            Cell promoted = (Cell)Global.promoteFactory.get(type).getConstructor
                    (Position.class, Color.class, IObserver.class).newInstance(fromCell.position, fromCell.color, this);
            setCell(fromCell.position, promoted);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Cell getCellAt(int line, int column) {
        return this.cells[line][column];
    }

    private Cell getCellAt(Position position){
        return this.cells[position.getLine()][position.getColumn()];
    }

    private void setCell(Position toSet, Cell newCell){
        this.cells[toSet.getLine()][toSet.getColumn()] = newCell;
        newCell.update(toSet);
    }

    //print the board
    void print(){
        int lineNumber = 8;
        for(int i=0; i<LENGTH; i++){
            System.out.print(lineNumber+"\t");
            lineNumber--;
            for(int j=0; j<LENGTH; j++){
                System.out.print(this.cells[i][j].getName() + "|");
            }
            System.out.println();
            System.out.println("\t---\t---\t---\t---\t---\t---\t---\t---");
        }
        System.out.println("\ta \t b \t c \t d \t e \t f \t g \t h");
    }

    //checks if the king is in check after the supposed move
    private boolean checkAfterMove(Position from, Position to, boolean blackTurn){
        Board newBoard = this.clone();
        Cell fromCell = newBoard.getCellAt(from);
        Cell toCell = newBoard.getCellAt(to);

        //move the piece:
        newBoard.setCell(to, fromCell); //move fromCell to position to
        newBoard.setCell(from, new EmptyCell(from, newBoard)); //create new EmptyCell at the from position

        boolean inCheck = false;
        toCell.remove();

        if(blackTurn){ //black's turn

            //check if black king is in check:
            for(Cell piece: newBoard.whitePieces){
                if(piece.validMoves(newBoard).contains(newBoard.blackKing.position)){
                    inCheck = true;
                    break;
                }
            }
        }else {

            //check if black king is in check:
            for(Cell piece: newBoard.blackPieces){
                if(piece.validMoves(newBoard).contains(newBoard.whiteKing.position)){
                    inCheck = true;
                    break;
                }
            }
        }

        return inCheck;
    }

    @Override
    protected Board clone(){
        Board newBoard = new Board();

        for (int i=0; i<LENGTH; i++){
            for(int j=0; j<LENGTH; j++){
                Cell curr = this.cells[i][j].clone(newBoard);
                newBoard.cells[i][j] = curr;
            }
        }

        return newBoard;
    }

    //updating white/black pieces + kings
    @Override
    public void update(Cell cell, boolean isAdded) {
        Color currColor = cell.color;
        //if the cell is empty- nothing to update
        if(currColor == Color.NONE){
            return;
        }

        if(!isAdded){
            //won't do anything if set doesn't contain the cell:
            blackPieces.remove(cell);
            whitePieces.remove(cell);
        } else {
          if(currColor == Color.BLACK){
              blackPieces.add(cell);
              if(cell instanceof King){
                  blackKing = (King) cell;
              }
          } else {
              whitePieces.add(cell);
              if(cell instanceof King){
                  whiteKing = (King) cell;
              }
          }
        }

    }
}