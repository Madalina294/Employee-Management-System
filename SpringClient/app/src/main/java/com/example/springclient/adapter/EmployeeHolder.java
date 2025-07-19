package com.example.springclient.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;

public class EmployeeHolder extends RecyclerView.ViewHolder {

    TextView firstName, lastName, email;
    ImageButton menuButton;
    
    public EmployeeHolder(@NonNull View itemView) {
        super(itemView);
        firstName = itemView.findViewById(R.id.employeeListItem_firstName);
        lastName = itemView.findViewById(R.id.employeeListItem_lastName);
        email = itemView.findViewById(R.id.employeeListItem_Email);
        menuButton = itemView.findViewById(R.id.employeeListItem_menuButton);
    }
}
