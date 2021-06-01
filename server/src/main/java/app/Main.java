package app;

import collection.CollectionManager;
import collection.CollectionManagerImpl;
import command.*;
import command_manager.CommandManager;
import command_manager.ServerCommandManager;
import connection.*;
import data.*;
import object.VehicleDisplayer;
import object.VehicleDisplayerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(ServerConnectionManager.class);

    public static void main(String[] args) {
        try {
            int port;
            try {
                port = Integer.parseInt(System.getenv("PORT"));
            } catch (Exception e) {
                System.out.println("Переменная окружения PORT не найдена");
                return;
            }
            AbstractFactory abstractFactory = new AbstractFactoryImpl();
            DAOFactory daoFactory = new PSQLDAOFactory();
            String url;
            try {
                url = System.getenv("URL");
            } catch (Exception e) {
                System.out.println("Переменная окружения URL не найдена");
                return;
            }
            String login;
            try {
                login = System.getenv("LOGIN");
            } catch (Exception e) {
                System.out.println("Переменная окружения LOGIN не найдена");
                return;
            }
            String password;
            try {
                password = System.getenv("PASSWORD");
            } catch (Exception e) {
                System.out.println("Переменная окружения PASSWORD не найдена");
                return;
            }
            daoFactory.start(url, login, password);
            VehicleDAO vehicleDAO = new PSQLVehicleDAO(daoFactory, abstractFactory);
            CollectionManager repository = new CollectionManagerImpl(vehicleDAO);
            UsersDAO usersDAO = new PSQLUsersDAO(daoFactory, new SHA256CryptoModule());
            AuthModule authModule = new AuthModuleImpl(usersDAO);
            CommandManager commandManager = new ServerCommandManager(getCommands(repository, abstractFactory, authModule), abstractFactory, getVehicleCommands(), authModule);
            RequestReader requestReader = new RequestReaderImpl();
            ResponseSender responseSender = new ResponseSenderImpl();
            ConnectionManager connectionManager = new ServerConnectionManager(port, commandManager, requestReader, responseSender, abstractFactory);
            Server server = new ServerImpl(connectionManager, repository);
            server.run();
        } catch (NullPointerException e) {
            System.out.println("Сервер завершил работу.");
        } catch (Exception e) {
            System.out.println("Сервер завершил работу с ошибкой.");
            logger.error(Arrays.toString(e.getStackTrace()), e.getMessage());
        }
    }

    private static Set<String> getVehicleCommands() {
        Set<String> vehicleCommands = new HashSet<>();
        vehicleCommands.add("add");
        vehicleCommands.add("update");
        return vehicleCommands;
    }

    private static Map<String, Command> getCommands(CollectionManager collectionManager, AbstractFactory abstractFactory, AuthModule authModule) {
        VehicleDisplayer vehicleDisplayer = new VehicleDisplayerImpl();
        Map<String, Command> commandMap = new HashMap<>();
        commandMap.put("login", new LoginCommand(authModule, abstractFactory));
        commandMap.put("register", new RegisterCommand(authModule, abstractFactory));
        commandMap.put("info", new Info(collectionManager, abstractFactory));
        commandMap.put("help", new Help(abstractFactory));
        commandMap.put("show", new Show(collectionManager, abstractFactory, vehicleDisplayer));
        commandMap.put("add", new AddElement(collectionManager, abstractFactory));
        commandMap.put("update", new UpdateIDElement(collectionManager, abstractFactory));
        commandMap.put("remove_by_id", new RemoveByID(collectionManager, abstractFactory));
        commandMap.put("clear", new Clear(collectionManager, abstractFactory));
        commandMap.put("head", new Head(collectionManager, abstractFactory, vehicleDisplayer));
        commandMap.put("remove_head", new RemoveHead(collectionManager, abstractFactory, vehicleDisplayer));
        commandMap.put("min_by_distance_travelled", new MinByDistanceTravelled(collectionManager, abstractFactory, vehicleDisplayer));
        commandMap.put("print_ascending", new PrintAscending(collectionManager, abstractFactory, vehicleDisplayer));
        commandMap.put("remove_first", new RemoveFirst(collectionManager, abstractFactory));
        commandMap.put("print_field_ascending_number_of_wheels", new PrintFieldAscendingNumberOfWheels(collectionManager, abstractFactory));
        return commandMap;
    }


}
