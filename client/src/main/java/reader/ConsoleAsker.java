package reader;

import communication.User;
import communication.UserImpl;
import exceptions.WrongArgumentException;
import object.Coordinates;
import object.FuelType;
import object.Vehicle;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleAsker extends AbstractAsker{


    public ConsoleAsker(Scanner userScanner) {
        super(userScanner);
    }

    public Vehicle createVehicle() {
        String name;
        while (true) {
            try {
                System.out.println("Введите название машины: ");
                name = userScanner.nextLine();
                if ((name == null) || (name.equals("")))
                    throw new NullPointerException("Поле не может быть null, Строка не может быть пустой");
                break;
            } catch (NullPointerException e) {
                System.out.println("Поле не может быть null");
            }
        }
        System.out.print("\nEnter coordinateX of the new car: ");
        Float coordinateX = coordinatesX();
        if ((coordinateX < -836) || (coordinateX == null)) throw new NullPointerException();
        System.out.println("\nEnter coordinateY of the new car: ");
        Float coordinateY = coordinatesY();
        if (coordinateY == null) throw new NullPointerException("Поле не может быть null");
        Coordinates coordinates = new Coordinates(coordinateX, coordinateY);
        Integer enginePower = null;
        String tmp;
        while (true) {
            try {
                System.out.println("Введите мощность двигателя: ");
                tmp = userScanner.nextLine().trim();
                enginePower = Integer.parseInt(tmp);
                if ((enginePower == null) || (enginePower < 0))
                    throw new NullPointerException("Поле не может быть null, Значение поля должно быть больше 0");
                break;
            } catch (NullPointerException e) {
                System.out.println("Поле не может быть null, Значение поля должно быть больше 0. Повторите ввод!");
            } catch (NumberFormatException e) {
                System.out.println("Некорректный формат, повторите ввод.");
            } catch (NoSuchElementException exception) {
                System.out.println("Пользовательский ввод не обнаружен!");
                System.exit(0);
            } catch (Exception e) {
                System.out.println("Что-то пошло не так. Повторите ввод.");
            }
        }
        Integer numberOfWheels = null;
        while (true) {
            try {
                System.out.println("Введите количество колёс машины: ");
                tmp = userScanner.nextLine().trim();
                numberOfWheels = Integer.parseInt(tmp);
                if ((numberOfWheels == null) || (numberOfWheels < 0))
                    throw new NullPointerException("Поле не может быть null, Значение поля должно быть больше 0");
                break;
            } catch (NullPointerException e) {
                System.out.println("Поле не может быть null, Значение поля должно быть больше 0. Повторите ввод!");
            } catch (NumberFormatException e) {
                System.out.println("Некорректный формат, повторите ввод.");
            } catch (NoSuchElementException exception) {
                System.out.println("Пользовательский ввод не обнаружен!");
                System.exit(0);
            } catch (Exception e) {
                System.out.println("Что-то пошло не так. Повторите ввод.");
            }
        }

        Integer distanceTravelled = null;
        String distance;
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите дистанцию");
                distance = scanner.nextLine();
                if (!distance.equals("")) {
                    if (Integer.parseInt(distance) < 0)
                        throw new NullPointerException();
                    distanceTravelled = Integer.parseInt(distance);
                }
                break;

            } catch (IllegalArgumentException | InputMismatchException e) {
                System.out.println("Аргумент должен быть числом");
            } catch (NullPointerException e) {
                System.out.println("Значение поля должно быть больше 0");
            } catch (Exception e) {
                System.out.println("Что-то пошло не так.");
            }
        }

        FuelType type = null;
        while (true) {
            try {
                System.out.print("\nВведите тип топлива (Diesel,Alcohol,Kerosene,ManPower,Antimatter): ");

                String fuelType = userScanner.nextLine();
                if (!fuelType.equals("")) {
                    type = FuelType.valueOf(fuelType.toUpperCase());
                }
                break;
            } catch (NoSuchElementException exception) {
                System.out.println("Пользовательский ввод не обнаружен!");
                System.exit(0);
            } catch (IllegalArgumentException e) {
                System.out.println("Неверный ввод. Повторите попытку");
            } catch (Exception e) {
                System.out.println("Что-то пошло не так. Повторите ввод.");
            }
        }
        System.out.println("Машина " + name + " была успешно создана!");
        return new Vehicle(name, coordinates, enginePower, numberOfWheels, distanceTravelled, type);
    }

    private Float coordinatesX() {
        String tmp;
        Float x;
        while (true) {
            try {
                System.out.println("Введите координату Х: ");
                tmp = userScanner.nextLine().trim();
                x = Float.parseFloat(tmp);
                if (x <= -836) {
                    throw new WrongArgumentException();
                }
                break;
            } catch (NoSuchElementException exception) {
                System.out.println("Пользовательский ввод не обнаружен!");
                System.exit(0);
            } catch (NumberFormatException e) {
                System.out.println("Некорректный формат, повторите ввод.");
            } catch (WrongArgumentException e) {
                System.out.println("Значение Х должно быть больше -836.");
            } catch (Exception e) {
                System.out.println("Что-то пошло не так. Повторите ввод.");
            }
        }
        return x;
    }

    private Float coordinatesY() {
        String tmp;
        Float y;
        while (true) {
            try {
                System.out.println("Введите координату Y: ");
                tmp = userScanner.nextLine().trim();
                y = Float.parseFloat(tmp);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный формат, повторите ввод.");
            } catch (NoSuchElementException exception) {
                System.out.println("Пользовательский ввод не обнаружен!");
                System.exit(0);
            } catch (Exception e) {
                System.out.println("Что-то пошло не так. Повторите ввод.");
            }
        }
        return y;
    }

    @Override
    public User readUser() {
        System.out.println("Введите имя пользователя:");
        String username = userScanner.nextLine().trim();
        System.out.println("Введите пароль:");
        String password = userScanner.nextLine().trim();
        return new UserImpl(username, password);
    }
}
