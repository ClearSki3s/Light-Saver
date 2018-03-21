package WeatherstationControl;

/**
 * Created by Trist on 21-3-2018.
 */
public class Measurement {

/**
 * A class to convert raw measurements
 * into human readable measurements
 */

    /**
     * Calculate the temperature
     *
     * @param mval  Raw value from weatherstation
     * @return The temperature in degree Celsius
     * @since 1.0
     */
    public static double calcTemperature(double mval)
    {
        return ((mval / 10) - 32) / 1.8;
    }

    /**
     * Calculate the windspeed
     *
     * @param mval  Raw value from weatherstation
     * @return The windspeed in km/h
     * @since 1.0
     */
    public static double calcWindSpeed(short mval)
    {
        return mval * 1.609344;
    }

    /**
     * Calculate the amount of rain
     *
     * @param mval  Raw value from weatherstation
     * @return The amount of rain in mm
     * @since 1.0
     */
    public static double calcRainFall(short mval)
    {
        return mval * 0.2;
    }
}