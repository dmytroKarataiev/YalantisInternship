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

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adkdevelopment.e_contact.provider.tasks.TasksColumns;
import com.adkdevelopment.e_contact.remote.RSSNewsItem;

import java.util.List;
import java.util.Vector;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by karataev on 4/8/16.
 */
public class TempFragment2 extends Fragment {

    @Bind(R.id.temp_title_text) TextView mTempTitle;

    // List of tasks
    private List<RSSNewsItem> mItemList;

    public TempFragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.temp_layout, container, false);

        App.getApiManager().getService().getData().enqueue(callback);

        return rootView;
    }

    /**
     * Custom callback to perform actions on data update
     */
    private Callback<List<RSSNewsItem>> callback = new Callback<List<RSSNewsItem>>() {
        @Override
        public void onResponse(Call<List<RSSNewsItem>> call, Response<List<RSSNewsItem>> response) {
            mItemList = response.body();
            Log.d("TempFragment2", "mItemList:" + mItemList.size());

            Vector<ContentValues> cVVTasks = new Vector<>(mItemList.size());

            for (RSSNewsItem each : mItemList) {

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

                cVVTasks.add(tasksItems);
            }

            int inserted = 0;
            ContentResolver resolver = getContext().getContentResolver();

            if (cVVTasks.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVTasks.size()];
                cVVTasks.toArray(cvArray);
                inserted = resolver.bulkInsert(TasksColumns.CONTENT_URI, cvArray);
            }
            Log.d("TempFragment2", "inserted:" + inserted);

            getContext().getContentResolver().notifyChange(TasksColumns.CONTENT_URI, null, false);
        }

        @Override
        public void onFailure(Call<List<RSSNewsItem>> call, Throwable t) {
            Log.d("TempFragment2", "error " + t.toString());
        }
    };
}
