package WeatherstationControl;

public class RawMeasurement
{

    private String stationId;
    private java.sql.Timestamp dateStamp;
    private short outsideTemp;
    private short windSpeed;
    private short avgWindSpeed;
    private short rainRate;
    private short sunrise;
    private short sunset;
    private short UVLevel;
    private short solarRad;
    
    public RawMeasurement()
    {
    
    }

    // dateStamp
    public void setDateStamp (java.sql.Timestamp ts) { this.dateStamp = ts;};
    public java.sql.Timestamp getDateStamp () { return dateStamp; };

    // outsideTemp
    public void setOutsideTemp (short val) { this.outsideTemp = val;};
    public short getOutsideTemp () { return outsideTemp; };

    // windSpeed
    public void setWindSpeed (short val) { this.windSpeed = val;};
    public short getWindSpeed () { return windSpeed; };

    // avgWindSpeed
    public void setAvgWindSpeed (short val) { this.avgWindSpeed = val;};
    public short getAvgWindSpeed () { return avgWindSpeed; };

    // rainRate
    public void setRainRate (short val) { this.rainRate = val;};
    public short getRainRate () { return rainRate; };

    // sunrise
    public void setSunrise (short val) { this.sunrise = val;};
    public short getSunrise () { return sunrise; };

    // sunset
    public void setSunset (short val) { this.sunset = val;};
    public short getSunset () { return sunset; };

    // UVLevel
    public void setUVLevel (short val) { this.UVLevel = val;};
    public short getUVLevel () { return UVLevel; };

    // solarRad
    public void setSolarRad (short val) { this.solarRad = val;};
    public short getSolarRad () { return solarRad; };

    public String toString()
    {
        String s = "RawMeasurement:"
            + "\nstationId = " + stationId
            + "\ndateStamp = " + dateStamp
            + "\noutsideTemp = " + outsideTemp
            + "\nwindSpeed = " + windSpeed
            + "\navgWindSpeed = " + avgWindSpeed
            + "\nrainRate = " + rainRate
            + "\nsunrise = " + sunrise
            + "\nsunset = " + sunset
            + "\nuvlevel = " + UVLevel;
        return s; 
    } 
    
    
}
