package com.example.menuws.ui.orders;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.menuws.DataBaseManager;
import com.example.menuws.R;
import com.example.menuws.ui.restaurants.RestaurantsAdapter;
import com.example.menuws.ui.settings.FoodItemsData;

import java.util.List;


public class MyOrderFragment extends Fragment {
    public static LinearLayout orderBag;
    public static TextView itemCount, totalAmount;
    View view;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    DataBaseManager db;
    List<FoodItemsData.FoodItems> foodItems;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_order, container, false);
        orderBag = (LinearLayout) view.findViewById(R.id.orderBag);
        itemCount = (TextView) view.findViewById(R.id.counter);
        totalAmount = (TextView) view.findViewById(R.id.totalAmount);
        db = new DataBaseManager(this.getActivity());
        String whereClause = "RestaurantId = '" + RestaurantsAdapter.restaurantId + "'";
        foodItems = db.retrieveFoodItemRows(whereClause);
        recyclerView = view.findViewById(R.id.myOrderList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyOrderAdapter(this.getActivity(), foodItems);
        recyclerView.setAdapter(adapter);
        orderBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(MyOrderFragment.this)
                        .navigate(R.id.action_navigation_restaurants_btn_to_ordersFragment);
            }
        });
        return view;
    }
}