package it.unibo.isi.seeiot.androidexample02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import it.unibo.isi.seeiot.androidexample02.agents.Agent;
import it.unibo.isi.seeiot.androidexample02.agents.AgentFactory;
import it.unibo.isi.seeiot.androidexample02.agents.AgentType;
import it.unibo.isi.seeiot.androidexample02.asynctask.AgentAsAsyncTask;

public class MainActivity extends AppCompatActivity {
    private TextView counterLabel;
    private Button upButton, downButton, stopButton;
    private Agent agent;
    private AgentAsAsyncTask agentAsAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        //createAndStartAgent();
        createAndStartAsyncTask();
    }

    private void initUI() {
        counterLabel = findViewById(R.id.counterLabel);

        upButton = findViewById(R.id.upButton);
        upButton.setOnClickListener(view1 -> upButtonOnClickEvent());

        downButton = findViewById(R.id.downButton);
        downButton.setOnClickListener(view -> downButtonOnClickEvent());

        stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(view -> stopButtonOnClickEvent());

        upButton.setEnabled(false);
        downButton.setEnabled(true);
        stopButton.setEnabled(true);
    }

    private void upButtonOnClickEvent() {
//        agent.countUp();
        agentAsAsyncTask.countUp();
        upButton.setEnabled(false);
        downButton.setEnabled(true);
    }

    private void downButtonOnClickEvent() {
//        agent.countDown();
        agentAsAsyncTask.countDown();
        upButton.setEnabled(true);
        downButton.setEnabled(false);
    }

    private void stopButtonOnClickEvent() {
//        agent.stop();
        agentAsAsyncTask.stop();
        upButton.setEnabled(false);
        downButton.setEnabled(false);
        stopButton.setEnabled(false);
    }

    private void createAndStartAgent() {
        agent = AgentFactory.create(AgentType.WRONG, counterLabel);
//        agent = AgentFactory.create(AgentType.RUN_ON_UI, counterLabel, this);
//        agent = AgentFactory.create(AgentType.HANDLER, counterLabel);
        new Thread(agent).start();
    }

    private void createAndStartAsyncTask() {
        agentAsAsyncTask = new AgentAsAsyncTask(counterLabel);
        agentAsAsyncTask.execute();
    }
}