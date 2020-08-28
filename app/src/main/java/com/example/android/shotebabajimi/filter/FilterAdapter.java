package com.example.android.shotebabajimi.filter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.shotebabajimi.R;
import com.example.android.shotebabajimi.filter.model.Filter;
import com.example.android.shotebabajimi.results.model.CarOwner;
import com.example.android.shotebabajimi.utils.ColorUtils;
import com.example.android.shotebabajimi.utils.FileUtils;
import com.example.android.shotebabajimi.utils.FilterUtils;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;


public class FilterAdapter extends PagedListAdapter<Filter, FilterAdapter.ViewHolder> {

    private OnItemClickListener _onItemClickListener;

    private CompositeDisposable _compositeDisposable;

    public interface OnItemClickListener {
        void onItemClick(Filter filter);
    }


    public static final DiffUtil.ItemCallback<Filter> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Filter>() {

                @Override
                public boolean areItemsTheSame(@NonNull Filter oldItem, @NonNull Filter newItem) {
                    return oldItem.id == newItem.id;
                }

                @Override
                public boolean areContentsTheSame(@NonNull Filter oldItem, @NonNull Filter newItem) {
                    return oldItem.startYear == newItem.startYear &&
                            oldItem.endYear == newItem.endYear;
                }
            };

    protected FilterAdapter(@NonNull DiffUtil.ItemCallback<Filter> diffCallback,
                            OnItemClickListener listener, CompositeDisposable compositeDisposable) {
        super(diffCallback);
        _onItemClickListener = listener;
        _compositeDisposable = compositeDisposable;
    }

    @NonNull
    @Override
    public FilterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.filter_list_item_layout, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterAdapter.ViewHolder holder, int position) {
        Filter filter = getItem(position);
        if (filter == null) return;
        holder.bind(filter);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView _dateRangeTextView;
        private TextView _genderTextView;
        private TextView _countriesTextView;
        private LinearLayout _colorsViewGroup;
        private TextView _resultCountTextView;

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
            rootView.setOnClickListener(this);
            _dateRangeTextView = (TextView) rootView.findViewById(R.id.dateRangeTextView);
            _genderTextView = (TextView) rootView.findViewById(R.id.genderTextView);
            _countriesTextView = (TextView) rootView.findViewById(R.id.countriesTextView);
            _colorsViewGroup = (LinearLayout) rootView.findViewById(R.id.colorsViewGroup);
            _resultCountTextView = (TextView) rootView.findViewById(R.id.filterResultCount);
        }

        private void bind(Filter filter) {
            Context ctxt = _dateRangeTextView.getContext();
            _dateRangeTextView.setText(String.format(Locale.getDefault(),"%d - %d",
                    filter.startYear, filter.endYear));
            String gender = filter.gender;
            _genderTextView.setText(
                    (gender.length() == 0) ? ctxt.getString(R.string.all) :
                            gender.substring(0, 1).toUpperCase() +  gender.substring(1));
            for (int i = 0; i < filter.countries.size(); i++) {
                if (i > 0) _countriesTextView.append("  ");
                _countriesTextView.append(filter.countries.get(i));
            }
            if (filter.countries.size() == 0) _countriesTextView.setText(R.string.all);
            bindColors(filter.colors);

            Observable<List<CarOwner>> ownersObservable = FileUtils.getCarOwners(ctxt);
            Single<Integer> selectedCarOwners = FilterUtils.getResultCount(filter, ownersObservable);
            _compositeDisposable.add(
                    selectedCarOwners
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    value -> {
                                        String pattern = "###,###";
                                        DecimalFormat formatter = new DecimalFormat(pattern);
                                        _resultCountTextView.setText(formatter.format(value));
                                    })
            );
        }

        private void bindColors(List<String> colors) {
            for (int i = 0; i < colors.size(); i++) {
                addColorTextView(colors, i);
                addColorDrawable(colors, i);
            }

            if (colors.size() == 0) {
                List<String> all = new ArrayList<>();
                all.add(_colorsViewGroup.getContext().getString(R.string.all));
                addColorTextView(all, 0);
            }
        }

        private void addColorTextView(List<String> colors, int i) {
            Context ctxt = _colorsViewGroup.getContext();
            TextView colorTextView = new TextView(ctxt);
            colorTextView.setText(colors.get(i));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    8, ctxt.getResources().getDisplayMetrics());
            int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    2, ctxt.getResources().getDisplayMetrics());
            params.setMargins(0, 0, right, 0);
            if (i > 0) params.leftMargin = left;
            colorTextView.setLayoutParams(params);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                colorTextView.setTextAppearance(R.style.PropertyValueStyle);
            } else {
                colorTextView.setTextAppearance(ctxt, R.style.PropertyValueStyle);
            }

            Typeface typeface = ResourcesCompat.getFont(ctxt, R.font.barlow_regular);
            colorTextView.setTypeface(typeface);
            _colorsViewGroup.addView(colorTextView);
        }

        private void addColorDrawable(List<String> colors, int i) {
            Context ctxt = _colorsViewGroup.getContext();
            View colorDrawable = new View(ctxt);

            colorDrawable.setBackgroundDrawable(
                    ContextCompat.getDrawable(ctxt, R.drawable.color_drawable));
            ColorUtils.setBackgroundColor(ColorUtils.getColorResource(colors.get(i)),
                    colorDrawable);
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    8, ctxt.getResources().getDisplayMetrics());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    10, ctxt.getResources().getDisplayMetrics());
            int bottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    3, ctxt.getResources().getDisplayMetrics());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.gravity = Gravity.BOTTOM;
            params.setMargins(0, 0, 0, bottom);

            colorDrawable.setLayoutParams(params);
            _colorsViewGroup.addView(colorDrawable);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Filter filter = getItem(position);
            _onItemClickListener.onItemClick(filter);
        }
    }
}
