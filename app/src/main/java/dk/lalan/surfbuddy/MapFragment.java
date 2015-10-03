package dk.lalan.surfbuddy;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;


public class MapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        MapView map = (MapView) view.findViewById(R.id.mapview);
        SurfLocation surfLocation = ((LocationInformationActivity) getActivity()).getSurfLocation();

        map.setBuiltInZoomControls(false);
        map.setMultiTouchControls(true);
        map.getController().setZoom(12);
        map.getController().setCenter(new GeoPoint(surfLocation.getlatitude(), surfLocation.getLongitude()));

        return view;
    }
}
