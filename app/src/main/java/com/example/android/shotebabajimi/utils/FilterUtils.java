package com.example.android.shotebabajimi.utils;

import com.example.android.shotebabajimi.filter.model.Filter;
import com.example.android.shotebabajimi.results.model.CarOwner;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;


public class FilterUtils {

    /**
     * Yields an RxObservable of vehicle owners matching the applied filter
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

    /**
     * Returns the number of vehicle owners matching the applied filter
     * @param filter the applied filter
     * @param ownersObservable list of vehicle owners
     */
    public static Single<Integer> getResultCount(Filter filter, Observable<List<CarOwner>> ownersObservable) {
        return ownersObservable
                .map(owners ->
                        Observable.fromIterable(owners))
                .flatMap(carOwnerObservable ->
                        carOwnerObservable.filter(
                                carOwner -> test(filter, carOwner)))
                .toList()
                .map(owners -> owners.size());
    }

    public static Boolean test(Filter filter, CarOwner carOwner) {
        return isWithinDuration(filter.startYear, filter.endYear, carOwner.getModelYear()) &&
                sameGender(filter.gender, carOwner.getGender()) &&
                oneOf(filter.countries, carOwner.getCountry()) &&
                oneOf(filter.colors, carOwner.getCarColor());
    }

    public static Boolean isWithinDuration(int startYear, int endYear, int year) {
        return year >= startYear && year <= endYear;
    }

    public static Boolean sameGender(String filterGender, String ownerGender) {
        return filterGender.equals("") || ownerGender.equalsIgnoreCase(filterGender);
    }

    public static Boolean oneOf(List<String> collection, String value) {
        return  collection.size() == 0 || collection.contains(value);
    }
}
