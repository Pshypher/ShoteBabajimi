package com.example.android.shotebabajimi.filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.shotebabajimi.R;
import com.example.android.shotebabajimi.filter.model.Filter;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class MainActivity extends AppCompatActivity {

    private FiltersViewModel viewModel;
    private FilterAdapter adapter;
    private RecyclerView filtersRecyclerView;
    private ProgressBar loadingIndicator;
    private TextView emptyFiltersTextView;

    private Disposable disposable;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filtersRecyclerView = (RecyclerView) findViewById(R.id.filtersRecyclerView);
        loadingIndicator = (ProgressBar) findViewById(R.id.loadingIndicator);
        emptyFiltersTextView = (TextView) findViewById(R.id.emptyFilterTextView);
        viewModel = new ViewModelProvider(this).get(FiltersViewModel.class);
        initializeAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.filters
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PagedList<Filter>>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(PagedList<Filter> filterPagedList) {
                adapter.submitList(filterPagedList);
                if (adapter.getItemCount() > 0) revealFilterList();
                else displayEmptyFiltersView();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, e.getMessage());

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (disposable != null) disposable.dispose();
    }

    private void initializeAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        adapter = new FilterAdapter(FilterAdapter.DIFF_CALLBACK);
        filtersRecyclerView.setLayoutManager(layoutManager);
        filtersRecyclerView.setAdapter(adapter);

        if (!isConnected()) return;
    }

    public void revealFilterList() {
        filtersRecyclerView.setVisibility(VISIBLE);
        loadingIndicator.setVisibility(GONE);
        emptyFiltersTextView.setVisibility(GONE);
    }

    public void displayEmptyFiltersView() {
        filtersRecyclerView.setVisibility(GONE);
        loadingIndicator.setVisibility(GONE);
        emptyFiltersTextView.setVisibility(VISIBLE);
    }

    private boolean isConnected() {
        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}