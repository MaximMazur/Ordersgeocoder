package ua.maxim.ordersgeocoder;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;

import org.parceler.Parcels;

import java.util.List;

import ua.maxim.ordersgeocoder.Data.Order;
import ua.maxim.ordersgeocoder.Services.DownloadOrders;
import ua.maxim.ordersgeocoder.Services.GetGeodata;

public class MapsController {

    public final static String PARAM_ORDER_LIST = "ParcelableOrderList";
    public final static String PARAM_PINTENT = "PendingIntent";
    public final static String PARAM_ORDER = "ParcelableOrder";

    private MapsInteraction mMap;
    private Activity mContext;
    private PendingIntent piDownloadOrders;
    private PendingIntent piGetGeodata;



    private final int mDownloadOrdersTaskCode = 0;
    private final int mGetGeodataTaskCode = 1;

    public MapsController(MapsInteraction map, Activity context){
        this.mMap = map;
        this.mContext = context;

        piDownloadOrders = mContext.createPendingResult(mDownloadOrdersTaskCode, new Intent(), 0);
        piGetGeodata = mContext.createPendingResult(mGetGeodataTaskCode, new Intent(), 0);

    }

    public void lookForNewOrders() {
        mContext.startService(new Intent(mContext, DownloadOrders.class).putExtra(PARAM_PINTENT, piDownloadOrders));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case mDownloadOrdersTaskCode:

                List<Order> orders = Parcels.unwrap(data.getParcelableExtra(MapsController.PARAM_ORDER_LIST));

                for (Order order : orders) {
                    mContext.startService(new Intent(mContext, GetGeodata.class)
                            .putExtra(MapsController.PARAM_ORDER, Parcels.wrap(order))
                            .putExtra(MapsController.PARAM_PINTENT, piGetGeodata));
                }

                break;
            case mGetGeodataTaskCode:

                Order order = Parcels.unwrap(data.getParcelableExtra(MapsController.PARAM_ORDER));

                checkAndDrawOrder(order);

                break;
        }
    }

    private void checkAndDrawOrder(Order order) {


        if (order.getDepartureAddress().isCoordinatesSet()
                && order.getDestinationAddress().isCoordinatesSet()) {

            mMap.drawMarkers(order.getDepartureAddress().getCoordinates(), order.getDestinationAddress().getCoordinates());
        }

    }

}
