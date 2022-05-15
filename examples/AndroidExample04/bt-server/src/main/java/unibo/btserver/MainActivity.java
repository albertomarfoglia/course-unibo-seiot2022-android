package unibo.btserver;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import unibo.btlib.BluetoothChannel;
import unibo.btlib.CommChannel;
import unibo.btlib.RealBluetoothChannel;
import unibo.btlib.BluetoothServer;
import unibo.btserver.utils.C;

/**
 * Bluetooth server implementation for physical device (@RealBluetoothChannel).
 */
public class MainActivity extends AppCompatActivity {
    private BluetoothServer btServer;
    private final BluetoothServerListener btServerListener = new BluetoothServerListener();
    private final List<RealBluetoothChannel> bluetoothChannelList = new ArrayList<>();
    // @BluetoothAdapter represents the entry-point for every interaction based on bluetooth.
    private final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(btAdapter != null && !btAdapter.isEnabled()) {
            Log.i(C.APP_LOG_TAG, "Request the enabling of the BT to the system");
            startActivityForResult(
                new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),
                C.bluetooth.ENABLE_BT_REQUEST
            );
        }

        initUI();
    }

    private void initUI() {
        findViewById(R.id.startButton).setOnClickListener(l -> {
            if(btAdapter != null) {
                startBluetoothServer();
            } else {
                Toast.makeText(this, "Bluetooth is not supported!", Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.sendBtn).setOnClickListener(l -> {
            final String messageToBeSent = ((EditText)findViewById(R.id.editText))
                    .getText().toString();
            for(BluetoothChannel ch : bluetoothChannelList) {
                ch.sendMessage(messageToBeSent);
            }
            ((EditText)findViewById(R.id.editText)).setText("");
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        btServer.terminate();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
                                    @Nullable final Intent data) {
        if(requestCode == C.bluetooth.ENABLE_BT_REQUEST && resultCode == RESULT_OK) {
            Log.d(C.APP_LOG_TAG, "Bluetooth enabled!");
        }

        if(requestCode == C.bluetooth.ENABLE_BT_REQUEST && resultCode == RESULT_CANCELED) {
            Log.d(C.APP_LOG_TAG, "Bluetooth not enabled!");
        }
    }

    private void startBluetoothServer() {
        btServer = new BluetoothServer(
                C.bluetooth.BT_SERVER_UUID,
                C.bluetooth.BT_SERVER_NAME,
                btServerListener
        );
        btServer.start();
    }

    /**
     *
     */
    class BluetoothServerListener implements BluetoothServer.Listener {

        @Override
        public void onServerActive() {
            ((TextView) findViewById(R.id.statusLabel)).setText(getString(R.string.server_started_text));
            findViewById(R.id.startButton).setEnabled(false);
        }

        /**
         * Callback performed when a client connects to the server
         * @param btChannel wrap the socket used to communicate with the client
         */
        @Override
        public void onConnectionAccepted(final CommChannel btChannel) {
            // add the event to the UI queue
            runOnUiThread(() -> ((TextView) findViewById(R.id.statusLabel))
                    .setText(getString(R.string.client_connected_text)));


            btChannel.registerListener(new RealBluetoothChannel.Listener() {
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

            bluetoothChannelList.add((RealBluetoothChannel) btChannel);
        }
    }
}
