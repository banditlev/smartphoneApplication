package dk.lalan.surfbuddy;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import models.SurfLocation;

/**
 * Created by lalan on 02/10/15.
 */
public class BrowseService extends Service {


    public static final String UPDATE_IS_COMMING = "dk.lalan.surfbuddy.browseservice.UPDATE_IS_COMMING";
    private final IBinder iBinder = new BrowseBinder();
    private Thread servicecallthread;
    private ArrayList<SurfLocation> locations;
    private Location myLocation, surfLocation;

    @Override
    public IBinder onBind(Intent intent) {
        initLocations();
        myLocation = new Location("");
        myLocation.setLatitude(56.15);
        myLocation.setLongitude(10.17);
        return iBinder;
    }

    private void initLocations() {
        locations = new ArrayList<>();
        locations.add(new SurfLocation(0, 8.62, 57.12, 0.0, 0.0, 0.0, "Klitmøller", null, null, 0, 0));
        locations.add(new SurfLocation(0, 10.31, 55.22, 0.0, 0.0, 0.0, "Skæring", null, null, 0, 0));
    }

    public void startFetcher() {
        Log.e("***", "TEST");
        servicecallthread = new Thread() {
            public void run() {
                fetchWeather();
            }
        };
        this.servicecallthread.start();
    }

    public void fetchWeather(){
        try {

            for(SurfLocation sf : locations) {

                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?units=metric&lat=" + sf.getlatitude() + "&lon=" + sf.getLongitude());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                Log.i("****", response.toString());

                JSONObject responseObj = new JSONObject(response.toString());

                int direction = (int) responseObj.getJSONObject("wind").getDouble("deg");
                double wind = responseObj.getJSONObject("wind").getDouble("speed");
                double temp = responseObj.getJSONObject("main").getDouble("temp");
                double waveHeight = responseObj.getJSONObject("main").getDouble("sea_level");

                sf.setWindDir(direction);
                sf.setWindSpeed(wind);
                sf.setTemperatur(temp);
                sf.setWaveHeight(waveHeight);

                surfLocation = new Location("");
                surfLocation.setLongitude(sf.getLongitude());
                surfLocation.setLatitude(sf.getlatitude());
                double dist = Math.round(myLocation.distanceTo(surfLocation) / 1000);
                sf.setDist(dist);
                //sf.setDist(myLocation.distanceTo(surfLocation));

                urlConnection.disconnect();
            }
            Intent i = new Intent(UPDATE_IS_COMMING);
            sendBroadcast(i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SurfLocation> getLocations() {
        return locations;
    }

    public class BrowseBinder extends Binder {
        BrowseService getService(){
            return BrowseService.this;
        }
    }
}