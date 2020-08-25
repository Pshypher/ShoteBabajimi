package com.example.android.shotebabajimi.http;

import com.example.android.shotebabajimi.model.Filter;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface FilterServiceAPI {

    @GET
    Observable<List<Filter>> getFilters();
}
