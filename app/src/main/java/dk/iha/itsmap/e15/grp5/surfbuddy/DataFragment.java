package dk.iha.itsmap.e15.grp5.surfbuddy;


import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;




/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment {
    private TextView windDirection, windSpeed, temp, distance, updated, description;
    private ImageView windSurfDirection, surfDirection;
    private LocationManager locationManager;
    private String provider;
    private Location myLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        SurfLocation sf = ((LocationInformationActivity) getActivity()).getSurfLocation();

        windDirection = (TextView) view.findViewById(R.id.data_fragment_winddirection_textView);
        windSpeed = (TextView) view.findViewById(R.id.data_fragment_windspeed_textview);
        temp = (TextView) view.findViewById(R.id.data_fragment_temp_textview);
        distance = (TextView) view.findViewById(R.id.data_fragment_dist_textview);
        updated = (TextView) view.findViewById(R.id.data_fragment_update);
        description = (TextView) view.findViewById(R.id.data_fragment_conditions_textview);

        surfDirection = (ImageView) view.findViewById(R.id.data_fragment_surfdirection_imageview);
        windSurfDirection = (ImageView) view.findViewById(R.id.data_fragment_windsurfdirection_imageview);

        windDirection.setText(sf.getWindDirString());
        windSpeed.setText(sf.getWindSpeed() + " " + this.getResources().getString(R.string.knots));
        temp.setText(sf.getTemperatur() + " ºC");

        if (sf.getDistance() == 0.0){
            Location surfLocation = new Location("");
            surfLocation.setLongitude(sf.getLongitude());
            surfLocation.setLatitude(sf.getlatitude());

            //Location myLocation = new Location("");
            //myLocation.setLatitude(56.15);
            //myLocation.setLongitude(10.20);

            locationManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);
            myLocation = locationManager.getLastKnownLocation(provider);

            if(myLocation == null){
                myLocation = new Location("");
                myLocation.setLatitude(56.171981);
                myLocation.setLongitude(10.190967);
            }

            double dist = myLocation.distanceTo(surfLocation) / 1000;
            dist = Math.round(dist * 100);
            dist = dist / 100;
            sf.setDistance(dist);
        }

        distance.setText(sf.getDistance() + " km");
        description.setText(this.getResources().getString(R.string.conditions_is) + " " + sf.getDescribtion());
        updated.setText(sf.getUpdated());

        if (sf.isSurfable()){
            windSurfDirection.setImageDrawable(this.getResources().getDrawable(R.drawable.arrow_green));
        }else{
            windSurfDirection.setImageDrawable(this.getResources().getDrawable(R.drawable.arrow_red));
        }

        float pivotX = windSurfDirection.getLayoutParams().width/2;
        float pivotY = windSurfDirection.getLayoutParams().height/2;
        RotateAnimation animArrow = new RotateAnimation(0,  -(360-sf.getWindDir()), pivotX, pivotY);
        animArrow.setInterpolator(new LinearInterpolator());
        animArrow.setFillAfter(true);
        animArrow.setDuration(2000);
        windSurfDirection.startAnimation(animArrow);

        pivotX = surfDirection.getLayoutParams().width/2;
        pivotY = surfDirection.getLayoutParams().height/2;
        RotateAnimation animCircle = new RotateAnimation(0, sf.getSurfDir(), pivotX, pivotY);
        animCircle.setInterpolator(new LinearInterpolator());
        animCircle.setFillAfter(true);
        animCircle.setDuration(2000);
        surfDirection.startAnimation(animCircle);

        return view;
    }


}
