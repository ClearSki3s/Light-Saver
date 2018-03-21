package WeatherstationControl;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;


public class WeatherStation
{

    private Connection myConn = null;

    /**
    * Connect naar de weerstation database
    *
    * @since     1.0
    */
    public WeatherStation()
    {
        this("145.48.203.28","5329","aws_data","aws","aws");
    }

    /**
    * Connect naar de weerstation database
    *
    * @param host   IP adres van database of 'localhost'
    * @param port   Port van mysql daemon (default 3306)
    * @param dbName Naam van de database
    * @since     1.0
    */
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


        /**
    * Lees meest recente (uit db) barometer
        *
        * @return   <code>barometer value [inches]</code>
        * @since     1.0
        */
    public short getMostRecentBarometer()
    {
        return getMostRecentMeasurement().getBarometer();

    }

        /**
    * Lees meest recente (uit db) binnen temperatuur
        *
        * @return   <code>InsideTemp value [F]</code>
        * @since        1.0
        */
    public short getMostRecentInsideTemp()
    {
        return getMostRecentMeasurement().getInsideTemp();

    }

        /**
    * Lees meest recente (uit db) binnen relatieve luchtvochtigheid
        *
        * @return   <code>InsideHum value [%]</code>
        * @since        1.0
        */
    public short getMostRecentInsideHum()
    {
        return getMostRecentMeasurement().getInsideHum();

    }

        /**
    * Lees meest recente (uit db) buiten temperatuur
        *
        * @return   <code>InsideTemp value [F]</code>
        * @since        1.0
        */
    public short getMostRecentOutsideTemp()
    {
        return getMostRecentMeasurement().getOutsideTemp();
    }

        /**
    * Lees meest recente (uit db) windsnelheid
        *
        * @return   <code>Windspeed value [m/s]</code>
        * @since        1.0
        */
    public short getMostRecentWindSpeed()
    {
        return getMostRecentMeasurement().getWindSpeed();

    }

        /**
    * Lees meest recente (uit db) gemiddelde windsnelheid
        *
        * @return   <code>Average windspeed [m/s]</code>
        * @since        1.0
        */
    public short getMostRecentAvgWindSpeed()
    {
        return getMostRecentMeasurement().getAvgWindSpeed();

    }

        /**
    * Lees meest recente (uit db) windrichting
        *
        * @return   <code>winddir [m/s]</code>
        * @since        1.0
        */
    public short getMostRecentWindDir()
    {
        return getMostRecentMeasurement().getWindDir();

    }

        /**
    * Lees meest recente (uit db) buiten relatieve luchtvochtigheid
        *
        * @return   <code>OutsideHum [%]</code>
        * @since        1.0
        */
    public short getMostRecentOutsideHum()
    {
        return getMostRecentMeasurement().getOutsideHum();
    }

        /**
    * Lees meest recente (uit db) rainrate
        *
        * @return   <code>rainrate [2mm clicks]</code>
        * @since        1.0
        */
    public short getMostRecentRainRate()
    {
        return getMostRecentMeasurement().getRainRate();
    }

        /**
    * Lees meest recente (uit db) UV level
        *
        * @return   <code>UV level</code>
        * @since        1.0
        */
    public short getMostRecentUVLevel()
    {
        return getMostRecentMeasurement().getUVLevel();
    }

        /**
    * Lees meest recente (uit db) Solar Radiation
        *
        * @return   <code>Solar Radiation</code>
        * @since        1.0
        */
    public short getMostRecentSolarRadiation()
    {
        return getMostRecentMeasurement().getSolarRad();
    }

        /**
    * Lees meest recente (uit db) xmitBatt
        *
        * @return   <code>xmitBatt</code>
        * @since        1.0
        */
    public short getMostRecentXmitBatt()
    {
        return getMostRecentMeasurement().getXmitBatt();
    }

        /**
    * Lees meest recente (uit db) battLevel
        *
        * @return   <code>battLevel</code>
        * @since        1.0
        */
    public short getMostRecentBattLevel()
    {
        return getMostRecentMeasurement().getBattLevel();
    }

