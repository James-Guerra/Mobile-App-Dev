package com.example.menuws.ui.settings;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.blure.complexview.ComplexView;
import com.example.menuws.DataBaseManager;
import com.example.menuws.R;
import com.example.menuws.ui.restaurants.RestaurantsData;

import java.util.List;

import static android.view.View.GONE;
import static com.example.menuws.ui.settings.MyItemsFragment.itemFormTitle;
import static com.example.menuws.ui.settings.MyItemsFragment.itemName;
import static com.example.menuws.ui.settings.MyItemsFragment.itemPrice;
import static com.example.menuws.ui.settings.MyItemsFragment.submitItemBtn;

public class MyRestaurantsAdapter extends RecyclerView.Adapter<MyRestaurantsAdapter.ViewHolder> {
    FragmentActivity fragmentActivity;
    private final List<RestaurantsData.Restaurants> myRestaurants;
    public static int restaurantId;
    DataBaseManager db;
    View view;


    public MyRestaurantsAdapter(FragmentActivity fragmentActivity, View view, Context context, List<RestaurantsData.Restaurants> list) {
        this.fragmentActivity = fragmentActivity;
        this.view = view;
        myRestaurants = list;
        db = new DataBaseManager(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myRestName;
        LinearLayout toItemsFrag;
        ImageButton deleteRestaurant, editRestaurant;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            myRestName = (TextView) itemView.findViewById(R.id.myRestaurantRestName);
            toItemsFrag = (LinearLayout) itemView.findViewById(R.id.toItemsFrag);
            deleteRestaurant = (ImageButton) itemView.findViewById(R.id.deleteRestaurant);
            editRestaurant = (ImageButton) itemView.findViewById(R.id.editRestaurant);
        }
    }

    @NonNull
    @Override
    public MyRestaurantsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_restaurants_row_list_layout, parent, false);
        return new MyRestaurantsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRestaurantsAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(myRestaurants.get(position));
        holder.myRestName.setText(myRestaurants.get(position).restName);

        //Navigate to Items Fragment
        holder.toItemsFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantId = myRestaurants.get(position).id;
                MyItemsFragment fragment = new MyItemsFragment();
                FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.myRestFrag, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        //Delete restaurant button
        holder.deleteRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteRestaurantRow(DataBaseManager.REST_TABLE, String.valueOf(myRestaurants.get(position).id));
                myRestaurants.remove(position);
                MyRestaurantsAdapter.this.notifyItemRemoved(position);
                MyRestaurantsAdapter.this.notifyItemRangeChanged(position, myRestaurants.size());
            }
        });

        //Edit restaurant button
        holder.editRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText restName, restType, restMinOrder, restAddress;
                ComplexView addRestForm = view.findViewById(R.id.addRestForm);
                RecyclerView myRestaurantsList = view.findViewById(R.id.myRestaurantsList);
                Button updateRestForm = view.findViewById(R.id.updateRestForm);
                Button submitRestForm = view.findViewById(R.id.submitRestForm);
                TextView restFormTitle = view.findViewById(R.id.restaurantFormTitle);
                restName = view.findViewById(R.id.restNameInput);
                restType = view.findViewById(R.id.restTypeInput);
                restMinOrder = view.findViewById(R.id.minOrderInput);
                restAddress = view.findViewById(R.id.restAddressInput);

                //animate editing form
                addRestForm.setVisibility(View.VISIBLE);
                myRestaurantsList.setVisibility(GONE);
                submitRestForm.setVisibility(GONE);
                updateRestForm.setVisibility(View.VISIBLE);
                addRestForm.animate().translationY(0).setDuration(350);
                restFormTitle.setText("Update Restaurant");

                //set all input fields to existing information
                restName.setText(myRestaurants.get(position).restName);
                restType.setText(myRestaurants.get(position).restType);
                restMinOrder.setText(myRestaurants.get(position).minOrder);
                restAddress.setText(myRestaurants.get(position).restAddress);

                //update restaurant on click
                updateRestForm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        restaurantId = myRestaurants.get(position).id;
                        String whereClause = "RestaurantId = '" + restaurantId + "'";
                        db.updateRestaurantRow(
                                DataBaseManager.REST_TABLE,
                                whereClause,
                                restName.getText().toString().trim(),
                                restType.getText().toString().trim(),
                                Double.parseDouble(restMinOrder.getText().toString().trim()),
                                restAddress.getText().toString().trim()
                        );
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return myRestaurants.size();
    }
}
