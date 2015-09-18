package dk.lalan.aarhweatherapp_group_5;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lalan on 18/09/15.
 */
public class WeatherService extends IntentService{

    private Thread servicecallthread;
    private JSONObject result;

    public WeatherService() {
        super("WeatherService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final int delay = Integer.valueOf(intent.getStringExtra("delay"))*1000;

        servicecallthread = new Thread() {
            public void run() {
                try {
                    sleep(delay);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getWeatherCall();
                this.run();
            }
        };
        this.servicecallthread.start();
    }

    public void getWeatherCall(){
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?id=2624652&units=metric");
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
            result = new JSONObject();

            result.put("location", responseObj.getString("name"));
            result.put("temp", responseObj.getJSONObject("main").getDouble("temp"));
            result.put("wind", responseObj.getJSONObject("wind").getDouble("speed"));
            result.put("desc", responseObj.getJSONArray("weather").getJSONObject(0).getString("description"));

            Log.i("****", result.toString());

            urlConnection.disconnect();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getWeather(){
        return result;
    }
}
