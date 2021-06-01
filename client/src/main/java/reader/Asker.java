package reader;

import communication.User;
import object.Vehicle;

public interface Asker {
    Vehicle createVehicle();
    User readUser();
}
