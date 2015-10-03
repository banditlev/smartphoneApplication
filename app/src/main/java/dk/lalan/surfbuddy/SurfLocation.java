package dk.lalan.surfbuddy;

import android.util.Log;

/**
 * Created by lundtoft on 02/10/15.
 */
public class SurfLocation {
    private long id;
    private double longitude, latitude, windSpeed, temperatur, waveHeight, distance;
    private String name, describtion, locationDescription;
    private int windDir, surfDir, level;

    /**
     * Constructor that fills all information
     *  @param id
     * @param longitude
     * @param latitude
     * @param windSpeed
     * @param temperatur
     * @param waveHeight
     * @param name
     * @param describtion
     * @param level
     * @param windDir
     * @param surfDir
     * @param locationDescription
     */
    public SurfLocation(long id, Double longitude, Double latitude, Double windSpeed, Double temperatur, Double waveHeight, String name, String describtion, int level, int windDir, int surfDir, String locationDescription, double distance) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.windSpeed = windSpeed;
        this.temperatur = temperatur;
        this.waveHeight = waveHeight;
        this.name = name;
        this.describtion = describtion;
        this.level = level;
        this.windDir = windDir;
        this.surfDir = surfDir;
        this.locationDescription = locationDescription;
        this.distance = distance;
    }

    /**
     * Simple constructor that sets all variables to null
     */
    public SurfLocation(){
        this.id = 0;
        this.longitude = 0.0;
        this.latitude = 0.0;
        this.windSpeed = 0.0;
        this.temperatur = 0.0;
        this.waveHeight = 0.0;
        this.name = null;
        this.describtion = null;
        this.level = 0;
        this.windDir = 0;
        this.surfDir = 0;
        this.locationDescription = null;
        this.distance = 0.0;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getlatitude() {
        return latitude;
    }

    public void setlatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getTemperatur() {
        return temperatur;
    }

    public void setTemperatur(Double temperatur) {
        this.temperatur = temperatur;
    }

    public Double getWaveHeight() {
        return waveHeight;
    }

    public void setWaveHeight(Double waveHeight) {
        this.waveHeight = waveHeight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getWindDir() {
        return windDir;
    }

    public void setWindDir(int surfWind) {
        this.windDir = surfWind;
    }

    public int getSurfDir() {
        return surfDir;
    }

    public void setSurfDir(int surfDir) {
        this.surfDir = surfDir;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isSurfable(){
        int absDist = Math.abs(surfDir - windDir);

        if(absDist < 30){
            return true;
        }else{
            return false;
        }
    }
    public String getWindDirString(){
        String windString = "";
        int wind = getWindDir();
        if(wind >= 337 || wind < 22){
            windString = "N";
        }
        if(wind >= 22 && wind < 67){
            windString = "NE";
        }
        if(wind >= 67 && wind < 112){
            windString = "E";
        }
        if(wind >= 112 && wind < 157){
            windString = "SE";
        }
        if(wind >= 157 && wind < 202){
            windString = "S";
        }
        if(wind >= 202 && wind < 247){
            windString = "SW";
        }
        if(wind >= 247 && wind < 292){
            windString = "W";
        }
        if(wind >= 292 && wind < 337){
            windString = "NW";
        }
        return windString;
    }
}