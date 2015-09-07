package dk.lalan.backgroundapp_group_5;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by lalan on 07/09/15.
 */
public class BackgroundService extends IntentService {

    public BackgroundService() {
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String message = intent.getStringExtra("message");
        int delay = Integer.valueOf(intent.getStringExtra("delay"))*1000;

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Log.e("Error", "*** ThreadFEJL!!! ");;
        }
        Intent activityB = new Intent(getApplicationContext(), ActivityB.class);
        activityB.putExtra("message", message);
        activityB.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(activityB);
    }


}
