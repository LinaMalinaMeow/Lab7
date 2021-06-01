package object;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Vehicle implements Comparable<Vehicle>, Serializable {
    private int id;//Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
    // Значение этого поля должно генерироваться автоматически
    private String name;//Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates;//Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer enginePower; //Поле не может быть null, Значение поля должно быть больше 0
    private Integer numberOfWheels; //Поле не может быть null, Значение поля должно быть больше 0
    private Integer distanceTravelled; //Поле может быть null, Значение поля должно быть больше 0
    private FuelType fuelType; //Поле может быть null
    private String username;

    public Vehicle(int id, String name, Coordinates coordinates,
                   Date creationDate, Integer enginePower,
                   Integer numberOfWheels, Integer distanceTravelled,
                   FuelType fuelType) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.distanceTravelled = distanceTravelled;
        this.fuelType = fuelType;
    }

    public Vehicle(String name,
                   Coordinates coordinates,
                   Integer enginePower,
                   Integer numberOfWheels,
                   Integer distanceTravelled,
                   FuelType fuelType) {
        this.id = new Random().nextInt(Integer.MAX_VALUE);
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date();
        this.enginePower = enginePower;
        this.numberOfWheels = numberOfWheels;
        this.distanceTravelled = distanceTravelled;
        this.fuelType = fuelType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate() {
        this.creationDate = new Date();
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public void setDistanceTravelled(int distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Integer getEnginePower() {
        return enginePower;
    }

    public Integer getNumberOfWheels() {
        return numberOfWheels;
    }

    public Integer getDistanceTravelled() {
        return distanceTravelled;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public String getFuelTypeString() {
        if(this.fuelType == null){
            return "-";
        }
        /*switch (this.fuelType) {
            case DIESEL:
                return "Дизель";
            case ALCOHOL:
                return "Алкоголь";
            case KEROSENE:
                return "Керосин";
            case MANPOWER:
                return "ЧеловеческаяСила";
            case ANTIMATTER:
                return "Антиматерия";
            default:
                return "Неизвестно";
        }*/
        return this.fuelType.toString();
    }

    /**
     *
     * @return creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public int compareTo(Vehicle vehicle) {
        return (int) (this.getCoordinates().getX() - vehicle.getCoordinates().getX()) == 0 ? (int) (this.getCoordinates().getY() - vehicle.getCoordinates().getY()) : (int) (this.getCoordinates().getX() - vehicle.getCoordinates().getX());
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return "\n\tID: " + id +
                "\n\tИмя: " + name +
                "\n\tКоординаты: (" + coordinates  +
                ")\n\tМощность двигателя: " + enginePower +
                "\n\tКолёс: " + numberOfWheels +
                "\n\tПроехано: " + distanceTravelled +
                "\n\tТип топлива: " +fuelType +
                "\n\tВладелец: " + username +
                "\n\tВремя создания" + format.format(creationDate);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    //c://jasghdjashg/BANANA_LAB5
}