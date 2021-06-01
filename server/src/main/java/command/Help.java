package command;


import app.AbstractFactory;
import communication.Response;
import communication.User;
import object.Vehicle;

public class Help extends AbstractCommand implements Command {
    private AbstractFactory factory;

    public Help(AbstractFactory factory){
        super("help","Вывести справку по доступным командам");
        this.factory = factory;
    }
    @Override
    public Response execute(String str, Vehicle vehicle, User user){
        return factory.getResponse(true,"help: вывести справку по доступным командам\n" +
                "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element}: добавить новый элемент в коллекцию\n" +
                "update id {element}: обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id: удалить элемент из коллекции по его id\n" +
                "clear: очистить коллекцию\n" +
                "execute_script file_name: считать и исполнить скрипт из указанного файла\n" +
                "remove_first: удалить первый элемент из коллекции\n" +
                "head: вывести первый элемент коллекции\n" +
                "remove_head: вывести первый элемент коллекции и удалить его\n" +
                "min_by_distance_travelled: вывести любой объект из коллекции, значение поля distanceTravelled которого является минимальным\n" +
                "print_ascending: вывести элементы коллекции в порядке возрастания\n" +
                "print_field_ascending_number_of_wheels: вывести значения поля numberOfWheels всех элементов в порядке возрастания\n" +
                "login: войти в систему\n" +
                "register: зарегистрироваться в системе");
    }
}
