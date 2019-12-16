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
        if (this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        }
        return false;
    }

    public void setLocationListener(LocationListener locationListener) {
        this.locationListener = locationListener;
    }
}