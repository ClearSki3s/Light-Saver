package HUELampControl;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import HUELampControl.HUELamp;
import com.google.gson.*;

public class Main {

    public static void main(String[] args) {
	// write your code here

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
        System.out.println(lampen.get(i).getName());
    }

    public static JsonObject readJsonFromUrl(String url) throws IOException, JsonIOException {
        InputStream is = new URL(url).openStream();

        JsonParser parser = new JsonParser();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return parser.parse(jsonText).getAsJsonObject();
        } finally {
            is.close();
        }
    }
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}

