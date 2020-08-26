package com.example.android.shotebabajimi.filter.data;


import androidx.annotation.NonNull;
import androidx.paging.DataSource;

import com.example.android.shotebabajimi.filter.model.Filter;

import io.reactivex.disposables.CompositeDisposable;

public class MockFilterDataSourceFactory extends DataSource.Factory<Long, Filter> {

    CompositeDisposable mCompositeDisposable;

    public MockFilterDataSourceFactory(CompositeDisposable compositeDisposable) {
        mCompositeDisposable = compositeDisposable;
    }

    @NonNull
    @Override
    public DataSource<Long, Filter> create() {
        MockFilterDataSource filterDataSource = new MockFilterDataSource(mCompositeDisposable);
        return filterDataSource;
    }
}
