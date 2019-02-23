package Model.Components.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for storing and returning weather for an airport
 *
 * @author Mark Vittozzi
 */

public class StoredWeather implements WeatherModule {

    private List<String> storedWeather;
    private String delay;

    public StoredWeather() {
        storedWeather = new ArrayList<>();
        this.delay = "";
    }

    /**
     * @param delay: a string representing the delay at this airport
     */
    public void setDelay(String delay) {
        this.delay = delay;
    }

    /**
     * Adds a string representing weather to the list containing
     * all weathers
     *
     * @param weather: A string representing the weather
     */
    @Override
    public void addWeather(String weather) {
        storedWeather.add(weather);
    }

    /**
     * Checks if the index variable is equal to the size of the weather list
     * this is used for making sure the index variable does not go over
     * the length-1
     *
     * @param index: the index variable used by the object who holds this class
     * @return a boolean representing if the index is equal to the list length
     */
    @Override
    public boolean checkId(int index) {
        return (index == storedWeather.size());
    }

    /**
     * returns the weather string at the specified index
     *
     * @param index the index of the string to be returned
     * @return the weather string
     */
    @Override
    public String getWeather(int index) {
        return storedWeather.get(index) + "," + delay;
    }
}
