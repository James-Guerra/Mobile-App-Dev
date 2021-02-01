package com.example.menuws.ui.orders;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuws.R;
import com.example.menuws.ui.settings.FoodItemsData;

import java.util.ArrayList;
import java.util.List;

import static com.example.menuws.ui.orders.MyOrderFragment.itemCount;
import static com.example.menuws.ui.orders.MyOrderFragment.orderBag;
import static com.example.menuws.ui.orders.MyOrderFragment.totalAmount;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    public View mView;
    private final List<FoodItemsData.FoodItems> myOrders;
    public static ArrayList<String[]> displayedOrders;
    public Context context;
    public static double total = 0.0;
    public static int counter = 0;
    private Fragment MyRestaurantsFragment;

    public MyOrderAdapter(Context context, List<FoodItemsData.FoodItems> list) {
        myOrders = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodName, foodPrice;
        ImageView foodImage;
        CheckBox selectedItem;
        LinearLayout orderBag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = (TextView) itemView.findViewById(R.id.myOrderFoodName);
            foodPrice = (TextView) itemView.findViewById(R.id.myOrderFoodPrice);
            foodImage = (ImageView) itemView.findViewById(R.id.myOrderFoodImage);
            selectedItem = (CheckBox) itemView.findViewById(R.id.selectedItem);
        }
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_order_row_list_layout, parent, false);
        return new MyOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {
        displayedOrders = new ArrayList<>();
        holder.itemView.setTag(myOrders.get(position));
        holder.foodName.setText(myOrders.get(position).foodName);
        holder.foodPrice.setText("$" + myOrders.get(position).foodPrice);
        String[] itemDetails = new String[3];
        holder.selectedItem.setOnClickListener((v) -> {
            int pos = holder.getAdapterPosition();

            myOrders.get(pos).setChecked(holder.selectedItem.isChecked());
            itemDetails[0] = myOrders.get(pos).foodName;
            itemDetails[1] = myOrders.get(pos).foodPrice;
            itemDetails[2] = String.valueOf(myOrders.get(pos).id);
            if (myOrders.get(pos).isChecked()) {
                holder.selectedItem.setChecked(true);
                total += Double.parseDouble(myOrders.get(pos).foodPrice);
                counter += 1;
                displayedOrders.add(itemDetails);
            } else {
                holder.selectedItem.setChecked(false);
                total -= Double.parseDouble(myOrders.get(pos).foodPrice);
                counter -= 1;
                displayedOrders.remove(itemDetails);
            }
            total = (double) Math.round(total * 100) / 100;

            if(counter > 0) {
                orderBag.setVisibility(View.VISIBLE);
                orderBag.animate().translationY(-100).setDuration(350);
                itemCount.setText(counter + " items in bag.");
                totalAmount.setText("$" + String.valueOf(total));
            } else {
                orderBag.animate().translationY(400).setDuration(350);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myOrders.size();
    }
}
