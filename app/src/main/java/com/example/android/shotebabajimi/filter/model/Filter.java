package com.example.android.shotebabajimi.filter.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filter implements Parcelable {

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

    public Filter(int id, int startYear, int endYear, String gender, List<String> countries,
                  List<String> colors) {
        this.id = id;
        this.startYear = startYear;
        this.endYear = endYear;
        this.gender = gender;
        this.countries = countries;
        this.colors = colors;
    }

    private Filter(Parcel in) {
        id = in.readInt();
        startYear = in.readInt();
        endYear = in.readInt();
        gender = in.readString();
        countries = in.createStringArrayList();
        colors = in.createStringArrayList();
    }

    public static final Creator<Filter> CREATOR = new Creator<Filter>() {
        @Override
        public Filter createFromParcel(Parcel in) {
            return new Filter(in);
        }

        @Override
        public Filter[] newArray(int size) {
            return new Filter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(startYear);
        dest.writeInt(endYear);
        dest.writeString(gender);
        dest.writeStringList(countries);
        dest.writeStringList(colors);
    }
}