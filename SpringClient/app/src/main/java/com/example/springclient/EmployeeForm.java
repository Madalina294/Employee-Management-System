package com.example.springclient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import com.example.springclient.model.EmployeeDto;
import com.example.springclient.retrofit.EmployeeApi;
import com.example.springclient.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeForm extends AppCompatActivity {

    private Long employeeId = null; // null pentru create, nu null pentru update
    private EditText inputEditFirstName, inputEditLastName, inputEditEmail;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        loadEmployeeDataIfUpdate();
    }

    private void initializeComponents(){
       inputEditFirstName = findViewById(R.id.form_TextFieldFirstName);
       inputEditLastName = findViewById(R.id.form_TextFieldLastName);
       inputEditEmail = findViewById(R.id.form_TextFieldEmail);
       buttonSave = findViewById(R.id.form_buttonSave);

        RetrofitService retrofitService = new RetrofitService();
        EmployeeApi employeeApi = retrofitService.getRetrofit().create(EmployeeApi.class);

        buttonSave.setOnClickListener(view -> {
            String firstName = String.valueOf(inputEditFirstName.getText());
            String lastName = String.valueOf(inputEditLastName.getText());
            String email = String.valueOf(inputEditEmail.getText());

            EmployeeDto employee = new EmployeeDto();
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setEmail(email);

            if (employeeId != null) {
                // Update existing employee
                employee.setId(employeeId);
                employeeApi.updateEmployee(employeeId, employee).enqueue(new Callback<EmployeeDto>() {
                    @Override
                    public void onResponse(Call<EmployeeDto> call, Response<EmployeeDto> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(EmployeeForm.this, "Update successful!", Toast.LENGTH_SHORT).show();
                            finish(); // Close activity and return to list
                        } else {
                            Toast.makeText(EmployeeForm.this, "Update failed!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<EmployeeDto> call, Throwable t) {
                        Toast.makeText(EmployeeForm.this, "Update failed: " + t.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                });
            } else {
                // Create new employee
                employeeApi.save(employee).enqueue(new Callback<EmployeeDto>() {
                    @Override
                    public void onResponse(Call<EmployeeDto> call, Response<EmployeeDto> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(EmployeeForm.this, "Save successful!", Toast.LENGTH_SHORT).show();
                            finish(); // Close activity and return to list
                        } else {
                            Toast.makeText(EmployeeForm.this, "Save failed!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<EmployeeDto> call, Throwable t) {
                        Toast.makeText(EmployeeForm.this, "Save failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
            
    }

    private void loadEmployeeDataIfUpdate() {
        Intent intent = getIntent();
        employeeId = intent.getLongExtra("employee_id", -1);
        
        if (employeeId != -1) {
            // This is an update operation, load existing data
            String firstName = intent.getStringExtra("employee_firstName");
            String lastName = intent.getStringExtra("employee_lastName");
            String email = intent.getStringExtra("employee_email");
            
            inputEditFirstName.setText(firstName);
            inputEditLastName.setText(lastName);
            inputEditEmail.setText(email);
            
            // Change button text to indicate update
            buttonSave.setText("Update Employee");
        } else {
            // This is a create operation
            employeeId = null;
            buttonSave.setText("Save Employee");
        }
    }
}