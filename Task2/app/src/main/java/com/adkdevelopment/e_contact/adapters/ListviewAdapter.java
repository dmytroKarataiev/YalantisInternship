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
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adkdevelopment.e_contact.DetailActivity;
import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.TasksFragment;
import com.adkdevelopment.e_contact.remote.RSSNewsItem;
import com.adkdevelopment.e_contact.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Simple CursorAdapter which populates ListView from the database
 * Created by karataev on 4/9/16.
 */
public class ListviewAdapter extends CursorAdapter {

    @Bind(R.id.task_item_type_image) ImageView mTypeImage;
    @Bind(R.id.task_item_type_text) TextView mTypeText;
    @Bind(R.id.task_item_likes_text) TextView mLikesText;
    @Bind(R.id.task_item_address) TextView mAddress;
    @Bind(R.id.task_item_registered) TextView mRegistered;
    @Bind(R.id.task_item_elapsed) TextView mElapsed;
    @Bind(R.id.task_item_card) CardView mCardView;

    Activity mActivity;

    public ListviewAdapter(Activity activity, Cursor c, int flags) {
        super(activity, c, flags);
        mActivity = activity;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.task_item_grid, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {
        ButterKnife.bind(this, view);

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

        mTypeImage.setImageResource(Utilities.getTypeIcon(type));
        mTypeText.setText(Utilities.getType(context, type));
        mLikesText.setText(String.valueOf(likes));
        mAddress.setText(address);
        mRegistered.setText(Utilities.getFormattedDate(registered));
        mElapsed.setText(Utilities.getRelativeDate(registered));

        mCardView.setOnClickListener(new View.OnClickListener() {
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

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(RSSNewsItem.TASKITEM, rssNewsItem);

                // Check if a phone supports shared transitions
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //noinspection unchecked always true
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(
                            mActivity,
                            Pair.create(view.findViewById(R.id.task_item_type_text),
                                    view.findViewById(R.id.task_item_type_text).getTransitionName()),
                            Pair.create(view.findViewById(R.id.task_item_registered),
                                    view.findViewById(R.id.task_item_registered).getTransitionName()))
                            .toBundle();
                    mActivity.startActivity(intent, bundle);
                } else {
                    mActivity.startActivity(intent);
                }
            }
        });
    }
}
