package Model.Components;

/**
 * This class is the Component object for the Composite
 * pattern. The methods inside are overridden by its
 * children.
 *
 * @author Matt Antantis
 * @author Ian Randman
 */
public interface Trip {

    /**
     * Gets the airfare cost for a trip
     *
     * @return the airfare cost
     */
    int getAirfare();

    /**
     * Gets the origin of the trip
     *
     * @return the origin of the trip
     */
    String getOrigin();

    /**
     * Gets the destination of the trip
     *
     * @return the destination of the trip
     */
    String getDestination();

    /**
     * Gets the data to show to the user
     *
     * @return the data of the trip
     */
    String getData();
}
