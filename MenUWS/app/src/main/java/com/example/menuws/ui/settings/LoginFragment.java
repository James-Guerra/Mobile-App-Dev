package com.example.menuws.ui.settings;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menuws.DataBaseManager;
import com.example.menuws.R;

import java.util.ArrayList;

import static android.view.View.GONE;

public class LoginFragment extends Fragment {
    public static boolean isLoggedIn = false;
    public static ArrayList<Users> loggedInUser;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        DataBaseManager db = new DataBaseManager(this.getActivity());
        ImageButton backBtn = (ImageButton) view.findViewById(R.id.loginBackBtn);
        TextView regFromLogin = (TextView) view.findViewById(R.id.regFromLogin);
        EditText loginEmailInput = (EditText) view.findViewById(R.id.loginEmailInput);
        EditText loginPwInput = (EditText) view.findViewById(R.id.loginPasswordInput);
        Button loginBtn = (Button) view.findViewById(R.id.loginBtn);

        /* Begin code reference
        Underline TextView Using HTML
        Author: Vijay Ram
        Source: https://stackoverflow.com/questions/8033316/to-draw-an-underline-below-the-textview-in-android/43757835#
        :~:text=If%20your%20TextView%20has%20fixed,it%20right%20below%20your%20TextView.&text=String%20html%20%3D%20%22,setText(Html.
         */
        String underline = "<i><u>Register?<u><i>";isLoggedIn = true;
        regFromLogin.setText(Html.fromHtml(underline));
        /*
        End code reference
         */
        regFromLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_navigation_settings_btn);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String whereClause = "Email = '" + loginEmailInput.getText().toString().trim() + "'";
                loggedInUser = db.retrieveUserRows(whereClause);

                if(loginEmailInput.getText().toString().trim().length() > 0 && loginPwInput.getText().toString().trim().length() > 0
                        && loginEmailInput.getText().toString().trim().equals(loggedInUser.get(0).email)
                        && loginPwInput.getText().toString().trim().equals(loggedInUser.get(0).password)) {
                    isLoggedIn = true;
                    NavHostFragment.findNavController(LoginFragment.this)
                            .navigate(R.id.action_loginFragment_to_navigation_settings_btn);
                } else {
                    Toast.makeText(getActivity(), "Email or password incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}