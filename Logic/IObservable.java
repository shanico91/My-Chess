package com.company.Logic;

public interface IObservable {

    void subscribe(IObserver observer);
    void unsubscribe(IObserver observer);
    void notify(boolean isAdded);

}
