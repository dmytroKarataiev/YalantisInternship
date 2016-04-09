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

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adkdevelopment.e_contact.provider.tasks.TasksColumns;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by karataev on 4/9/16.
 */
public class ListviewAdapter extends CursorAdapter {

    @Bind(R.id.task_item_type_image) ImageView mTypeImage;
    @Bind(R.id.task_item_type_text) TextView mTypeText;
    @Bind(R.id.task_item_likes_image) ImageView mLikesImage;
    @Bind(R.id.task_item_likes_text) TextView mLikesText;
    @Bind(R.id.task_item_address) TextView mAddress;
    @Bind(R.id.task_item_registered) TextView mRegistered;
    @Bind(R.id.task_item_elapsed) TextView mElapsed;
    @Bind(R.id.task_item_card) CardView mCardView;

    public ListviewAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.task_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        ButterKnife.bind(this, view);

        int status = cursor.getInt(cursor.getColumnIndex(TasksColumns.STATUS));
        int likes = cursor.getInt(cursor.getColumnIndex(TasksColumns.LIKES));
        String address = cursor.getString(cursor.getColumnIndex(TasksColumns.ADDRESS));
        final long registered = cursor.getLong(cursor.getColumnIndex(TasksColumns.DATE_REGISTERED));

        // TODO: 4/8/16 add helper methods
        mTypeText.setText("Status " + status);
        mLikesText.setText("Likes " + likes);
        mAddress.setText(address);
        mRegistered.setText("Reg: " + registered);
        mElapsed.setText("Reg: " + registered);

        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "registered:" + registered, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
