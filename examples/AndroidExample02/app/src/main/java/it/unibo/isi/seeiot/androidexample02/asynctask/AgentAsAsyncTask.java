package it.unibo.isi.seeiot.androidexample02.asynctask;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.TextView;

import java.util.Locale;

public class AgentAsAsyncTask extends AsyncTask<Object, Integer, Void> {
    private volatile boolean up = true;
    private volatile boolean stop = false;

    @SuppressLint("StaticFieldLeak")
    private TextView label;

    public AgentAsAsyncTask(final TextView label){
        this.label = label;
    }

    @Override
    protected Void doInBackground(Object... objs) {
        int cnt = 0;

        while(!stop){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(up){
                cnt = cnt + 1;
            } else {
                cnt = cnt - 1;
            }

            publishProgress(cnt);
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... value) {
        label.setText(String.format(Locale.ITALY, "%d", value[0]));
    }

    public void countUp(){
        up = true;
    }

    public void countDown(){
        up = false;
    }

    public void stop(){
        stop = true;
    }
}
