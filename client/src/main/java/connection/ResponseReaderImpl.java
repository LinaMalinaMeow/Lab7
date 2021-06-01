package connection;

import communication.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ResponseReaderImpl implements ResponseReader {
    @Override
    public Response readResponse(DatagramSocket datagramSocket, int bufferSize, int timeout) throws IOException, ClassNotFoundException {
        byte[] answerInBytes = new byte[bufferSize];
        DatagramPacket datagramPacket = new DatagramPacket(answerInBytes, answerInBytes.length);
        datagramSocket.setSoTimeout(timeout);
        datagramSocket.receive(datagramPacket);
        ByteArrayInputStream byteStream = new ByteArrayInputStream(answerInBytes);
        ObjectInputStream obs = new ObjectInputStream(byteStream);
        return (Response) obs.readObject();
    }
}