        /**
    * Lees meest recente (uit db) sunrise
        *
        * @return   <code>sunrise</code>
        * @since        1.0
        */
    public short getMostRecentSunrise()
    {
        return getMostRecentMeasurement().getSunrise();

    }

    public short getMostRecentSunset()
    {
        return getMostRecentMeasurement().getSunset();

    }

        /**
    * Lees uit db alle buitentemperaturen
    *
        * @return   <code>short[]</code>
        * @since        1.0
        */
    public short[] getAllOutsideTemp()
    {
        ArrayList<RawMeasurement> mArr = getAllMeasurements();
        short[] values = new short[mArr.size()];
        int count = 0;
        for(RawMeasurement m: mArr )
        {
            values[count++] = m.getOutsideTemp();
        }
        return values;
    }

        /**
    * Lees uit db alle barometer waarden
    *
        * @return   <code>short[]</code>
        * @since        1.0
        */
    public short[] getAllBarometer()
    {
        ArrayList<RawMeasurement> mArr = getAllMeasurements();
        short[] values = new short[mArr.size()];
        int count = 0;
        for(RawMeasurement m: mArr )
        {
            values[count++] = m.getBarometer();
        }
        return values;
    }



        /**
    * Lees meest recente meeting (uit db)
        *
        * @return   <code>RawMeasurement</code>
        * @since        1.0
        */
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
                m.setStationId( rs.getString("stationId") );
                m.setDateStamp( rs.getTimestamp(2));
                m.setBarometer( Short.valueOf(rs.getString("barometer")) );
                m.setInsideTemp( Short.valueOf(rs.getString("insideTemp")) );
                m.setInsideHum( Short.valueOf(rs.getString("insideHum")) );
                m.setOutsideTemp( Short.valueOf(rs.getString("outsideTemp")) );
                m.setWindSpeed( Short.valueOf(rs.getString("windSpeed")) );
                m.setAvgWindSpeed( Short.valueOf(rs.getString("avgWindSpeed")) );
                m.setWindDir( Short.valueOf(rs.getString("windDir")) );
                m.setOutsideHum( Short.valueOf(rs.getString("outsideHum")) );
                m.setRainRate( Short.valueOf(rs.getString("rainRate")) );
                m.setUVLevel( Short.valueOf(rs.getString("UVLevel")) );
                m.setSolarRad( Short.valueOf(rs.getString("solarRad")) );
                m.setXmitBatt( Short.valueOf(rs.getString("xmitBatt")) );
                m.setBattLevel( Short.valueOf(rs.getString("battLevel")) );
//              m.setForeIcon( Short.valueOf(rs.getString("foreIcon")) );
                m.setSunrise( Short.valueOf(rs.getString("sunrise")) );
                m.setSunset( Short.valueOf(rs.getString("sunset")) );
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


