package com.example.android.shotebabajimi.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.shotebabajimi.R;
import com.example.android.shotebabajimi.filter.model.Filter;
import com.example.android.shotebabajimi.results.ResultsActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class FilterActivity extends AppCompatActivity implements
        FilterAdapter.OnItemClickListener {

    private FiltersViewModel _viewModel;
    private FilterAdapter _adapter;
    private RecyclerView _filtersRecyclerView;
    private ProgressBar _loadingIndicator;
    private TextView _emptyFiltersTextView;

    private CompositeDisposable _compositeDisposable;

    private static final String TAG = "FilterActivity";

    private static final String FILTER_EXTRAS_KEY = "filter_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        _filtersRecyclerView = (RecyclerView) findViewById(R.id.filtersRecyclerView);
        _loadingIndicator = (ProgressBar) findViewById(R.id.loadingIndicator);
        _emptyFiltersTextView = (TextView) findViewById(R.id.emptyFilterTextView);
        _viewModel = new ViewModelProvider(this).get(FiltersViewModel.class);
        _compositeDisposable = new CompositeDisposable();
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        _viewModel.filters
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PagedList<Filter>>() {
            @Override
            public void onSubscribe(Disposable d) {
                _compositeDisposable.add(d);
            }

            @Override
            public void onNext(PagedList<Filter> filterPagedList) {
                _adapter.submitList(filterPagedList);
                if (_adapter.getItemCount() > 0) filtersFeedback();
                else emptyFiltersFeedback();
            }

            @Override
            public void onError(Throwable e) { Log.d(TAG, e.getMessage()); }

            @Override
            public void onComplete() { }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        _compositeDisposable.clear();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView titleTextView = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        titleTextView.setText(R.string.filter_title);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        _adapter = new FilterAdapter(FilterAdapter.DIFF_CALLBACK, this, _compositeDisposable);
        _filtersRecyclerView.setLayoutManager(layoutManager);
        _filtersRecyclerView.setClipToPadding(true);
        _filtersRecyclerView.setAdapter(_adapter);

        if (!isConnected()) return;
    }

    public void filtersFeedback() {
        _filtersRecyclerView.setVisibility(VISIBLE);
        _loadingIndicator.setVisibility(GONE);
        _emptyFiltersTextView.setVisibility(GONE);
    }

    public void emptyFiltersFeedback() {
        _filtersRecyclerView.setVisibility(GONE);
        _loadingIndicator.setVisibility(GONE);
        _emptyFiltersTextView.setVisibility(VISIBLE);
    }

    private boolean isConnected() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onItemClick(Filter filter) {
        Intent intent = new Intent(FilterActivity.this, ResultsActivity.class);
        intent.putExtra(FILTER_EXTRAS_KEY, filter);
        startActivity(intent);
    }
}