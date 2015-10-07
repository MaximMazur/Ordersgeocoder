package ua.maxim.ordersgeocoder;

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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, MapsMarkerInteration {

    private GoogleMap mMap;
    private final LatLng mHomePosition = new LatLng(51, 10);
    private MapsController mController;

    private BitmapDescriptor mDepartureMarker;
    private BitmapDescriptor mDestinationMarker;

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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mController.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mController = new MapsController(this, this);
        mController.lookForOrders();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mController.onStop();
        mController = null;
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

    @Override
    public void drawMarkers(LatLng departure, LatLng destination) {

        mMap.addMarker(new MarkerOptions().position(departure).icon(mDepartureMarker));
        mMap.addMarker(new MarkerOptions().position(destination).icon(mDestinationMarker));
        mMap.addPolyline((new PolylineOptions())
                .add(departure, destination)
                .width(DEFAULT_WIDTH));

    }

    @Override
    public void clearMarkers() {
        if (mMap != null) {
            mMap.clear();
        }
    }
}
