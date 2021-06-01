package connection;

import communication.Response;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Selector;

public interface ResponseSender {
    void sendResponse(Response response, SocketAddress address, DatagramChannel channel) throws IOException;
}
