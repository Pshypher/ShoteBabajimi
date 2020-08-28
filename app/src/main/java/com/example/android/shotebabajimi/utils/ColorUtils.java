package com.example.android.shotebabajimi.utils;

import android.os.Build;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.example.android.shotebabajimi.R;

public class ColorUtils {

    @ColorRes
    public static int getColorResource(String color) {

        int colorResource;
        switch(color) {
            case "Maroon": colorResource = R.color.maroon; break;
            case "Puce": colorResource = R.color.puce; break;
            case "Violet": colorResource = R.color.violet; break;
            case "Blue": colorResource = R.color.blue; break;
            case "Orange": colorResource = R.color.orange; break;
            case "Fuscia": colorResource = R.color.fuschia; break;
            case "Red": colorResource = R.color.red; break;
            case "Mauv":  colorResource = R.color.mauv; break;
            case "Aquamarine": colorResource = R.color.aqua_marine; break;
            case "Pink": colorResource = R.color.pink; break;
            case "Khaki": colorResource = R.color.khaki; break;
            case "Yellow": colorResource = R.color.yellow; break;
            case "Purple": colorResource = R.color.purple; break;
            case "Goldenrod": colorResource = R.color.golden_rod; break;
            case "Teal": colorResource = R.color.teal; break;
            case "Crimson": colorResource = R.color.crimson; break;
            case "Indigo": colorResource = R.color.indigo; break;
            case "Turquoise": colorResource = R.color.turquoise; break;
            case "Green": colorResource = R.color.green; break;
            default: throw new IllegalArgumentException("Color " + color + " not found");
        }

        return colorResource;
    }

    public static void setBackgroundColor(@ColorRes int color, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackgroundTintList(
                    ContextCompat.getColorStateList(view.getContext(), color));
        } else {
            view.setBackgroundColor(
                    ContextCompat.getColor(view.getContext(), android.R.color.transparent));
        }
    }
}
