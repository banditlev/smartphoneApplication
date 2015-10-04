package dk.lalan.surfbuddy;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;


public class MapFragment extends Fragment {

    private DefaultResourceProxyImpl resourceProxy;
    private ArrayList<OverlayItem> items = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        MapView map = (MapView) view.findViewById(R.id.mapview);
        SurfLocation surfLocation = ((LocationInformationActivity) getActivity()).getSurfLocation();

        GeoPoint surfPoint = new GeoPoint(surfLocation.getlatitude(), surfLocation.getLongitude());
        GeoPoint gpsPoint = new GeoPoint(56.147850, 10.200130);

        map.setBuiltInZoomControls(false);
        map.setMultiTouchControls(true);
        map.getController().setZoom(12);
        map.getController().setCenter(surfPoint);

        //Init marker list
        items = new ArrayList<>();
        resourceProxy = new DefaultResourceProxyImpl(view.getContext());
        addMarker(gpsPoint, this.getResources().getDrawable(R.drawable.gps), map);                                                                          
        addMarker(surfPoint, this.getResources().getDrawable(R.drawable.marker), map);


        return view;
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
