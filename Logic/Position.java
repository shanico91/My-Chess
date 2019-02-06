package com.company.Logic;

public class Position {
    private int row;
    private int line;

    public Position(){}
    public Position(int line, int row){
        this.setRow(row);
        this.setLine(line);
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getLine() {
        return line;
    }

    public int getRow() {
        return row;
    }

    public Position clone(){
        return new Position(this.line, this.row);
    }

    @Override
    public boolean equals(Object obj) { //TODO: do I need this?
        if (obj instanceof Position) {
            if (((Position)obj).getLine() == this.line && ((Position)obj).getRow() == this.row)
                return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.line*10 + this.row;
    }

    @Override
    public String toString() {
        return "row: " + this.row + " line: " + this.line;
    }
}
