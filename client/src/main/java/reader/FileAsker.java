package reader;

import communication.User;
import communication.UserImpl;
import object.Coordinates;
import object.FuelType;
import object.Vehicle;

import java.util.Scanner;

public class FileAsker extends AbstractAsker{
    public FileAsker(Scanner userScanner) {
        super(userScanner);
    }

    /**
     *
     * @return creates a car
     *
     */
    @Override
    public Vehicle createVehicle() {
        String name = userScanner.nextLine();
        if ((name == null) || (name.equals(" ")))
            throw new NullPointerException("Поле не может быть null, Строка не может быть пустой");
        Float coordinateX = userScanner.nextFloat();
        if ((coordinateX < -836) || (coordinateX == null)) throw new NullPointerException();
        Float coordinateY = userScanner.nextFloat();
        if (coordinateY == null) throw new NullPointerException("Поле не может быть null");
        Coordinates coordinates = new Coordinates(coordinateX, coordinateY);

        // java.util.Date creationDate;

        Integer enginePower = userScanner.nextInt();
        if ((enginePower == null) || (enginePower < 0))
            throw new NullPointerException("Поле не может быть null, Значение поля должно быть больше 0");

        Integer numberOfWheels = userScanner.nextInt();
        if (numberOfWheels == null || numberOfWheels < 0)
            throw new NullPointerException("Значение поля должно быть больше 0");

        Integer distanceTravelled;
        try{
            distanceTravelled = userScanner.nextInt();
        }catch (Exception e){
            distanceTravelled = null;
            userScanner.nextLine();
        }

        if (distanceTravelled != null && distanceTravelled < 0)
            throw new NullPointerException("Значение поля должно быть больше 0");

        userScanner.nextLine();
        String fuelType;
        fuelType = userScanner.nextLine();
        FuelType type;
        switch (fuelType) {
            case "Дизель":
                type = FuelType.DIESEL;
                break;
            case "Алкоголь":
                type = FuelType.ALCOHOL;
                break;
            case "Керосин":
                type = FuelType.KEROSENE;
                break;
            case "ЧеловеческаяСила":
                type = FuelType.MANPOWER;
                break;
            case "Антиматерия":
                type = FuelType.ANTIMATTER;
                break;
            default:
                type = null;
        }
        System.out.println("Машина " + name + " была успешно создана!");
        return new Vehicle(name, coordinates, enginePower, numberOfWheels, distanceTravelled, type);
    }

    @Override
    public User readUser() {
        String username = userScanner.nextLine().trim();
        String password = userScanner.nextLine().trim();
        return new UserImpl(username, password);
    }
}
