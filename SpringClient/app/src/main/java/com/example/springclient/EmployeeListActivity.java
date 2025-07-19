package com.example.springclient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.adapter.EmployeeAdapter;
import com.example.springclient.model.EmployeeDto;
import com.example.springclient.retrofit.EmployeeApi;
import com.example.springclient.retrofit.RetrofitService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmployeeListActivity extends AppCompatActivity implements EmployeeAdapter.OnEmployeeActionListener {

    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        recyclerView = findViewById(R.id.employeeList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton floatingActionButton = findViewById(R.id.employeeList_fab);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, EmployeeForm.class);
            startActivity(intent);
        });

        loadEmployees();

    }

    private void loadEmployees(){

        RetrofitService retrofitService = new RetrofitService();
        EmployeeApi employeeApi = retrofitService.getRetrofit().create(EmployeeApi.class);
        employeeApi.getAllEmployees()
                .enqueue(new Callback<List<EmployeeDto>>() {
                    @Override
                    public void onResponse(Call<List<EmployeeDto>> call, Response<List<EmployeeDto>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            populateListView(response.body());
                        } else {
                            Toast.makeText(EmployeeListActivity.this, "No data received!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<EmployeeDto>> call, Throwable t) {
                        Toast.makeText(EmployeeListActivity.this, "Failed to load employees: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }


                });

    }
    
    private void populateListView(List<EmployeeDto> employeesList){
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(employeesList, this);
        recyclerView.setAdapter(employeeAdapter);

    }
    
    @Override
    public void onUpdateEmployee(EmployeeDto employee) {
        // Navigate to EmployeeForm with employee data for editing
        Intent intent = new Intent(this, EmployeeForm.class);
        intent.putExtra("employee_id", employee.getId());
        intent.putExtra("employee_firstName", employee.getFirstName());
        intent.putExtra("employee_lastName", employee.getLastName());
        intent.putExtra("employee_email", employee.getEmail());
        startActivity(intent);
    }
    
    @Override
    public void onDeleteEmployee(EmployeeDto employee) {
        // Show confirmation dialog before deleting
        new AlertDialog.Builder(this)
                .setTitle("Delete Employee")
                .setMessage("Are you sure you want to delete " + employee.getFirstName() + " " + employee.getLastName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    deleteEmployeeFromServer(employee);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    
    private void deleteEmployeeFromServer(EmployeeDto employee) {
        RetrofitService retrofitService = new RetrofitService();
        EmployeeApi employeeApi = retrofitService.getRetrofit().create(EmployeeApi.class);
        
        employeeApi.deleteEmployee(employee.getId())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(EmployeeListActivity.this, "Employee deleted successfully", Toast.LENGTH_SHORT).show();
                            loadEmployees(); // Refresh the list
                        } else {
                            Toast.makeText(EmployeeListActivity.this, "Failed to delete employee", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(EmployeeListActivity.this, "Error deleting employee: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loadEmployees();
    }
}
