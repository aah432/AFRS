package Model.Managers;

import Model.Components.Flight;
import Model.Components.Itinerary;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles interactions with the list of flights
 *
 * @author Matt Antantis
 */
public class FlightManager extends Manager {

    private ArrayList<Flight> flights;
    private AirportManager airportManager;

    /**
     * The FlightManager constructor
     */
    public FlightManager(AirportManager airportManager) {
        flights = new ArrayList<>();
        this.airportManager = airportManager;

        try {
            buildList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds each flight from a line in a text file
     *
     * @throws FileNotFoundException if flights.txt is not found
     */
    private void buildList() throws FileNotFoundException {
        reader = new Scanner(new FileReader("data/flights.txt"));

        reader.nextLine();
        while (reader.hasNext()) {
            String[] line = reader.nextLine().split(",");
            flights.add(new Flight(line));
        }
        System.out.println("Build all flights");
    }

    /**
     * Builds a list of potential flights from an origin and destination
     *
     * @param origin      the origin airport code
     * @param destination the destination airport code
     * @return a list of all potential flights combinations
     */
    public ArrayList<Itinerary> getPotentialItineraries(String origin, String destination, int legs) {
        ArrayList<Flight> potentialFlights = getFlightsBetween(origin, destination);

        // Gather all single leg itineraries
        ArrayList<Itinerary> potentialItineraries = new ArrayList<>();
        for (Flight f : potentialFlights) {
            potentialItineraries.add(new Itinerary(new Flight[]{f}));
        }

        // Checks for possible multi flight itineraries
        if (legs > 0) {
            ArrayList<Flight> fromOrigin = new ArrayList<>();
            ArrayList<Flight> fromDestination = new ArrayList<>();
            for (Flight f : flights) {
                if (f.getOrigin().equals(origin) && !potentialFlights.contains(f)) {
                    fromOrigin.add(f);
                } else if (f.getDestination().equals(destination) && !potentialFlights.contains(f)) {
                    fromDestination.add(f);
                }
            }

            // Checks to see if the two flights form a valid itinerary
            for (Flight ori : fromOrigin) {
                for (Flight desti : fromDestination) {
                    if (checkConnection(ori, desti))
                        potentialItineraries.add(new Itinerary(new Flight[]{ori, desti}));
                }
            }

            // Checks for extra valid itineraries if 2 connections are allowed
            if (legs > 1) {
                ArrayList<Flight> temp;
                for (Flight ori : fromOrigin) {
                    for (Flight desti : fromDestination) {
                        // Gets flights connecting the other two legs
                        temp = getFlightsBetween(ori.getDestination(), desti.getOrigin());

                        // Checks to see if the three selected flights are valid
                        for (Flight f : temp) {
                            if (checkConnection(ori, f) && checkConnection(f, desti)) {
                                potentialItineraries.add(new Itinerary(new Flight[]{ori, f, desti}));
                            }
                        }
                    }
                }
            }
        }
        return potentialItineraries;
    }

    /**
     * Builds a list of flights between two airports
     *
     * @param origin      the code of the origin airport
     * @param destination the code of the destination airport
     * @return the list of all flights between the two
     */
    private ArrayList<Flight> getFlightsBetween(String origin, String destination) {
        ArrayList<Flight> potential = new ArrayList<>();
        for (Flight f : flights) {
            if (f.validFlight(origin, destination))
                potential.add(f);
        }
        return potential;
    }

    /**
     * Checks to see if two flights are able to connect
     *
     * @param first  the first flight
     * @param second the connecting flight
     * @return if the two flights can be connected
     */
    private boolean checkConnection(Flight first, Flight second) {
        if (first.getDestination().equals(second.getOrigin())) {
            int overlay = airportManager.getDelay(first.getDestination());
            if (overlay >= 60) {
                int hours = overlay / 60;
                overlay = overlay % 60;
                overlay += hours * 100;
            }
            return overlay + first.getNumericArrival() <= second.getNumericDeparture();
        } else
            return false;
    }

}
