package com.example.menuws.ui.restaurants;

import android.content.Context;

import com.example.menuws.DataBaseManager;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsData {
        public static List<RestaurantsData.Restaurants> ITEMS = new ArrayList<Restaurants>();

        public RestaurantsData(Context c) {
            DataBaseManager db = new DataBaseManager(c);
            ITEMS = db.retrieveRestaurantRows(null);
        }

        public static class Restaurants {
            public int id;
            public String restName;
            public String restType;
            public String minOrder;
            public String restAddress;
            public int userId;

            public Restaurants(int id, String restName, String restType, String minOrder, String restAddress, int userId) {
                this.id = id;
                this.restName = restName;
                this.restType = restType;
                this.minOrder = minOrder;
                this.restAddress = restAddress;
                this.userId = userId;
            }
        }
}
