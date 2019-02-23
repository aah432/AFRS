package Model.Managers;

import Model.Components.Airport;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This class holds all the airports for the system
 * and responds to requests for information about them.
 *
 * @author Matt Antantis
 * @author Mark Vittozzi
 */
public class AirportManager extends Manager {
    private Map<String, Airport> airports;

    /**
     * The constructor for the AirportManger
     */
    public AirportManager() {
        airports = new HashMap<>();

        try {
            buildList();
            buildConnections();
            buildDelays();
            buildWeather();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Builds the list of Airports by reading a text file
     *
     * @throws FileNotFoundException throws an exception if cities.txt is not found
     */
    private void buildList() throws FileNotFoundException {
        reader = new Scanner(new FileReader("data/cities.txt"));
        System.out.print("initializing airports");
        while (reader.hasNext()) {
            String[] line = reader.nextLine().split(",");
            airports.put(line[0], new Airport(line[0], line[1]));
            System.out.print(".");
        }
        System.out.println("done");

    }

    /**
     * Updates the airports in the list with the connection
     * time form a text file
     *
     * @throws FileNotFoundException if connection_times.txt is not found an exception will be thrown
     */
    private void buildConnections() throws FileNotFoundException {
        reader = new Scanner(new FileReader("data/connection_times.txt"));
        System.out.print("initializing connections");
        reader.nextLine();
        while (reader.hasNext()) {
            String[] line = reader.nextLine().split(",");
            airports.get(line[0]).setConnectionTime(Integer.parseInt(line[1]));
            System.out.print(".");
        }
        System.out.println("done");
    }

    /**
     * Updates the airports in the list with the delay
     * time from a text file
     *
     * @throws FileNotFoundException if delay_times.txt is not found an exception will be thrown
     */
    private void buildDelays() throws FileNotFoundException {
        reader = new Scanner(new FileReader("data/delay_times.txt"));
        System.out.print("initializing delays");
        reader.nextLine();
        while (reader.hasNext()) {
            String[] line = reader.nextLine().split(",");
            airports.get(line[0]).setDelay(Integer.parseInt(line[1]));
            System.out.print(".");
        }
        System.out.println("done");
    }

    /**
     * Updates the airports in the list with the weather data
     * from a text file
     *
     * @throws FileNotFoundException if weather.txt is not found
     */
    private void buildWeather() throws FileNotFoundException {
        reader = new Scanner(new FileReader("data/weather.txt"));
        System.out.print("initializing weather");
        while (reader.hasNext()) {
            String[] line = reader.nextLine().split(",");
            for (int i = 1; i < line.length; i += 2) {
                airports.get(line[0]).addWeather(line[0] + "," + line[i] + "," + line[i + 1]);
            }
            System.out.print(".");
        }
        System.out.println("done");
    }

    /**
     * retrieves airport via airport code
     *
     * @param code code being used to retrieve airport
     * @return airport
     */
    public Airport getAirport(String code) {
        return airports.get(code);
    }

    /**
     * retrieves delay via airport code
     *
     * @param code airport code for airport
     * @return delay time
     */
    int getDelay(String code) {
        return airports.get(code).getOverlay();
    }

    public String getAirportInfo(String airportCode, int id) {
        return airports.get(airportCode).getWeather(id);
    }

    public void switchClientModule(int id, String server) {
        for (String s : airports.keySet()) {
            airports.get(s).changeModule(id, server);
        }
    }

}
