package com.example.android.shotebabajimi.filter.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filter {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("start_year")
    @Expose
    public int startYear;
    @SerializedName("end_year")
    @Expose
    public int endYear;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("countries")
    @Expose
    public List<String> countries = null;
    @SerializedName("colors")
    @Expose
    public List<String> colors = null;
}