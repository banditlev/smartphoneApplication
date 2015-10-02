package dk.lalan.surfbuddy;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import models.SurfLocation;

/**
 * Created by lundtoft on 02/10/15.
 */
public class WeatherService extends IntentService {

    private Thread servicecallthread;
    private Database db;
    private Intent broadcastIntent;
    public static final String WEATHER_UPDATE = "dk.lalan.surfbuddy.weatherupdate";

    public WeatherService() {
        super("WeatherService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        db = new Database(getApplicationContext());

        servicecallthread = new Thread() {
            public void run() {
                fetchWeather();
                try {
                    sleep(5000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.run();
            }
        };
        this.servicecallthread.start();
    }

    public void fetchWeather(){
        try {

            for(SurfLocation sf : db.getAllLocations()) {

                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?units=metric&lat=" + sf.getlatitude() + "&lon=" + sf.getLongitude());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                //Log.i("****", response.toString());

                JSONObject responseObj = new JSONObject(response.toString());

                int direction = (int) responseObj.getJSONObject("wind").getDouble("deg");
                double wind = responseObj.getJSONObject("wind").getDouble("speed");
                double temp = responseObj.getJSONObject("main").getDouble("temp");
                double waveHeight = responseObj.getJSONObject("main").getDouble("sea_level");
                String desc = responseObj.getJSONArray("weather").getJSONObject(0).getString("description");

                db.updateLocation(sf.getId(), direction, wind, temp, waveHeight, desc);

                urlConnection.disconnect();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        broadcastIntent = new Intent(WEATHER_UPDATE);
        sendBroadcast(broadcastIntent);
    }

}
