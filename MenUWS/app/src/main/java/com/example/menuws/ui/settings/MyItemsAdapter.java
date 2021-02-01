package com.example.menuws.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuws.DataBaseManager;
import com.example.menuws.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static com.example.menuws.ui.settings.MyItemsFragment.addItemForm;
import static com.example.menuws.ui.settings.MyItemsFragment.submitItemBtn;
import static com.example.menuws.ui.settings.MyItemsFragment.recyclerView;
import static com.example.menuws.ui.settings.MyItemsFragment.itemFormTitle;
import static com.example.menuws.ui.settings.MyItemsFragment.itemName;
import static com.example.menuws.ui.settings.MyItemsFragment.itemPrice;

public class MyItemsAdapter extends RecyclerView.Adapter<MyItemsAdapter.ViewHolder> {
    private List<FoodItemsData.FoodItems> myItems, myRest;
    public static String itemId;
    public MyItemsAdapter(Context context, List<FoodItemsData.FoodItems> list) {
        myItems = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView displayItemName, displayItemPrice;
        ImageButton deleteItem, editItem;
        String itemId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            displayItemName = (TextView) itemView.findViewById(R.id.displayItemName);
            displayItemPrice = (TextView) itemView.findViewById(R.id.displayItemPrice);
            deleteItem = (ImageButton)itemView.findViewById(R.id.deleteItem);
            editItem = (ImageButton) itemView.findViewById(R.id.editItem);
        }
    }

    @NonNull
    @Override
    public MyItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_items_row_list_layout, parent, false);
        return new MyItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemsAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(myItems.get(position));
//        holder.restItemsTitle.setText(myRestaurants.);
        holder.displayItemName.setText(myItems.get(position).foodName);
        holder.displayItemPrice.setText("$ " + myItems.get(position).foodPrice);
        holder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(GONE);
                addItemForm.setVisibility(View.VISIBLE);
                addItemForm.animate().translationY(0).setDuration(350);
                submitItemBtn.setText("Update");
                itemFormTitle.setText("Update Item");
                itemName.setText(myItems.get(position).foodName);
                itemPrice.setText(myItems.get(position).foodPrice);
                itemId = String.valueOf(myItems.get(position).id);
            }
        });

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseManager db = new DataBaseManager(MyItemsFragment.context);
                db.deleteRow(DataBaseManager.FOOD_ITEMS_TABLE, String.valueOf(myItems.get(position).id));
                db.close();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myItems.size();
    }
}
