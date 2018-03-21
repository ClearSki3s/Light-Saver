package HUELampControl;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmbro on 24-11-2017.
 */

public class HUELamp implements Serializable {

    private static transient JsonObject state;
    private String id;
    private boolean on;
    private int bri;
    private int hue;
    private int sat;
    private String effect;
    private String type;
    private String name;


    private float[] hsv;

    public HUELamp(JsonObject json, String key) {

        try {
            id = key;
            state = json.getAsJsonObject("state");
            on = state.get("on").getAsBoolean();
            bri = state.get("bri").getAsInt();
            hue = state.get("hue").getAsInt();
            sat = state.get("sat").getAsInt();
            effect = state.get("effect").getAsString();

            type = json.get("type").getAsString();
            name = json.get("name").getAsString();

            hsv = new float[3];
            hsv[0]= hue/(float)182.0;
            hsv[1]= sat/(float)254.0;
            hsv[2]= bri/(float)254.0;

        } catch (JsonIOException e) {
            e.printStackTrace();
        }

    }

    public JsonObject getState() {
        return state;
    }

    public boolean isOn() {
        return on;
    }

    public int getBri() {
        return bri;
    }

    public int getHue() {
        return hue;
    }

    public int getSat() {
        return sat;
    }

    public String getEffect() {
        return effect;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public float[] getHsv() {
        return hsv;
    }

    public String getId() { return id; }
}

