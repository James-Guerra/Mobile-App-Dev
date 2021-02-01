package com.example.menuws.ui.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blure.complexview.ComplexView;
import com.example.menuws.DataBaseManager;
import com.example.menuws.MainActivity;
import com.example.menuws.R;
import com.example.menuws.ui.restaurants.RestaurantsData;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;

public class MyRestaurantsFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    View view;
    List<RestaurantsData.Restaurants> myRestaurants;
    DataBaseManager db;
    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_restaurants, container, false);
        ComplexView addRestForm = view.findViewById(R.id.addRestForm);
        context = getContext();
        recyclerView = view.findViewById(R.id.myRestaurantsList);

        //change action bar to custom action bar
        MainActivity main = (MainActivity) getActivity();
        main.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        main.getSupportActionBar().setDisplayShowCustomEnabled(true);
        main.getSupportActionBar().setCustomView(R.layout.custom_options_menu);
        ImageButton addRestaurantBtn = (ImageButton) main.findViewById(R.id.addNewRestaurantBtn);

        //animate create restaurant form
        addRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(GONE);
                addRestForm.setVisibility(View.VISIBLE);
                addRestForm.animate().translationY(0).setDuration(350);
            }
        });

        //setup recyclerview
        db = new DataBaseManager(this.getActivity());
        db.openReadable();
        String whereClause = "UserId = '" + LoginFragment.loggedInUser.get(0).id + "'";
        myRestaurants = db.retrieveRestaurantRows(whereClause);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyRestaurantsAdapter(this.getActivity(), view, context, myRestaurants);
        recyclerView.setAdapter(adapter);

        DataBaseManager db1 = new DataBaseManager(getActivity());
        Button addRestBtn = view.findViewById(R.id.submitRestForm);


        //submit restaurant on click
        addRestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restNameStr, restTypeStr, restMinOrderStr, restAddressStr;
                EditText restName = (EditText) view.findViewById(R.id.restNameInput);
                EditText restType = (EditText) view.findViewById(R.id.restTypeInput);
                EditText minOrder = (EditText) view.findViewById(R.id.minOrderInput);
                EditText address = (EditText) view.findViewById(R.id.restAddressInput);

                restNameStr = restName.getText().toString().trim();
                restTypeStr = restType.getText().toString().trim();
                restMinOrderStr = minOrder.getText().toString().trim();
                restAddressStr = address.getText().toString().trim();
//                insert new restaurant
                boolean recInserted = db.addRestaurantRow(DataBaseManager.REST_TABLE, restNameStr,
                        restTypeStr, Double.parseDouble(restMinOrderStr), restAddressStr, LoginFragment.loggedInUser.get(0).id);

                if(recInserted && restNameStr.length() > 0 && restTypeStr.length() > 0 &&
                        restMinOrderStr.length() > 0 && restAddressStr.length() > 0 && addRestBtn.getText().toString().equals("Submit")) {
                    recyclerView.setVisibility(View.VISIBLE);
                    addRestForm.setVisibility(GONE);

                    //update recyclerview instantaneously (without reload)
                    myRestaurants.add(new RestaurantsData.Restaurants(0, restNameStr, restTypeStr, restMinOrderStr, restAddressStr, LoginFragment.loggedInUser.get(0).id));
                    adapter.notifyDataSetChanged();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(address.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    Toast.makeText(getActivity(), "Restaurant Added Successfully", Toast.LENGTH_SHORT).show();
                    restName.setText("");
                    restType.setText("");
                    minOrder.setText("");
                    address.setText("");
                }
                else {
                    Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


}