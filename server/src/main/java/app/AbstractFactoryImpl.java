package app;

import communication.Response;
import communication.ResponseImpl;
import object.VehicleBuilder;
import object.VehicleBuilderImpl;

public class AbstractFactoryImpl implements AbstractFactory{
    @Override
    public Response getResponse(boolean successful, String message) {
        return new ResponseImpl(message, successful);
    }

    @Override
    public VehicleBuilder getVehicleBuilder() {
        return new VehicleBuilderImpl();
    }
}
