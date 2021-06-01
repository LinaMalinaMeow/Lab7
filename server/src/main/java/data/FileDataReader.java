package data;

import object.Coordinates;
import object.FuelType;
import object.Vehicle;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.PriorityQueue;

public class FileDataReader implements DataReader{
    private final PriorityQueue<Vehicle> vehiclePriorityQueue = new PriorityQueue<>();
    private final String fileName;

    public FileDataReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public PriorityQueue<Vehicle> readCollection() throws ParserConfigurationException {
        //newInstance - статический метод класса
        //DocumentBuilderFactory, который создает и возвращает объект фабрики DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        try {
            File file1 = new File(fileName);
            if (file1.exists() && file1.canRead()) {
                try (InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName))) {
                    Document document = documentBuilder.parse(new InputSource(reader));
                    if (document.getElementsByTagName("vehicles").getLength() == 0) {
                        throw new ParseException("vehicles обязательный атрибут в файле", 0);
                    }
                    NodeList vehiclesNodeList = document.getElementsByTagName("vehicle");
                    for (int i = 0; i < vehiclesNodeList.getLength(); i++) {
                        try {
                            Element nodeVehicle = (Element) vehiclesNodeList.item(i); //приведение типов, получение итого элемента
                            //id
                            if (nodeVehicle.getElementsByTagName("id").getLength() == 0) {
                                throw new ParseException("ID - обязательное поле", 0);
                            }
                            int id = Integer.parseInt(nodeVehicle.getElementsByTagName("id") //конвертируем строку в целое число
                                    .item(0).getTextContent());//получаем все теги id из ноды, но так как айди один, то забираем нулевой
                            if (id <= 0) {
                                throw new IllegalArgumentException("ID должен быть больше 0");
                            }
                            if (this.vehiclePriorityQueue.stream().anyMatch(x -> x.getId() == id)) {
                                throw new IllegalArgumentException("ID должен быть уникальным");
                            }

                            //name
                            if (nodeVehicle.getElementsByTagName("name").getLength() == 0) {
                                throw new ParseException("Name - обязательное поле", 0);
                            }
                            String name = nodeVehicle.getElementsByTagName("name")
                                    .item(0).getTextContent();
                            if (name.equals("")) {
                                throw new IllegalArgumentException("Name должен быть не пустым");
                            }

                            //coordinates
                            if (nodeVehicle.getElementsByTagName("coordinates").getLength() == 0) {
                                throw new ParseException("Coordinates - обязательное поле", 0);
                            }
                            Element nodeCoordinates = (Element) nodeVehicle.getElementsByTagName("coordinates").item(0);
                            if (nodeCoordinates.getElementsByTagName("x").getLength() == 0) {
                                throw new ParseException("X - обязательное поле", 0);
                            }
                            Float x = Float.valueOf(nodeCoordinates.getElementsByTagName("x").item(0).getTextContent());
                            if (x < -836) {
                                throw new IllegalArgumentException("X должен быть больше -836");
                            }
                            if (nodeCoordinates.getElementsByTagName("y").getLength() == 0) {
                                throw new ParseException("Y - обязательное поле", 0);
                            }
                            Float y = Float.valueOf(nodeCoordinates.getElementsByTagName("y").item(0).getTextContent());

                            //creationDate
                            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                            format.setLenient(false);// чтобы формат справа соответствовал формату слева
                            if (nodeVehicle.getElementsByTagName("creationDate").getLength() == 0) {
                                throw new ParseException("creationDate - обязательное поле", 0);
                            }
                            Date creationDate = format.parse(nodeVehicle.getElementsByTagName("creationDate")
                                    .item(0).getTextContent());

                            //enginePower
                            if (nodeVehicle.getElementsByTagName("enginePower").getLength() == 0) {
                                throw new ParseException("enginePower - обязательное поле", 0);
                            }
                            Integer enginePower = Integer.valueOf(nodeVehicle.getElementsByTagName("enginePower")
                                    .item(0).getTextContent());
                            if (enginePower <= 0) {
                                throw new IllegalArgumentException("enginePower должен быть больше 0");
                            }

                            //numberOfWheels
                            if (nodeVehicle.getElementsByTagName("numberOfWheels").getLength() == 0) {
                                throw new ParseException("numberOfWheels - обязательное поле", 0);
                            }
                            Integer numberOfWheels = Integer.valueOf(nodeVehicle.getElementsByTagName("numberOfWheels")
                                    .item(0).getTextContent());
                            if (numberOfWheels <= 0) {
                                throw new IllegalArgumentException("numberOfWheels должен быть больше 0");
                            }

                            Integer distanceTravelled = null; //необязательна и может быть null
                            if (nodeVehicle.getElementsByTagName("distanceTravelled").getLength() != 0) {
                                distanceTravelled = Integer.valueOf(nodeVehicle.getElementsByTagName("distanceTravelled")
                                        .item(0).getTextContent());
                            }

                            FuelType fuelType = null;
                            if (nodeVehicle.getElementsByTagName("fuelType").getLength() != 0) {
                                fuelType = FuelType.valueOf(nodeVehicle.getElementsByTagName("fuelType")
                                        .item(0).getTextContent());
                            }
                            Vehicle vehicle = new Vehicle(id, name, new Coordinates(x, y), creationDate,
                                    enginePower, numberOfWheels, distanceTravelled, fuelType);
                            vehiclePriorityQueue.add(vehicle);
                        } catch (IllegalArgumentException | ParseException e) {
                            System.out.println("Эта машинка не может быть добвлена в коллекцию");
                        }
                    }
                }
            } else {
                System.out.println("Нет прав на чтение или файл не существует. Добавьте права/Создайте файл и запустите программу вновь");
                System.exit(1);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка. Файл не найден.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Ошибка. Нет доступа к файлу.");
            System.exit(1);
        } catch (Exception e) {
            System.out.println("Ошибка обработки коллекции!");
            System.exit(1);
        }
        return vehiclePriorityQueue;
    }
}
