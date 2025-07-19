package com.example.springclient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.model.EmployeeDto;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeHolder> {

    private List<EmployeeDto> employeesList;

    public EmployeeAdapter(List<EmployeeDto> employeesList) {
        this.employeesList = employeesList;
    }

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_list_item, parent, false);
        return new EmployeeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {
        EmployeeDto employee = employeesList.get(position);
        holder.firstName.setText(employee.getFirstName());
        holder.lastName.setText(employee.getLastName());
        holder.email.setText(employee.getEmail());

    }

    @Override
    public int getItemCount() {
        return employeesList.size();
    }
}
