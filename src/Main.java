import HUELampControl.HUELamp;
import WeatherstationControl.Measurement;
import WeatherstationControl.RawMeasurement;
import WeatherstationControl.WeatherStation;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static HUELampControl.JsonConnect.readJsonFromUrl;

/**
 * Created by Trist on 21-3-2018.
 */
public class Main {
    public static void main(String[] args) {

        String url = "http://145.48.205.33/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB/lights";
        ArrayList<HUELamp> lampen;

        lampen = new ArrayList<>();
        try {
            JsonObject response = readJsonFromUrl(url);
            Iterator<String> iterator = response.keySet().iterator();
            while(iterator.hasNext()) {
                String key = iterator.next();
                lampen.add(new HUELamp(response.getAsJsonObject(key), key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i< lampen.size(); i++)
        {
            System.out.println(lampen.get(i).getName());
        }

        WeatherStation ws = new WeatherStation();

        RawMeasurement rawMeasurement = ws.getMostRecentMeasurement();

        System.out.println(rawMeasurement);

        Measurement measurement = new Measurement();
        System.out.println(measurement.calcTemperature(ws.getMostRecentOutsideTemp()));
        System.out.println(measurement.calcRainFall(ws.getMostRecentRainRate()));
        System.out.println(measurement.calcWindSpeed(ws.getMostRecentWindSpeed()));
    }


}
