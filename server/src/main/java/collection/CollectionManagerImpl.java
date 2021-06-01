package collection;

import communication.User;
import data.DataReader;
import data.DataWriter;
import data.VehicleDAO;
import exceptions.PersistentException;
import object.FuelType;
import object.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class CollectionManagerImpl implements CollectionManager{

    private PriorityQueue<Vehicle> vehiclePriorityQueue;
    private Date initializationDate;
    private LocalDate lastUpdate;
    private static final Logger logger = LoggerFactory.getLogger(CollectionManagerImpl.class);
    private final Lock lock;
    private final VehicleDAO vehicleDAO;

    //изменение в коллекции происходит только при успешном изменении в БД


    public CollectionManagerImpl(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
        this.vehiclePriorityQueue = new PriorityQueue<>();
        this.lock = new ReentrantLock(); //нужно для синхронизации доступа к коллекции/БД разных потоков
        loadCollection();
    }

    private void loadCollection() {
        try {
            vehiclePriorityQueue = new PriorityQueue<>(vehicleDAO.getVehicles());
            initializationDate = new Date();
            logger.debug("Коллекция загружена из файла.");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Ошибка при чтении коллекции: " + e.getMessage());
            System.exit(1);
        }
    }

    public PriorityQueue<Vehicle> getPriorityQueue() {
        return this.vehiclePriorityQueue;
    }

    @Override
    public String info() {
        lock();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        StringBuilder builder = new StringBuilder();
        builder.append("Тип коллекции : ").append(vehiclePriorityQueue.getClass()).append("\n");
        builder.append("Количество элементов в коллекции : ").append(vehiclePriorityQueue.size()).append("\n");
        builder.append("Дата создания: ").append(format.format(initializationDate)).append("\n");
        if (!(lastUpdate == null)) {
            builder.append("Дата последнего обновления : ").append(lastUpdate);
        } else {
            builder.append("Дата последнего обновления : ещё не обновлялась");
        }
        unlock();
        return builder.toString();
    }

    public ArrayList<Vehicle> getAscVehicles() {
        lock();
        ArrayList<Vehicle> ascVehicles = vehiclePriorityQueue.stream().sorted().collect(Collectors.toCollection(ArrayList::new));
        unlock();
        return ascVehicles;
    }

    public Vehicle getVehicleMinDistanceTravelled() {
        lock();
        //поиск минимальной дистанции из коллекции автомобилей
        int min = this.vehiclePriorityQueue.stream().map(Vehicle::getDistanceTravelled)//в метод map передаём ссылку на метод для отображения потока машин в поток Integer,
                // stream объектов vehicle переводим в stream Integer. Преобразуем поток автомобилей в поток distanceTravelled
                .mapToInt(x -> x).min().orElse(Integer.MAX_VALUE);// если вдруг минимумм не будет найден, то возвращается максимальное значение
        Vehicle vehicle = this.vehiclePriorityQueue.stream()
                .filter(x -> x.getDistanceTravelled() == min).findAny().orElse(null);
        unlock();
        return vehicle;
        // отбор всех автомобилей, у которых distanceTravelled является минимальным и получение любого автомобиля из этого списка
    }

    public List<Integer> getAscNumberWheels() {
        lock();
        List<Integer> integers = this.vehiclePriorityQueue.stream().map(Vehicle::getNumberOfWheels)//ссылка на метод
                .sorted().collect(Collectors.toList());
        unlock();
        return integers;
    }

    public void clear(User user) {
        lock();
        try {
            vehicleDAO.deleteVehicles(user);
            lastUpdate = LocalDate.now();
            vehiclePriorityQueue = vehiclePriorityQueue.stream().filter(x -> !x.getUsername().equals(user.getLogin())).collect(Collectors.toCollection(PriorityQueue::new));
        } catch (PersistentException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            unlock();
        }
    }

    public boolean removeById(int id, User user) {
        lock();
        try {
            //происходит отбор по id и получение автомобиля с таким id
            if(vehiclePriorityQueue.stream().anyMatch(x -> x.getId() == id && x.getUsername().equals(user.getLogin()))) {
                vehicleDAO.deleteVehicleByID(id);
                this.vehiclePriorityQueue = vehiclePriorityQueue.stream().filter(x -> x.getId() != id || !x.getUsername().equals(user.getLogin())).collect(Collectors.toCollection(PriorityQueue::new));
                lastUpdate = LocalDate.now();
                return true;
            } else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            unlock();
        }
    }

    @Override
    public boolean removeFirst(User user) {
        lock();
        lastUpdate = LocalDate.now();
        try {
            Vehicle vehicle = vehiclePriorityQueue.stream().filter(x-> x.getUsername().equals(user.getLogin())).max(Vehicle::compareTo).get();
            vehicleDAO.deleteVehicle(vehicle);
            vehiclePriorityQueue.remove(vehicle);
            return true;
        } catch (NoSuchElementException | PersistentException e) {
            return false;
        } finally {
            unlock();
        }
    }

    public Vehicle removeHead(User user) {
        lock();
        lastUpdate = LocalDate.now();
        try {
            Vehicle vehicle = vehiclePriorityQueue.stream().filter(x-> x.getUsername().equals(user.getLogin())).max(Vehicle::compareTo).get();
            vehicleDAO.deleteVehicle(vehicle);
            vehiclePriorityQueue.remove(vehicle);
            return vehicle;
        } catch (NoSuchElementException | PersistentException e) {
            return null;
        } finally {
            unlock();
        }
    }

    public void add(Vehicle vehicle, User user) {
        lock();
        try {
            lastUpdate = LocalDate.now();
            vehicle.setUsername(user.getLogin());
            vehicleDAO.insertVehicle(vehicle);
            this.vehiclePriorityQueue.add(vehicle);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        } finally {
            unlock();
        }

    }



    public boolean update(int id, Vehicle vehicle, User user) {
        lock();
        Vehicle has = this.vehiclePriorityQueue.stream().filter(x -> x.getId() == id && x.getUsername().equals(user.getLogin())).findAny().orElse(null);
        if (has == null) {
            unlock();
            return false;
        }
        try {
            vehicle.setId(id);
            vehicle.setUsername(user.getLogin());
            vehicleDAO.updateVehicle(vehicle);
            this.vehiclePriorityQueue.remove(has);
            this.vehiclePriorityQueue.add(vehicle);
        } catch (PersistentException e) {
            e.printStackTrace();
            unlock();
            return false;
        }
        unlock();
        return true;
    }

    private void lock() {
        try {
            while (!lock.tryLock()) {
                lock.wait(100);
            }
        } catch (InterruptedException ignored) {}
    }

    private void unlock() {
        lock.unlock();
    }
}
