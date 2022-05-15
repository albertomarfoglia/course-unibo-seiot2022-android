package unibo.btlib;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import unibo.btlib.utils.C;

public final class ConnectToEmulatedBluetoothServerTask extends ConnectionTask {
    private Socket socket;

    public ConnectToEmulatedBluetoothServerTask(final EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    protected Integer doInBackground(Void... unused) {
        try {
            socket = new Socket(InetAddress.getByName(C.emulator.HOST_IP), C.emulator.HOST_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close( );
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return CONNECTION_CANCELED;
        }
        connectedChannel = new EmulatedBluetoothChannel(socket);
        return CONNECTION_DONE;
    }
}
