package com.example.menuws.ui.settings;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.blure.complexview.ComplexView;
import com.example.menuws.DataBaseManager;
import com.example.menuws.MainActivity;
import com.example.menuws.R;
import com.example.menuws.ui.restaurants.RestaurantsData;

import org.w3c.dom.Text;

import java.util.List;

import static android.view.View.GONE;


public class MyItemsFragment extends Fragment {
    public static RecyclerView recyclerView;
    public static ComplexView addItemForm;
    public static Button submitItemBtn;
    public static TextView itemFormTitle;
    public static EditText itemName, itemPrice;
    public static Context context;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    View view;
    List<FoodItemsData.FoodItems> myItems;
    DataBaseManager db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_items_list, container, false);
        context = getContext();
        TextView restItemsTitle = (TextView) view.findViewById(R.id.restaurantItemsTitle);
        MainActivity main = (MainActivity) getActivity();
        addItemForm = view.findViewById(R.id.addItemForm);
        recyclerView = view.findViewById(R.id.myItemsList);
        ImageButton addItemButton = (ImageButton) main.findViewById(R.id.addNewRestaurantBtn);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(GONE);
                addItemForm.setVisibility(View.VISIBLE);
                addItemForm.animate().translationY(0).setDuration(350);
            }
        });

        db = new DataBaseManager(this.getActivity());
        db.openReadable();
        String whereClause = "RestaurantId = '" + MyRestaurantsAdapter.restaurantId + "'";
        myItems = db.retrieveFoodItemRows(whereClause);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyItemsAdapter(this.getActivity(), myItems);
        recyclerView.setAdapter(adapter);
        db.close();
        db = new DataBaseManager(this.getActivity());
        submitItemBtn = view.findViewById(R.id.submitItemForm);
        itemFormTitle = view.findViewById(R.id.itemFormTitle);
        itemName = (EditText) view.findViewById(R.id.foodItemNameInput);
        itemPrice = (EditText) view.findViewById(R.id.foodItemPriceInput);
        if(submitItemBtn.getText().equals("Submit")) {
            submitItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String itemNameStr, itemPriceStr;
                    itemNameStr = itemName.getText().toString().trim();
                    itemPriceStr = itemPrice.getText().toString().trim();

                    boolean recInserted = db.addItemRow(DataBaseManager.FOOD_ITEMS_TABLE, itemNameStr,
                            Double.parseDouble(itemPriceStr), MyRestaurantsAdapter.restaurantId);

                    if(recInserted && itemNameStr.length() > 0 && itemPriceStr.length() > 0) {
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(itemPrice.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                        recyclerView.setVisibility(View.VISIBLE);
                        submitItemBtn.setVisibility(GONE);
                        Toast.makeText(getActivity(), "Item Added Successfully", Toast.LENGTH_SHORT).show();
                        myItems.add(new FoodItemsData.FoodItems(0, itemNameStr, itemPriceStr, MyRestaurantsAdapter.restaurantId));
                        adapter.notifyDataSetChanged();
                        itemName.setText("");
                        itemPrice.setText("");
                    }
                    else {
                        Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        else {
            submitItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String whereClause = "id='" + MyItemsAdapter.itemId + "'";
                    db.updateRow(DataBaseManager.FOOD_ITEMS_TABLE, whereClause, itemName.getText().toString(), Double.parseDouble(itemPrice.getText().toString()));
                }
            });
        }

        return view;
    }
}