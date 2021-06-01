package connection;

import collection.CollectionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

public class ServerImpl implements Server {
    private final ConnectionManager connectionManager;
    private final CollectionManager collectionManager;


    public ServerImpl(ConnectionManager connectionManager, CollectionManager collectionManager) {
        this.connectionManager = connectionManager;
        this.collectionManager = collectionManager;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        checkConsole(bufferedReader);
        while (true) {
            connectionManager.manageConnection();
        }
    }

    private void checkConsole(BufferedReader bufferedReader) {
        new Thread(() -> {
            while (true)
                try {
                    if (bufferedReader.ready()) {
                        String line = bufferedReader.readLine();
                        String[] lines = line.trim().split("\\s+");
                        try {
                            if (lines[0].trim().equalsIgnoreCase("exit")) {
                                System.exit(0);
                            } else
                                System.err.println("Неизвестная команда. Поддерживаемые команды: exit.");
                        } catch (Exception e) {
                            System.err.println("Ошибка при чтении команды: " + e.getMessage());
                        }
                    }
                } catch (Exception ignored) {
                }
        }).start();
    }
}
