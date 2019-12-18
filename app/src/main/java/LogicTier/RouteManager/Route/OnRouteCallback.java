package LogicTier.RouteManager.Route;

import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

public interface OnRouteCallback {
    void OnRouteLoaded(PolylineOptions options, LatLngBounds bounds, int padding);
}
