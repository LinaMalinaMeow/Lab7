package connection;

import communication.Request;
import communication.RequestImpl;
import connection.RequestReader;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

public class RequestReaderImpl implements RequestReader {

    public Request deserializeRequest(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream obs = new ObjectInputStream(byteStream);
        return (Request) obs.readObject();
    }
}
