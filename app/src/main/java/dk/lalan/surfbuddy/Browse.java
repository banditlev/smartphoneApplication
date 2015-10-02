package dk.lalan.surfbuddy;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Browse extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
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
