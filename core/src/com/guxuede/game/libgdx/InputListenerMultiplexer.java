package com.guxuede.game.libgdx;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Array;

/**
 * Created by guxuede on 2016/10/7 .
 */
public class InputListenerMultiplexer implements EventListener {

    private Array<EventListener> listeners = new Array(4);

    public InputListenerMultiplexer () {
    }

    public InputListenerMultiplexer (EventListener... listeners) {
        for (int i = 0; i < listeners.length; i++)
            this.listeners.add(listeners[i]);
    }

    public void addListener (int index, EventListener listener) {
        if (listener == null) throw new NullPointerException("listener cannot be null");
        listeners.insert(index, listener);
    }

    public void removeListener (int index) {
        listeners.removeIndex(index);
    }

    public void addListener (EventListener listener) {
        if (listener == null) throw new NullPointerException("listener cannot be null");
        listeners.add(listener);
    }

    public void removeListener (EventListener listener) {
        listeners.removeValue(listener, true);
    }

    /** @return the number of listeners in this multiplexer */
    public int size () {
        return listeners.size;
    }

    public void clear () {
        listeners.clear();
    }

    public void setListeners (Array<EventListener> listeners) {
        this.listeners = listeners;
    }

    public Array<EventListener> getListeners () {
        return listeners;
    }


    @Override
    public boolean handle(Event event) {
        for (int i = 0, n = listeners.size; i < n; i++)
            if (listeners.get(i).handle(event)) return true;
        return false;
    }
}
