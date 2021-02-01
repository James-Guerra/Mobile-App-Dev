package com.example.menuws.ui.settings;

import android.content.Context;

import com.example.menuws.DataBaseManager;
import com.example.menuws.ui.restaurants.RestaurantsData;

import java.util.ArrayList;
import java.util.List;

public class FoodItemsData {
    public static List<FoodItemsData.FoodItems> ITEMS = new ArrayList<FoodItems>();

    public FoodItemsData(Context c) {
        DataBaseManager db = new DataBaseManager(c);
        ITEMS = db.retrieveFoodItemRows(null);
    }
    public static class FoodItems {
        public int id;
        public String foodName;
        public String foodPrice;
        public int restaurantId;
        public boolean isChecked;

        public FoodItems(int id, String foodName, String foodPrice, int restaurantId) {
            this.id = id;
            this.foodName = foodName;
            this.foodPrice = foodPrice;
            this.restaurantId = restaurantId;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
