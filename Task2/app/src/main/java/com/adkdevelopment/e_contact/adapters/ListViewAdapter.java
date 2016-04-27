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

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.TasksFragment;
import com.adkdevelopment.e_contact.interfaces.OnAdapterClick;
import com.adkdevelopment.e_contact.remote.TaskItem;
import com.adkdevelopment.e_contact.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Simple CursorAdapter which populates ListView from the database
 * Created by karataev on 4/9/16.
 */
public class ListViewAdapter extends CursorAdapter {

    /**
     * Cache of the children views for a list item.
     */
    public static class ViewHolder {
        @Bind(R.id.task_item_type_image) ImageView mTypeImage;
        @Bind(R.id.task_item_type_text) TextView mTypeText;
        @Bind(R.id.task_item_likes_text) TextView mLikesText;
        @Bind(R.id.task_item_address) TextView mAddress;
        @Bind(R.id.task_item_registered) TextView mRegistered;
        @Bind(R.id.task_item_elapsed) TextView mElapsed;
        @Bind(R.id.task_item_card) CardView mCardView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private OnAdapterClick mOnAdapterClick;

    public ListViewAdapter(Context context, Cursor c, OnAdapterClick listener) {
        // we set zero as a flag, because we use Loaders in the fragment
        super(context, c, 0);
        mOnAdapterClick = listener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.task_item_grid, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        final ViewHolder viewHolder = (ViewHolder) view.getTag();

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
        viewHolder.mTypeText.setText(Utilities.getType(context, type));
        viewHolder.mLikesText.setText(String.valueOf(likes));
        viewHolder.mAddress.setText(address);
        viewHolder.mRegistered.setText(Utilities.getFormattedDate(registered));
        viewHolder.mElapsed.setText(Utilities.getRelativeDate(registered));

        viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskItem taskItem = new TaskItem();

                taskItem.setDatabaseId(uniqueId);
                taskItem.setTitle(title);
                taskItem.setType(type);
                taskItem.setStatus(status);
                taskItem.setCreated(created);
                taskItem.setRegistered(registered);
                taskItem.setAssigned(assigned);
                taskItem.setResponsible(responsible);
                taskItem.setDescription(description);

                // Check if a phone supports shared transitions
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Pair<View, String> pair = Pair.create((View) viewHolder.mCardView,
                            viewHolder.mCardView.getTransitionName());
                    mOnAdapterClick.onTaskClickTransition(taskItem, pair);
                } else {
                    mOnAdapterClick.onTaskClick(taskItem);
                }

            }
        });
    }
}
