package app;

import communication.Response;
import object.VehicleBuilder;

public interface AbstractFactory {
    Response getResponse(boolean successful, String message);
    VehicleBuilder getVehicleBuilder();
}
