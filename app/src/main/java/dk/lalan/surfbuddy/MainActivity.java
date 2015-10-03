package dk.lalan.surfbuddy;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



//Inspired by: http://treyrobinson.net/blog/android-l-tutorials-part-3-recyclerview-and-cardview/
public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private CardviewAdapter mAdapter;

    public List<SurfLocation> favorites = new ArrayList<>();

    private Database db;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        //Inspiration: http://www.vogella.com/tutorials/AndroidServices/article.html
        @Override
        public void onReceive(Context context, Intent intent){
            if(intent.getAction().equals(WeatherService.WEATHER_UPDATE)) {
                for (SurfLocation sf : db.getAllLocations()) {
                    Toast.makeText(MainActivity.this, sf.getDescribtion(), Toast.LENGTH_SHORT).show();
                }
                favorites = db.getAllLocations();
                if(!favorites.isEmpty()) {
                    mAdapter = new CardviewAdapter(favorites, R.layout.main_activity_card_view, getApplicationContext());
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        db = new Database(getApplicationContext());
        db.clearDB();
        favorites = db.getAllLocations();

        if(!favorites.isEmpty()){
            mAdapter = new CardviewAdapter(favorites, R.layout.main_activity_card_view, this);
            mRecyclerView.setAdapter(mAdapter);
        }else{

        }
        db.addLocation("Klitmøller", 120, 1, 55.57, 10.09);

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
