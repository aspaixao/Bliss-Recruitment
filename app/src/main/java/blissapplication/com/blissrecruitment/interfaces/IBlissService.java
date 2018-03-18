package blissapplication.com.blissrecruitment.interfaces;

import java.util.List;

import blissapplication.com.blissrecruitment.model.Health;
import blissapplication.com.blissrecruitment.model.Question;
import blissapplication.com.blissrecruitment.model.Share;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

// Created by Alexandre Paixao on 3/17/2018.

public interface IBlissService {

    @GET("health")
    Call<Health> getServiceHealth();

    @GET("questions")
    Call<List<Question>> getQuestions(@Query("limit") int limit, @Query("offset") int offset,
                                      @Query("filter") String filter);

    @GET("questions/{question_id}")
    Call<Question> getQuestion(@Path("question_id") int questionId);

    @PUT("questions/{question_id}")
    Call<Question> putQuestion(@Path("question_id") int questionId);

    @POST("share")
    Call<Health> postShare(@Body Share share);
}
