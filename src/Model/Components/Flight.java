package Model.Components;

/**
 * Stores the data for a specific flight
 *
 * @author Matt Antantis
 * @author Ian Randman
 */
public class Flight implements Trip {

    private String originCode;
    private String destinationCode;
    private String departureTime;
    private String arrivalTime;
    private int flightNumber;
    private int airfare;
    private int numericArrival;
    private int numericDeparture;

    /**
     * The constructor for Flight from an array of Strings
     *
     * @param flight the details for the flight
     */
    public Flight(String[] flight) {
        this.originCode = flight[0];
        this.destinationCode = flight[1];
        this.departureTime = flight[2];
        this.arrivalTime = flight[3];
        this.flightNumber = Integer.parseInt(flight[4]);
        this.airfare = Integer.parseInt(flight[5]);
        setNumericTimes();
    }

    /**
     * Tests if the flight is between a requested origin and destination
     *
     * @param origin      the origin airport code
     * @param destination the destination airport code
     * @return if the flight is between the two given airports
     */
    public boolean validFlight(String origin, String destination) {
        return this.originCode.equals(origin) && this.destinationCode.equals(destination);
    }

    /**
     * Gets the airfare cost for the flight
     *
     * @return the airfare for the flight
     */
    @Override
    public int getAirfare() {
        return airfare;
    }

    /**
     * Gets the origin of the trip
     *
     * @return the code for the origin airport
     */
    @Override
    public String getOrigin() {
        return originCode;
    }

    /**
     * Gets the destination of the trip
     *
     * @return the code for the destination airport
     */
    @Override
    public String getDestination() {
        return destinationCode;
    }

    /**
     * converts string time (10:30a) into military time for easier use of sorting
     */
    private void setNumericTimes() {
        if (arrivalTime.charAt(arrivalTime.length() - 1) == 'a')
            numericArrival = 0;
        else
            numericArrival = 1200;

        numericArrival += buildTime(arrivalTime);

        if (departureTime.charAt(departureTime.length() - 1) == 'a')
            numericDeparture = 0;
        else
            numericDeparture = 1200;

        numericDeparture += buildTime(departureTime);
    }

    /**
     * helper function for setNumericTimes()
     *
     * @param time time of arrival or departures
     * @return int version of time
     */
    private int buildTime(String time) {
        int num = 0;
        for (int i = 0; i < time.length(); i++) {
            char value = time.charAt(i);
            if (value >= '0' && value <= '9') {
                num += value - 48;
                num *= 10;
            }
        }
        return (num / 10) % 1200;
    }

    /**
     * returns numeric arrival time
     */
    public int getNumericArrival() {
        return numericArrival;
    }

    /**
     * returns numeric departure time
     */
    public int getNumericDeparture() {
        return numericDeparture;
    }

    /**
     * Gets the information to display to the user
     *
     * @return the flight data to show to the user
     */
    public String getData() {
        return flightNumber + "," + originCode + "," + departureTime + "," + destinationCode + "," + arrivalTime;
    }

    /**
     * Converts the flight data into a string
     *
     * @return the string version of the flight
     */
    @Override
    public String toString() {
        return originCode + "," + destinationCode + "," + departureTime + "," + arrivalTime + "," + flightNumber + "," + airfare;
    }
}
