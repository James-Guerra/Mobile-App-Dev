package com.example.menuws.ui.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuws.R;

import java.util.ArrayList;

import static com.example.menuws.ui.orders.MyOrderAdapter.displayedOrders;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    public OrdersAdapter(Context context, ArrayList<String[]>list) {
        displayedOrders = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderName, decrementOrder, incrementOrder, numberOfItems;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderName = itemView.findViewById(R.id.foodItemName);
            decrementOrder = itemView.findViewById(R.id.decrementItem);
            incrementOrder = itemView.findViewById(R.id.incrementItem);
            numberOfItems = itemView.findViewById(R.id.numberOfItems);
        }
    }
    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_row_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(displayedOrders.get(position));
        holder.orderName.setText(displayedOrders.get(position)[0]);
        holder.decrementOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayedOrders.get(position)[2] = String.valueOf(Integer.parseInt(displayedOrders.get(position)[2]) - 1);
                holder.numberOfItems.setText(String.valueOf(displayedOrders.get(position)[2]));
                if(displayedOrders.get(position)[2].equals("0")) {
                    holder.numberOfItems.setText("0");
                }
            }
        });

        holder.incrementOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayedOrders.get(position)[2] = String.valueOf(Integer.parseInt(displayedOrders.get(position)[2]) + 1);
                holder.numberOfItems.setText(String.valueOf(displayedOrders.get(position)[2]));
            }
        });
    }

    @Override
    public int getItemCount() {
        return displayedOrders.size();
    }
}
