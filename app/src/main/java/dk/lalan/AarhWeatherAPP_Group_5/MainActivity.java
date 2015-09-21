package dk.lalan.aarhweatherapp_group_5;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Test;

import org.json.JSONException;
import org.json.JSONObject;

import dk.lalan.aarhweatherapp_group_5.WeatherService.WeatherBinder;

public class MainActivity extends Activity {

    private Switch boundSwitch;
    private EditText delay;
    private TextView location, temp, wind, desc;
    private WeatherService mService;
    private boolean mBound = false;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().compareTo(mService.UPDATE_IS_COMMING) == 0){
                //SetStuff
                Toast.makeText(getApplicationContext(), "Update Received", Toast.LENGTH_SHORT).show();
                updateUI();
            }
        }
    };

    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            WeatherBinder binder = (WeatherBinder) service;
            mService = binder.getService();
            mBound = true;
            mService.startWeatherCall();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location = (TextView) findViewById(R.id.location);
        temp = (TextView) findViewById(R.id.temp);
        desc = (TextView) findViewById(R.id.desc);
        wind = (TextView) findViewById(R.id.wind);
        delay = (EditText) findViewById(R.id.delay);

        IntentFilter filter = new IntentFilter(mService.UPDATE_IS_COMMING);
        registerReceiver(mReceiver, filter);

        boundSwitch = (Switch) findViewById(R.id.bindswitch);
        boundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent intent = new Intent(getApplicationContext(), WeatherService.class);
                    // delay er en string fordi det nok skal kunne ændres af brugeren..
                    intent.putExtra("delay", delay.getText().toString());
                    bindService(intent, con, Context.BIND_AUTO_CREATE);
                    Log.i("****", "Bind Service ");
                    mBound = true;
                } else {
                    unbindService(con);
                    mService = null;
                    Log.i("****", "Unbind Service ");
                    mBound = false;
                }
            }
        });



    }

    public void updateUI(){
        JSONObject currentWeather = mService.getWeather();
        try {
            location.setText(currentWeather.getString("location"));
            temp.setText(currentWeather.getString("temp").toString()+" °C");
            desc.setText(currentWeather.getString("desc"));
            wind.setText(currentWeather.getString("wind").toString()+" m/s");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, WeatherService.class);
        // delay er en string fordi det nok skal kunne ændres af brugeren..
        intent.putExtra("delay", delay.getText().toString());
        bindService(intent, con, Context.BIND_AUTO_CREATE);
        Log.i("****", "Bind Service ");
    }


}
