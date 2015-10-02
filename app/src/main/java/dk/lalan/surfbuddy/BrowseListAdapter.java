package dk.lalan.surfbuddy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import models.SurfLocation;

/**
 * Created by lalan on 02/10/15.
 */
public class BrowseListAdapter extends ArrayAdapter<SurfLocation> {
    private final int layout;
    private final Context context;
    private final ArrayList<SurfLocation> data;

    public BrowseListAdapter(Context context, int resource, ArrayList<SurfLocation> data) {
        super(context, resource, data);
        this.layout = resource;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        ViewHolder holder = null;

        if(row == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layout, parent, false);

            holder = new ViewHolder();
            holder.locationName = (TextView) row.findViewById(R.id.browse_location_textview);
            holder.windSpeed = (TextView) row.findViewById(R.id.browse_wind_textview);
            holder.dist = (TextView) row.findViewById(R.id.browse_distance_textview);
        }else{
            holder = (ViewHolder) row.getTag();
        }

        SurfLocation surfLocation = data.get(position);

        holder.locationName.setText(surfLocation.getName());
        holder.windSpeed.setText(surfLocation.getWindSpeed().toString() + " knots");
        holder.dist.setText(surfLocation.getDist() + " km");

        return row;
    }

    static class ViewHolder{
        TextView locationName, windSpeed, dist;
    }
}
