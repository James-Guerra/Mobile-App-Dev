package com.example.menuws.ui.settings;

public class Users {
    public int id;
    public String fName;
    public String lName;
    public String email;
    public String password;
    public int admin;

    public Users(int id, String fName, String lName, String email, String password, int admin) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }
}
