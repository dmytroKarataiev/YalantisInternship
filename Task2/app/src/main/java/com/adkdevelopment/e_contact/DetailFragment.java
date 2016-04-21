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
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adkdevelopment.e_contact.adapters.PhotoAdapter;
import com.adkdevelopment.e_contact.provider.photos.PhotosColumns;
import com.adkdevelopment.e_contact.remote.TaskItem;
import com.adkdevelopment.e_contact.utils.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment implements PhotoAdapter.OnImageClick{

    @Bind(R.id.task_title_text) TextView task_title_text;
    @Bind(R.id.task_status) TextView task_status;
    @Bind(R.id.task_created_date) TextView task_created_date;
    @Bind(R.id.task_registered_date) TextView task_registered_date;
    @Bind(R.id.task_assigned_date) TextView task_assigned_date;
    @Bind(R.id.task_description) TextView task_description;
    @Bind(R.id.task_responsible_name) TextView task_responsible_name;
    @Bind(R.id.my_recycler_view) RecyclerView recyclerView;
    @Bind(R.id.task_created_text) TextView task_created_text;
    @Bind(R.id.task_registered_text) TextView task_registered_text;
    @Bind(R.id.task_assigned_text) TextView task_assigned_text;
    @Bind(R.id.task_responsible_text) TextView task_responsible_text;

    // As per specification - each element (or button? or what?) should have an onClickListener
    // which shows a toast with element name
    @OnClick({ R.id.task_title_text,
            R.id.task_status,
            R.id.task_created_date,
            R.id.task_registered_date,
            R.id.task_assigned_date,
            R.id.task_description,
            R.id.task_responsible_name,
            R.id.task_created_text,
            R.id.task_registered_text,
            R.id.task_assigned_text,
            R.id.task_responsible_text
    })

    // Shows a Toast on each element click
    public void showToast(View view) {
        Toast.makeText(getContext(),
                ((TextView) view).getText(), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_fragment_task, container, false);
        ButterKnife.bind(this, rootView);

        // to prevent multiple calls to getContext()
        Context context = getContext();

        Intent intent = getActivity().getIntent();
        TaskItem mNewsItem;

        if (intent.hasExtra(TaskItem.TASKITEM)) {

            mNewsItem = intent.getParcelableExtra(TaskItem.TASKITEM);

            // Time parsing and creating a nice textual version (should be changed to Calendar)
            String dateCreated = Utilities.getFormattedDate(mNewsItem.getCreated());
            String dateRegistered = Utilities.getFormattedDate(mNewsItem.getRegistered());
            String dateAssigned = Utilities.getFormattedDate(mNewsItem.getAssigned());

            task_title_text.setText(Utilities.getType(context, mNewsItem.getType()));
            task_status.setText(Utilities.getStatus(context, mNewsItem.getStatus()));

            // sets color of a status TextView shape according to the schema
            GradientDrawable gradientDrawable = (GradientDrawable) task_status.getBackground();
            gradientDrawable.setColor(Utilities.getBackgroundColor(context, mNewsItem.getStatus()));

            task_created_date.setText(dateCreated);
            task_registered_date.setText(dateRegistered);
            task_assigned_date.setText(dateAssigned);
            task_responsible_name.setText(mNewsItem.getResponsible());
            task_description.setText(Html.fromHtml(mNewsItem.getDescription()));

            Cursor cursor = context.getContentResolver()
                    .query(PhotosColumns.CONTENT_URI,
                            null,
                            PhotosColumns.TASK_ID + " LIKE ?",
                            new String[] { "" + mNewsItem.getDatabaseId()},
                            null);

            if (cursor != null) {
                List<String> photos = new ArrayList<>(cursor.getCount());

                while (cursor.moveToNext()) {
                    photos.add(cursor.getString(cursor.getColumnIndex(PhotosColumns.URL)));
                }
                cursor.close();
                mNewsItem.setPhoto(photos);
            }

        } else {
            // If there is no outside intent - fetch example photos
            List<String> dummyPhotos = new ArrayList<>();
            dummyPhotos.addAll(Arrays.asList(getResources()
                    .getStringArray(R.array.task_image_links)));

            mNewsItem = new TaskItem();
            mNewsItem.setPhoto(dummyPhotos);
        }

        // To boost performance as we know that size won't change
        recyclerView.setHasFixedSize(true);

        // Horizontal LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                context,
                RecyclerView.HORIZONTAL,
                false
        );

        recyclerView.setLayoutManager(layoutManager);

        // Adapter with data about different activities
        PhotoAdapter photoAdapter = new PhotoAdapter(mNewsItem.getPhoto(), context, this);
        recyclerView.setAdapter(photoAdapter);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onImageItemClick(View view, int position) {
        Toast.makeText(getContext(),
                view.getContentDescription(),
                Toast.LENGTH_SHORT).show();
    }
}
