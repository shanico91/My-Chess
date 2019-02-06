package com.company.Logic;

import com.company.Pieces.Cell;

public interface IObserver {

    void update (Cell cell, boolean isAdded);
}
