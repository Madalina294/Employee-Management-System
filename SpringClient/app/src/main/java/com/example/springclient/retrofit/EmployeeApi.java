package com.example.springclient.retrofit;

import com.example.springclient.model.EmployeeDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmployeeApi {

    @GET("/api/employees/get-all")
    Call<List<EmployeeDto>> getAllEmployees();

    @POST("/api/employees/save")
    Call<EmployeeDto> save(@Body EmployeeDto employee);

    @DELETE("/api/employees/{id}")
    Call<Void> deleteEmployee(@Path("id") Long id);

    @PUT("/api/employees/{id}")
    Call<EmployeeDto> updateEmployee(@Path("id") Long id, @Body EmployeeDto employee);
}
