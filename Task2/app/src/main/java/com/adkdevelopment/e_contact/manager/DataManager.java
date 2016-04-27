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

package com.adkdevelopment.e_contact.manager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;

import com.adkdevelopment.e_contact.interfaces.Manager;
import com.adkdevelopment.e_contact.provider.photos.PhotosColumns;
import com.adkdevelopment.e_contact.provider.tasks.TasksColumns;
import com.adkdevelopment.e_contact.remote.TaskItem;

import java.util.List;

/**
 * Data manager performs data insertion
 * (later can be extended to retrieve and operate on data)
 * Created by karataev on 4/27/16.
 */
public class DataManager implements Manager {

    private ContentResolver mContentResolver;

    @Override
    public void init(Context context) {
        mContentResolver = context.getContentResolver();
    }

    /**
     * Stores data to the database
     * @param itemList items to save
     * @return true if successful, false otherwise
     */
    public boolean storeData(List<TaskItem> itemList) {

        if (itemList != null && itemList.size() > 0) {

            for (TaskItem each : itemList) {

                ContentValues tasksItems = new ContentValues();

                tasksItems.put(TasksColumns.ID_TASK, each.getId());
                tasksItems.put(TasksColumns.STATUS, each.getStatus());
                tasksItems.put(TasksColumns.TYPE, each.getType());
                tasksItems.put(TasksColumns.DESCRIPTION, each.getDescription());
                tasksItems.put(TasksColumns.ADDRESS, each.getAddress());
                tasksItems.put(TasksColumns.RESPONSIBLE, each.getResponsible());
                tasksItems.put(TasksColumns.DATE_CREATED, each.getCreated());
                tasksItems.put(TasksColumns.DATE_REGISTERED, each.getRegistered());
                tasksItems.put(TasksColumns.DATE_ASSIGNED, each.getAssigned());
                tasksItems.put(TasksColumns.LONGITUDE, each.getLongitude());
                tasksItems.put(TasksColumns.LATITUDE, each.getLatitude());
                tasksItems.put(TasksColumns.LIKES, each.getLikes());
                tasksItems.put(TasksColumns.TITLE, each.getTitle());

                // retrieve id of just inserted row and put it in a table, where it is a foreign key for photos
                long id = ContentUris.parseId(mContentResolver.insert(TasksColumns.CONTENT_URI, tasksItems));

                for (String photo : each.getPhoto()) {
                    ContentValues photoValues = new ContentValues();
                    photoValues.put(PhotosColumns.TASK_ID, id);
                    photoValues.put(PhotosColumns.URL, photo);
                    mContentResolver.insert(PhotosColumns.CONTENT_URI, photoValues);
                }
            }
            mContentResolver.notifyChange(TasksColumns.CONTENT_URI, null, false);
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        mContentResolver = null;
    }
}
