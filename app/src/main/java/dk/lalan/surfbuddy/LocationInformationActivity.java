package dk.lalan.surfbuddy;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LocationInformationActivity extends AppCompatActivity {

    private SurfLocation surfLocation;
    private boolean isFavorite;
    private FloatingActionButton fab;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_information);

        surfLocation = new SurfLocation();
        surfLocation.fillDataFromJson((getIntent().getStringExtra("surfLocation")));

        //Inspired by: http://www.androidrey.com/android-design-support-library-tablayout/
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(getResources().getColor(R.color.tabbar_menu_dim), Color.WHITE);

        setTitle(surfLocation.getName());

        if(findViewById(R.id.large_mapview) != null){
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            if (viewPager != null) {
                setupViewPagerTablet(viewPager);
            }
            tabLayout.setupWithViewPager(viewPager);

            FrameLayout largeMapView = (FrameLayout) findViewById(R.id.large_mapview);
            Fragment f = new MapFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.large_mapview, f).commit();

            getSupportActionBar().setElevation(12);

        }else{
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
            if (viewPager != null) {
                setupViewPagerPhone(viewPager);
            }
            tabLayout.setupWithViewPager(viewPager);

            getSupportActionBar().setElevation(0);
        }




        fab = (FloatingActionButton) findViewById(R.id.favBtn);


        db = new Database(getApplicationContext());

        for(SurfLocation sl : db.getAllLocations()){
            if(sl.getName().equals(surfLocation.getName())){
                isFavorite = true;
                fab.setImageDrawable(getDrawable(R.drawable.ic_favorite_white_24dp));
                surfLocation.setId(sl.getId());
            }
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavorite){
                    fab.setImageDrawable(getDrawable(R.drawable.ic_favorite_border_white_24dp));
                    Toast.makeText(getApplicationContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                    db.removeLocation(surfLocation.getId());
                    isFavorite = false;
                }else {
                    fab.setImageDrawable(getDrawable(R.drawable.ic_favorite_white_24dp));
                    Toast.makeText(getApplicationContext(), "Added to favorites!", Toast.LENGTH_SHORT).show();
                    surfLocation.setId(db.addLocation(surfLocation.getName(), surfLocation.getSurfDir(), surfLocation.getLevel(), surfLocation.getlatitude(), surfLocation.getLongitude()));
                    isFavorite = true;
                }
            }
        });

    }

    //Inspiration from: https://github.com/chrisbanes/cheesesquare
    private void setupViewPagerPhone(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new DataFragment(), "Data");
        adapter.addFragment(new InformationFragment(), "Information");
        adapter.addFragment(new MapFragment(), "Map");
        viewPager.setAdapter(adapter);
    }
    private void setupViewPagerTablet(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new DataFragment(), "Data");
        adapter.addFragment(new InformationFragment(), "Information");
        viewPager.setAdapter(adapter);
    }

    public SurfLocation getSurfLocation() {
        return surfLocation;
    }

    //Inspiration from: https://github.com/chrisbanes/cheesesquare
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
