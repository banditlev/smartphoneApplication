package dk.lalan.surfbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Browse extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        final ListView listview = (ListView) findViewById(R.id.browse_listview);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        listview.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1 ,list));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_browse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.browse_action_distance:
                Toast.makeText(getApplicationContext(),"distance", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.browse_action_wind:
                Toast.makeText(getApplicationContext(),"wind", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.browse_action_level:
                Toast.makeText(getApplicationContext(),"level", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.browse_action_refresh:
                Toast.makeText(getApplicationContext(),"refresh", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.browse_action_search:
                Toast.makeText(getApplicationContext(),"search", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
