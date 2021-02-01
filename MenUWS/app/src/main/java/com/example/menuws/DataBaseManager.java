package com.example.menuws;

/*
How to store image in sqlite database
https://stackoverflow.com/questions/9357668/how-to-store-image-in-sqlite-database#:~:text=Inorder%20to%20store%20images%20to,to%20set%20it%20to%20imageview.
*/

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.menuws.ui.restaurants.RestaurantsData;
import com.example.menuws.ui.settings.FoodItemsData;
import com.example.menuws.ui.settings.Users;

import java.util.ArrayList;


public class DataBaseManager {
    public static final String REST_TABLE = "Restaurants";
    public static final String DB_NAME = "MenUWS";
//    public static final String ADMINS_TABLE = "Admins";
    public static final String USERS_TABLE = "Users";
    public static final String FOOD_ITEMS_TABLE = "Food_Items";
    public static final int DB_VERSION = 9;
    public static final String[] REST_COLUMNS = new String[] {"RestaurantId", "RestaurantName", "FoodType", "MinOrder", "Address", "UserId"};
    public static final String[] FOOD_ITEMS_COLUMNS = new String[] {"id", "Item", "Price", "RestaurantId"};
    public static final String [] USER_COLUMNS = new String [] {"id", "FirstName", "LastName", "Email", "Password", "Admin"};

    private static final String CREATE_RESTAURANTS_TABLE = "CREATE TABLE " + REST_TABLE +
            " (RestaurantId INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "RestaurantName TEXT, " +
            "FoodType TEXT, " +
            "MinOrder FLOAT, " +
            "Address TEXT, " +
            "UserId INTEGER, " +
            "RestaurantImage BLOB" +
            ");";

    private static final String CREATE_FOOD_ITEMS_TABLE = "CREATE TABLE " +  FOOD_ITEMS_TABLE +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Item	TEXT, " +
            "Price FLOAT, " +
            "RestaurantId INTEGER" +
            ");";

    private static final String CREATE_USERS_TABLE = "CREATE TABLE " +  USERS_TABLE +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "FirstName	TEXT, " +
            "LastName TEXT, " +
            "Email TEXT, " +
            "Password TEXT, " +
            "Admin INTEGER" +
            ");";
    private SQLiteOpenHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DataBaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DataBaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRestaurantRow(String tableName, String restName, String restType, Double minOrder, String address, int userId) {
        ContentValues newObj = new ContentValues();
        newObj.put("RestaurantName", restName);
        newObj.put("FoodType", restType);
        newObj.put("MinOrder", minOrder);
        newObj.put("Address", address);
        newObj.put("UserId", userId);
        try {
            db.insertOrThrow("Restaurants", null, newObj);
        }
        catch (Exception e) {
            Log.e("Error in inserting rows", e.toString());
            e.printStackTrace();
        }
        db.close();
        return true;
    }

    public boolean addItemRow(String tableName, String itemName, double itemPrice, int restaurantId) {
        ContentValues newItem = new ContentValues();
        newItem.put("Item", itemName);
        newItem.put("Price", itemPrice);
        newItem.put("RestaurantId", restaurantId);
        try {
            db.insertOrThrow("Food_Items", null, newItem);
        }
        catch (Exception e) {
            Log.e("Error in inserting rows", e.toString());
            e.printStackTrace();
        }
        db.close();
        return true;
    }

    public boolean addUserRow(String fName, String lName, String email, String password, int admin) {
        ContentValues newUser = new ContentValues();
        newUser.put("FirstName", fName);
        newUser.put("LastName", lName);
        newUser.put("Email", email);
        newUser.put("Password", password);
        newUser.put("Admin", admin);
        try {
            db.insertOrThrow(USERS_TABLE, null, newUser);
        }
        catch (Exception e) {
            Log.e("Error in inserting rows", e.toString());
            e.printStackTrace();
        }
        db.close();
        return true;
    }

    public ArrayList<RestaurantsData.Restaurants> retrieveRestaurantRows(String whereClause) {
        ArrayList<RestaurantsData.Restaurants> restaurantRows = new ArrayList<>();
        Cursor cursor = db.query(REST_TABLE, REST_COLUMNS, whereClause, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            RestaurantsData.Restaurants restaurants = new RestaurantsData.Restaurants(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    String.valueOf(cursor.getDouble(3)),
                    cursor.getString(4),
                    cursor.getInt(5)
//                    cursor.getBlob(6)
            );
            restaurantRows.add(restaurants);
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return restaurantRows;
    }

    public ArrayList<FoodItemsData.FoodItems> retrieveFoodItemRows(String whereClause) {
        ArrayList<FoodItemsData.FoodItems> itemRows = new ArrayList<>();
        Cursor cursor = db.query(FOOD_ITEMS_TABLE, FOOD_ITEMS_COLUMNS, whereClause, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FoodItemsData.FoodItems items = new FoodItemsData.FoodItems(
                    cursor.getInt(0),
                    cursor.getString(1),
                    String.valueOf(cursor.getDouble(2)),
                    cursor.getInt(3)
            );
            itemRows.add(items);
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return itemRows;
    }

    public ArrayList<Users> retrieveUserRows(String whereClause) {
        ArrayList<Users> userRows = new ArrayList<>();
        Cursor cursor = db.query(USERS_TABLE, USER_COLUMNS, whereClause, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Users users = new Users(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            );
            userRows.add(users);
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return userRows;
    }


    public void deleteRow(String tableName, String id) {
        db.execSQL("DELETE FROM " + tableName
                + " WHERE id "  + "= '" + id + "'");
    }

    public void deleteRestaurantRow(String tableName, String id) {
        db.execSQL("DELETE FROM " + tableName
                + " WHERE RestaurantId "  + "= '" + id + "'");
    }

    public void updateRow(String tableName, String whereClause, String itemReplacement, Double priceReplacement) {
        ContentValues cv = new ContentValues();
        cv.put("Item", itemReplacement);
        cv.put("Price", priceReplacement);
        db.update(tableName, cv, whereClause, null);
    }

    public void updateRestaurantRow(String tableName, String whereClause, String restName, String restType, double minOrder, String address) {
        ContentValues cv = new ContentValues();
        cv.put("RestaurantName", restName);
        cv.put("FoodType", restType);
        cv.put("MinOrder", minOrder);
        cv.put("Address", address);
        db.update(tableName, cv, whereClause, null);
    }

    public void updateUserRow(String tableName, String whereClause, int restaurantId) {
        ContentValues cv = new ContentValues();
        cv.put("RestaurantId", restaurantId);
        db.update(tableName, cv, whereClause, null);
    }

    public static class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper (Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_RESTAURANTS_TABLE);
            db.execSQL(CREATE_FOOD_ITEMS_TABLE);
            db.execSQL(CREATE_USERS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Restaurants table", "Upgrading database i.e dropping table and re-creating it");
            db.execSQL("DROP TABLE IF EXISTS " + REST_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + FOOD_ITEMS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
            onCreate(db);
        }
    }
}
