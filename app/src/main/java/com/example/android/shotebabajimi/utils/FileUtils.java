package com.example.android.shotebabajimi.utils;

import android.content.Context;
import android.util.Log;

import com.example.android.shotebabajimi.R;
import com.example.android.shotebabajimi.results.model.CarOwner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class FileUtils {

    private static final String TAG = "FileUtils";

    public static Observable<List<CarOwner>> getCarOwners(Context context) {
        return Observable.fromCallable(new Callable<List<CarOwner>>() {
                @Override
                public List<CarOwner> call() throws Exception {
                    return readFile(context);
                }})
                .subscribeOn(Schedulers.io());
    }

    private static List<CarOwner> readFile(Context context) {

        List<CarOwner> carOwners = new ArrayList<CarOwner>();

        InputStream inputStream = context.getResources().openRawResource(R.raw.ehealth);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, Charset.forName("UTF-8"))
        );


        try {
            String line = reader.readLine();
            int count = 1;
            while (line != null) {
                line = reader.readLine();
                count++;
                if (line == null) break;
                CarOwner owner = parse(line, count);
                carOwners.add(owner);
            }
        } catch (IOException e) {
            Log.wtf(TAG, "readFile: " + e.getMessage(), e);
        }

        return carOwners;
    }

    private static CarOwner parse(String line, int count) {

        Cursor cursor = next(0, line);
        String id = cursor.data;
        cursor = next(cursor.end + 1, line);
        String firstName = cursor.data;
        cursor = next(cursor.end + 1, line);
        String lastName = cursor.data;
        cursor = next(cursor.end + 1, line);
        String email = cursor.data;
        cursor = next(cursor.end + 1, line);
        String country = cursor.data;
        cursor = next(cursor.end + 1, line);
        String model = cursor.data;
        cursor = next(cursor.end + 1, line);
        int year = Integer.parseInt(cursor.data);
        cursor = next(cursor.end + 1, line);
        String color = cursor.data;
        cursor = next(cursor.end + 1, line);
        String gender = cursor.data;
        cursor = next(cursor.end + 1, line);
        String job = cursor.data;
        String bio = line.substring(cursor.end + 1);

        return new CarOwner(firstName, lastName, email, country, model, year, color, gender, job, bio);
    }

    private static class Cursor {

        private String data;
        private int start;
        private int end;

        public Cursor(String data, int start, int end) {
            this.data = data;
            this.start = start;
            this.end = end;
        }
    }

    private static Cursor next(int position, String line) {
        int end = position;

        boolean withinQuotes = false;
        if (line.charAt(position) == '"') withinQuotes = true;

        while (end < line.length()) {
            end++;
            char ch = line.charAt(end);
            if (withinQuotes && ch == ',') {
                char prev = line.charAt(end - 1);
                if (prev == '"') break;
            } else if (!withinQuotes && ch == ',') {
                break;
            }
        }


        String data = (end < line.length()) ? line.substring(position, end) : "";
        return new Cursor(data, position, end);
    }
}
