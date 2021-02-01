package com.example.menuws.ui.settings
        ;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.menuws.DataBaseManager;
import com.example.menuws.R;

import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SettingsFragment extends Fragment {
    Button toLoginFrag, logOut;
    TextView toRegFrag;
    LinearLayout initialAccDisplay, toMyRestFrag;
    LinearLayout secondaryAccDisplay;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //variables
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        toMyRestFrag = (LinearLayout) view.findViewById(R.id.toMyRestFrag);
        toLoginFrag = (Button) view.findViewById(R.id.toLoginFrag);
        toRegFrag = (TextView) view.findViewById(R.id.toRegFrag);
        initialAccDisplay = (LinearLayout) view.findViewById(R.id.initialAccDisplay);
        secondaryAccDisplay = (LinearLayout) view.findViewById(R.id.secondaryAccDisplay);
        logOut = (Button) view.findViewById(R.id.logOutBtn);
        String underline = "<i><u>Sign Up<u><i>";
        toRegFrag.setText(Html.fromHtml(underline));
        toLoginFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SettingsFragment.this)
                        .navigate(R.id.action_navigation_settings_btn_to_loginFragment);
            }
        });

        toRegFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SettingsFragment.this)
                        .navigate(R.id.action_navigation_settings_btn_to_registerFragment);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment.isLoggedIn = false;
                initialAccDisplay.setVisibility(VISIBLE);
                secondaryAccDisplay.setVisibility(GONE);
            }
        });

        if(LoginFragment.isLoggedIn) {
            initialAccDisplay.setVisibility(GONE);
            secondaryAccDisplay.setVisibility(VISIBLE);
            if(LoginFragment.loggedInUser.get(0).admin == 1) {
                toMyRestFrag.setVisibility(VISIBLE);
            }
            else {
                toMyRestFrag.setVisibility(GONE);
            }
        }

        toMyRestFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SettingsFragment.this)
                        .navigate(R.id.action_navigation_settings_btn_to_myRestaurantsFragment);
            }
        });
        return view;
    }
}