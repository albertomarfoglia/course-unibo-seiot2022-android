package it.unibo.isi.seeiot.androidexample02.agents.impl;

import android.app.Activity;
import android.widget.TextView;

import java.util.Locale;

import it.unibo.isi.seeiot.androidexample02.agents.AbstractAgent;

public class AgentWithRunOnUiThread extends AbstractAgent {
    private Activity activity;
    private TextView label;

    public AgentWithRunOnUiThread(final Activity activity, final TextView label){
        this.activity = activity;
        this.label = label;
    }

    /**
     * If the method is performed by a different thread other than the Main,
     * its execution will be queued in the Main Thread’s event queue.
     * NOTE: using multiple nested threads reduces the readability of the code
     * @param value: current count
     */
    @Override
    public void updateCounter(int value) {
        activity.runOnUiThread(() -> label.setText(String.format(Locale.ITALY, "%d", value)));
    }
}