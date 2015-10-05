package dk.iha.itsmap.e15.grp5.surfbuddy;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class BrowseActivity extends AppCompatActivity {

    public BrowseService mService;
    public boolean mBound = false;
    private ListView listview;
    private RelativeLayout progress;
    private ProgressBar progressBar;
    private ArrayAdapter<SurfLocation> browserAdapter;
    private List<SurfLocation> locations;
    private TextView progressText;
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
                updateUI();
            } else if(intent.getAction().compareTo(mService.UPDATE_PROGRESS) == 0){
                Log.e("prog", ""+intent.getIntExtra("progress", 0));
                progressText.setText(""+intent.getIntExtra("progress", 0)+"%");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        progressText = (TextView) findViewById(R.id.browse_progress_text);

        progressBar = (ProgressBar) findViewById(R.id.browse_progressbar);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);

        progress = (RelativeLayout) findViewById(R.id.browse_progressbar_layout);
        listview = (ListView) findViewById(R.id.browse_listview);

    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter(mService.UPDATE_IS_COMMING);
        registerReceiver(mReceiver, filter);
        IntentFilter filter2 = new IntentFilter(mService.UPDATE_PROGRESS);
        registerReceiver(mReceiver, filter2);

        Intent intent = new Intent(this, BrowseService.class);
        bindService(intent, con, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mBound) {
            unbindService(con);
            unregisterReceiver(mReceiver);
            mBound = false;
        }
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
                progress.setVisibility(View.VISIBLE);
                listview.setVisibility(View.INVISIBLE);
                mService.startFetcher();
                Toast.makeText(getApplicationContext(),"refresh", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.browse_action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public static Comparator<SurfLocation> DistComparator = new Comparator<SurfLocation>() {

        public int compare(SurfLocation sf1, SurfLocation sf2) {

            double sf1Dist = sf1.getDistance();
            double sf2Dist = sf2.getDistance();

            if(sf1Dist == sf2Dist){
                return 0;
            }else if(sf1Dist > sf2Dist){
                return 1;
            }else{
                return -1;
            }
        }
    };

    private void updateUI() {
        locations = mService.getLocations();
        Collections.sort(locations, WindComparator);
        browserAdapter = new BrowseListAdapter(this, R.layout.activity_browse_listview_element, locations);
        listview.setAdapter(browserAdapter);
        progress.setVisibility(View.INVISIBLE);
        listview.setVisibility(View.VISIBLE);
    }

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

            if(sf1Level == sf2Level){
                return 0;
            }else if(sf1Level > sf2Level){
                return 1;
            }else{
                return -1;
            }
        }
    };
}
