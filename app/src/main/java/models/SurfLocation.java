package models;


/**
 * Created by lalan on 02/10/15.
 */
public class SurfLocation {
    public long id;
    public double longitude, latitude, windSpeed, temperatur, waveHeight, dist;
    public String name, describtion;
    public int windDir, surfDir, level;

    public SurfLocation() {
    }
}
