package Model.Components.Weather;

import java.util.List;
/**
 * An object to hold information from JSON
 *
 * @author Mark Vittozzi
 */

public class Weather {


    List<String> Temp;

    /**
     * @return a string representing the temp list
     */
    public String getTemp(){
        String resp = " Temperature: ";
        for(String t : Temp){
            resp += t + " ";
        }
        return resp;
    }

    /**
     * @return the string representing the temp list
     */
    @Override
    public String toString(){
        return getTemp();
    }


}
