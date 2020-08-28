package com.example.android.shotebabajimi.results;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.shotebabajimi.R;
import com.example.android.shotebabajimi.results.model.CarOwner;
import com.example.android.shotebabajimi.utils.ColorUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResultsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> _items;

    private static final int COUNT = 0;
    private static final int RESULTS = 1;

    private static final String TAG = "ResultsAdapter";

    public ResultsAdapter() {
        _items = new ArrayList<Object>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case COUNT:
                itemView = inflater.inflate(R.layout.result_count_item_layout, parent, false);
                holder = new CountViewHolder(itemView);
                break;
            case RESULTS:
                itemView = inflater.inflate(R.layout.result_list_item_layout, parent, false);
                holder = new ResultViewHolder(itemView);
                break;
            default:
                throw new IllegalArgumentException("View type: " + viewType + " not found");
        }
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case RESULTS:
                CarOwner owner = (CarOwner) _items.get(position);
                ((ResultViewHolder) holder).bind(owner);
                break;
            case COUNT:
                Integer count = (Integer) _items.get(position);
                ((CountViewHolder) holder).bind(count);
                break;
            default:
                throw new IllegalArgumentException("No such view type " + holder.getItemViewType());
        }

    }

    @Override
    public int getItemCount() {
        if (_items == null) return 0;
        return _items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (_items.get(position) instanceof Integer) {
            return COUNT;
        } else if (_items.get(position) instanceof CarOwner) {
            return RESULTS;
        }
        return super.getItemViewType(position);
    }

    public void add(List<Object> results) {
        _items.addAll(results);
        notifyDataSetChanged();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView namesTextView;
        private TextView emailTextView;
        private TextView countryTextView;
        private TextView modelTextView;
        private TextView colorTextView;
        private View colorDrawable;
        private TextView yearTextView;
        private TextView genderTextView;
        private TextView jobTitleTextView;
        private TextView bioTextView;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);

            namesTextView = (TextView) itemView.findViewById(R.id.namesTextView);
            emailTextView = (TextView) itemView.findViewById(R.id.emailTextView);
            countryTextView = (TextView) itemView.findViewById(R.id.countryTextView);
            modelTextView = (TextView) itemView.findViewById(R.id.modelTextView);
            colorTextView = (TextView) itemView.findViewById(R.id.colorTextView);
            colorDrawable = itemView.findViewById(R.id.colorDrawable);
            yearTextView = (TextView) itemView.findViewById(R.id.yearTextView);
            genderTextView = (TextView) itemView.findViewById(R.id.genderTextView);
            jobTitleTextView = (TextView) itemView.findViewById(R.id.jobTitleTextView);
            bioTextView = (TextView) itemView.findViewById(R.id.bioTextView);
        }

        public void bind(CarOwner owner) {
            namesTextView.setText(String.format(Locale.getDefault(), "%s %s",
                    owner.getFirstName(), owner.getLastName()));
            emailTextView.setText(owner.getEmail());
            countryTextView.setText(owner.getCountry());
            modelTextView.setText(owner.getCarModel());
            colorTextView.setText(owner.getCarColor());
            ColorUtils.setBackgroundColor(
                    ColorUtils.getColorResource(owner.getCarColor()), colorDrawable);
            yearTextView.setText(String.format(Locale.getDefault(),
                    "%d", owner.getModelYear()));
            genderTextView.setText(owner.getGender());
            jobTitleTextView.setText(owner.getJobTitle());
            bioTextView.setText(owner.getBio());
        }
    }

    public static class CountViewHolder extends RecyclerView.ViewHolder {

        private TextView resultsCountTextView;

        public CountViewHolder(@NonNull View itemView) {
            super(itemView);
            resultsCountTextView = itemView.findViewById(R.id.countTextView);
        }

        public void bind(Integer count) {
            Context ctxt = resultsCountTextView.getContext();
            DecimalFormat formatter = new DecimalFormat("###,###");
            String output = formatter.format(count);
            resultsCountTextView.setText(ctxt.getString(R.string.results_list_count, output));
        }
    }
}
