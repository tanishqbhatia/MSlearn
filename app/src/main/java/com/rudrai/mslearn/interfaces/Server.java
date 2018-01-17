package com.rudrai.mslearn.interfaces;

import com.rudrai.mslearn.models.Login;
import com.rudrai.mslearn.models.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Tanishq Bhatia on 1/16/2018 at 2:15 PM.
 * Contact at tanishqbhatia1995@gmail.com or +919780702709
 */

public interface Server {
    @POST("functions.php?func=register")
    @FormUrlEncoded
    Call<Register> register(@Field("name") String name,
                            @Field("email") String email,
                            @Field("password") String password);

    @POST("functions.php?func=login")
    @FormUrlEncoded
    Call<Login> login(@Field("email") String email,
                      @Field("password") String password);
}
