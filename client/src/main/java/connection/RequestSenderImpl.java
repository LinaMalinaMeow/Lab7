package connection;

import communication.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class RequestSenderImpl implements RequestSender {

    @Override
    public void send(Request request, SocketAddress socketAddress, DatagramSocket datagramSocket) throws IOException {
        byte[] commandInBytes = writeObject(request);
        DatagramPacket datagramPacket = new DatagramPacket(commandInBytes, commandInBytes.length, socketAddress);
        datagramSocket.send(datagramPacket);
    }

    private byte[] writeObject(Request request) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(request);
        return outputStream.toByteArray();
    }
}
