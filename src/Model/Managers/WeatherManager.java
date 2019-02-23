package Model.Managers;

import Model.Components.Weather.StoredWeather;
import Model.Components.Weather.WeatherModule;
import Model.Components.Weather.WebWeather;

import java.util.HashMap;

/**
 * An object that creates, holds, interacts with and modifies the weather modules
 * of a specific airport
 *
 * @author Mark Vittozzi
 */

public class WeatherManager extends Manager {


    private HashMap<Integer, WeatherModule> weatherModuleMap;
    private HashMap<Integer, Integer> indexMap;
    private WeatherModule storedWeather;
    private String airportCode;

    /**
     * @param airportCode: A string representing the name of the airport this object belongs to
     */
    public WeatherManager(String airportCode) {
        this.weatherModuleMap = new HashMap<>();
        this.indexMap = new HashMap<>();
        this.storedWeather = new StoredWeather();
        this.airportCode = airportCode;
    }

    /**
     * Adds the passed in weather to the module
     *
     * @param weather: A string representing weather
     */
    public void addWeather(String weather) {
        storedWeather.addWeather(weather);
    }

    /**
     * Changes the weather module
     *
     * @param clientId: An int representing the user
     * @param server:   a string representing the server to be switched to
     */
    public void switchModules(int clientId, String server) {
        if (server.equals("local")) {
            weatherModuleMap.replace(clientId, storedWeather);
        } else {
            weatherModuleMap.replace(clientId, new WebWeather(airportCode));
        }
    }

    /**
     * Obtains the weather from the weather module
     * determines if the index variable is too large and resets it
     *
     * @param clientId: An int representing the users id
     * @return a string representing the weather for the airport
     */
    public String getWeather(int clientId) {
        if (!(weatherModuleMap.containsKey(clientId))) {
            indexMap.put(clientId, 0);
            weatherModuleMap.put(clientId, storedWeather);
        }
        if (weatherModuleMap.get(clientId).checkId(indexMap.get(clientId))) {
            indexMap.replace(clientId, 0);
        }

        String weather = weatherModuleMap.get(clientId).getWeather(indexMap.get(clientId));
        indexMap.replace(clientId, indexMap.get(clientId) + 1);

        return weather;
    }

    /**
     * sets the delay of the storedWeather Module
     *
     * @param delay a string representing a delay
     */
    public void setDelay(String delay) {
        storedWeather.setDelay(delay);
    }

}