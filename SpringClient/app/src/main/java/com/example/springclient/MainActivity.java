package com.example.springclient;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
    }

    private void initializeComponents(){
       EditText inputEditFirstName = findViewById(R.id.form_TextFieldFirstName);
       EditText inputEditLastName = findViewById(R.id.form_TextFieldLastName);
       EditText inputEditEmail = findViewById(R.id.form_TextFieldEmail);
       Button buttonSave = findViewById(R.id.form_buttonSave);

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

           employeeApi.save(employee).enqueue(new Callback<EmployeeDto>() {
               @Override
               public void onResponse(Call<EmployeeDto> call, Response<EmployeeDto> response) {
                   Toast.makeText(MainActivity.this, "Save successful!", Toast.LENGTH_SHORT).show();
               }

               @Override
               public void onFailure(Call<EmployeeDto> call, Throwable t) {
                   Toast.makeText(MainActivity.this, "Save failed!", Toast.LENGTH_SHORT).show();
               }
           });
       });


    }
}