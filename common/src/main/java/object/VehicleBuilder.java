package object;

import java.awt.*;
import java.util.Date;

//для удобного создания Vehicle(паттерн Builder).

public interface VehicleBuilder {
    VehicleBuilder setId(int id);
    VehicleBuilder setName(String name);
    VehicleBuilder setX(float x);
    VehicleBuilder setY(float y);
    VehicleBuilder setCreationDate(Date date);
    VehicleBuilder setEnginePower(int enginePower);
    VehicleBuilder setNumberOfWheels(int numberOfWheels);
    VehicleBuilder setDistanceTravelled(Integer distanceTravelled);
    VehicleBuilder setFuelType(FuelType fuelType);
    VehicleBuilder setUsername(String string);
    Vehicle build();
    Vehicle buildId();
}
