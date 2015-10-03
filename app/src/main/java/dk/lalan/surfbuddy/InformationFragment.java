package dk.lalan.surfbuddy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InformationFragment extends Fragment {
    SurfLocation surfLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        surfLocation = ((LocationInformationActivity) getActivity()).getSurfLocation();
        TextView informationText = (TextView) view.findViewById(R.id.fragment_information_text);
        TextView difficultyLevel = (TextView) view.findViewById(R.id.difficulty_text);
        TextView idealDirection = (TextView) view.findViewById(R.id.ideal_direction);
        //idealDirection.setText(surfLocation.getSurfDir());
        crawlFile(informationText, difficultyLevel);
        return view;
    }

    //Crawl spot file and search for info using spot name
    public void crawlFile(TextView info, TextView level){
        boolean dataFound = false;
        InputStream inputStream = this.getResources().openRawResource(R.raw.suftspots);

        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputReader);
        String line;

        try {
            while (( line = bufferedReader.readLine()) != null && !dataFound) {
                String[] row = line.split(";");
                if (row[0].contains(surfLocation.getName())){
                    level.setText(row[4].trim());
                    info.setText(row[5].trim());
                    dataFound = true;
                }
            }
        }catch (IOException e) {
            //Here goes error ;)
        }
    }
}
