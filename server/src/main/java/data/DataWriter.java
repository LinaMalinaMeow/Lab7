package data;

import object.Vehicle;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.util.PriorityQueue;

public interface DataWriter {
    void save(PriorityQueue<Vehicle> vehiclePriorityQueue) throws TransformerConfigurationException, ParserConfigurationException;
}
