package HUELampControl;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;

import HUELampControl.HUELamp;
import com.google.gson.*;

public class JsonConnect {

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

