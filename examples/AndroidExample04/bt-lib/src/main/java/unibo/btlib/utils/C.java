package unibo.btlib.utils;

public class C {
    public static final String LIB_TAG = "BluetoothLib";

    public class channel {
        public static final int MESSAGE_RECEIVED = 0;
        public static final int MESSAGE_SENT = 1;
    }

    public class message {
        public static final char MESSAGE_TERMINATOR = '\n';
    }

    public class emulator {
        public static final String HOST_IP = "10.0.2.2";
        public static final int HOST_PORT = 8080;
    }
}
