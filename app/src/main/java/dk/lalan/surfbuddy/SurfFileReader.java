package dk.lalan.surfbuddy;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import models.SurfLocation;

/**
 * Created by lundtoft on 03/10/15.
 */
public class SurfFileReader {

    private final Context context;

    public SurfFileReader(Context context){
        this.context = context;
    }

    /**
     *  SurfFileReader is inspired from: http://stackoverflow.com/questions/4087674/android-read-text-raw-resource-file
     *
     * @return List of all surf locations known
     */
    public List<SurfLocation> getAllLocations(){

        InputStream inputStream = context.getResources().openRawResource(R.raw.suftspots);

        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputReader);
        String line;

        ArrayList<SurfLocation> surfLocations = new ArrayList<>();

        try {
            while (( line = bufferedReader.readLine()) != null) {
                String[] row = line.split(";");
                SurfLocation sl = new SurfLocation();

                sl.setName(row[0]);
                sl.setlatitude(Double.parseDouble(row[1].trim()));
                sl.setLongitude(Double.parseDouble(row[2].trim()));
                sl.setSurfDir(Integer.parseInt(row[3].trim()));
                sl.setLevel(Integer.parseInt(row[4].trim()));
                sl.setLocationDescription(row[5]);

                surfLocations.add(sl);
            }
        }
        catch (IOException e) {
            //Here goes error ;)
        }

        return surfLocations;
    }

}
