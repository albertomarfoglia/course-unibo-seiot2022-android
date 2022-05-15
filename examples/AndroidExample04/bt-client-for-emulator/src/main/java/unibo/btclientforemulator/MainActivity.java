package unibo.btclientforemulator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import unibo.btlib.BluetoothChannel;
import unibo.btlib.ConnectToEmulatedBluetoothServerTask;
import unibo.btlib.ConnectionTask;
import unibo.btlib.EmulatedBluetoothChannel;

public class MainActivity extends AppCompatActivity {
    private BluetoothChannel btChannel;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        findViewById(R.id.connectBtn).setOnClickListener(l -> connectToBTServer());

        findViewById(R.id.sendBtn).setOnClickListener(l -> {
            final String message = ((EditText)findViewById(R.id.editText)).getText().toString();
            sendMessage(message);
            ((EditText)findViewById(R.id.editText)).setText("");
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        btChannel.close();
    }

    private void connectToBTServer() {
        new ConnectToEmulatedBluetoothServerTask(new ConnectionTask.EventListener() {
            @Override
            public void onConnectionActive(final BluetoothChannel channel) {
                ((TextView) findViewById(R.id.statusLabel)).setText(String.format(
                    "Status : connected to server on device %s",
                    "host machine"
                ));

                findViewById(R.id.connectBtn).setEnabled(false);

                btChannel = channel;
                btChannel.registerListener(new EmulatedBluetoothChannel.Listener() {
                    @Override
                    public void onMessageReceived(String receivedMessage) {
                        ((TextView) findViewById(R.id.chatLabel)).append(String.format(
                            "> [RECEIVED from %s] %s\n",
                            btChannel.getRemoteDeviceName(),
                            receivedMessage
                        ));
                    }

                    @Override
                    public void onMessageSent(String sentMessage) {
                        ((TextView) findViewById(R.id.chatLabel)).append(String.format(
                            "> [SENT to %s] %s\n",
                            btChannel.getRemoteDeviceName(),
                            sentMessage
                        ));
                    }
                });
            }

            @Override
            public void onConnectionCanceled() {
                ((TextView) findViewById(R.id.statusLabel)).setText(String.format(
                    "Status : unable to connect, device %s not found!",
                    "host machine"
                ));
            }
        }).execute();
    }

    private void sendMessage(final String message) {
        new Thread(() -> btChannel.sendMessage(message)).start();
    }
}
