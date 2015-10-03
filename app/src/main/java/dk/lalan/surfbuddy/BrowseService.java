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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import models.SurfLocation;

/**
 * Created by lalan on 02/10/15.
 */
public class BrowseService extends Service {


    public static final String UPDATE_IS_COMMING = "dk.lalan.surfbuddy.browseservice.UPDATE_IS_COMMING";
    private final IBinder iBinder = new BrowseBinder();
    private Thread servicecallthread;
    private List<SurfLocation> locations;
    private Location myLocation, surfLocation;

    @Override
    public IBinder onBind(Intent intent) {
        initLocations();
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        myLocation = lm.getLastKnownLocation
                (LocationManager.GPS_PROVIDER);
        Log.i("***", "LOCATION - long: " + myLocation.getLongitude() + " lat: " + myLocation.getLatitude());
        return iBinder;
    }

    private void initLocations() {
        SurfFileReader sfr = new SurfFileReader(getApplicationContext());
        locations = sfr.getAllLocations();


    }

    public void startFetcher() {
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

                int direction;
                try{
                    direction = (int) responseObj.getJSONObject("wind").getDouble("deg");
                }catch (JSONException e){
                    direction = 0;
                }

                double wind = responseObj.getJSONObject("wind").getDouble("speed");
                double temp = responseObj.getJSONObject("main").getDouble("temp");

                sf.setWindDir(direction);
                sf.setWindSpeed(wind);
                sf.setTemperatur(temp);

                surfLocation = new Location("");
                surfLocation.setLongitude(sf.getLongitude());
                surfLocation.setLatitude(sf.getlatitude());
                double dist = myLocation.distanceTo(surfLocation) / 1000;
                dist = Math.round(dist * 100);
                dist = dist / 100;
                sf.setDistance(dist);

                urlConnection.disconnect();
                //break;
            }
            Intent i = new Intent(UPDATE_IS_COMMING);
            sendBroadcast(i);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<SurfLocation> getLocations() {
        return locations;
    }

    public class BrowseBinder extends Binder {
        BrowseService getService(){
            return BrowseService.this;
        }
    }
}