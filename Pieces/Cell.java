package com.company.Pieces;

import com.company.Logic.IObservable;
import com.company.Logic.IObserver;
import com.company.Logic.IPosGetter;
import com.company.Logic.Position;

import static com.company.Logic.Global.LENGTH;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class Cell implements IObservable {

    public Color color;
    public Position position;
    private List<IObserver> subscribers;

    public Cell(Position position, IObserver observer){
        this.position = position;
        this.color = Color.NONE;
        this.subscribers = new ArrayList<>();
        subscribe(observer);
        notify(true);
    }

    public Cell(Position position, Color color, IObserver observer){
        this.position = position;
        this.color = color;
        this.subscribers = new ArrayList<>();
        subscribe(observer);
        notify(true);
    }

    @Override
    public void subscribe(IObserver observer) {
        subscribers.add(observer);
    }

    @Override
    public void unsubscribe(IObserver observer) {
        subscribers.remove(observer);
    }

    @Override
    public void notify(boolean isAdded) {
        for (IObserver obs: subscribers) {
            obs.update(this, isAdded);
        }
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public void update(Position position){
        this.setPosition(position);
        //can add here other things to update if I want to
    }

    public Color getColor(){
        return this.color;
    }

    public abstract Cell clone(IObserver observer);
    public abstract String getName();
    public abstract HashSet<Position> validMoves(IPosGetter g);

    protected boolean continueAdding(IPosGetter g, int line, int row, HashSet<Position> ans){
        Color currColor = g.getCellAt(line, row).getColor();
        if(currColor != this.color){
            ans.add(new Position(line, row));
        }
        return currColor == Color.NONE;
    }

    protected HashSet<Position> checkUp(IPosGetter g){
        HashSet<Position> ans = new HashSet<>();

        int currLine = position.getLine();
        int currRow = position.getRow();

        for(int line = currLine-1; line >= 0; line--){ //up
            if(!continueAdding(g, line, currRow, ans)) break;

        }
        return ans;
    }

    protected HashSet<Position> checkDown(IPosGetter g) {
        HashSet<Position> ans = new HashSet<>();

        int currLine = position.getLine();
        int currRow = position.getRow();

        for(int line = currLine+1; line < 8; line++){ //down
            if(!continueAdding(g, line, currRow, ans)) break;
        }

        return ans;
    }

    protected HashSet<Position> checkRight(IPosGetter g) {
        HashSet<Position> ans = new HashSet<>();

        int currLine = position.getLine();
        int currRow = position.getRow();

        for(int row = currRow+1; row < 8; row++){ //right
            if(!continueAdding(g, currLine, row, ans)) break;
        }

        return ans;
    }

    protected HashSet<Position> checkLeft(IPosGetter g) {
        HashSet<Position> ans = new HashSet<>();

        int currLine = position.getLine();
        int currRow = position.getRow();

        for (int row = currRow - 1; row >= 0; row--) { //left
            if (!continueAdding(g, currLine, row, ans)) break;
        }

        return ans;
    }

    protected HashSet<Position> upDownRightLeft(IPosGetter g){
        HashSet<Position> ans = new HashSet<>();

        ans.addAll(checkDown(g));
        ans.addAll(checkUp(g));
        ans.addAll(checkRight(g));
        ans.addAll(checkLeft(g));

        return ans;
    }

    //TODO: get rid of code duplication
    protected HashSet<Position> checkDiagonallyUp(IPosGetter g) {
        HashSet<Position> ans = new HashSet<>();

        int currLine = position.getLine();
        int currRow = position.getRow();

        int j = currRow + 1;
        int l = currRow -1;
        for(int i = currLine-1; i>=0; i--){
            if(j<8) { //diagonally up-right
                if (!continueAdding(g, i, j, ans)) break;
                j++;
            }
            if(l >= 0){ //diagonally up-left
                if (!continueAdding(g, i, j, ans)) break;
                l--;
            }
        }
        return ans;
    }

    protected HashSet<Position> checkDiagonallyDown(IPosGetter g) {
        HashSet<Position> ans = new HashSet<>();

        int currLine = position.getLine();
        int currRow = position.getRow();

        int w = currRow + 1;
        int r = currRow -1;

        for(int i = currLine+1; i<8; i++){

            if(w<8) { //diagonally down-right
                if (!continueAdding(g, i, w, ans)) break;
                w++;
            }

            if(r >= 0){ //diagonally down-left
                if (!continueAdding(g, i, r, ans)) break;
                r--;
            }
        }
        return ans;
    }

    protected HashSet<Position> checkDiagonals(IPosGetter g) {
        HashSet<Position> ans = new HashSet<>();

        ans.addAll(checkDiagonallyUp(g));
        ans.addAll(checkDiagonallyDown(g));

        return ans;
    }

    protected boolean onBoard(int line, int row){
        if(line<LENGTH && row<LENGTH && line>=0 && row>=0){
            return true;
        }

        return false;
    }

    protected HashSet<Position> checkPositions(IPosGetter g, Position[] moves){

        HashSet<Position> ans = new HashSet<>();
        int currLine = position.getLine();
        int currRow = position.getRow();

        for (Position pos: moves){
            int line = currLine+pos.getLine();
            int row = currRow+pos.getRow();
            if (onBoard(line, row)){
                //checks if I can step there (color is different than mine)
                continueAdding(g, line, row, ans);
            }
        }

        return ans;
    }

    public void remove() {
        notify(false);
    }
}
