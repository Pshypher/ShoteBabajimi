package com.example.android.shotebabajimi.results;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.shotebabajimi.R;
import com.example.android.shotebabajimi.filter.model.Filter;

import com.example.android.shotebabajimi.results.model.CarOwner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class ResultsActivity extends AppCompatActivity  {

    private RecyclerView _recyclerView;
    private ResultsAdapter _adapter;
    private TextView _missingFileTextView;
    private ProgressBar _loadingIndicator;

    private Disposable _disposable;

    private Filter _filter;

    private static final String FILTER_EXTRAS_KEY = "filter_key";
    private static final String TAG = "ResultsActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(FILTER_EXTRAS_KEY)) {
            _filter = extras.getParcelable(FILTER_EXTRAS_KEY);
        }
        init();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (_disposable != null && !_disposable.isDisposed()) {
            _disposable.dispose();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Object> results = new ArrayList<Object>();
        ResultsViewModel viewModel = new ViewModelProvider(this,
                new ResultsViewModelFactory(getApplication(), _filter)).get(ResultsViewModel.class);
        viewModel.getSelectedCarOwners()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CarOwner>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        _disposable = d;
                    }

                    @Override
                    public void onNext(CarOwner carOwner) {
                        results.add(carOwner);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.wtf(TAG, "File not found.", e);
                    }

                    @Override
                    public void onComplete() {
                        results.add(0, results.size());
                        _adapter.add(results);
                        if (_adapter.getItemCount() > 0) resultsFeedback();
                        else noResultsFeedback();
                    }
                });
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageButton backButton = toolbar.findViewById(R.id.backButton);
        backButton.setVisibility(View.VISIBLE);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(ResultsActivity.this);
            }
        });
        TextView titleTextView = toolbar.findViewById(R.id.toolbarTitle);
        if (_filter != null) {
            String title = getString(R.string.results_title, _filter.startYear, _filter.endYear);
            titleTextView.setText(title);
        }
        setSupportActionBar(toolbar);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        _recyclerView = (RecyclerView) findViewById(R.id.resultsRecyclerView);
        _missingFileTextView = (TextView) findViewById(R.id.missingFileTextView);
        _loadingIndicator = (ProgressBar) findViewById(R.id.loadingIndicator);
        _recyclerView.setHasFixedSize(true);
        _recyclerView.setLayoutManager(layoutManager);
        _recyclerView.setClipToPadding(true);

        _adapter = new ResultsAdapter();
        _recyclerView.setAdapter(_adapter);
    }

    private void noResultsFeedback() {
        _recyclerView.setVisibility(View.GONE);
        _loadingIndicator.setVisibility(View.GONE);
        _missingFileTextView.setVisibility(View.VISIBLE);
    }

    private void resultsFeedback() {
        _recyclerView.setVisibility(View.VISIBLE);
        _loadingIndicator.setVisibility(View.GONE);
        _missingFileTextView.setVisibility(View.GONE);
    }
}
