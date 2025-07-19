package com.example.springclient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.model.EmployeeDto;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeHolder> {

    private List<EmployeeDto> employeesList;
    private OnEmployeeActionListener actionListener;

    public interface OnEmployeeActionListener {
        void onUpdateEmployee(EmployeeDto employee);
        void onDeleteEmployee(EmployeeDto employee);
    }

    public EmployeeAdapter(List<EmployeeDto> employeesList, OnEmployeeActionListener actionListener) {
        this.employeesList = employeesList;
        this.actionListener = actionListener;
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

        // Set click listener for menu button
        holder.menuButton.setOnClickListener(view -> {
            showPopupMenu(view, employee);
        });
    }

    private void showPopupMenu(View view, EmployeeDto employee) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        popup.getMenuInflater().inflate(R.menu.fab_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            
            if (itemId == R.id.menu_update) {
                if (actionListener != null) {
                    actionListener.onUpdateEmployee(employee);
                }
                return true;
            } else if (itemId == R.id.menu_delete) {
                if (actionListener != null) {
                    actionListener.onDeleteEmployee(employee);
                }
                return true;
            }
            
            return false;
        });

        popup.show();
    }

    @Override
    public int getItemCount() {
        return employeesList.size();
    }
}
