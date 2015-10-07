package ua.maxim.ordersgeocoder;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import ua.maxim.ordersgeocoder.Data.Order;
import ua.maxim.ordersgeocoder.Services.DownloadOrders;
import ua.maxim.ordersgeocoder.Services.GetGeodata;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public final static String PARAM_PINTENT    = "pendingIntent";
    public final static String PARAM_ORDER_LIST = "ParcelableOrderList";
    public final static String PARAM_ORDER      = "ParcelableOrder";

    private final int mDownloadOrdersTaskCode = 0;
    private final int mGetGeodataTaskCode     = 1;

    private GoogleMap mMap;
    private final LatLng mHomePosition = new LatLng(51, 10);

    private BitmapDescriptor mDepartureMarker;
    private BitmapDescriptor mDestinationMarker;

    private PendingIntent piDownloadOrders;
    private PendingIntent piGetGeodata;

    private final float DEFAULT_ZOOM = 6.5f;
    private final float DEFAULT_WIDTH = 5.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDepartureMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        mDestinationMarker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);

        piDownloadOrders    = createPendingResult(mDownloadOrdersTaskCode, new Intent(), 0);
        piGetGeodata        = createPendingResult(mGetGeodataTaskCode, new Intent(), 0);

        startService(new Intent(this, DownloadOrders.class).putExtra(PARAM_PINTENT, piDownloadOrders));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case mDownloadOrdersTaskCode:

                List<Order> orders = data.getParcelableArrayListExtra(PARAM_ORDER_LIST);

                for (Order order: orders) {
                    startService(new Intent(this, GetGeodata.class)
                            .putExtra(PARAM_ORDER, order)
                            .putExtra(PARAM_PINTENT, piGetGeodata));
                }

                break;
            case mGetGeodataTaskCode:

                Order order = data.getParcelableExtra(PARAM_ORDER);

                drawOrder(order);

                break;
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mHomePosition, DEFAULT_ZOOM));
    }

    private void drawOrder(Order order){


        if (order.getDepartureAddress().isCoordinatesSet()
                && order.getDestinationAddress().isCoordinatesSet()){

            drawMarkers(order.getDepartureAddress().getCoordinates(), order.getDestinationAddress().getCoordinates());
        }

    }

    private void drawMarkers(LatLng departure, LatLng destination) {

        mMap.addMarker(new MarkerOptions().position(departure).icon(mDepartureMarker));
        mMap.addMarker(new MarkerOptions().position(destination).icon(mDestinationMarker));
        mMap.addPolyline((new PolylineOptions())
                .add(departure, destination)
                .width(DEFAULT_WIDTH));

    }
}
