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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private List<CarOwner> mCarOwners;

    public ResultsAdapter() {
        mCarOwners = new ArrayList<CarOwner>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.result_list_item_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CarOwner owner = mCarOwners.get(position);
        holder.bind(owner);
    }

    @Override
    public int getItemCount() {
        if (mCarOwners == null) return 0;
        return mCarOwners.size();
    }

    public void addVehicleOwners(List<CarOwner> owners) {
        mCarOwners.addAll(owners);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView namesTextView;
        private TextView emailTextView;
        private TextView countryTextView;
        private TextView carColorYearTextView;
        private TextView genderTextView;
        private TextView jobTitleTextView;
        private TextView bioTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            namesTextView = (TextView) itemView.findViewById(R.id.namesValueTextView);
            emailTextView = (TextView) itemView.findViewById(R.id.emailValueTextView);
            countryTextView = (TextView) itemView.findViewById(R.id.countryValueTextView);
            carColorYearTextView = (TextView) itemView.findViewById(R.id.carColorYearValue);
            genderTextView = (TextView) itemView.findViewById(R.id.genderValueTextView);
            jobTitleTextView = (TextView) itemView.findViewById(R.id.jobTitleValue);
            bioTextView = (TextView) itemView.findViewById(R.id.bioValueTextView);
        }

        public void bind(CarOwner owner) {
            namesTextView.setText(String.format(Locale.getDefault(), "%s %s",
                    owner.getFirstName(), owner.getLastName()));
            emailTextView.setText(owner.getEmail());
            countryTextView.setText(owner.getCountry());
            carColorYearTextView.setText(String.format(Locale.getDefault(), "%s, %s, %s",
                    owner.getCarModel(), owner.getCarColor(), owner.getModelYear()));
            genderTextView.setText(owner.getGender());
            jobTitleTextView.setText(owner.getJobTitle());
            bioTextView.setText(owner.getBio());
        }
    }
}
