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

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.util.Log;

import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.remote.TaskItem;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Helper class with additional static functions
 * Created by karataev on 4/9/16.
 */
public class Utilities {

    private static final String TAG = Utilities.class.getSimpleName();

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
        return sharedPreferences.getInt(context.getString(R.string.sharedprefs_key_sort), TaskItem.TYPE_ALL);
    }

    /**
     * Returns formatted date in a String
     * @param unformattedDate in millis
     * @return String formatted in "MMM d, yyyy", Locale aware
     */
    public static String getFormattedDate(long unformattedDate) {

        Date date = new Date(unformattedDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());

        return simpleDateFormat.format(date);
    }

    /**
     * Returns String with a name of current sorting preference
     * @param context from which call is made
     * @param position sort preference
     * @return String representation of a sort preference
     */
    public static String getActionbarTitle(Context context, int position) {

        switch (position) {
            case TaskItem.TYPE_ALL:
                return context.getString(R.string.menu_item_sort_all);
            case TaskItem.TYPE_PUBLIC:
                return context.getString(R.string.type_public);
            case TaskItem.TYPE_IMPROVEMENT:
                return context.getString(R.string.type_improvement);
            case TaskItem.TYPE_ARCHITECTURE:
                return context.getString(R.string.type_architecture);
            default:
                return context.getString(R.string.app_name);
        }
    }

    /**
     * Helper class to get String representation of work types according to a database schema
     * @param context from which call is made
     * @param type int status representation
     * @return String representation of the status
     */
    public static String getType(Context context, int type) {
        switch (type) {
            case TaskItem.TYPE_ALL:
                return context.getString(R.string.type_other);
            case TaskItem.TYPE_PUBLIC:
                return context.getString(R.string.type_public);
            case TaskItem.TYPE_IMPROVEMENT:
                return context.getString(R.string.type_improvement);
            case TaskItem.TYPE_ARCHITECTURE:
                return context.getString(R.string.type_architecture);
            default:
                return context.getString(R.string.type_unknown);
        }
    }

    /**
     * Helper class to get int of a Drawable of work types according to a database schema
     * @param type int status representation
     * @return int Drawable of the status
     */
    public static int getTypeIcon(int type) {
        switch (type) {
            case TaskItem.TYPE_ALL:
                return R.drawable.ic_type_other;
            case TaskItem.TYPE_PUBLIC:
                return R.drawable.ic_type_trash;
            case TaskItem.TYPE_IMPROVEMENT:
                return R.drawable.ic_type_issues;
            case TaskItem.TYPE_ARCHITECTURE:
                return R.drawable.ic_type_paint;
            default:
                return R.drawable.ic_type_other;
        }
    }

    /**
     * Helper class to get String representation of statuses according to a database schema
     * @param context from which call is made
     * @param status int status representation
     * @return String representation of the status
     */
    public static String getStatus(Context context, int status) {
        switch (status) {
            case TaskItem.STATUS_PROGRESS:
                return context.getString(R.string.status_progress);
            case TaskItem.STATUS_COMPLETED:
                return context.getString(R.string.status_completed);
            case TaskItem.STATUS_WAITING:
                return context.getString(R.string.status_waiting);
            default:
                return context.getString(R.string.status_unknown);
        }
    }

    /**
     * Returns true if Google Play Services available on the phone,
     * otherwise tries to ask user to install it
     * @param activity from which call is made
     * @return true if present, false otherwise
     */
    public static boolean checkPlayServices(Activity activity) {
        int result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity);
        if (result != ConnectionResult.SUCCESS) {
            if (GoogleApiAvailability.getInstance().isUserResolvableError(result)) {
                GoogleApiAvailability.getInstance().getErrorDialog(activity, result, 0).show();
            } else {
                Log.e(TAG, "PlayServices not available");
            }
            return false;
        }
        return true;
    }

    /**
     * Returns int of a color resource for corresponding status of a task
     * @param context from which call is made
     * @param status status according to the database schema
     * @return int of a corresponding color from resources
     */
    public static int getBackgroundColor(Context context, int status) {

        switch (status) {
            case TaskItem.STATUS_PROGRESS:
                return ContextCompat.getColor(context, R.color.status_progress);
            case TaskItem.STATUS_COMPLETED:
                return ContextCompat.getColor(context, R.color.status_completed);
            case TaskItem.STATUS_WAITING:
                return ContextCompat.getColor(context, R.color.status_waiting);
            default:
                return ContextCompat.getColor(context, R.color.colorAccent);
        }

    }
}
