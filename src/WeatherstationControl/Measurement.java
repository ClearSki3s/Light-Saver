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


    //Calculate the windspeed
    public static double calcWindSpeed(short mval)
    {
        return mval * 1.609344;
    }


    //Calculate the amount of rain
    public static double calcRainFall(short mval)
    {
        return mval * 0.2;
    }

    //Transform data into readable string
    public static String transformTime(short mval)
    {
        if(mval == 0)
        {
            return "not available";
        }

        String mvalString = Short.toString(mval);
        if(mvalString.length() == 3)
        {
            mvalString = "0" + mvalString;
        }

        String hourString = mvalString.substring(0,2);
        String minuteString = mvalString.substring(2,4);

        return hourString + ":" + minuteString;
    }
}