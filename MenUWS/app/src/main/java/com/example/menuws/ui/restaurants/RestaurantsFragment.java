package com.example.menuws.ui.restaurants;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.menuws.DataBaseManager;
import com.example.menuws.R;
import com.example.menuws.ui.restaurants.RestaurantsData.Restaurants;

import java.util.List;

public class RestaurantsFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    View view;
    List<Restaurants> restaurants;
    DataBaseManager db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_restaurants_list, container, false);
        db = new DataBaseManager(this.getActivity());
        restaurants = db.retrieveRestaurantRows(null);
        recyclerView = view.findViewById(R.id.restaurants_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RestaurantsAdapter(this.getActivity(), restaurants);
        recyclerView.setAdapter(adapter);
        return view;
    }
}