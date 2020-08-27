package com.example.android.shotebabajimi.results.model;

public class CarOwner {

    private long mId;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mCountry;
    private String mCarModel;
    private int mModelYear;
    private String mCarColor;
    private String mGender;
    private String mJobTitle;
    private String mBio;

    public CarOwner(String firstName, String lastName, String email,
                    String country, String carModel, int modelYear, String carColor,
                    String gender, String jobTitle, String bio) {
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mEmail = email;
        this.mCountry = country;
        this.mCarModel = carModel;
        this.mModelYear = modelYear;
        this.mCarColor = carColor;
        this.mGender = gender;
        this.mJobTitle = jobTitle;
        this.mBio = bio;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        this.mCountry = country;
    }

    public String getCarModel() {
        return mCarModel;
    }

    public void setCarModel(String mCarModel) {
        this.mCarModel = mCarModel;
    }

    public int getModelYear() {
        return mModelYear;
    }

    public void setModelYear(int year) {
        this.mModelYear = year;
    }

    public String getCarColor() {
        return mCarColor;
    }

    public void setCarColor(String color) {
        this.mCarColor = color;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        this.mGender = gender;
    }

    public String getJobTitle() {
        return mJobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.mJobTitle = jobTitle;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        this.mBio = bio;
    }

    @Override
    public String toString() {
        return "CarOwner{" +
                ", mLastName='" + mLastName + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mModelYear=" + mModelYear +
                ", mJobTitle='" + mJobTitle + '\'' +
                '}';
    }
}
