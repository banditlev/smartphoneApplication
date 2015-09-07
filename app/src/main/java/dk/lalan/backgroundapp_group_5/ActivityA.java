package dk.lalan.backgroundapp_group_5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityA extends AppCompatActivity {

    private EditText messageEditText, delayEditText;
    private Button goButton;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        //Inspiration: http://www.vogella.com/tutorials/AndroidServices/article.html
        @Override
        public void onReceive(Context context, Intent intent){
            String timeleft = intent.getStringExtra("timeleft");
            Toast.makeText(ActivityA.this, "Timeleft: " + timeleft, Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        goButton = (Button) findViewById(R.id.buttonGo);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                messageEditText = (EditText) findViewById(R.id.editTextMessage);
                delayEditText = (EditText) findViewById(R.id.editTextDelay);

                String message = messageEditText.getText().toString();
                String delay = delayEditText.getText().toString();

                Intent bgService = new Intent(getApplicationContext(), BackgroundService.class);
                bgService.putExtra("message", message);
                bgService.putExtra("delay", delay);

                startService(bgService);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_a, menu);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("dk.lalan.backgroundapp_group_5"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
