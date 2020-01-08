package LogicTier.RouteManager.Route;

import java.io.Serializable;
import java.util.ArrayList;

public class Waypoint implements Serializable {
    private boolean hasBeenChecked;
    private boolean isVisitedChecked;
    private int id;
    private String name;
    private String description;
    private String latitude;
    private String longitude;
    private double height;
    private ArrayList<String> multimedia;


    //Constructor without id <-- Use this one for inserting waypoints in DB
    public Waypoint(boolean hasBeenChecked, boolean isVisitedChecked, String name, String description, String latitude, String longitude, double height, ArrayList<String> multimedia) {
        //ID == 0
        this(hasBeenChecked, isVisitedChecked, 0, name, description, latitude, longitude, height, multimedia);
    }

    //Constructor with id <-- Use this to load waypoints from the DB
    public Waypoint(boolean hasBeenChecked, boolean isVisitedChecked, int id, String name, String description, String latitude, String longitude, double height, ArrayList<String> multimedia) {
        this.hasBeenChecked = hasBeenChecked;
        this.isVisitedChecked = isVisitedChecked;
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.height = height;
        this.multimedia = multimedia;
    }

    public boolean isHasBeenChecked()
    {
        return hasBeenChecked;
    }

    public void setHasBeenChecked(boolean hasBeenChecked)
    {
        this.hasBeenChecked = hasBeenChecked;
    }

    public boolean isVisitedChecked()
    {
        return isVisitedChecked;
    }

    public void setVisitedChecked(boolean visitedChecked)
    {
        isVisitedChecked = visitedChecked;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public double getHeight()
    {
        return height;
    }

    public void setHeight(double height)
    {
        this.height = height;
    }

    public ArrayList<String> getMultimedia()
    {
        return multimedia;
    }

    public void setMultimedia(ArrayList<String> multimedia)
    {
        this.multimedia = multimedia;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}