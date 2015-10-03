package dk.lalan.surfbuddy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class LocationInformationActivity extends AppCompatActivity {

    private SurfLocation surfLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_information);

        surfLocation = new SurfLocation();
        surfLocation.fillDataFromJson((getIntent().getStringExtra("surfLocation")));

        //Inspired by: http://www.androidrey.com/android-design-support-library-tablayout/
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        getSupportActionBar().setElevation(0);
        tabLayout.setElevation(12);
        tabLayout.setTabTextColors(getResources().getColor(R.color.tabbar_menu_dim), Color.WHITE);

        setTitle(surfLocation.getName());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        tabLayout.setupWithViewPager(viewPager);

    }

    //Inspiration from: https://github.com/chrisbanes/cheesesquare
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new DataFragment(), "Data");
        adapter.addFragment(new InformationFragment(), "Information");
        adapter.addFragment(new MapFragment(), "Map");
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
