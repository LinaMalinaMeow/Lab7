package connection;

import communication.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

public class ResponseSenderImpl implements ResponseSender{

    public void sendResponse(Response response, SocketAddress address, DatagramChannel channel) throws IOException {
        sendBytes(wrapResponse(response), address, channel);
    }

    public byte[] wrapResponse(Response object) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(object);
        return outputStream.toByteArray();
    }

    private void sendBytes(byte[] bytes, SocketAddress address, DatagramChannel channel) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        channel.send(byteBuffer, address);
    }
}
