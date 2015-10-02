package models;

/**
 * Created by lalan on 02/10/15.
 */
public class SurfLocation {
    public Double longitude, latetude, windSpeed, temperatur, waveHeight;
    public String name, describtion, level;
    public int surfWindFrom, surfwindTo;
    public Boolean surfAble;

    public SurfLocation(Double longitude, Double latetude, Double windSpeed, Double temperatur, Double waveHeight, String name, String describtion, String level, int surfWindFrom, int surfwindTo, Boolean surfAble) {
        this.longitude = longitude;
        this.latetude = latetude;
        this.windSpeed = windSpeed;
        this.temperatur = temperatur;
        this.waveHeight = waveHeight;
        this.name = name;
        this.describtion = describtion;
        this.level = level;
        this.surfWindFrom = surfWindFrom;
        this.surfwindTo = surfwindTo;
        this.surfAble = surfAble;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatetud() {
        return latetude;
    }

    public void setLatetud(Double latetud) {
        this.latetude = latetud;
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

    public int getSurfWindFrom() {
        return surfWindFrom;
    }

    public void setSurfWindFrom(int surfWindFrom) {
        this.surfWindFrom = surfWindFrom;
    }

    public int getSurfwindTo() {
        return surfwindTo;
    }

    public void setSurfwindTo(int surfwindTo) {
        this.surfwindTo = surfwindTo;
    }

    public Boolean getSurfAble() {
        return surfAble;
    }

    public void setSurfAble(Boolean surfAble) {
        this.surfAble = surfAble;
    }
}
