package models;

/**
 * Created by banditlev on 02/10/15.
 */
public class DummySurfer {

    public String name;

    public DummySurfer(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return "Mon, 12:30 PM, Mostly sunny";
    }

    public String getWindDirection(){
        return "NW";
    }
}
