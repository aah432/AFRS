package Model.Components.Weather;

/**
 * A interface for the two weather module classes to implement
 * (Stored weather and web weather)
 *
 * @author Mark Vittozzi
 */

public interface WeatherModule {

    String getWeather(int id);

    void addWeather(String weather);

    boolean checkId(int id);

    void setDelay(String delay);
}
