package dk.iha.itsmap.e15.grp5.surfbuddy;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

 


public class MapFragment extends Fragment implements LocationListener {

    private DefaultResourceProxyImpl resourceProxy;
    private ArrayList<OverlayItem> items = new ArrayList<>();
    private LocationManager locationManager;
    private String provider;
    private Location location;
    private MapView map;
    private SurfLocation surfLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        map = (MapView) view.findViewById(R.id.mapview);
        surfLocation = ((LocationInformationActivity) getActivity()).getSurfLocation();
        GeoPoint surfPoint = new GeoPoint(surfLocation.getlatitude(), surfLocation.getLongitude());

        //Init marker list
        items = new ArrayList<>();
        resourceProxy = new DefaultResourceProxyImpl(view.getContext());

        //Inspired from: http://www.vogella.com/tutorials/AndroidDrawables/article.html
        locationManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(provider);
        locationManager.requestLocationUpdates(provider, 400, 1, this);

        if (location != null) {
            onLocationChanged(location);
        }

        map.setBuiltInZoomControls(false);
        map.setMultiTouchControls(true);
        map.getController().setZoom(12);
        map.getController().setCenter(surfPoint);

        addMarker(surfPoint, this.getResources().getDrawable(R.drawable.marker), map);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location loc) {
        if(map != null) {
            items.clear();

            GeoPoint gpsPoint = new GeoPoint(loc.getLatitude(), loc.getLongitude());
            GeoPoint surfPoint = new GeoPoint(surfLocation.getlatitude(), surfLocation.getLongitude());
            addMarker(gpsPoint, this.getResources().getDrawable(R.drawable.gps), map);
            addMarker(surfPoint, this.getResources().getDrawable(R.drawable.marker), map);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    /**
     * Adding a marker to the map
     * Inspiration from the following sources:
     *      - http://developer.blackberry.com/android/apisupport/replc_google_maps_with_openstreet.html
     *      - http://stackoverflow.com/questions/10533071/osmdroid-add-custom-icons-to-itemizedoverlay
     *      - https://github.com/osmdroid/osmdroid
     *
     * @param geoPoint
     * @param icon
     * @param map
     */
    public void addMarker(GeoPoint geoPoint, Drawable icon, MapView map){

        OverlayItem myLocationOverlayItem = new OverlayItem("Title", "Desc", geoPoint);

        //Change size of icon
        Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
        icon = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));

        myLocationOverlayItem.setMarker(icon);

        items.add(myLocationOverlayItem);

        ItemizedIconOverlay<OverlayItem> currentLocationOverlay = new ItemizedIconOverlay<>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        return true;
                    }
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return true;
                    }
                }, resourceProxy);

        map.getOverlays().add(currentLocationOverlay);
    }
}
