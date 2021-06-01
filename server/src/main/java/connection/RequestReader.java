package connection;

import communication.Request;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;

public interface RequestReader {
    Request deserializeRequest(byte[] bytes) throws IOException, ClassNotFoundException;
}