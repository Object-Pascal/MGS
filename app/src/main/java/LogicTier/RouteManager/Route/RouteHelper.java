package LogicTier.RouteManager.Route;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class RouteHelper
{
    public ArrayList<String> getAllRouteNames(Context context) {

        String allRoutes = readFileFromInternalStorage(context, "routes");

        String[] routes = allRoutes.split(",");

        ArrayList<String> routeNames = new ArrayList<>();
        for(int i = 0; i < routes.length; i++) {
            routeNames.add(routes[i].replace(" ", ""));
        }

        return routeNames;
    }

    private void appendRouteName(Context context,String routeName) {

        String allRoutes = readFileFromInternalStorage(context, "routes");

        if(allRoutes == null) {
            writeFileOnInternalStorage(context, "routes", routeName);
            return;
        }

        if(allRoutes.contains(routeName)) {
            return;
        }

        allRoutes += ", " + routeName;

        writeFileOnInternalStorage(context, "routes", allRoutes);

    }

    public Route getRoute(Context context, String routeName) {

         String stringRoute = readFileFromInternalStorage(context, routeName);

         if(stringRoute != null) {

             try {
                 JSONObject jsonRoute = new JSONObject(stringRoute);
                 JSONArray jsonRouteArray = jsonRoute.getJSONArray("route");

                 Route route = new Route();
                 route.title = jsonRoute.getString("title");

                 ArrayList<Waypoint> waypoints = new ArrayList<>();

                 for(int i = 0; i < jsonRouteArray.length(); i++) {
                     waypoints.add(new Waypoint(jsonRouteArray.getJSONObject(i)));
                 }

                 route.setRoute(waypoints);


                 return route;
             }
             catch (JSONException ex) {
                 Log.e("JsonError, RouteHelper", ex.getMessage());
             }
         }

        return null;
    }

    public void uploadRoute(Context context, ArrayList<Waypoint> waypoints, String routeTitle) {

        Route route = new Route();
        route.title = routeTitle;
        route.setRoute(waypoints);

        uploadRoute(context, route);

    }

    public void uploadRoute(Context context, Route route) {

        Gson gson = new Gson();
        String json = gson.toJson(route);

        appendRouteName(context, route.title);
        writeFileOnInternalStorage(context, route.title, json);

    }


    private void writeFileOnInternalStorage(Context context, String sFileName, String sBody){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(sFileName + ".json", Context.MODE_PRIVATE));
            outputStreamWriter.write(sBody);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("route helper", "File write failed: " + e.toString());
        }
    }

    private String readFileFromInternalStorage(Context context, String filename) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(filename + ".json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("route helper", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("route helper", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
