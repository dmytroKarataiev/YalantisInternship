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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.util.Log;

import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.data.local.PhotoRealm;
import com.adkdevelopment.e_contact.data.local.TaskRealm;
import com.adkdevelopment.e_contact.data.model.TaskAddress;
import com.adkdevelopment.e_contact.data.model.TaskFile;
import com.adkdevelopment.e_contact.data.model.TaskObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.RealmList;

/**
 * Helper class with additional static functions
 * Created by karataev on 4/9/16.
 */
public class Utilities {

    private static final String TAG = Utilities.class.getSimpleName();

    /**
     * Returns formatted relative data
     * @param millis date to format in milliseconds
     * @return String with relative date (ex: 7 days ago)
     */
    public static String getRelativeDate(Long millis) {
        Date date = new Date(millis * 1000);
        return DateUtils.getRelativeTimeSpanString(date.getTime()).toString();
    }

    /**
     * Returns formatted date in a String
     * @param unformattedDate in millis
     * @return String formatted in "MMM d, yyyy", Locale aware
     */
    public static String getFormattedDate(long unformattedDate) {

        Date date = new Date(unformattedDate * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());

        return simpleDateFormat.format(date);
    }

    public static TaskRealm convertTask(TaskObject taskObject) {

        TaskRealm objectRealm = new TaskRealm();

        objectRealm.setId(taskObject.getId());
        objectRealm.setTitle(taskObject.getTitle());
        objectRealm.setType(taskObject.getType().getId());
        objectRealm.setTypeText(taskObject.getType().getName());
        objectRealm.setStatus(taskObject.getState().getId());
        objectRealm.setStatusText(taskObject.getState().getName());
        objectRealm.setCreated(taskObject.getCreatedDate());
        objectRealm.setRegistered(taskObject.getCreatedDate());
        objectRealm.setAssigned(taskObject.getStartDate());
        objectRealm.setCategory(taskObject.getCategory().getId());
        objectRealm.setCategoryText(taskObject.getCategory().getName());

        if (taskObject.getPerformers() != null && taskObject.getPerformers().size() > 0) {
            objectRealm.setResponsible(taskObject.getPerformers().get(0).getOrganization());
        }

        objectRealm.setDescription(taskObject.getBody());
        objectRealm.setLikes(taskObject.getLikesCounter());

        TaskAddress taskAddress = null;
        if (taskObject.getAddress() != null) {
            taskAddress = taskObject.getAddress();
        } else if (taskObject.getUser().getAddress() != null) {
            taskAddress = taskObject.getUser().getAddress();
        }

        if (taskAddress != null) {
            objectRealm.setAddress(taskAddress.getCity().getName() + ", "
                    + taskAddress.getStreet().getName() + ", "
                    + taskAddress.getHouse().getName() + ", "
                    + taskAddress.getFlat());
        }

        if (taskObject.getGeoAddress() != null) {
            try {
                objectRealm.setLatitude(Double.parseDouble(taskObject.getGeoAddress().getLatitude()));
                objectRealm.setLongitude(Double.parseDouble(taskObject.getGeoAddress().getLongitude()));
            } catch (NumberFormatException e) {
                Log.d(TAG, "e: " + e);
            }
        }

        if (taskObject.getFiles() != null && taskObject.getFiles().size() > 0) {
            RealmList<PhotoRealm> photoRealmList = new RealmList<>();
            for (TaskFile file : taskObject.getFiles()) {
                PhotoRealm photoRealm = new PhotoRealm();
                photoRealm.setImageUrl(file.getFilename());
                photoRealmList.add(photoRealm);
            }
            objectRealm.setPhoto(photoRealmList);
        }

        return objectRealm;

    }

    /**
     * Returns int of a color resource for corresponding status of a task
     * @param context from which call is made
     * @param status status according to the database schema
     * @return int of a corresponding color from resources
     */
    public static int getBackgroundColor(Context context, int status) {

        switch (status) {
            case TaskRealm.WHERE_MODERATION:
            case TaskRealm.WHERE_PROGRESS:
            case TaskRealm.WHERE_UNKNOWN_7:
            case TaskRealm.WHERE_UNKNOWN_8:
            case TaskRealm.WHERE_UNKNOWN_9:
                return ContextCompat.getColor(context, R.color.status_progress);
            case TaskRealm.WHERE_UNKNOWN_10:
            case TaskRealm.WHERE_DONE:
                return ContextCompat.getColor(context, R.color.status_completed);
            case TaskRealm.WHERE_STILL_MODERATION:
            case TaskRealm.WHERE_ACCEPTED:
            case TaskRealm.WHERE_REVIEW:
                return ContextCompat.getColor(context, R.color.status_waiting);
            default:
                return ContextCompat.getColor(context, R.color.colorAccent);
        }
    }

    /**
     * Helper class to get int of a Drawable of work types according to a database schema
     * @param type int status representation
     * @return int Drawable of the status
     */
    public static int getCategoryIcon(int type) {
        switch (type) {
            case TaskRealm.CATEGORY_NO_ANSWER:
            case TaskRealm.CATEGORY_OTHER:
            case TaskRealm.CATEGORY_IMPROVEMENTS:
            case TaskRealm.CATEGORY_HOTLINE:
                return R.drawable.ic_type_issues;
            case TaskRealm.CATEGORY_AGRICULTURE:
            case TaskRealm.CATEGORY_LAND:
                return R.drawable.ic_type_trash;
            case TaskRealm.CATEGORY_CORRUPTION:
            case TaskRealm.CATEGORY_GAMBLING:
            case TaskRealm.CATEGORY_VERKHOVNA_RADA:
            case TaskRealm.CATEGORY_GOV_LOCAL:
            case TaskRealm.CATEGORY_GOV_BUILDING:
            case TaskRealm.CATEGORY_GOV_INQUIRY:
            case TaskRealm.CATEGORY_EXECUTIVE:
            case TaskRealm.CATEGORY_ECONOMY:
            case TaskRealm.CATEGORY_LAW:
            case TaskRealm.CATEGORY_FINANCE:
            case TaskRealm.CATEGORY_CRIME:
                return R.drawable.ic_type_gov;
            case TaskRealm.CATEGORY_MEDIA:
            case TaskRealm.CATEGORY_MEDIA_POL:
            case TaskRealm.CATEGORY_TOURISM:
                return R.drawable.ic_type_tourism;
            case TaskRealm.CATEGORY_ARCHIVES:
            case TaskRealm.CATEGORY_LABOR:
            case TaskRealm.CATEGORY_DEPOSITS:
            case TaskRealm.CATEGORY_RESIDENTS:
            case TaskRealm.CATEGORY_WAGES:
            case TaskRealm.CATEGORY_COMPENSATION:
            case TaskRealm.CATEGORY_CONSUMERS:
                return R.drawable.ic_type_paint;
            default:
                return R.drawable.ic_type_other;
        }
    }

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

}
