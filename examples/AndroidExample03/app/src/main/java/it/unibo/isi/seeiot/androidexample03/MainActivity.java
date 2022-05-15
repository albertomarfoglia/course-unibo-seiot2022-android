package it.unibo.isi.seeiot.androidexample03;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Objects;
import java.util.Random;

import it.unibo.isi.seeiot.androidexample03.netutils.Http;

public class MainActivity extends AppCompatActivity {

    /*
     * To test the application, refer to the service: http://dummy.restapiexample.com/
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        findViewById(R.id.connectionStatusBtn).setOnClickListener(v -> {
            final ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            final NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();

            if(activeNetwork.isConnectedOrConnecting()){
                ((TextView)findViewById(R.id.statusLabel)).setText("Network is connected");
            }
        });

        findViewById(R.id.getBtn).setOnClickListener(v -> {
            tryHttpGet();
        });

        findViewById(R.id.postBtn).setOnClickListener(v -> {
            try {
                tryHttpPost();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void tryHttpGet(){
        final String url = "http://dummy.restapiexample.com/api/v1/employee/1";

        Http.get(url, response -> {
            if(response.code() == HttpURLConnection.HTTP_OK){
                try {
                    ((TextView)findViewById(R.id.resLabel)).setText(response.contentAsString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void tryHttpPost() throws JSONException {

        final String url = "http://dummy.restapiexample.com/api/v1/create";
        final String content = new JSONObject()
                .put("name","test" + new Random().nextLong())
                .put("salary","1000")
                .put("age","30").toString();

        Http.post(url, content.getBytes(), response -> {
            if(response.code() == HttpURLConnection.HTTP_OK){
                try {
                    ((TextView)findViewById(R.id.resLabel)).setText(response.contentAsString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
