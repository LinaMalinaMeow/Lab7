package data;

import collection.CollectionManagerImpl;
import object.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.PriorityQueue;

public class FileDataWriter implements DataWriter {

    private final String fileName;

    private static final Logger logger = LoggerFactory.getLogger(CollectionManagerImpl.class);

    public FileDataWriter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(PriorityQueue<Vehicle> vehiclePriorityQueue) throws TransformerConfigurationException, ParserConfigurationException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder(); //документ билдер создает новый элемент
        Document document = documentBuilder.newDocument();
        Element elementVehicles = document.createElement("vehicles");
        document.appendChild(elementVehicles);

        for (Vehicle vehicle : vehiclePriorityQueue) {
            //создание тега vehicle
            Element elementVehicle = document.createElement("vehicle");
            //создание тега id
            Element elementId = document.createElement("id");
            // установка в тег id данных об id из объекта vehicle
            elementId.setTextContent(String.valueOf(vehicle.getId()));
            // добавление тега id в тег vehicle
            elementVehicle.appendChild(elementId);

            Element elementName = document.createElement("name");
            elementName.setTextContent(String.valueOf(vehicle.getName()));
            elementVehicle.appendChild(elementName);

            Element elementCoordinates = document.createElement("coordinates");
            elementVehicle.appendChild(elementCoordinates);

            Element elementX = document.createElement("x");
            elementX.setTextContent(String.valueOf(vehicle.getCoordinates().getX()));
            Element elementY = document.createElement("y");
            elementY.setTextContent(String.valueOf(vehicle.getCoordinates().getY()));

            elementCoordinates.appendChild(elementX);
            elementCoordinates.appendChild(elementY);

            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Element elementCreationDate = document.createElement("creationDate");
            elementCreationDate.setTextContent(format.format(vehicle.getCreationDate()));
            elementVehicle.appendChild(elementCreationDate);

            Element elementEnginePower = document.createElement("enginePower");
            elementEnginePower.setTextContent(String.valueOf(vehicle.getEnginePower()));
            elementVehicle.appendChild(elementEnginePower);

            Element elementNumberOfWheels = document.createElement("numberOfWheels");
            elementNumberOfWheels.setTextContent(String.valueOf(vehicle.getNumberOfWheels()));
            elementVehicle.appendChild(elementNumberOfWheels);

            if (vehicle.getDistanceTravelled() != null) {
                Element elementDistanceTravelled = document.createElement("distanceTravelled");
                elementDistanceTravelled.setTextContent(String.valueOf(vehicle.getDistanceTravelled()));
                elementVehicle.appendChild(elementDistanceTravelled);
            }

            if (vehicle.getFuelType() != null) {
                Element elementFuelType = document.createElement("fuelType");
                elementFuelType.setTextContent(String.valueOf(vehicle.getFuelType()));
                elementVehicle.appendChild(elementFuelType);
            }

            elementVehicles.appendChild(elementVehicle);
        }
        //объект, который преобразует созданную структуру в xml формат
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");//включить табуляцию и поставить пробелы
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource domSource = new DOMSource(document);

        //создаю объект типа файл, чтобы посмотреть у него существование и права на файл, если всё существует,
        // тогда делаем запись, в остальных случах бросаем исключения.
        try {
            File fc = new File(fileName);
            if (fc.exists() && fc.canWrite()) {
                try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName))) {
                    StreamResult streamResult = new StreamResult(writer);
                    transformer.transform(domSource, streamResult);
                    logger.debug("Коллекция сохранена в файл.");
                }
            } else if(fc.exists()){
                logger.error("Ошибка при сохранении: Добавьте права на чтение файла и попробуйте снова!");
            } else {
                logger.error("Ошибка при сохранении: Файл не найден.");
            }
        } catch (Exception e) {
            logger.error("Ошибка обработки коллекции!");
        }
    }
}
