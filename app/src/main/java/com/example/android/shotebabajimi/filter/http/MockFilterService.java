package com.example.android.shotebabajimi.filter.http;

import com.example.android.shotebabajimi.filter.model.Filter;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MockFilterService {

    @GET("/v3/b4cdeed3-327b-4591-9b06-aaf043e65497/")
    Observable<List<Filter>> getFilters(@Query("key") long id, @Query("size") int size);
}
