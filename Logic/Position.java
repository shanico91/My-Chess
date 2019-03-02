package com.company.Logic;

public class Position {
    private int column;
    private int line;

    public Position(){}
    public Position(int line, int column){
        this.setColumn(column);
        this.setLine(line);
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public Position clone(){
        return new Position(this.line, this.column);
    }

    @Override
    public boolean equals(Object obj) { // do I need this? yes!!
        if (obj instanceof Position) {
            if (((Position)obj).getLine() == this.line && ((Position)obj).getColumn() == this.column)
                return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.line*10 + this.column;
    }

    @Override
    public String toString() {
        return "column: " + this.column + " line: " + this.line;
    }
}
