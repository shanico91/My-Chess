package com.company.Pieces;

import com.company.Logic.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class Cell implements IObservable {

    public Color color;
    public Position position;
    private List<IObserver> subscribers;
    protected CheckMoves[] checkMyvalidMoves;

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

    public HashSet<Position> validMoves(IPosGetter g){
        HashSet<Position> ans = new HashSet<>();
        for(CheckMoves check : checkMyvalidMoves){
            ans.addAll(check.validMoves(g, this.position, this.color));
        }
        return ans;
    }

    public void remove() {
        notify(false);
    }
}
