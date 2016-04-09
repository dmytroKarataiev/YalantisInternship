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

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adkdevelopment.e_contact.provider.tasks.TasksColumns;
import com.adkdevelopment.e_contact.utils.CursorRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by karataev on 4/8/16.
 */
public class TasksAdapter extends CursorRecyclerViewAdapter<TasksAdapter.ViewHolder> {

    private final Activity mActivity;

    // Provide a reference to the views for each data item
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @Bind(R.id.task_item_type_image) ImageView mTypeImage;
        @Bind(R.id.task_item_type_text) TextView mTypeText;
        @Bind(R.id.task_item_likes_image) ImageView mLikesImage;
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
    public void onBindViewHolder(TasksAdapter.ViewHolder viewHolder, Cursor cursor) {

        int status = cursor.getInt(cursor.getColumnIndex(TasksColumns.STATUS));
        int likes = cursor.getInt(cursor.getColumnIndex(TasksColumns.LIKES));
        String address = cursor.getString(cursor.getColumnIndex(TasksColumns.ADDRESS));
        final long registered = cursor.getLong(cursor.getColumnIndex(TasksColumns.DATE_REGISTERED));

        // TODO: 4/8/16 add helper methods
        viewHolder.mTypeText.setText("Status " + status);
        viewHolder.mLikesText.setText("Likes " + likes);
        viewHolder.mAddress.setText(address);
        viewHolder.mRegistered.setText("Reg: " + registered);
        viewHolder.mElapsed.setText("Reg: " + registered);

        viewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "registered:" + registered, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);

        return new ViewHolder(v);
    }

    public TasksAdapter(Activity activity, Cursor cursor) {
        super(activity, cursor);
        // to support SharedTransitions
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
