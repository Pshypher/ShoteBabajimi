package com.example.android.shotebabajimi.filter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.shotebabajimi.R;
import com.example.android.shotebabajimi.filter.model.Filter;

import java.util.Locale;


public class FilterAdapter extends PagedListAdapter<Filter, FilterAdapter.ViewHolder> {


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

    protected FilterAdapter(@NonNull DiffUtil.ItemCallback<Filter> diffCallback) {
        super(diffCallback);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView dateRangeTextView;
        private TextView genderTextView;
        private TextView countriesTextView;
        private TextView colorsTextView;

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
            dateRangeTextView = (TextView) rootView.findViewById(R.id.dateRangeTextView);
            genderTextView = (TextView) rootView.findViewById(R.id.genderTextView);
            countriesTextView = (TextView) rootView.findViewById(R.id.countriesTextView);
            colorsTextView = (TextView) rootView.findViewById(R.id.colorsValueTextView);
        }

        private void bind(Filter filter) {
            dateRangeTextView.setText(String.format(Locale.getDefault(),"%d - %d",
                    filter.startYear, filter.endYear));
            genderTextView.setText(filter.gender);
            String countries = filter.countries.toString();
            countriesTextView.setText(countries.substring(1, countries.length() - 1));
            String colors = filter.colors.toString();
            colorsTextView.setText(colors.substring(1, colors.length() - 1));
        }
    }
}
