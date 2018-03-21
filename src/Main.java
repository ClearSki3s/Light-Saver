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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import static HUELampControl.JsonConnect.readJsonFromUrl;


/**
 * Created by Trist on 21-3-2018.
 * Note: Solarrad is in Watts/m^2
 */
public class Main {
    public static void main(String[] args) {
        JsonConnect jsonConnect = new JsonConnect();
        String url = "http://145.48.205.33/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB/lights";
        ArrayList<HUELamp> lampen;
        JsonObject json;
        String jsonString;
        JsonObject response;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        lampen = new ArrayList<>();
        WeatherStation ws = new WeatherStation();
        while (true) {
            try {
                response = readJsonFromUrl(url);
                Iterator<String> iterator = response.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    lampen.add(new HUELamp(response.getAsJsonObject(key), key));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            Measurement measurement = new Measurement();
            System.out.println(measurement.calcTemperature(ws.getMostRecentOutsideTemp()));
            System.out.println(measurement.calcRainFall(ws.getMostRecentRainRate()));
            System.out.println(measurement.calcWindSpeed(ws.getMostRecentWindSpeed()));

            if (measurement.transformTime(ws.getMostRecentSunrise()).equals(sdf.format(cal.getTime()))) {
                for (int i = 0; i < lampen.size(); i++) {
                    json = new JsonObject();
                    url = "http://145.48.205.33/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB/lights/" + lampen.get(i).getId() + "/state";
                    json.addProperty("on", false);
                    System.out.println(i + ": " + json);

                    jsonString = json.toString();

                    try {
                        jsonConnect.writeJsonToUrl(url, jsonString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (measurement.transformTime(ws.getMostRecentSunset()).equals(sdf.format(cal.getTime()))) {
                for (int i = 0; i < lampen.size(); i++) {
                    json = new JsonObject();
                    url = "http://145.48.205.33/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB/lights/" + lampen.get(i).getId() + "/state";
                    json.addProperty("on", true);
                    System.out.println(i + ": " + json);

                    jsonString = json.toString();

                    try {
                        jsonConnect.writeJsonToUrl(url, jsonString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (measurement.calcRainFall(ws.getMostRecentRainRate()) > 1) {
                for (int i = 0; i < lampen.size(); i++) {
                    json = new JsonObject();
                    url = "http://145.48.205.33/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB/lights/" + lampen.get(i).getId() + "/state";
                    json.addProperty("on", true);
                    json.addProperty("bri", 254);
                    json.addProperty("hue", 43690);
                    json.addProperty("sat", 254);
                    System.out.println(i + ": " + json);

                    jsonString = json.toString();

                    try {
                        jsonConnect.writeJsonToUrl(url, jsonString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (measurement.calcTemperature(ws.getMostRecentOutsideTemp()) > 20) {
                for (int i = 0; i < lampen.size(); i++) {
                    json = new JsonObject();
                    url = "http://145.48.205.33/api/iYrmsQq1wu5FxF9CPqpJCnm1GpPVylKBWDUsNDhB/lights/" + lampen.get(i).getId() + "/state";
                    json.addProperty("on", true);
                    json.addProperty("bri", 254);
                    json.addProperty("hue", 1);
                    json.addProperty("sat", 254);
                    System.out.println(i + ": " + json);

                    jsonString = json.toString();

                    try {
                        jsonConnect.writeJsonToUrl(url, jsonString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }



        /*
        System.out.println("Temp in graden: " + measurement.calcTemperature(ws.getMostRecentOutsideTemp()));
        System.out.println("Regen hoeveelheid:" + measurement.calcRainFall(ws.getMostRecentRainRate()));
        System.out.println("Windsnelheid in Km/u: " + measurement.calcWindSpeed(ws.getMostRecentWindSpeed()));
        System.out.println(measurement.transformTime(ws.getMostRecentSunrise()));
        System.out.println(measurement.transformTime(ws.getMostRecentSunset()));
        System.out.println(ws.getMostRecentUVLevel());
        System.out.println(ws.getMostRecentSolarRadiation());

        gpioController gpio = new gpioController();
        gpio.initGpio();
        while(true)
        {
            gpio.gpioControl(18, 1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gpio.gpioControl(18, 0);
        }
        */
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
