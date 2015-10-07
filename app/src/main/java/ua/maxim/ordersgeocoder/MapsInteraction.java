package ua.maxim.ordersgeocoder;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

public interface MapsInteraction {
    public void drawMarkers(LatLng departure, LatLng destination);
}
