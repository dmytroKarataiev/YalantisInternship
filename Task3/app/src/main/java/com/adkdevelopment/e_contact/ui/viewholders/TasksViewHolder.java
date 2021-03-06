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

package com.adkdevelopment.e_contact.ui.viewholders;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.utils.Utilities;
import com.adkdevelopment.e_contact.data.local.TaskRealm;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Separate ViewHolder for Tasks
 * Created by karataev on 5/11/16.
 */
public class TasksViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.task_item_type_image)
    ImageView mTypeImage;
    @BindView(R.id.task_item_type_text)
    TextView mTypeText;
    @BindView(R.id.task_item_likes_text)
    TextView mLikesText;
    @BindView(R.id.task_item_address)
    TextView mAddress;
    @BindView(R.id.task_item_registered)
    TextView mRegistered;
    @BindView(R.id.task_item_elapsed)
    TextView mElapsed;
    @BindView(R.id.task_item_card)
    CardView mCardView;

    public TasksViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setData(TaskRealm item) {
        mTypeImage.setImageResource(Utilities.getCategoryIcon(item.getCategory()));
        mTypeText.setText(item.getCategoryText());
        mLikesText.setText(String.valueOf(item.getLikes()));

        if (item.getAddress() != null) {
            mAddress.setText(item.getAddress());
        }
        mRegistered.setText(Utilities.getFormattedDate(item.getCreated()));
        mElapsed.setText(Utilities.getRelativeDate(item.getCreated()));
    }
}
