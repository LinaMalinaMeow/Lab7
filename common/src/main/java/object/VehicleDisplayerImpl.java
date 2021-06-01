package object;

import java.text.SimpleDateFormat;

public class VehicleDisplayerImpl implements VehicleDisplayer{
    @Override
    public String displayVehicle(Vehicle vehicle) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return  "\n\tID: " + vehicle.getId() +
                "\n\tИмя: " + vehicle.getName() +
                "\n\tКоординаты: (" + vehicle.getCoordinates().getX() + ", " + vehicle.getCoordinates().getY() +
                ")\n\tМощность двигателя: " + vehicle.getEnginePower() +
                "\n\tКолёс: " + vehicle.getNumberOfWheels() +
                "\n\tПроехано: " + vehicle.getDistanceTravelled() +
                "\n\tТип топлива: " + vehicle.getFuelTypeString() +
                "\n\tВремя создания: " + format.format(vehicle.getCreationDate()) +
                "\n\tВладелец: " + vehicle.getUsername();
    }
}
