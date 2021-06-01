package connection;

import communication.Request;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public interface RequestSender {
    void send(Request request, SocketAddress socketAddress, DatagramSocket datagramSocket) throws IOException;
}
