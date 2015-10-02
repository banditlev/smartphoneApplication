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
        Log.e("***", "Fav name: " + favorite.getName());
        
        viewHolder.favoriteName.setText(favorite.getName());
        //viewHolder.favoriteImage.setImageDrawable(mContext.getDrawable(favorite.getImageResourceId(mContext)));
    }

    @Override
    public int getItemCount() {
        return favorites == null ? 0 : favorites.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView favoriteName;
        public ImageView favoriteImage;

        public ViewHolder(View itemView) {
            super(itemView);
            favoriteName = (TextView) itemView.findViewById(R.id.favoriteName);
            //favoriteImage = (ImageView)itemView.findViewById(R.id.favoriteImage);
        }

    }
}
