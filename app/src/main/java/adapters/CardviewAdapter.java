package adapters;


import android.content.Context;
import android.os.Debug;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dk.lalan.surfbuddy.R;
import models.DummySurfer;
import models.SurfLocation;

/**
 * Created by banditlev on 02/10/15.
 */
//Inspired by: http://treyrobinson.net/blog/android-l-tutorials-part-3-recyclerview-and-cardview/
public class CardviewAdapter extends RecyclerView.Adapter<CardviewAdapter.ViewHolder> {

    private List<DummySurfer> favorites;
    private int rowLayout;
    private Context mContext;

    public CardviewAdapter(List<DummySurfer> favorites, int rowLayout, Context context){
        this.favorites = favorites;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i){
        DummySurfer favorite = favorites.get(i);
        
        viewHolder.favoriteName.setText(favorite.getName());
        viewHolder.favoriteWindDirectionImage.setImageDrawable(mContext.getDrawable(R.mipmap.ic_direction));
        viewHolder.favoriteDescription.setText(favorite.getDescription());
        viewHolder.favoriteWindDirection.setText(favorite.getWindDirection());
    }

    @Override
    public int getItemCount() {
        return favorites == null ? 0 : favorites.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView favoriteName, favoriteDescription, favoriteWindDirection;
        public ImageView favoriteWindDirectionImage;

        public ViewHolder(View itemView) {
            super(itemView);
            favoriteName = (TextView) itemView.findViewById(R.id.favoriteName);
            favoriteWindDirectionImage = (ImageView)itemView.findViewById(R.id.favoriteWindDirectionImage);
            favoriteDescription = (TextView) itemView.findViewById(R.id.favoriteDescription);
            favoriteWindDirection = (TextView) itemView.findViewById(R.id.favoriteWindDirection);
        }

    }
}
