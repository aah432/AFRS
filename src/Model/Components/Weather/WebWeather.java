package Model.Components.Weather;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * A class to gather weather information from the web for an airport
 *
 * @author Mark Vittozzi
 */


public class WebWeather implements WeatherModule {

    // the URL of the site the weather info is stored
    private static final String URL_PREFACE = "https://soa.smext.faa.gov/asws/api/airport/status/";

    private String airportCode;

    /**
     * @param airportCode: Takes in an airport code, this is the airport the weather info will be for
     */
    public WebWeather(String airportCode) {
        this.airportCode = airportCode;
    }

    /**
     * @param id an id that is not used but required for interface
     * @return a string representing the weather for an airport
     */
    @Override
    public String getWeather(int id) {
        return generateWeather();
    }

    // A method that is not used but required for interface
    @Override
    public void addWeather(String weather) {
    }

    // A method that is not used but required for interface
    @Override
    public boolean checkId(int id) {
        return false;
    }

    // A method that is not used but is required for interface
    @Override
    public void setDelay(String delay) {

    }

    /**
     * Establishes a connection with the URL and obtains the weather info
     * stores them in objects using GSON
     *
     * @return a string representing the weather
     */
    private String generateWeather() {

        String airline = URL_PREFACE + airportCode;
        WebAirport webAirport = null;

        try {
            URL FAA_URL = new URL(airline);
            HttpURLConnection urlConnection = (HttpURLConnection) FAA_URL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestProperty("Accept", "application/" + "json");
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            Gson gson = new Gson();

            webAirport = gson.fromJson(response.toString(), WebAirport.class);
            in.close();

        } catch (MalformedURLException e) {
            System.out.print("Malformed URL: ");
            System.out.println(e.getMessage());
        } catch (ProtocolException e) {
            System.out.print("Unsupported protocol: ");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return webAirport.toString();
    }
}
