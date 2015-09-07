package dk.lalan.backgroundapp_group_5;

import android.app.IntentService;
import android.content.Intent;

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
        int delay = new Integer(intent.getStringExtra("delay"));
    }
}
