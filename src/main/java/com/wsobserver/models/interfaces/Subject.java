package com.wsobserver.models.interfaces;

import com.wsobserver.models.interfaces.Observer;

public interface Subject {
    void addObserver(Observer o);
    void notifyObservers();
    void removeObserver(Observer o);
}


