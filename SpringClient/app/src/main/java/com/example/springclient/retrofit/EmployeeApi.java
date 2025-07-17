package com.example.springclient.retrofit;

import com.example.springclient.model.EmployeeDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EmployeeApi {

    @GET("/api/employees/get-all")
    Call<List<EmployeeDto>> getAllEmployees();

    @POST("/api/employees/save")
    Call<EmployeeDto> save(@Body EmployeeDto employee);
}
