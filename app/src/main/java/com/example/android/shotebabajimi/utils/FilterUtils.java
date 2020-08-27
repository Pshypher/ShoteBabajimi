package com.example.android.shotebabajimi.utils;

import android.text.TextUtils;

import com.example.android.shotebabajimi.filter.model.Filter;
import com.example.android.shotebabajimi.results.model.CarOwner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;


public class FilterUtils {

    /**
     * Removes car owners whose properties do not match with the filter criteria
     * @param filter the applied filter
     * @param ownersObservable list of vehicle owners
     */
    public static Observable<CarOwner> applyFilter(Filter filter, Observable<List<CarOwner>> ownersObservable) {
        return ownersObservable
                .map(owners ->
                    Observable.fromIterable(owners))
                .flatMap(carOwnerObservable ->
                        carOwnerObservable.filter(
                                carOwner -> test(filter, carOwner)));
    }

    private static Boolean test(Filter filter, CarOwner carOwner) {
        return isWithinDuration(filter, carOwner) &&
                sameGender(filter, carOwner) &&
                oneOf(filter.countries, carOwner.getCountry()) &&
                oneOf(filter.colors, carOwner.getCarColor());
    }

    private static Boolean isWithinDuration(Filter filter, CarOwner carOwner) {
        return carOwner.getModelYear() >= filter.startYear &&
                carOwner.getModelYear() <= filter.endYear;
    }

    private static Boolean sameGender(Filter filter, CarOwner carOwner) {
        return TextUtils.isEmpty(filter.gender) ||
                carOwner.getGender().equalsIgnoreCase(filter.gender);
    }

    private static Boolean oneOf(List<String> collection, String value) {
        return collection.isEmpty() || collection.contains(value);
    }
}
