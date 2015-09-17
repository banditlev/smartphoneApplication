package dk.lalan.noteapp_group_5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ActivityA extends AppCompatActivity {
    private Button newTextNoteBtn, saveTextNoteBtn, showTextnotesBtn;
    private String textnote;
    private Database db;
    private int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new Database(getApplicationContext());
        //db.clearDB();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        newTextNoteBtn = (Button) findViewById(R.id.buttonActivityANewTextnote);
        saveTextNoteBtn = (Button) findViewById(R.id.buttonActivityASaveTextnote);
        showTextnotesBtn = (Button) findViewById(R.id.buttonActivityAShowTextnotes);

        newTextNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityB.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        saveTextNoteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                db = new Database(getApplicationContext());
                if(textnote != null) {
                    db.addNote(textnote);
                }
                Toast.makeText(getApplicationContext(), "Note added to DB", Toast.LENGTH_LONG).show();
            }
        });


        showTextnotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new Database(getApplicationContext());
                Log.e("***", db.getAllNotes().toString());
                Intent intent = new Intent(getApplicationContext(), ActivityC.class);
                startActivity(intent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("textnote")) {
                textnote = data.getExtras().getString("textnote");
            }
        }
    }
}
