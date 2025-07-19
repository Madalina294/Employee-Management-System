package com.example.springclient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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


public class EmployeeListActivity extends AppCompatActivity {

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
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(employeesList);
        recyclerView.setAdapter(employeeAdapter);

    }
    @Override
    protected void onResume() {
        super.onResume();
        loadEmployees();
    }
}
