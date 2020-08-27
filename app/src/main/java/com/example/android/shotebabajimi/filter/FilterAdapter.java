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

    private OnItemClickListener mOnItemClickListener;

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
                            OnItemClickListener listener) {
        super(diffCallback);
        mOnItemClickListener = listener;
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

        private TextView dateRangeTextView;
        private TextView genderTextView;
        private TextView countriesTextView;
        private TextView colorsTextView;

        public ViewHolder(@NonNull View rootView) {
            super(rootView);
            rootView.setOnClickListener(this);
            dateRangeTextView = (TextView) rootView.findViewById(R.id.dateRangeTextView);
            genderTextView = (TextView) rootView.findViewById(R.id.genderTextView);
            countriesTextView = (TextView) rootView.findViewById(R.id.countriesTextView);
            colorsTextView = (TextView) rootView.findViewById(R.id.colorsValueTextView);
        }

        private void bind(Filter filter) {
            dateRangeTextView.setText(String.format(Locale.getDefault(),"%d - %d",
                    filter.startYear, filter.endYear));
            genderTextView.setText((filter.gender.equals("")) ? "ALL" : filter.gender);
            String countries = filter.countries.toString();
            countries = countries.substring(1, countries.length() - 1);
            countries = (countries.length() > 0) ? countries : "ALL";
            countriesTextView.setText(countries);
            String colors = filter.colors.toString();
            colors = colors.substring(1, colors.length() - 1);
            colors = (colors.length() > 0) ? colors : "ALL";
            colorsTextView.setText(colors);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Filter filter = getItem(position);
            mOnItemClickListener.onItemClick(filter);
        }
    }
}
