package com.example.android.shotebabajimi.filter;

import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;

import com.example.android.shotebabajimi.filter.data.MockFilterDataSourceFactory;
import com.example.android.shotebabajimi.filter.model.Filter;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class FiltersViewModel extends ViewModel {

    Observable<PagedList<Filter>> filters;
    private CompositeDisposable mCompositeDisposable;

    private static final int SIZE = 10;
    private static final String TAG = "FiltersViewModel";

    public FiltersViewModel() {
        mCompositeDisposable = new CompositeDisposable();
        MockFilterDataSourceFactory dataSourceFactory = new MockFilterDataSourceFactory(mCompositeDisposable);
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(SIZE)
                .setInitialLoadSizeHint(SIZE)
                .setEnablePlaceholders(false)
                .build();

        filters = new RxPagedListBuilder<>(
                dataSourceFactory, 5)
                .buildObservable();

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
    }
}
