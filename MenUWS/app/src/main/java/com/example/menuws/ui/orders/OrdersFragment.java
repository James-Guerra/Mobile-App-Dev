package com.example.menuws.ui.orders;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.menuws.R;
import com.example.menuws.ui.settings.LoginFragment;
import com.example.menuws.ui.settings.SettingsFragment;

import java.util.ArrayList;

import static android.view.View.GONE;
import static com.example.menuws.ui.orders.MyOrderAdapter.counter;

public class OrdersFragment extends Fragment {
    View view;
    public static TextView orderPrice, gst, totalPrice;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        LinearLayout ordersFragLayout, displayOrders, displayLoginFromOrders;
        TextView registerFromOrders;
        Button loginFromOrders;
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        if ( LoginFragment.isLoggedIn || counter > 0) {
            ordersFragLayout = view.findViewById(R.id.ordersFragLayout);
            displayOrders = view.findViewById(R.id.displayOrders);
            orderPrice = view.findViewById(R.id.orderPrice);
            gst = view.findViewById(R.id.gst);
            totalPrice = view.findViewById(R.id.totalPrice);
            displayLoginFromOrders = view.findViewById(R.id.displayLoginFromOrders);
            //https://stackoverflow.com/questions/23517879/set-background-color-programmatically
            ordersFragLayout.setBackgroundColor(Color.parseColor("#15BEF3"));
            displayOrders.setVisibility(View.VISIBLE);
            displayLoginFromOrders.setVisibility(GONE);
            orderPrice.setText("Order price: $" + String.valueOf(MyOrderAdapter.total * 0.9));
            gst.setText("GST: $" + String.valueOf((double) Math.round( (MyOrderAdapter.total * 0.1) * 100) / 100));
            totalPrice.setText("Total Price: $" + String.valueOf((double) Math.round(MyOrderAdapter.total * 100 ) / 100));
        } else {
            registerFromOrders = view.findViewById(R.id.registerFromOrders);
            loginFromOrders = view.findViewById(R.id.loginFromOrders);
            String underline = "<i><u>Sign Up<u><i>";
            registerFromOrders.setText(Html.fromHtml(underline));
            loginFromOrders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavHostFragment.findNavController(OrdersFragment.this)
                            .navigate(R.id.action_navigation_orders_btn_to_loginFragment);
                }
            });

            registerFromOrders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavHostFragment.findNavController(OrdersFragment.this)
                            .navigate(R.id.action_navigation_orders_btn_to_registerFragment);
                }
            });
        }

        return view;
    }
}