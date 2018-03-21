import HUELampControl.HUELamp;
import HUELampControl.JsonConnect;
import WeatherstationControl.Measurement;
import WeatherstationControl.RawMeasurement;
import WeatherstationControl.WeatherStation;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static HUELampControl.JsonConnect.readJsonFromUrl;


/**
 * Created by Trist on 21-3-2018.
 */
public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        JsonConnect jsonConnect = new JsonConnect();
        String url = "http://145.48.205.33/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB/lights";
        ArrayList<HUELamp> lampen;
        JsonObject json;
        String jsonString;
        JsonObject response;
        JsonParser parser = new JsonParser();
        lampen = new ArrayList<>();
        try {
            response = readJsonFromUrl(url);
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
            //System.out.println(lampen.get(i).getName());
            lampen.get(i).setBri(254);
            lampen.get(i).setHue(254);
            lampen.get(i).setSat(43690);
        }

        WeatherStation ws = new WeatherStation();

        RawMeasurement rawMeasurement = ws.getMostRecentMeasurement();

        System.out.println(rawMeasurement);

        Measurement measurement = new Measurement();
        System.out.println(measurement.calcTemperature(ws.getMostRecentOutsideTemp()));
        System.out.println(measurement.calcRainFall(ws.getMostRecentRainRate()));
        System.out.println(measurement.calcWindSpeed(ws.getMostRecentWindSpeed()));

        for(int i = 0; i< lampen.size(); i++) {
            json = new JsonObject();
            url = "http://145.48.205.33/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB/lights/" + lampen.get(i).getId() + "/state";
            json.addProperty("on", true);
            json.addProperty("bri", 254);
            json.addProperty("hue", 43690);
            json.addProperty("sat", 254);
            System.out.println(i + ": " + json);

            jsonString = json.toString();

            try {
                jsonConnect.writeJsonToUrl(url,jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
