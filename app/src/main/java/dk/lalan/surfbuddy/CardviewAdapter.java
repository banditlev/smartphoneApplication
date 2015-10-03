package dk.lalan.surfbuddy;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

/**
 * Created by banditlev on 02/10/15.
 */
//Inspired by: http://treyrobinson.net/blog/android-l-tutorials-part-3-recyclerview-and-cardview/
public class CardviewAdapter extends RecyclerView.Adapter<CardviewAdapter.ViewHolder> {
    private List<SurfLocation> favorites;
    private int rowLayout;
    private Context context;

    public CardviewAdapter(List<SurfLocation> favorites, int rowLayout, Context _context){
        this.favorites = favorites;
        this.rowLayout = rowLayout;
        this.context = _context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i){
        final SurfLocation favorite = favorites.get(i);
        final int row = i;
        viewHolder.favoriteName.setText(favorite.getName());
        viewHolder.favoriteDescription.setText(favorite.getDescribtion());
        viewHolder.favoriteWindDirection.setText(favorite.getWindDirString());
        viewHolder.favoriteTemp.setText(Double.toString(favorite.getTemperatur()) + "º C");
        viewHolder.favoriteWindSpeed.setText(Double.toString(favorite.getWindSpeed()) + " knots");
        viewHolder.favoriteWindDirectionRing.setImageDrawable(context.getDrawable(R.drawable.direction_neutral));

        //If surfable use green arrow and ring else use red
        if(favorite.isSurfable()){
            viewHolder.favoriteWindDirectionArrow.setImageDrawable(context.getDrawable(R.drawable.arrow_green));
        }else{
            viewHolder.favoriteWindDirectionArrow.setImageDrawable(context.getDrawable(R.drawable.arrow_red));
        }

        //Set up button listeners for each card
        viewHolder.cardviewNavigationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start google maps
                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", favorite.getlatitude(), favorite.getLongitude(), "Surf spot - " + favorite.getName());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                context.startActivity(intent);
            }
        });
        viewHolder.cardviewMoreInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        animateIcons(viewHolder, favorite);
    }

    @Override
    public int getItemCount() {
        return favorites == null ? 0 : favorites.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView favoriteName, favoriteDescription, favoriteWindDirection, favoriteWindSpeed, favoriteTemp, cardviewNavigationButton, cardviewMoreInfoButton;
        public ImageView favoriteWindDirectionRing, favoriteWindDirectionArrow;

        public ViewHolder(View itemView) {
            super(itemView);
            favoriteName = (TextView) itemView.findViewById(R.id.favoriteName);
            favoriteDescription = (TextView) itemView.findViewById(R.id.favoriteDescription);
            favoriteWindDirection = (TextView) itemView.findViewById(R.id.favoriteWindDirection);
            favoriteWindSpeed = (TextView) itemView.findViewById(R.id.favoriteWindSpeed);
            favoriteTemp = (TextView) itemView.findViewById(R.id.favoriteTemp);
            favoriteWindDirectionRing = (ImageView) itemView.findViewById(R.id.favoriteWindDirectionRing);
            favoriteWindDirectionArrow = (ImageView) itemView.findViewById(R.id.favoriteWindDirectionArrow);
            cardviewMoreInfoButton = (TextView) itemView.findViewById(R.id.carviewMoreInfoButton);
            cardviewNavigationButton = (TextView) itemView.findViewById(R.id.cardviewNavigateButton);
        }

    }

    //Do animation of rotating both pictures to sesired angle from 0
    //Inspired by: http://www.learn-android-easily.com/2013/07/imageview-animation-in-android.html
    //animation setup for both icons
    public void animateIcons(ViewHolder viewHolder, SurfLocation location){
        RotateAnimation animRing = new RotateAnimation(0, location.getSurfDir(), 120, 120);
        RotateAnimation animArrow = new RotateAnimation(0, -(360-location.getWindDir()), 50, 50);
        animRing.setInterpolator(new LinearInterpolator());
        animArrow.setInterpolator(new LinearInterpolator());
        animRing.setFillAfter(true);
        animArrow.setFillAfter(true);
        animRing.setDuration(2000);
        animArrow.setDuration(2000);

        viewHolder.favoriteWindDirectionArrow.startAnimation(animArrow);
        viewHolder.favoriteWindDirectionRing.startAnimation(animRing);
    }
}

