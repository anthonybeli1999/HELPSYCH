package com.example.helpsych.Adapters;

import android.content.Context;

import com.example.helpsych.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter {
    Context context;
    List<User>  UserList;

    public void filtrar(ArrayList<User> filtroUsuarios)
    {
        this.UserList = filtroUsuarios;
        notify();
    }
}
