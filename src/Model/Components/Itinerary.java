package Model.Components;

/**
 * This class stores the information for a series of flights
 * between two airports
 *
 * @author Matt Antantis
 * @author Ian Randman
 */
public class Itinerary implements Trip {

    private Flight[] flights;

    /**
     * The constructor for the Itinerary
     *
     * @param flights the list of flights in the itinerary
     */
    public Itinerary(Flight[] flights) {
        this.flights = flights;
    }

    /**
     * Gets the number of flights in the itinerary
     *
     * @return the number of flights
     */
    private int getNumFlights() {
        return flights.length;
    }

    /**
     * Gets the total airfare cost for the trip
     *
     * @return the total airfare for the trip
     */
    @Override
    public int getAirfare() {
        int sum = 0;
        for (Flight f : flights) {
            sum += f.getAirfare();
        }
        return sum;
    }

    /**
     * Gets the data to display to the user
     *
     * @return the data for the itinerary
     */
    public String getData() {
        StringBuilder result = new StringBuilder(getAirfare() + "," + getNumFlights());
        for (Flight f : flights) {
            result.append(",").append(f.getData());
        }
        return result.toString();
    }

    /**
     * Gets the origin of the trip
     *
     * @return the code for the starting airport
     */
    @Override
    public String getOrigin() {
        return flights[0].getOrigin();
    }

    /**
     * Gets the destination of the trip
     *
     * @return the code for the final airport
     */
    @Override
    public String getDestination() {
        return flights[flights.length - 1].getDestination();
    }

    /**
     * Converts the itinerary data into a string
     *
     * @return the string version of the itinerary
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("" + getNumFlights());
        for (Flight f : flights) {
            result.append(",").append(f.toString());
        }
        return result.toString();
    }

    /**
     * obtains the arrival time for the final flight in the itinerary
     *
     * @return integer time of arrival for the itinerary
     */
    public int getArrivalTime() {
        return flights[flights.length - 1].getNumericArrival();
    }

    /**
     * obtains the departure time of an itinerary
     *
     * @return integer time of departure for the itinerary
     */
    public int getDepartureTime() {
        return flights[0].getNumericDeparture();
    }
}
