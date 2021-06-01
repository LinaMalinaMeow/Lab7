package data;

import app.AbstractFactory;
import communication.User;
import exceptions.BuildException;
import exceptions.PersistentException;
import object.FuelType;
import object.Vehicle;
import object.VehicleBuilder;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

//PSQLVehicleDAO, PSQLUsersDAO - классы доступа к базе данных.
//можно добавлять Vehicle, удалять, изменять, получать все.

public class PSQLVehicleDAO implements VehicleDAO{

    private final DAOFactory daoFactory;
    private final AbstractFactory factory;

    public PSQLVehicleDAO(DAOFactory daoFactory, AbstractFactory factory) throws SQLException {
        this.daoFactory = daoFactory;
        this.factory = factory;
        create();
    }

    private void create() throws SQLException {
        String create = "CREATE TABLE IF NOT EXISTS vehicles (" +
                "id serial primary key," +
                "name varchar," +
                "coordinates_x float," +
                "coordinates_y float," +
                "creationdate date," +
                "engine_power integer," +
                "number_of_wheels integer," +
                "distance_travelled integer," +
                "fuel_type varchar," +
                "username varchar)";
        Statement statement = daoFactory.createConnection().createStatement();
        statement.execute(create);
    }

    @Override
    public Collection<Vehicle> getVehicles() {
        try (Connection connection = daoFactory.createConnection()) {
            Statement statement = connection.createStatement();
            Collection<Vehicle> vehicles = new ArrayList<>();
            ResultSet r = statement.executeQuery("SELECT * FROM vehicles");
            while (r.next()) {
                VehicleBuilder builder = factory.getVehicleBuilder();
                try {
                    buildVehicle(r, builder);
                    vehicles.add(builder.buildId());
                } catch (BuildException e) {
                    throw new PersistentException("UNKNOWN", e.getMessage());
                }
            }
            return vehicles;
        } catch (SQLException e) {
            throw new PersistentException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
    }

    @Override
    public Vehicle getVehicle(int id) {
        try (Connection connection = daoFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM vehicles WHERE id=?");
            Vehicle vehicle = null;
            statement.setInt(1, id);
            ResultSet r = statement.executeQuery();
            while (r.next()) {
                VehicleBuilder builder = factory.getVehicleBuilder();
                try {
                    buildVehicle(r, builder);
                    vehicle = builder.buildId();
                } catch (BuildException e) {
                    throw new PersistentException("UNKNOWN", e.getMessage());
                }
            }
            return vehicle;
        } catch (SQLException e) {
            throw new PersistentException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
    }

    private void buildVehicle(ResultSet r, VehicleBuilder builder) throws SQLException {
        builder.setId(r.getInt(1))
                .setName(r.getString(2))
                .setX(r.getInt(3))
                .setY(r.getFloat(4))
                .setCreationDate(r.getDate(5))
                .setEnginePower(r.getInt(6))
                .setNumberOfWheels(r.getInt(7));
        Integer distanceTravelled = r.getInt(8);
        builder.setDistanceTravelled(distanceTravelled);
        String fuelType = (r.getString(9));
        builder.setFuelType(fuelType == null ? null : FuelType.valueOf(fuelType));
        builder.setUsername(r.getString(10));
    }


    @Override
    public void insertVehicle(Vehicle vehicle) {
        try (Connection connection = daoFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO vehicles VALUES (default, " +
                    "?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, vehicle.getName());
            statement.setFloat(2, vehicle.getCoordinates().getX());
            statement.setFloat(3, vehicle.getCoordinates().getY());
            statement.setDate(4, new java.sql.Date(vehicle.getCreationDate().getTime()));
            statement.setInt(5, vehicle.getEnginePower());
            statement.setInt(6, vehicle.getNumberOfWheels());
            if(vehicle.getDistanceTravelled() == null)
                statement.setNull(7, Types.INTEGER);
            else
                statement.setInt(7, vehicle.getDistanceTravelled());
            if(vehicle.getFuelType() == null)
                statement.setNull(8, Types.VARCHAR);
            else
                statement.setString(8, vehicle.getFuelType().toString());
            statement.setString(9, vehicle.getUsername());
            statement.execute();
            ResultSet set = statement.getGeneratedKeys();
            if (set.next()) {
                int id = set.getInt(set.findColumn("id"));
                vehicle.setId(id);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistentException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
    }

    @Override
    public boolean updateVehicle(Vehicle vehicle) {
        try (Connection connection = daoFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE vehicles SET " +
                    "name = ?," +
                    "coordinates_x = ?," +
                    "coordinates_y = ?," +
                    "creationdate = ?," +
                    "engine_power = ?," +
                    "number_of_wheels = ?," +
                    "distance_travelled = ?," +
                    "fuel_type = ?" +
                    "WHERE id = ?");
            statement.setString(1, vehicle.getName());
            statement.setFloat(2, vehicle.getCoordinates().getX());
            statement.setFloat(3, vehicle.getCoordinates().getY());
            statement.setDate(4, new java.sql.Date(vehicle.getCreationDate().getTime()));
            statement.setInt(5, vehicle.getEnginePower());
            statement.setInt(6, vehicle.getNumberOfWheels());
            if(vehicle.getDistanceTravelled() == null)
                statement.setNull(7, Types.INTEGER);
            else
                statement.setInt(7, vehicle.getDistanceTravelled());
            if(vehicle.getFuelType() == null)
                statement.setNull(8, Types.VARCHAR);
            else
                statement.setString(8, vehicle.getFuelType().toString());
            statement.setInt(9, vehicle.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new PersistentException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
    }

    @Override
    public boolean deleteVehicle(Vehicle vehicle) {
        try (Connection connection = daoFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM vehicles WHERE id=?");
            statement.setInt(1, vehicle.getId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new PersistentException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
    }

    @Override
    public boolean deleteVehicleByID(int id) {
        try (Connection connection = daoFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM vehicles WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistentException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
    }

    @Override
    public boolean deleteVehicles(User user) {
        try (Connection connection = daoFactory.createConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM vehicles WHERE username = ?");
            statement.setString(1, user.getLogin());
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new PersistentException(String.valueOf(e.getErrorCode()), e.getMessage());
        }
    }
}
