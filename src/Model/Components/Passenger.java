package Model.Components;

/**
 * This class stores data for the Passenger
 * for a reservation
 *
 * @author Matt Antantis
 */
public class Passenger {
    private String name;

    /**
     * The constructor for Passenger
     *
     * @param name the name of the Passenger
     */
    public Passenger(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the passenger
     *
     * @return the passenger's name
     */
    public String getName() {
        return name;
    }

    /**
     * Determines if this passenger is equal to another object
     *
     * @param o the object being compared
     * @return if the two objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;

        Passenger that = (Passenger) o;

        return this.name.equals(that.name);
    }
}
