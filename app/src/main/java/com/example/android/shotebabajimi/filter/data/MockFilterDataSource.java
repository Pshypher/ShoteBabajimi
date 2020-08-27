package com.example.android.shotebabajimi.filter.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.android.shotebabajimi.filter.http.MockFilterClient;
import com.example.android.shotebabajimi.filter.http.MockFilterService;
import com.example.android.shotebabajimi.filter.model.Filter;

import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Retrofit;

public class MockFilterDataSource extends PageKeyedDataSource<Long, Filter> {

    private MockFilterService mService;
    private CompositeDisposable mCompositeDisposable;

    private static final String TAG = "MockFilterDataSource";

    public MockFilterDataSource(CompositeDisposable compositeDisposable) {
        Retrofit retrofit = MockFilterClient.getInstance();
        mService = retrofit.create(MockFilterService.class);
        mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<Long, Filter> callback) {
        Observable<List<Filter>> filterObservable = mService.getFilters(1, params.requestedLoadSize);
        Disposable disposable =
                filterObservable
                        .subscribe(filters -> callback.onResult(filters, null, (long) 5),
                                throwable -> Log.d(TAG, throwable.getMessage()));
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Filter> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Filter> callback) {

        Observable<List<Filter>> filterObservable = mService.getFilters(params.key, params.requestedLoadSize);
        Disposable disposable = filterObservable
                .subscribe(new Consumer<List<Filter>>() {
                               @Override
                               public void accept(List<Filter> filters) throws Exception {
                                   long nextKey = (params.key == filters.size()) ? null : params.key + 1;
                                   callback.onResult(filters, nextKey);
                               }
                           },
                        throwable -> Log.d(TAG, Objects.requireNonNull(throwable.getMessage())));
        mCompositeDisposable.add(disposable);
    }
}
