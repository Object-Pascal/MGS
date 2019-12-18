package LogicTier.RouteManager;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.xml.sax.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import LogicTier.RouteManager.Route.OnRouteCallback;
import LogicTier.RouteManager.Route.RouteDataParser;
import LogicTier.RouteManager.Route.Route;
import LogicTier.RouteManager.Route.Waypoint;

public class RouteManager {
    private static final String API_KEY = "b9b76fa5-992f-4271-bdb3-3a5e2ebc0bf3\n";

    private Route route;
    private Context context;

    private OnRouteCallback routeCallback;

    public RouteManager(OnRouteCallback routeCallback, Route route, Context context) {
        this.routeCallback = routeCallback;
        this.route = route;
        this.context = context;
    }

    public Waypoint CheckIfCloseToWaypoint(double userLatitude, double userLongitude) {
        Waypoint closestWaypoint = null;
        for (Waypoint w : this.route.getRoute()) {
            float[] checkResult = new float[1];
            Location.distanceBetween(Double.parseDouble(w.getLatitude()), Double.parseDouble(w.getLongitude()), userLatitude, userLongitude, checkResult);

            if (checkResult[0] < 10) {
                closestWaypoint = w;
            }
        }
        return closestWaypoint;
    }

    public void fetchRoute(@NotNull LatLng origin, @NotNull LatLng destination) {
        String url = getRouteUrl(origin, destination);
        RouteManager.FetchUrl FetchUrl = new RouteManager.FetchUrl();
        FetchUrl.execute(url);
    }

    private String getRouteUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        String trafficMode = "mode=walking";
        //String trafficMode = "mode=driving";

        String parameters = str_origin + "&" + str_dest + "&" + trafficMode + "&key=" + API_KEY;
        String output = "/json";

        //String url = "https://maps.googleapis.com/maps/api/directions" + output + "?" + parameters;
        String url = "http://145.48.6.80:3000/directions" + "?" + parameters;

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();        }
        return data;
    }

    public class FetchUrl extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    public class ParserTask extends AsyncTask<String, Integer, List<List<LatLng>>> {

        @Override
        protected List<List<LatLng>> doInBackground(String... jsonData) {
            JSONObject jObject;
            List<List<LatLng>> routeData = null;

            try {
                jObject = new JSONObject(jsonData[0]);

                Log.d("ParserTask", jsonData[0]);
                RouteDataParser parser = new RouteDataParser();
                Log.d("ParserTask", parser.toString());

                routeData = parser.parseRoutesInfo(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask-routes: ", routeData.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routeData;
        }

        @Override
        protected void onPostExecute(List<List<LatLng>> result) {
            if (result == null)
                return;

            PolylineOptions lineOptions = new PolylineOptions();

            LatLng northEast = result.get(0).get(0);
            LatLng southWest = result.get(0).get(1);

            result.remove(0);

            for (List<LatLng> leg : result) {
                lineOptions.addAll(leg);
            }

            lineOptions.width(12);
            lineOptions.color(Color.rgb(153, 187, 255));

            Log.d("onPostExecute", "onPostExecute lineoptions decoded");

            if (lineOptions != null) {
                LatLngBounds bounds = new LatLngBounds(southWest, northEast);
                routeCallback.OnRouteLoaded(lineOptions, bounds, 80);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }

    public Route getRoute() {
        return route;
    }
}