package com.example.loginseguro;

import java.util.regex.Pattern;

public class User {
    private String userName;
    private String password;
    private boolean isAuthenticated;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.isAuthenticated = false;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }
    //validacion de contraseña



    public boolean validarContrsena(String contrasena){
        //String regex = "^[a-zA-Z.-9@#$%^&+=!¡¿?_]+$";
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!¡¿?-_])(?!.*\\s).{8,}$";
        Pattern patter = Pattern.compile(regex);
        return patter.matcher(contrasena).matches();
    }
}
