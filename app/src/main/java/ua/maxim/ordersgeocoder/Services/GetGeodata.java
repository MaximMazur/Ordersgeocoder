package ua.maxim.ordersgeocoder.Services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import ua.maxim.ordersgeocoder.Data.DataBase;
import ua.maxim.ordersgeocoder.Data.Order;
import ua.maxim.ordersgeocoder.R;

/**
 * Created by Maxim on 07.10.2015.
 */
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
        Order order = intent.getParcelableExtra(DataBase.EXTRAS_ORDER);

        identifyCoordinates(order.getDepartureAddress());

        identifyCoordinates(order.getDestinationAddress());

//        DataBase.getInstance().updateOrder(order);
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
            }while (++currentTry <= 10 && geoResults.size() == 0);

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
