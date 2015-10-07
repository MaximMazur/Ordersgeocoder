package ua.maxim.ordersgeocoder.Services;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;

import ua.maxim.ordersgeocoder.Data.Order;
import ua.maxim.ordersgeocoder.MapsActivity;

public class GetGeodata extends IntentService {

    private int maxTry = 10;

    public GetGeodata(String name) {
        super(name);
    }

    public GetGeodata(){
        super("GetGeodata");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        PendingIntent pi = intent.getParcelableExtra(MapsActivity.PARAM_PINTENT);

        Order order = Parcels.unwrap(intent.getParcelableExtra(MapsActivity.PARAM_ORDER));

        identifyCoordinates(order.getDepartureAddress());

        identifyCoordinates(order.getDestinationAddress());

        Intent i = new Intent().putExtra(MapsActivity.PARAM_ORDER, Parcels.wrap(order));

        try {
            pi.send(this, 0, i);
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    private void identifyCoordinates(ua.maxim.ordersgeocoder.Data.Address address) {

        Address locationAddress;

        locationAddress = getCoordinates(address.toString());

        if(locationAddress != null){

            address.setLatitude(locationAddress.getLatitude());
            address.setLongitude(locationAddress.getLongitude());

        }
    }

    private Address getCoordinates(String address) {

        Geocoder gc = new Geocoder(this);
        List<Address> geoResults;
        int currentTry = 1;

        try {

            do {
                geoResults = gc.getFromLocationName(address, 1);
            }while (++currentTry <= maxTry && geoResults.size() == 0);

            if (geoResults.size()>0) {
                return geoResults.get(0);
            } else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
