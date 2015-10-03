package dk.lalan.surfbuddy;


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
        distance.setText(sf.getDistance() + " km");
        description.setText(this.getResources().getString(R.string.conditions_is) + sf.getDescribtion());
        updated.setText("???");

        if (sf.isSurfable()){
            windSurfDirection.setImageDrawable(this.getResources().getDrawable(R.drawable.arrow_green));
        }else{
            windSurfDirection.setImageDrawable(this.getResources().getDrawable(R.drawable.arrow_red));
        }

        RotateAnimation animArrow = new RotateAnimation(0, sf.getWindDir(), 80, 80);
        animArrow.setInterpolator(new LinearInterpolator());
        animArrow.setFillAfter(true);
        animArrow.setDuration(2000);
        windSurfDirection.startAnimation(animArrow);

        RotateAnimation animCircle = new RotateAnimation(0, sf.getSurfDir(), 200, 200);
        animCircle.setInterpolator(new LinearInterpolator());
        animCircle.setFillAfter(true);
        animCircle.setDuration(2000);
        surfDirection.startAnimation(animCircle);

        return view;
    }


}
