package LogicTier.RouteManager.GpsManager;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;

public class GPSManager {
    private LocationListener locationListener;
    private LocationManager locationManager;

    public GPSManager(LocationListener locationListener, Context context) {
        this.locationListener = locationListener;
        this.locationManager = (LocationManager)context.getSystemService(context.LOCATION_SERVICE);
    }

    public boolean isGPSEnabled() {
        boolean gpsEnabled = false;
        if (this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            gpsEnabled = true;
            return gpsEnabled;
        }
        return gpsEnabled;
    }

    public void setLocationListener(LocationListener locationListener) {
        this.locationListener = locationListener;
    }
}