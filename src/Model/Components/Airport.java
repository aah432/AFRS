package Model.Components;

import Model.Managers.WeatherManager;

/**
 * Stores the information for an airport
 *
 * @author Matt Antantis
 * @author Mark Vittozzi
 */
public class Airport {

    private String code;
    private String city;
    private int connectionTime;
    private int delay;
    private WeatherManager weatherManager;


    /**
     * The constructor for the airport object
     *
     * @param code the airport's three letter code
     * @param city the airport's city
     */
    public Airport(String code, String city) {
        this.code = code;
        this.city = city;
        this.weatherManager = new WeatherManager(code);
    }

    /**
     * Sets the connection time for the airport
     *
     * @param connectionTime the time for connections
     */
    public void setConnectionTime(int connectionTime) {
        this.connectionTime = connectionTime;
    }

    /**
     * Adds a weather report to the airport
     *
     * @param weather string containing the weather being added
     */
    public void addWeather(String weather) {
        weatherManager.addWeather(weather);
    }

    /**
     * Gets the most up to date weather report for the airport
     *
     * @return up to date weather
     */
    public String getWeather(int id) {
        return weatherManager.getWeather(id);
    }


    /**
     * Sets the delay time for the airport
     *
     * @param delay the time of the delay
     */
    public void setDelay(int delay) {
        this.delay = delay;
        this.weatherManager.setDelay(String.valueOf(delay));
    }

    /**
     * determines overlay at airports
     *
     * @return overlay
     */
    public int getOverlay() {
        return delay + connectionTime;
    }

    /**
     * Determines if an object is equal to this
     *
     * @param o the object being compared
     * @return if the two objects are equal
     */
    public boolean equals(Object o) {
        if (o.getClass().equals(this.getClass())) {
            Airport a = (Airport) o;
            return a.code.equals(this.code);
        }
        return false;
    }

    /**
     * Changes the weatherModule for the user
     *
     * @param clientId an int representing the users ID
     * @param server   the server that the client wants to switch to
     */
    public void changeModule(int clientId, String server) {
        weatherManager.switchModules(clientId, server);
    }
}
