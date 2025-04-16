package org.jblackjack.model;

import java.util.Observable;

public class CustomObservable extends Observable {
    @Override
    public synchronized void setChanged() {
        super.setChanged();
    }
}
