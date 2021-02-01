package com.example.menuws.ui.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.menuws.DataBaseManager;
import com.example.menuws.R;

import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends Fragment {
    List<Users> users = new ArrayList<>();
    public static int isAdmin = 0;
    public static int userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DataBaseManager db = new DataBaseManager(this.getActivity());
        Button regBtn;
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        regBtn = (Button) view.findViewById(R.id.registerBtn);
        ImageButton backBtn = view.findViewById(R.id.regBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(RegisterFragment.this)
                        .navigate(R.id.action_registerFragment_to_navigation_settings_btn);
            }
        });
        DataBaseManager db1 = new DataBaseManager(this.getContext());
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText regFNameInput, regLNameInput, regEmailInput, regPasswordInput, regReEnterPasswordInput;
                String fNameStr, lNameStr, emailStr, passwordStr, reEnterPasswordStr;
                CheckBox adminRegister;
                regFNameInput = (EditText) view.findViewById(R.id.regFNameInput);
                regLNameInput = (EditText) view.findViewById(R.id.regLNameInput);
                regEmailInput = (EditText) view.findViewById(R.id.regEmailInput);
                regPasswordInput = (EditText) view.findViewById(R.id.regPasswordInput);
                regReEnterPasswordInput = (EditText) view.findViewById(R.id.regValidPwInput);
                adminRegister = (CheckBox) view.findViewById(R.id.adminRegister);
                fNameStr = regFNameInput.getText().toString();
                lNameStr = regLNameInput.getText().toString();
                emailStr = regEmailInput.getText().toString();
                passwordStr = regPasswordInput.getText().toString();
                reEnterPasswordStr = regReEnterPasswordInput.getText().toString();
                if(adminRegister.isChecked()) {
                    isAdmin = 1;
                }
                boolean rowInserted = db.addUserRow(fNameStr, lNameStr, emailStr, passwordStr, isAdmin);
                users = db1.retrieveUserRows("email = '" + emailStr + "'");
                if(rowInserted && fNameStr.length() > 0 && lNameStr.length() > 0 && emailStr.length() > 0 &&
                        passwordStr.length() > 0 && passwordStr.equals(reEnterPasswordStr)) {
                    LoginFragment.isLoggedIn = true;
                    Toast.makeText(getActivity(), "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(RegisterFragment.this)
                            .navigate(R.id.action_registerFragment_to_navigation_settings_btn);
                    userId = users.get(0).id;
                } else {
                    Toast.makeText(getActivity(), "Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}