package data;

import object.Vehicle;

import javax.xml.parsers.ParserConfigurationException;
import java.util.PriorityQueue;

public interface DataReader {
    PriorityQueue<Vehicle> readCollection() throws ParserConfigurationException;
}
