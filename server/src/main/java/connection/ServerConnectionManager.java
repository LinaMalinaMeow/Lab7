package connection;

import app.AbstractFactory;
import command_manager.CommandManager;
import communication.Request;
import communication.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerConnectionManager implements ConnectionManager {

    private final int port;
    private final int CONNECTION_ATTEMPT_CNT = 100000;
    private final int DEFAULT_BUFFER_SIZE = 65536;
    private final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
    private final DatagramChannel channel;
    private final CommandManager commandManager;
    private final ResponseSender sender;
    private final RequestReader reader;
    private final AbstractFactory factory;
    private static final Logger logger = LoggerFactory.getLogger(ServerConnectionManager.class);
    private final ExecutorService readingPool = Executors.newFixedThreadPool(5);

    static class Client {
        byte[] bytes;
        SocketAddress address;

        public Client(byte[] bytes, SocketAddress address) {
            this.bytes = bytes;
            this.address = address;
        }

        public byte[] getBytes() {
            return bytes;
        }

        public SocketAddress getAddress() {
            return address;
        }
    }

    public ServerConnectionManager(int port, CommandManager commandManager, RequestReader reader, ResponseSender sender, AbstractFactory factory) throws IOException {
        this.port = port;
        this.commandManager = commandManager;
        this.reader = reader;
        this.sender = sender;
        this.factory = factory;
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        try {
            channel.bind(new InetSocketAddress(this.port));
        } catch (BindException e) {
            System.out.println("Порт уже используется другим приложением.");
            System.exit(1);
        }
    }

    private Client accept() throws IOException {
        SocketAddress address;
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        int cnt = 0;
        do {
            address = channel.receive(byteBuffer);
            cnt++;
        } while (address == null && cnt < CONNECTION_ATTEMPT_CNT);
        return new Client(buffer, address);
    }

    @Override
    public void manageConnection() {
        readRequest();
    }

    private void readRequest() {
        try {
            Client client = accept();
            readingPool.submit(() -> {
                try {
                    byte[] buffer = client.getBytes();
                    if (client.getAddress() == null)
                        return;
                    logger.debug("Соединение с клиентом установлено.");
                    Request request = reader.deserializeRequest(buffer);
                    handleRequest(request, client.getAddress());
                } catch (Exception e) {
                    logger.error("Ошибка во время чтения запроса.");
                }
            });
        } catch (IOException e) {
            logger.error("Ошибка IOE во время чтения запроса.");
        }
    }

    private void handleRequest(Request request, SocketAddress address) {
        new Thread(() -> {
            try {
                Response response = processRequest(request);
                sendResponse(response, address);
                logger.info("Запрос " + request + " успешно обработан.");
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("Ошибка " + e.getClass() + " при обработке запроса: " + request.toString());
                sendResponse(factory.getResponse(false, "Ошибка при обработке запроса"), address);
            }
        }).start();
    }

    private void sendResponse(Response response, SocketAddress address) {
        new Thread(() -> {
            try {
                sender.sendResponse(response, address, channel);
                logger.debug("Ответ клиенту отправлен.");
            } catch (IOException e) {
                logger.error("Ошибка при отправке ответа на запрос.");
            }
        }).start();
    }

    private Response processRequest(Request request) {
            switch (request.getType()) {
                case EXECUTE:
                    return commandManager.execute(request.getCommand(), request.getArg(), request.getVehicle(), request.getUser());
                case ASK_VEHICLE:
                    return commandManager.askVehicle(request.getCommand(), request.getUser());
                default:
                    return factory.getResponse(false, "Ошибка. Неизвестный тип запроса.");
            }
    }
}
