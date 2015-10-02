package dk.lalan.surfbuddy;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import models.SurfLocation;

public class MainActivity extends Activity {

    private Database db;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        //Inspiration: http://www.vogella.com/tutorials/AndroidServices/article.html
        @Override
        public void onReceive(Context context, Intent intent){
            if(intent.getAction().equals(WeatherService.WEATHER_UPDATE)) {
                for (SurfLocation sf : db.getAllLocations()) {
                    Toast.makeText(MainActivity.this, sf.getDescribtion(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(getApplicationContext());

        db.clearDB();
        //long id = db.addLocation("Klitmøller", 90, "Hardcore!", 57.12, 8.62);

        Intent bgService = new Intent(getApplicationContext(), WeatherService.class);
        startService(bgService);

        IntentFilter intentFilter = new IntentFilter(WeatherService.WEATHER_UPDATE);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
