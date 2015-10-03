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
import java.util.Collections;
import java.util.Comparator;

import models.SurfLocation;

public class BrowseActivity extends Activity {

    public BrowseService mService;
    public boolean mBound;
    private ListView listview;
    private RelativeLayout progress;
    private ProgressBar progressBar;
    private ArrayAdapter<SurfLocation> browserAdapter;
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
        browserAdapter = new BrowseListAdapter(this, R.layout.activity_browse_listview_element, locations);
        listview.setAdapter(browserAdapter);
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
            case R.id.browse_action_alphabetic:
                Toast.makeText(getApplicationContext(),"alphabetic", Toast.LENGTH_SHORT).show();
                Collections.sort(locations, NameComparator);
                browserAdapter.notifyDataSetChanged();
                return true;
            case R.id.browse_action_distance:
                Toast.makeText(getApplicationContext(),"distance", Toast.LENGTH_SHORT).show();
                Collections.sort(locations, DistComparator);
                browserAdapter.notifyDataSetChanged();
                return true;
            case R.id.browse_action_wind:
                Toast.makeText(getApplicationContext(),"wind", Toast.LENGTH_SHORT).show();
                Collections.sort(locations, WindComparator);
                browserAdapter.notifyDataSetChanged();
                return true;
            case R.id.browse_action_level:
                Collections.sort(locations, LevelComparator);
                browserAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"level", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.browse_action_refresh:
                mService.startFetcher();
                Toast.makeText(getApplicationContext(),"refresh", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.browse_action_search:
                Toast.makeText(getApplicationContext(),"search", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public static Comparator<SurfLocation> DistComparator = new Comparator<SurfLocation>() {

        public int compare(SurfLocation sf1, SurfLocation sf2) {

            double sf1Dist = sf1.getDist();
            double sf2Dist = sf2.getDist();

            if(sf1Dist == sf2Dist){
                return 0;
            }else if(sf1Dist > sf2Dist){
                return 1;
            }else{
                return -1;
            }
        }
    };

    public static Comparator<SurfLocation> NameComparator = new Comparator<SurfLocation>() {

        public int compare(SurfLocation sf1, SurfLocation sf2) {

            String sf1Name = sf1.getName();
            String sf2Name = sf2.getName();

            return sf1Name.compareToIgnoreCase(sf2Name);
        }
    };

    public static Comparator<SurfLocation> WindComparator = new Comparator<SurfLocation>() {

        public int compare(SurfLocation sf1, SurfLocation sf2) {

            double sf1Wind = sf1.getWindSpeed();
            double sf2Wind = sf2.getWindSpeed();

            if(sf1Wind == sf2Wind){
                return 0;
            }else if(sf1Wind > sf2Wind){
                return -1;
            }else{
                return 1;
            }
        }
    };

    public static Comparator<SurfLocation> LevelComparator = new Comparator<SurfLocation>() {

        public int compare(SurfLocation sf1, SurfLocation sf2) {

            int sf1Level = sf1.getLevel();
            int sf2Level = sf2.getLevel();

            if(sf1Level == sf1Level){
                return 0;
            }else if(sf1Level > sf1Level){
                return -1;
            }else{
                return 1;
            }
        }
    };
}
