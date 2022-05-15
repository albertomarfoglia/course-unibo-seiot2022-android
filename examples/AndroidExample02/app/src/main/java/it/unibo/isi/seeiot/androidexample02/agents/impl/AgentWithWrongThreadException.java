package it.unibo.isi.seeiot.androidexample02.agents.impl;

import android.util.Log;
import android.widget.TextView;

import java.util.Locale;

import it.unibo.isi.seeiot.androidexample02.agents.AbstractAgent;

public class AgentWithWrongThreadException extends AbstractAgent {
    private final TextView label;

    public AgentWithWrongThreadException(final TextView label){
        this.label = label;
    }

    /**
     * It violates the principle that only tha Main thread can performs
     * operations on the UI.
     * @param value: current count
     */
    @Override
    public void updateCounter(int value) {
        label.setText(String.format(Locale.ITALY, "%d", value));
    }
}
