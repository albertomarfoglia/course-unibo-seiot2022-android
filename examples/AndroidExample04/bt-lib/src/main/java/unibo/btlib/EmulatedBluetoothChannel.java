package unibo.btlib;

import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import unibo.btlib.utils.C;

public final class EmulatedBluetoothChannel extends BluetoothChannel {

    EmulatedBluetoothChannel(Socket socket){
        worker = new TcpWorker(socket);
        new Thread(worker).start();
    }

    @Override
    public String getRemoteDeviceName(){
        return "Arduino through PC";
    }

    /**
     *
     */
    private class TcpWorker implements ExtendedRunnable {
        private final Socket socket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        TcpWorker(Socket socket) {
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
            while (true) {
                try {
                    DataInputStream input = new DataInputStream(inputStream);

                    StringBuilder readbuffer = new StringBuilder();

                    byte inputByte;

                    while ((inputByte = input.readByte()) != 0) {
                        char chr = (char) inputByte;
                        if(chr != C.message.MESSAGE_TERMINATOR){
                            readbuffer.append(chr);
                        } else {
                            String inputString = readbuffer.toString();
                            Message receivedMessage = getBTChannelHandler().obtainMessage(
                                C.channel.MESSAGE_RECEIVED,
                                inputString.getBytes().length,
                                -1,
                                inputString.getBytes()
                            );
                            receivedMessage.sendToTarget();

                            readbuffer = new StringBuilder();
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
