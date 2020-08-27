package com.example.android.shotebabajimi.results.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.shotebabajimi.filter.model.Filter;
import com.example.android.shotebabajimi.results.ResultsViewModel;

public class ResultsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Application _application;
    private Filter _filter;

    public ResultsViewModelFactory(Application application, Filter filter) {
        _application = application;
        _filter = filter;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ResultsViewModel(_application, _filter);
    }
}
