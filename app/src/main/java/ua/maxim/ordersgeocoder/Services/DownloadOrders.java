package ua.maxim.ordersgeocoder.Services;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.parceler.Parcels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ua.maxim.ordersgeocoder.Data.Order;
import ua.maxim.ordersgeocoder.MapsActivity;


public class DownloadOrders extends IntentService {

    public DownloadOrders(String name) {
        super(name);
    }

    public DownloadOrders() {
        super("DownloadOrders");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        PendingIntent pi = intent.getParcelableExtra(MapsActivity.PARAM_PINTENT);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://mobapply.com/tests/orders/")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(response != null){

            String jsonString;

            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(response.body().byteStream()));

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }

                jsonString = result.toString();

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Order>>(){}.getType();
                List<Order> orders = (List<Order>) gson.fromJson(jsonString, listType);

                Intent i = new Intent().putExtra(MapsActivity.PARAM_ORDER_LIST, Parcels.wrap((ArrayList) orders));
                pi.send(this, 0, i);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }

        }

    }
}
