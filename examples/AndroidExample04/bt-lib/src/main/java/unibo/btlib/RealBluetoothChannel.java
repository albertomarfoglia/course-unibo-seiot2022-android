package unibo.btlib;

import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import unibo.btlib.utils.C;

public final class RealBluetoothChannel extends BluetoothChannel {

    private final String remoteDeviceName;

    RealBluetoothChannel(BluetoothSocket socket){
        remoteDeviceName = socket.getRemoteDevice().getName();
        worker = new BluetoothWorker(socket);
        new Thread(worker).start();
    }

    @Override
    public String getRemoteDeviceName(){
        return remoteDeviceName;
    }


    /**
     * Handles the connection with a single client socket. The exchanged data (sent and received)
     * are enclosed within messages sent to the Handler so that the MainThread (MainLooper)
     * can take action (display info messages).
     **/
    class BluetoothWorker implements ExtendedRunnable {
        private final BluetoothSocket socket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        BluetoothWorker(BluetoothSocket socket) {
            this.socket = socket;

            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {
                Log.e(C.LIB_TAG, "Error occurred when creating input stream", e);
            }
            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(C.LIB_TAG, "Error occurred when creating output stream", e);
            }

            inputStream = tmpIn;
            outputStream = tmpOut;
        }

        public void run() {
             while(true) {
                try {
                    DataInputStream input = new DataInputStream(inputStream);

                    StringBuilder readBuffer = new StringBuilder();

                    byte inputByte;

                    while ((inputByte = input.readByte()) != 0) {
                        char chr = (char) inputByte;
                        if(chr != C.message.MESSAGE_TERMINATOR){
                            readBuffer.append(chr);
                        } else {
                            String inputString = readBuffer.toString();
                            Message receivedMessage = getBTChannelHandler().obtainMessage(
                                C.channel.MESSAGE_RECEIVED,
                                inputString.getBytes().length,
                                -1,
                                inputString.getBytes()
                            );
                            receivedMessage.sendToTarget();
                            readBuffer = new StringBuilder();
                        }
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                byte[] bytesToBeSent = Arrays.copyOf(bytes, bytes.length+1);
                bytesToBeSent[bytesToBeSent.length -1] = C.message.MESSAGE_TERMINATOR;

                outputStream.write(bytesToBeSent);

                Message writtenMsg = getBTChannelHandler().obtainMessage(
                        C.channel.MESSAGE_SENT,
                        -1,
                        -1,
                        bytes
                );
                writtenMsg.sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                Log.e(C.LIB_TAG, "Could not close the connect socket", e);
            }
        }
    }
}
