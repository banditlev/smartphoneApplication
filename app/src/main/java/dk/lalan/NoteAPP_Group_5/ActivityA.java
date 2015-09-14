package dk.lalan.noteapp_group_5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import dk.lalan.NoteAPP_Group_5.Database;

public class ActivityA extends AppCompatActivity {
    private Button newTextNoteBtn, saveTextNoteBtn, showTextnotesBtn;
    private String textnote;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        Intent i = getIntent();
        textnote = i.getStringExtra("textnote");

        newTextNoteBtn = (Button) findViewById(R.id.buttonActivityANewTextnote);
        saveTextNoteBtn = (Button) findViewById(R.id.buttonActivityASaveTextnote);
        showTextnotesBtn = (Button) findViewById(R.id.buttonActivityAShowTextnotes);

        newTextNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ActivityB.class);
                startActivity(intent);
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
}
