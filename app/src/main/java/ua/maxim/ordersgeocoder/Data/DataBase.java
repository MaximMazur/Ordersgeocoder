package ua.maxim.ordersgeocoder.Data;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import ua.maxim.ordersgeocoder.Services.DownloadOrders;

public class DataBase {

    private static DataBase instance;
    private Context context;

    private DataBase(Context context){
        this.context = context.getApplicationContext();

    }

    public static DataBase getInstance(){

        return instance;

    }

    public static void instantiate(Context context){
        if(null == instance){
            synchronized (DataBase.class){
                if(null == instance){
                    instance = new DataBase(context);
                }
            }
        }
    }

    public void lookForNewOrders() {

        context.startService(new Intent(context, DownloadOrders.class));

    }

    public void addOrdersFromJson(String jsonString) {

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Order>>(){}.getType();
        List<Order> orders = (List<Order>) gson.fromJson(jsonString, listType);

        for (Order order: orders) {
            context.startService(new Intent(context, GetGeodata.class).putExtra())
        }

    }
}
