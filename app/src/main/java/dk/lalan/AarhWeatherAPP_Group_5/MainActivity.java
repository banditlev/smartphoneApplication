package dk.lalan.aarhweatherapp_group_5;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import dk.lalan.aarhweatherapp_group_5.WeatherService.WeatherBinder;

public class MainActivity extends Activity {

    private Switch boundSwitch;
    private Button button;
    private WeatherService mService;
    private boolean mBound = false;
    private ServiceConnection con = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            WeatherBinder binder = (WeatherBinder) service;
            mService = binder.getService();
            mService.startWeatherCall();
            mBound = true;

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

        boundSwitch = (Switch) findViewById(R.id.bindswitch);
        boundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent intent = new Intent(getApplicationContext(), WeatherService.class);
                    // delay er en string fordi det nok skal kunne ændres af brugeren..
                    intent.putExtra("delay", "1");
                    bindService(intent, con, Context.BIND_AUTO_CREATE);
                    Log.i("****", "Bind Service ");
                    mBound = true;
                } else {
                    unbindService(con);
                    Log.i("****", "Unbind Service ");
                    mBound = false;
                }
            }
        });

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("****Tryk",mService.getWeather().toString());
            }
        });
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
        intent.putExtra("delay", "1");
        bindService(intent, con, Context.BIND_AUTO_CREATE);
        Log.i("****", "Bind Service ");
    }


}
