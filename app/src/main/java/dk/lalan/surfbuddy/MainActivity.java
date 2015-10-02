package dk.lalan.surfbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

import adapters.CardviewAdapter;
import models.DummySurfer;
import models.SurfLocation;


//Inspired by: http://treyrobinson.net/blog/android-l-tutorials-part-3-recyclerview-and-cardview/
public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private CardviewAdapter mAdapter;

    public DummySurfer surf1 = new DummySurfer("Klitmøller");
    public DummySurfer surf2 = new DummySurfer("Klitmøller");
    public DummySurfer surf3 = new DummySurfer("Klitmøller");
    public DummySurfer surf4 = new DummySurfer("Klitmøller");
    public DummySurfer surf5 = new DummySurfer("Klitmøller");
    public DummySurfer surf6 = new DummySurfer("Klitmøller");
    public DummySurfer surf7 = new DummySurfer("Klitmøller");

    public List<DummySurfer> favorites = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Adding to List remove later
        favorites.add(surf1);
        favorites.add(surf2);
        favorites.add(surf3);
        favorites.add(surf4);
        favorites.add(surf5);
        favorites.add(surf6);
        favorites.add(surf7);

        mAdapter = new CardviewAdapter(favorites, R.layout.main_activity_card_view, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
