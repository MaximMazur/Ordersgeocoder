package ua.maxim.ordersgeocoder.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import ua.maxim.ordersgeocoder.Data.DataBase;


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
                DataBase.getInstance().addOrdersFromJson(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
