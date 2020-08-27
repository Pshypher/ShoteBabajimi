package com.example.android.shotebabajimi.utils;

import com.example.android.shotebabajimi.filter.model.Filter;
import com.example.android.shotebabajimi.results.model.CarOwner;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FilterUtilsTest {

    private ArrayList<String> _countries;
    private ArrayList<String> _colors;

    private Filter _filter;
    private CarOwner _a;
    private CarOwner _b;


    @Before
    public void setUp() {
        _countries = new ArrayList<>();
        _colors = new ArrayList<>();
        for (String country : new String[]
                {"Brazil", "Ireland", "Egypt", "Poland", "Niger", "Greece", "Peru"}) {
            _countries.add(country);
        }
        for (String color : new String[]
                {"Green", "Violet", "Yellow", "Blue"}) {
            _colors.add(color);
        }
        _filter = new Filter(1, 1991, 2009, "male", _countries, _colors);
        _a = new CarOwner("Scot", "Hainning", "shainning0@so-net.ne.jp",
                "Brazil", "Lincoln", 1996, "Blue", "Male",
                "Staff Accountant III", "Lorem ipsum dolor sit amet");
        _b = new CarOwner("Vannie", "Fitzer", "vfitzer1@samsung.com",
                "France", "Chrysler", 2005, "Green",
                "Female", "VP Quality Control",
                "Nulla facilisi. Cras non velit nec nisi vulputate nonummy. Maecenas tincidunt lacus at velit.");
    }

    @Test
    public void accept() {
        assertTrue(FilterUtils.test(_filter, _a));
    }

    @Test
    public void reject() {
        assertFalse(FilterUtils.test(_filter, _b));
    }

    @Test
    public void isWithinDuration() {
        assertTrue(FilterUtils.isWithinDuration(1991, 2009, 2005));
    }

    @Test
    public void notWithinDuration() {
        assertFalse(FilterUtils.isWithinDuration(1991, 2009, 2011));
    }

    @Test
    public void sameGender() {
        assertTrue(FilterUtils.sameGender("male", "male"));
    }

    @Test
    public void noFilterGender() {
        assertTrue(FilterUtils.sameGender("", "male"));
    }

    @Test
    public void missingOwnerGender() {
        assertFalse(FilterUtils.sameGender("male", ""));
    }

    @Test
    public void differentGender() {
        assertFalse(FilterUtils.sameGender("male", "female"));
    }

    @Test
    public void memberOfCountries() {
        assertTrue(FilterUtils.oneOf(_countries, "Niger"));
    }

    @Test
    public void notMemberOfCountries() {
        assertFalse(FilterUtils.oneOf(_countries, "France"));
    }

    @Test
    public void memberOfColors() {
        assertTrue(FilterUtils.oneOf(_colors, "Blue"));
    }

    @Test
    public void notMemberOfColors() {
        assertFalse(FilterUtils.oneOf(_colors, "Maroon"));
    }
}