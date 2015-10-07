package ua.maxim.ordersgeocoder;

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

import ua.maxim.ordersgeocoder.Data.DataBase;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng HOME_POSITION = new LatLng(51, 10);
    private LatLng DESTINATION_POSITION = new LatLng(52, 13);

    private BitmapDescriptor mDepartureMarker;
    private BitmapDescriptor mDestinationMarker;

    private float DEFAULT_ZOOM = 6.5f;

    private DataBase mDB;

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

        DataBase.instantiate(this);

        mDB = DataBase.getInstance();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mDB != null) mDB.lookForNewOrders();

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

        drawOrder(HOME_POSITION, DESTINATION_POSITION);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HOME_POSITION, DEFAULT_ZOOM));
    }

    private void drawOrder(LatLng departure, LatLng destination) {

        mMap.addMarker(new MarkerOptions().position(departure).icon(mDepartureMarker));
        mMap.addMarker(new MarkerOptions().position(destination).icon(mDestinationMarker));
        mMap.addPolyline((new PolylineOptions())
                .add(departure, destination));

    }
}
