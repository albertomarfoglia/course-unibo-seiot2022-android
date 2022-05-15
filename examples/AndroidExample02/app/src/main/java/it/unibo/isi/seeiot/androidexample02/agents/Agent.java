package it.unibo.isi.seeiot.androidexample02.agents;

public interface Agent extends Runnable {
    void countUp();
    void countDown();
    void stop();
}
