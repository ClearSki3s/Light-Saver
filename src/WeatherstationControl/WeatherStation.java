package WeatherstationControl;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;


public class WeatherStation
{

    private Connection myConn = null;


    public WeatherStation()
    {
        this("145.48.203.28","5329","aws_data","aws","aws");
    }


    //Connect naar de weerstation database

    public WeatherStation(String host, String port, String dbName, String userName, String password)
    {
        try
        {
            String url = "jdbc:mysql://" + host + ":" + port + "/"+ dbName + "?user="
            + userName
            + "&password="
            + password;
            Class.forName("com.mysql.jdbc.Driver").newInstance ();
            myConn = DriverManager.getConnection(url);
            System.out.println("Database connection established");
        }
        catch( SQLException ex)
        {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
        }
        catch(Exception ex)
        {
            System.out.println("Error : " + ex.getMessage());
        }
    }

    public short getMostRecentOutsideTemp()
    {
        return getMostRecentMeasurement().getOutsideTemp();
    }

    public short getMostRecentWindSpeed()
    {
        return getMostRecentMeasurement().getWindSpeed();
    }

    public short getMostRecentAvgWindSpeed()
    {
        return getMostRecentMeasurement().getAvgWindSpeed();
    }

    public short getMostRecentRainRate()
    {
        return getMostRecentMeasurement().getRainRate();
    }

    public short getMostRecentSunrise()
    {
        return getMostRecentMeasurement().getSunrise();
    }

    public short getMostRecentSunset()
    {
        return getMostRecentMeasurement().getSunset();
    }

    public short getMostRecentUVLevel()
    {
        return getMostRecentMeasurement().getUVLevel();
    }

    public short getMostRecentSolarRadiation()
    {
        return getMostRecentMeasurement().getSolarRad();
    }

    //MostRecent everything
    public RawMeasurement getMostRecentMeasurement()
    {
        RawMeasurement m = new RawMeasurement();
        try
        {
            // query:
            Statement s = myConn.createStatement();
            s.executeQuery("SELECT stationId, timestamp, " +
                    "barometer, " +
                    "insideTemp, " +
                    "insideHum, " +
                    "outsideTemp, " +
                    "windSpeed, " +
                    "avgWindSpeed, " +
                    "windDir, " +
                    "outsideHum, " +
                    "rainRate, " +
                    "UVLevel, " +
                    "solarRad, " +
                    "xmitBatt, " +
                    "battLevel, " +
                //  "foreIcon, " +
                    "sunrise, " +
                    "sunset " +
                    "FROM measurement order by measurementId desc limit 1");

            ResultSet rs = s.getResultSet();
            while( rs.next() )
            {
                m.setDateStamp( rs.getTimestamp(2));
                m.setOutsideTemp( Short.valueOf(rs.getString("outsideTemp")) );
                m.setWindSpeed( Short.valueOf(rs.getString("windSpeed")) );
                m.setAvgWindSpeed( Short.valueOf(rs.getString("avgWindSpeed")) );
                m.setRainRate( Short.valueOf(rs.getString("rainRate")) );
                m.setSunrise( Short.valueOf(rs.getString("sunrise")) );
                m.setSunset( Short.valueOf(rs.getString("sunset")) );
                m.setUVLevel( Short.valueOf(rs.getString("UVLevel")) );
                m.setSolarRad( Short.valueOf(rs.getString("solarRad")) );
            }
            rs.close();
            s.close();
        }
        catch( SQLException ex)
        {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
        }
        catch( Exception ex)
        {
                System.out.println("getMeasurement: " + ex.getMessage());
        }

        return m;
    }

    protected void finalize() throws Throwable
    {
        // Close database connection
        if( myConn != null )
        {
            try
            {
                myConn.close();
                System.out.println("Database connection terminated");
            }
            catch( Exception e ) {}
        }

        super.finalize();
    }

}