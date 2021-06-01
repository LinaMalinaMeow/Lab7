package object;


import java.util.Date;

public class VehicleBuilderImpl implements VehicleBuilder{

    private int id;
    private String name;
    private float x;
    private float y;
    private Date creationDate;
    private int enginePower;
    private int numberOfWheels;
    private Integer distanceTravelled;
    private FuelType fuelType;
    private String username;

    @Override
    public VehicleBuilder setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public VehicleBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public VehicleBuilder setX(float x) {
        this.x = x;
        return this;
    }

    @Override
    public VehicleBuilder setY(float y) {
        this.y = y;
        return this;
    }

    @Override
    public VehicleBuilder setCreationDate(Date date) {
        this.creationDate = date;
        return this;
    }

    @Override
    public VehicleBuilder setEnginePower(int enginePower) {
        this.enginePower = enginePower;
        return this;
    }

    @Override
    public VehicleBuilder setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
        return this;
    }

    @Override
    public VehicleBuilder setDistanceTravelled(Integer distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
        return this;
    }

    @Override
    public VehicleBuilder setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
        return this;
    }

    @Override
    public VehicleBuilder setUsername(String string) {
        this.username = string;
        return this;
    }

    @Override
    public Vehicle build() {
        Vehicle vehicle = new Vehicle(0, name, new Coordinates(x, y), creationDate, enginePower, numberOfWheels, distanceTravelled, fuelType);
        vehicle.setUsername(username);
        return vehicle;
    }

    @Override
    public Vehicle buildId() {
        Vehicle vehicle = new Vehicle(id, name, new Coordinates(x, y), creationDate, enginePower, numberOfWheels, distanceTravelled, fuelType);
        vehicle.setUsername(username);
        return vehicle;
    }
}
