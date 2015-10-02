package dk.lalan.surfbuddy;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import models.SurfLocation;

public class BrowseActivity extends Activity {

    public BrowseService mService;
    public boolean mBound;
    private ListView listview;
    private RelativeLayout progress;
    private ProgressBar progressBar;
    private ArrayList<SurfLocation> locations;
    private ServiceConnection con = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BrowseService.BrowseBinder binder = (BrowseService.BrowseBinder) service;
            mService = binder.getService();
            mService.startFetcher();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().compareTo(mService.UPDATE_IS_COMMING) == 0){
                //SetStuff
                //Toast.makeText(getApplicationContext(), "Update Received", Toast.LENGTH_SHORT).show();
                updateUI();

            }
        }
    };







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        locations = null;

        progressBar = (ProgressBar) findViewById(R.id.browse_progressbar);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);

        progress = (RelativeLayout) findViewById(R.id.browse_progressbar_layout);
        listview = (ListView) findViewById(R.id.browse_listview);

        IntentFilter filter = new IntentFilter(mService.UPDATE_IS_COMMING);
        registerReceiver(mReceiver, filter);

        Intent intent = new Intent(this, BrowseService.class);
        bindService(intent, con, Context.BIND_AUTO_CREATE);


    }

    private void updateUI() {
        locations = mService.getLocations();
        listview.setAdapter(new BrowseListAdapter(this, R.layout.activity_browse_listview_element, locations));
        progress.setVisibility(View.INVISIBLE);
        listview.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.browse_action_distance:
                Toast.makeText(getApplicationContext(),"distance", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.browse_action_wind:
                Toast.makeText(getApplicationContext(),"wind", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.browse_action_level:
                Toast.makeText(getApplicationContext(),"level", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.browse_action_refresh:
                mService.fetchWeather();
                Toast.makeText(getApplicationContext(),"refresh", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.browse_action_search:
                Toast.makeText(getApplicationContext(),"search", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
