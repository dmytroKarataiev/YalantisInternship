/*
 * MIT License
 *
 * Copyright (c) 2016. Dmytro Karataiev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.adkdevelopment.e_contact.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.text.format.DateUtils;

import com.adkdevelopment.e_contact.R;

import java.util.Date;

/**
 * Helper class with additional static funtions
 * Created by karataev on 4/9/16.
 */
public class Utilities {

    /**
     * Method to check if device is connected to the internet
     * @param context from which call is being made
     * @return true if connected, false otherwise
     */
    public static boolean isOnline(Context context) {
        if (context != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    /**
     * Returns formatted relative data
     * @param millis date to format in milliseconds
     * @return String with relative date (ex: 7 days ago)
     */
    public static String getRelativeDate(Long millis) {
        Date date = new Date(millis);
        return DateUtils.getRelativeTimeSpanString(date.getTime()).toString();
    }

    /**
     * Sets sorting preferences in SharedPreferences
     * @param context from which call is being made
     * @param sort preference according to the database schema
     */
    public static void setSortingPreference(Context context, int sort) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(context.getString(R.string.sharedprefs_key_sort), sort);
        editor.apply();
    }

    /**
     * Returns sorting preference
     * @param context from which call is being made
     * @return int preference according to the database schema
     */
    public static int getSortingPreference(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(context.getString(R.string.sharedprefs_key_sort), 0);
    }
}
