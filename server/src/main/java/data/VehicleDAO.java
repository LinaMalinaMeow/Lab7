package data;

import communication.User;
import object.Vehicle;

import java.util.Collection;

public interface VehicleDAO {
    Collection<Vehicle> getVehicles();
    Vehicle getVehicle(int id);
    void insertVehicle(Vehicle route);
    boolean updateVehicle(Vehicle route);
    boolean deleteVehicle(Vehicle route);
    boolean deleteVehicleByID(int id);
    boolean deleteVehicles(User user);
}
