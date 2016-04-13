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

package com.adkdevelopment.e_contact.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adkdevelopment.e_contact.DetailActivity;
import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.TasksFragment;
import com.adkdevelopment.e_contact.remote.RSSNewsItem;
import com.adkdevelopment.e_contact.utils.CursorRecyclerViewAdapter;
import com.adkdevelopment.e_contact.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Improved RecyclerViewAdapter to be able to get data from the cursor
 * Created by karataev on 4/8/16.
 */
public class TasksAdapter extends CursorRecyclerViewAdapter<TasksAdapter.ViewHolder> {

    private final Activity mActivity;
    
    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.task_item_type_image) ImageView mTypeImage;
        @Bind(R.id.task_item_type_text) TextView mTypeText;
        @Bind(R.id.task_item_likes_text) TextView mLikesText;
        @Bind(R.id.task_item_address) TextView mAddress;
        @Bind(R.id.task_item_registered) TextView mRegistered;
        @Bind(R.id.task_item_elapsed) TextView mElapsed;
        @Bind(R.id.task_item_card) CardView mCardView;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public void onBindViewHolder(final TasksAdapter.ViewHolder viewHolder, Cursor cursor) {

        final int uniqueId = cursor.getInt(TasksFragment.COL_TASKS_ID);
        final String title = cursor.getString(TasksFragment.COL_TASKS_TITLE);
        final int type = cursor.getInt(TasksFragment.COL_TASKS_TYPE);
        final int status = cursor.getInt(TasksFragment.COL_TASKS_STATUS);
        final long created = cursor.getLong(TasksFragment.COL_TASKS_CREATED);
        final long registered = cursor.getLong(TasksFragment.COL_TASKS_REGISTERED);
        final long assigned = cursor.getLong(TasksFragment.COL_TASKS_ASSIGNED);
        final String responsible = cursor.getString(TasksFragment.COL_TASKS_RESPONSIBLE);
        final String description = cursor.getString(TasksFragment.COL_TASKS_DESCRIPTION);
        int likes = cursor.getInt(TasksFragment.COL_TASKS_LIKES);
        String address = cursor.getString(TasksFragment.COL_TASKS_ADDRESS);

        viewHolder.mTypeImage.setImageResource(Utilities.getTypeIcon(type));
        viewHolder.mTypeText.setText(Utilities.getType(mActivity, type));
        viewHolder.mLikesText.setText(String.valueOf(likes));
        viewHolder.mAddress.setText(address);
        viewHolder.mRegistered.setText(Utilities.getFormattedDate(registered));
        viewHolder.mElapsed.setText(Utilities.getRelativeDate(registered));

        viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RSSNewsItem rssNewsItem = new RSSNewsItem();
                rssNewsItem.setDatabaseId(uniqueId);
                rssNewsItem.setTitle(title);
                rssNewsItem.setType(type);
                rssNewsItem.setStatus(status);
                rssNewsItem.setCreated(created);
                rssNewsItem.setRegistered(registered);
                rssNewsItem.setAssigned(assigned);
                rssNewsItem.setResponsible(responsible);
                rssNewsItem.setDescription(description);

                Intent intent = new Intent(mActivity, DetailActivity.class);
                intent.putExtra(RSSNewsItem.TASKITEM, rssNewsItem);

                // Check if a phone supports shared transitions
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //noinspection unchecked always true
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(
                            mActivity,
                            Pair.create(viewHolder.itemView.findViewById(R.id.task_item_type_text),
                                    viewHolder.itemView.findViewById(R.id.task_item_type_text).getTransitionName()),
                            Pair.create(viewHolder.itemView.findViewById(R.id.task_item_registered),
                                    viewHolder.itemView.findViewById(R.id.task_item_registered).getTransitionName()))
                            .toBundle();
                    mActivity.startActivity(intent, bundle);
                } else {
                    mActivity.startActivity(intent);
                }
            }
        });

    }

    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                // you have a choice hear: efficient grid-based layout with some problems in log
                // or inefficient linear+relative layout without errors in log
                .inflate(R.layout.task_item_grid, parent, false);

        return new ViewHolder(v);
    }

    public TasksAdapter(Activity activity, Cursor cursor) {
        super(cursor);
        // to support SharedTransitions we need an activity
        mActivity = activity;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        if (getCursor() != null) {
            return getCursor().getCount();
        } else {
            return 0;
        }
    }
}