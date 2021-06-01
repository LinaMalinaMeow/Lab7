package collection;

import communication.User;
import object.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public interface CollectionManager {
    void add(Vehicle vehicle, User user);
    boolean update(int id, Vehicle vehicle, User user);
    void clear(User user);
    Queue<Vehicle> getPriorityQueue();
    String info();
    Vehicle getVehicleMinDistanceTravelled();
    ArrayList<Vehicle> getAscVehicles();
    List<Integer> getAscNumberWheels();
    boolean removeById(int id, User user);
    Vehicle removeHead(User user);
    boolean removeFirst(User user);
}
