package communication;

import object.Vehicle;

public interface Request {
    RequestType getType();
    Vehicle getVehicle();
    String getArg();
    String getCommand();
    User getUser();
}
