package com.example.android.shotebabajimi.results;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.android.shotebabajimi.filter.model.Filter;
import com.example.android.shotebabajimi.results.model.CarOwner;
import com.example.android.shotebabajimi.utils.FileUtils;
import com.example.android.shotebabajimi.utils.FilterUtils;

import java.util.List;

import io.reactivex.Observable;

public class ResultsViewModel extends ViewModel {

    private Observable<CarOwner> _selectedCarOwnerObservable;
    private Application _application;
    private Filter _filter;

    private static final String TAG = "ResultsViewModel";

    public ResultsViewModel(@NonNull Application application, Filter filter) {
        _application = application;
        _filter = filter;
    }

    public Observable<CarOwner> getSelectedCarOwners() {
        if (_selectedCarOwnerObservable == null) {
            Observable<List<CarOwner>> allCarOwnersObservable = FileUtils.getCarOwners(_application);
            _selectedCarOwnerObservable = FilterUtils.applyFilter(_filter, allCarOwnersObservable);
        }

        return _selectedCarOwnerObservable;
    }

}
