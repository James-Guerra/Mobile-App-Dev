package com.example.menuws.ui.restaurants;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuws.ui.restaurants.RestaurantsData.Restaurants;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.menuws.R;
import com.example.menuws.ui.orders.MyOrderFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
    FragmentActivity fragmentActivity;
    private List<RestaurantsData.Restaurants> restaurants;
    public static int restaurantId;

    public RestaurantsAdapter(FragmentActivity fragmentActivity, List<RestaurantsData.Restaurants> restaurants) {
        this.restaurants = restaurants;
        this.fragmentActivity = fragmentActivity;
    }

    @NotNull
    @Override
    public RestaurantsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurants_row_list_layout, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.restaurant = restaurants.get(position);
        holder.restName.setText(restaurants.get(position).restName);
        holder.restFoodType.setText(restaurants.get(position).restType);
        holder.restMinOrder.setText(restaurants.get(position).minOrder);
        holder.restAddress.setText(restaurants.get(position).restAddress);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantId = restaurants.get(position).id;
                MyOrderFragment fragment = new MyOrderFragment();
                FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.restFrag, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
//        holder.restImg.setImageResource(restaurants.get(position).restaurantImage);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView restImg;
        public TextView restName, restFoodType, restMinOrder, restAddress;
        public Restaurants restaurant;

        public ViewHolder(View view) {
            super(view);
            restImg = (ImageView) view.findViewById(R.id.restaurantImage);
            restName = (TextView) view.findViewById(R.id.restName);
            restFoodType = (TextView) view.findViewById(R.id.restFoodType);
            restMinOrder = (TextView) view.findViewById(R.id.restMinOrder);
            restAddress = (TextView) view.findViewById(R.id.restAddress);
        }
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }
}