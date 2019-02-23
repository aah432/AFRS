package Controller.Requests;

import Controller.Sorting.AirfareSort;
import Controller.Sorting.ArrivalSort;
import Controller.Sorting.DepartureSort;
import Controller.Sorting.Sort;
import Model.Components.Airport;
import Model.Components.Itinerary;
import Model.Managers.AirportManager;
import Model.Managers.FlightManager;

import java.util.List;

/**
 * This class performs all actions for obtaining flight information
 *
 * @author Amber Harding
 * @author Matt Antantis
 * @author Mark Vittozzi
 * @author Ian Randman
 * @author Jonathon Chierchio
 */

public class FlightInfoRequest implements Request {

    private static final String ORIGIN_ERROR = "error,unknown origin";
    private static final String DESTINATION_ERROR = "error,unknown destination";
    private static final String CONNECTION_ERROR = "error,invalid connection limit";
    private static final String SORT_ERROR = "error,invalid sort order";

    private static final String DEPARTURE_SORT = "departure";
    private static final String ARRIVAL_SORT = "arrival";
    private static final String AIRFARE_SORT = "airfare";

    private String origin;
    private String destination;
    private int connections;
    private String sortOrder;
    private AirportManager airportManager;
    private FlightManager flightManager;
    private List<Itinerary> requestedItineraries;

    /**
     * The constructor for FlightInfoRequest
     *
     * @param command        a string array representing the information from the user
     * @param airportManager the airport manager needed for this task
     * @param flightManager  the flight manager needed for this task
     */
    public FlightInfoRequest(String[] command, AirportManager airportManager, FlightManager flightManager) {

        this.airportManager = airportManager;
        this.flightManager = flightManager;
        this.origin = command[2];
        this.destination = command[3];

        if (command.length > 4 && !command[4].equals("")) {
            try {
                this.connections = Integer.parseInt(command[4]);
            } catch (ArrayIndexOutOfBoundsException e) {
                this.connections = 2;
            }
        } else {
            this.connections = 2;
        }

        if (command.length == 6 && !command[5].equals("")) {
            sortOrder = command[5];
        } else {
            sortOrder = DEPARTURE_SORT;
        }
    }

    /**
     * Receives and returns the flight info from the managers
     *
     * @return the pertinent flight information
     */
    public String execute() {
        Airport originAirport = airportManager.getAirport(origin);
        Airport destinationAirport = airportManager.getAirport(destination);
        StringBuilder resp;

        //Error checking
        if (originAirport == null) {
            //Checks that origin airport exists

            resp = new StringBuilder(ORIGIN_ERROR);

        } else if (destinationAirport == null) {
            //Checks that destination airport exists

            resp = new StringBuilder(DESTINATION_ERROR);

        } else if (connections < 0
                || connections > 2) {
            //Checks connection limit is valid

            resp = new StringBuilder(CONNECTION_ERROR);

        } else if (!isValidSortOrder(sortOrder)) {
            //Checks sort order is valid

            resp = new StringBuilder(SORT_ERROR);

        } else {
            List<Itinerary> itineraries = flightManager.getPotentialItineraries(origin, destination, connections);

            resp = new StringBuilder("info," + itineraries.size());
            int count = 1;
            requestedItineraries = itineraries;
            sortItineraries(requestedItineraries);

            for (Itinerary itinerary : requestedItineraries) {
                resp.append("\n").append(count).append(",").append(itinerary.getData());
                count++;
            }
        }

        return resp.toString();

    }

    private boolean isValidSortOrder(String sortOrder) {
        return sortOrder.equals(DEPARTURE_SORT) ||
                sortOrder.equals(ARRIVAL_SORT) ||
                sortOrder.equals(AIRFARE_SORT);
    }

    public List<Itinerary> getRequestedItineraries() {
        return requestedItineraries;
    }

    /**
     * Method used to sort itineraries based on the sortOrder
     *
     * @param itineraries list of itineraries being sorted
     */
    private void sortItineraries(List<Itinerary> itineraries) {
        switch (sortOrder) {
            case ARRIVAL_SORT:
                Sort Arrival = new ArrivalSort();
                Arrival.sort(itineraries);
                break;
            case DEPARTURE_SORT:
                Sort Departure = new DepartureSort();
                Departure.sort(itineraries);
                break;
            case AIRFARE_SORT:
                Sort Airfare = new AirfareSort();
                Airfare.sort(itineraries);
                break;
        }
    }
}
