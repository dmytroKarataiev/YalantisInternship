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

package com.adkdevelopment.e_contact;

import android.text.format.DateUtils;

import com.adkdevelopment.e_contact.data.local.TaskObjectRealm;
import com.adkdevelopment.e_contact.data.local.TaskPhotoRealm;
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

    public static TaskObjectRealm convertTask(TaskObject taskObject) {

        TaskObjectRealm objectRealm = new TaskObjectRealm();

        objectRealm.setId(taskObject.getId());
        objectRealm.setTitle(taskObject.getTitle());
        objectRealm.setType(taskObject.getType().getId());
        objectRealm.setTypeText(taskObject.getType().getName());
        objectRealm.setStatus(taskObject.getState().getId());
        objectRealm.setStatusText(taskObject.getState().getName());
        objectRealm.setCreated(taskObject.getCreatedDate());
        objectRealm.setRegistered(taskObject.getCreatedDate());
        objectRealm.setAssigned(taskObject.getStartDate());

        if (taskObject.getPerformers() != null && taskObject.getPerformers().get(0) != null) {
            objectRealm.setResponsible(taskObject.getPerformers().get(0).getOrganization());
        }

        objectRealm.setDescription(taskObject.getBody());
        objectRealm.setLikes(taskObject.getLikesCounter());

        if (taskObject.getAddress() != null) {
            TaskAddress taskAddress = taskObject.getAddress();
            objectRealm.setAddress(taskAddress.getCity().getName() + ", "
                    + taskAddress.getStreet().getName() + ", "
                    + taskAddress.getHouse().getName() + ", "
                    + taskAddress.getFlat());
        }

        if (taskObject.getGeoAddress() != null) {
            objectRealm.setLatitude(Double.parseDouble(taskObject.getGeoAddress().getLatitude()));
            objectRealm.setLongitude(Double.parseDouble(taskObject.getGeoAddress().getLongitude()));
        }

        if (taskObject.getFiles() != null && taskObject.getFiles().size() > 0) {
            RealmList<TaskPhotoRealm> taskPhotoRealmList = new RealmList<>();
            for (TaskFile file : taskObject.getFiles()) {
                TaskPhotoRealm taskPhotoRealm = new TaskPhotoRealm();
                taskPhotoRealm.setImageUrl(file.getFilename());
                taskPhotoRealmList.add(taskPhotoRealm);
            }
            objectRealm.setPhoto(taskPhotoRealmList);
        }

        return objectRealm;

    }

}
