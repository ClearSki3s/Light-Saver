package HUELampControl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.IOException;


import HUELampControl.HUELamp;
import com.google.gson.*;
import com.google.api.client.http.*;
import com.sun.deploy.net.HttpResponse;

import javax.xml.ws.Response;

public class JsonConnect {

    public static JsonObject readJsonFromUrl(String url) throws IOException, JsonIOException {
        InputStream is = new URL(url).openStream();

        JsonParser parser = new JsonParser();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            System.out.println(jsonText);
            return parser.parse(jsonText).getAsJsonObject();
        } finally {
            is.close();
        }
    }

    public void writeJsonToUrl(String Jurl, String json) throws IOException, JsonIOException {
        URL url = new URL(Jurl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        osw.write(json);
        osw.flush();
        osw.close();
        System.out.println(connection.getResponseCode());
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