    private ArrayList<RawMeasurement> getMeasurementsFromQuery(String query)
    {
        ArrayList<RawMeasurement> mArr = new ArrayList<RawMeasurement>();

        try
        {
            // query:
            Statement s = myConn.createStatement();
            s.executeQuery(query);

            ResultSet rs = s.getResultSet();
            while( rs.next() )
            {
                RawMeasurement m = new RawMeasurement();

                m.setStationId( rs.getString("stationId") );
                m.setDateStamp( rs.getTimestamp(2));
                m.setBarometer( Short.valueOf(rs.getString("barometer")) );
                m.setInsideTemp( Short.valueOf(rs.getString("insideTemp")) );
                m.setInsideHum( Short.valueOf(rs.getString("insideHum")) );
                m.setOutsideTemp( Short.valueOf(rs.getString("outsideTemp")) );
                m.setWindSpeed( Short.valueOf(rs.getString("windSpeed")) );
                m.setAvgWindSpeed( Short.valueOf(rs.getString("avgWindSpeed")) );
                m.setWindDir( Short.valueOf(rs.getString("windDir")) );
                m.setOutsideHum( Short.valueOf(rs.getString("outsideHum")) );
                m.setRainRate( Short.valueOf(rs.getString("rainRate")) );
                m.setUVLevel( Short.valueOf(rs.getString("UVLevel")) );
                m.setSolarRad( Short.valueOf(rs.getString("solarRad")) );
                m.setXmitBatt( Short.valueOf(rs.getString("xmitBatt")) );
                m.setBattLevel( Short.valueOf(rs.getString("battLevel")) );
//              m.setForeIcon( Short.valueOf(rs.getString("foreIcon")) );
                m.setSunrise( Short.valueOf(rs.getString("sunrise")) );
                m.setSunset( Short.valueOf(rs.getString("sunset")) );
                mArr.add(m);
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

        return mArr;
    }
    
    
    /**
    * Construct ArrayList van Measurement objecten
        *
        * @return   <code>RawMeasurement</code>
        * @since        1.0
        */
    public ArrayList<RawMeasurement> getAllMeasurements()
    {
    	return getMeasurementsFromQuery("SELECT stationId, timestamp, " +
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
                    "FROM measurement");
    }

    /**
    * Retourneer alle measurement objecten tussen twee datums
    * @params d1    Begin datum
    * @params d2    Eind datum. d2 >= d1
    * @return   <code>RawMeasurement</code>
    * @since        1.0
    */
    public ArrayList<RawMeasurement> getAllMeasurementsBetween(LocalDate d1, LocalDate d2)
    {
    	String sd1 = d1.toString() + " 00:00:00";
    	String sd2 = d2.toString() + " 23:59:59";    	
    
    	return getMeasurementsFromQuery("SELECT stationId, timestamp, " +
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
                    "FROM measurement where timestamp between " +
                    "'" + sd1 + "' and '" + sd2 + "'");
    }
    
    
    /**
    * Retourneer alle measurement objecten tussen twee datums
    * @params d1    Begin datum, als "YYYY-MM-DD"
    * @params d2    Eind datum. d2 >= d1, als "YYYY-MM-DD"
    * @return   <code>RawMeasurement</code>
    * @since        1.0
    */
    public ArrayList<RawMeasurement> getAllMeasurementsBetween(String d1, String d2)
    {
    	String sd1 = d1 + " 00:00:00";
    	String sd2 = d2 + " 23:59:59";    	
    
    	return getMeasurementsFromQuery("SELECT stationId, timestamp, " +
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
                    "FROM measurement where timestamp between " +
                    "'" + sd1 + "' and '" + sd2 + "'");
    }
    
    /**
    * Retourneer alle measurement objecten van de afgelopen 24 uur
    *
    * @return   <code>RawMeasurement</code>
    * @since        1.0
    */
    public ArrayList<RawMeasurement> getAllMeasurementsLast24h()
    {

        return getAllMeasurementsLastHours(24);
    }


    /**
    * Retourneer alle measurement objecten tussen nu en nu minus hour
        * @params hour  Tijdspanne in uren tussen nu en hour.
        * @return   <code>RawMeasurement</code>
        * @since        1.0
        */
    public ArrayList<RawMeasurement> getAllMeasurementsLastHours(int hour)
    {

      return getMeasurementsFromQuery("SELECT stationId, timestamp, " +
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
                    "FROM measurement where timestamp between NOW() - INTERVAL " +
                    hour + " HOUR and NOW()");
    }


    /**
    * Retourneer alle measurement objecten tussen nu en nu minus days dagen
        * @params days  Tijdspanne in dagen tussen nu en days.
        * @return   <code>RawMeasurement</code>
        * @since        1.0
        */
    public ArrayList<RawMeasurement> getAllMeasurementsLastDays(int days)
    {

      return getMeasurementsFromQuery("SELECT stationId, timestamp, " +
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
                    "FROM measurement where timestamp between NOW() - INTERVAL " +
                    days + " DAY and NOW()");
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