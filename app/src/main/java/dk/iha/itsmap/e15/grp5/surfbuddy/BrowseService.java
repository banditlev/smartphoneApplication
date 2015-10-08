package dk.iha.itsmap.e15.grp5.surfbuddy;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lalan on 02/10/15.
 */
public class BrowseService extends Service {


    public static final String UPDATE_IS_COMMING = "dk.iha.itsmap.e15.grp5.surfbuddy.browseservice.UPDATE_IS_COMMING";
    public static final String UPDATE_PROGRESS = "dk.iha.itsmap.e15.grp5.surfbuddy.browseservice.UPDATE_PROGRESS";
    private final IBinder iBinder = new BrowseBinder();
    private Thread servicecallthread;
    private List<SurfLocation> locations;
    private Location myLocation, surfLocation;
    private String provider;
    private LocationManager locationManager;
    private boolean alive;

    @Override
    public IBinder onBind(Intent intent) {
        initLocations();
        //LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //Log.i("***", "LOCATION - long: " + myLocation.getLongitude() + " lat: " + myLocation.getLatitude());

        //Inspired from: http://www.vogella.com/tutorials/AndroidDrawables/article.html
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        myLocation = locationManager.getLastKnownLocation(provider);

        if(myLocation == null){
            myLocation = new Location("");
            myLocation.setLatitude(56.171981);
            myLocation.setLongitude(10.190967);
        }

        alive = true;

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

            int count = 0;
            for(SurfLocation sf : locations) {
                if (alive) {
                    StringBuilder response = new StringBuilder();

                    try {
                        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?units=metric" +
                                "&lat=" + sf.getlatitude() + "&lon=" + sf.getLongitude());
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }

                        urlConnection.disconnect();
                    } catch (Exception e) {
                        response.append("{\"coord\":{\"lon\":10.17,\"lat\":56.16},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"base\":\"stations\",\"main\":{\"temp\":13.53,\"pressure\":1015,\"humidity\":71,\"temp_min\":13,\"temp_max\":14},\"wind\":{\"speed\":9.8,\"deg\":90,\"gust\":14.9},\"rain\":{\"3h\":0.2},\"clouds\":{\"all\":92},\"dt\":1444119960,\"sys\":{\"type\":1,\"id\":5241,\"message\":0.0121,\"country\":\"DK\",\"sunrise\":1444109537,\"sunset\":1444149690},\"id\":2624647,\"name\":\"Århus Kommune\",\"cod\":200}");
                    }


                    Log.i("****", response.toString());

                    JSONObject responseObj = new JSONObject(response.toString());

                    int direction;
                    try {
                        direction = (int) responseObj.getJSONObject("wind").getDouble("deg");
                    } catch (JSONException e) {
                        direction = 0;
                    }

                    double wind = responseObj.getJSONObject("wind").getDouble("speed");
                    double temp = responseObj.getJSONObject("main").getDouble("temp");
                    String desc = responseObj.getJSONArray("weather").getJSONObject(0).getString("description");

                    sf.setWindDir(direction);
                    sf.setWindSpeed(wind);
                    sf.setTemperatur(temp);
                    sf.setDescribtion(desc);

                    surfLocation = new Location("");
                    surfLocation.setLongitude(sf.getLongitude());
                    surfLocation.setLatitude(sf.getlatitude());
                    double dist = myLocation.distanceTo(surfLocation) / 1000;
                    dist = Math.round(dist * 100);
                    dist = dist / 100;
                    sf.setDistance(dist);

                    String date = new SimpleDateFormat("EEE, HH:mm").format(new Date());
                    sf.setUpdated(date);

                    Intent broadcastIntent = new Intent(UPDATE_PROGRESS);
                    int progress = count * 100 / locations.size();
                    broadcastIntent.putExtra("progress", progress);
                    sendBroadcast(broadcastIntent);
                    count++;

                    //break;
                }
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

    public void kill(){
        alive = false;
    }

    public class BrowseBinder extends Binder {
        BrowseService getService(){
            return BrowseService.this;
        }
    }

}