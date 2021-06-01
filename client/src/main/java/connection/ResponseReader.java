package connection;

import communication.Response;

import java.io.IOException;
import java.net.DatagramSocket;

public interface ResponseReader {
    Response readResponse(DatagramSocket datagramSocket, int bufferSize, int timeout) throws IOException, ClassNotFoundException;
}
