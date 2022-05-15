package unibo.btlib;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

public class BluetoothServer extends Thread {
    private volatile boolean stop = false;
    private BluetoothServerSocket btServerSocket;
    private Listener listener;

    /**
     *
     * @param uuid: necessary to identify the communication
     * @param name: generic channel name
     * @param eventListener: implements two callbacks (defined outside)
     */
    public BluetoothServer(final String uuid, final String name, final Listener eventListener) {
        try {
            // Returns the serverSocket to wait for connection requests
            btServerSocket = BluetoothAdapter.getDefaultAdapter()
                    .listenUsingRfcommWithServiceRecord(name, UUID.fromString(uuid));
            listener = eventListener;
            if(listener != null) {
                listener.onServerActive();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(!stop) {
            try {
                final BluetoothSocket socket = btServerSocket.accept(); // blocking call
                if(listener != null) {
                    listener.onConnectionAccepted(new RealBluetoothChannel(socket));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void terminate() {
        stop = true;
    }

    public interface Listener {
        void onServerActive();
        void onConnectionAccepted(final CommChannel btChannel);
    }
}
