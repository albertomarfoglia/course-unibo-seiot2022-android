package it.unibo.isi.seeiot.androidexample02.agents;

import android.app.Activity;
import android.os.Looper;
import android.widget.TextView;

import it.unibo.isi.seeiot.androidexample02.agents.impl.AgentWithHandler;
import it.unibo.isi.seeiot.androidexample02.agents.impl.AgentWithRunOnUiThread;
import it.unibo.isi.seeiot.androidexample02.agents.impl.AgentWithWrongThreadException;
import it.unibo.isi.seeiot.androidexample02.handlers.MainUiHandler;

public class AgentFactory {
    public static Agent createAgentWithHandler(TextView view) {
        return new AgentWithHandler(new MainUiHandler(Looper.getMainLooper(), view));
    }

    public static Agent createAgentWithRunOnUiThread(TextView view, Activity activity) {
        return new AgentWithRunOnUiThread(activity, view);
    }

    public static Agent createAgentWithWrongThreadException(TextView view) {
        return new AgentWithWrongThreadException(view);
    }

    public static Agent create(AgentType type, TextView view) {
        return create(type, view, null);
    }

    public static Agent create(AgentType type, TextView view, Activity activity) {
        switch (type) {
            case HANDLER: return createAgentWithHandler(view);
            case RUN_ON_UI: return createAgentWithRunOnUiThread(view, activity);
            case WRONG: return createAgentWithWrongThreadException(view);
            default:
                throw new IllegalStateException("Invalid agent type");
        }
    }
}
