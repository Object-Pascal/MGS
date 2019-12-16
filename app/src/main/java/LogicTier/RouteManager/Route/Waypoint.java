package LogicTier.RouteManager.Route;

import java.util.ArrayList;

public class Waypoint {
    private boolean hasBeenChecked;
    private boolean isVisitedChecked;
    private int id;
    private String name;
    private String latitude;
    private String longitude;
    private double height;
    private ArrayList<String> multimedia;


    public Waypoint(boolean hasBeenChecked, boolean isVisitedChecked, int id, String name, String latitude, String longitude, double height, ArrayList<String> multimedia) {
        this.hasBeenChecked = hasBeenChecked;
        this.isVisitedChecked = isVisitedChecked;
        this.id = id;
        this.name = name;
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
}
