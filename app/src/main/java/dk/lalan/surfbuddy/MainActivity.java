package dk.lalan.surfbuddy;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

    public WeatherService mService;
    public boolean mBound;
    private ServiceConnection con = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            WeatherService.WeatherBinder binder = (WeatherService.WeatherBinder) service;
            mService = binder.getService();
            mService.startFetcher();
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

        Intent intent = new Intent(this, WeatherService.class);
        intent.putExtra("delay", "5");
        bindService(intent, con, Context.BIND_AUTO_CREATE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
