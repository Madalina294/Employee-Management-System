package com.example.springclient;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.springclient.model.EmployeeDto;
import com.example.springclient.retrofit.EmployeeApi;
import com.example.springclient.retrofit.RetrofitService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.logging.Level;
import java.util.logging.Logger;

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
       TextInputEditText inputEditFirstName = findViewById(R.id.form_TextFieldFirstName);
       TextInputEditText inputEditLastName = findViewById(R.id.form_TextFieldLastName);
       TextInputEditText inputEditEmail = findViewById(R.id.form_TextFieldEmail);
       MaterialButton buttonSave = findViewById(R.id.form_buttonSave);

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
                   Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, "Error occured", t);
               }
           });
       });


    }
}