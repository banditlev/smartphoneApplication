package models;

/**
 * Created by lalan on 02/10/15.
 */
public class SurfLocation {
    public double longitude, latitude, windSpeed, temperatur, waveHeight;
    public String name, describtion, level;
    public int windDir, surfDir;

    public SurfLocation(Double longitude, Double latitude, Double windSpeed, Double temperatur, Double waveHeight, String name, String describtion, String level, int windDir, int surfDir) {
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
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
}
