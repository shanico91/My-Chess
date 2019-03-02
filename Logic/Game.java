package com.company.Logic;

import com.company.Pieces.Color;

import java.util.Scanner;

public class Game {
    private Board board;

    private Scanner scanner;
    private boolean blackTurn; //false = white, true = black

    public Game(){
        this.board = new Board();
        this.scanner = new Scanner(System.in);
    }

    private void init(){
        this.board.init();
        this.blackTurn = false;
    }

    public void start(){
        this.init();
        System.out.println("input should be: letter+digit 'space' letter+digit");
        while(!board.isGameOver(blackTurn ? Color.BLACK : Color.WHITE)){
            this.board.print(); //print the board
            this.turn(this.blackTurn); //play turn
            this.blackTurn = !this.blackTurn; //switch players
        }
    }

    //play the turn of the current player
    private void turn(boolean turn){

        System.out.println(turn ? "Black's turn!" : "White's turn!");
        //get the move from console (POS from to POS to)
        String input = scanner.nextLine();
        String[] positions = input.split(" ");
        while(inputNotValid(positions)){
            input = scanner.nextLine();
            positions = input.split(" ");
        }
        Position from = parsePosition(positions[0]);
        Position to = parsePosition(positions[1]);

        while(!board.movePiece(from, to, blackTurn)){
            System.out.println("not a valid move, try again");
            input = scanner.nextLine();
            positions = input.split(" ");
            while(inputNotValid(positions)){
                input = scanner.nextLine();
                positions = input.split(" ");
            }

            from = parsePosition(positions[0]);
            to = parsePosition(positions[1]);
        }
    }

    private Position parsePosition(String input){
        return new Position('8'-input.charAt(1), input.charAt(0)-'a');
    }

    private boolean inputNotValid(String[] positions){

        if (positions.length != 2) return true;

        for (String position : positions) {
            if (!isPositionValid(position)) {
                System.out.println("input is not valid, try again");
                return true;
            }
        }

        return false;
    }

    private boolean isPositionValid(String position){

        if(position.charAt(0) > 104 || position.charAt(0) < 96 ||
                position.charAt(1) > 56 || position.charAt(1) < 49) return false;

        return true;
    }

    //TODO: maybe add the option for castling
}
