/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016.  Dmytro Karataiev
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

package com.adkdevelopment.yalantisinternship;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adkdevelopment.yalantisinternship.remote.RSSNewsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    @Bind(R.id.task_title_text)
    private TextView task_title_text;
    @Bind(R.id.task_status)
    private TextView task_status;
    @Bind(R.id.task_created_date)
    private TextView task_created_date;
    @Bind(R.id.task_registered_date)
    private TextView task_registered_date;
    @Bind(R.id.task_assigned_date)
    private TextView task_assigned_date;
    @Bind(R.id.task_description)
    private TextView task_description;
    @Bind(R.id.task_responsible_name)
    private TextView task_responsible_name;

    // As per specification - each element (or button? or what?) should have an onClickListener
    // which shows a toast with element name
    // Listener set only for elements, which change on download
    @OnClick({ R.id.task_title_text,
            R.id.task_status,
            R.id.task_created_date,
            R.id.task_registered_date,
            R.id.task_assigned_date,
            R.id.task_description,
            R.id.task_responsible_name })
            public void showToast(View view) {

        Toast.makeText(getActivity(),
                ((TextView) view).getText(), Toast.LENGTH_SHORT)
                .show();
    }

    // Global variable to get links to the photos from the fragment
    private RSSNewsItem mNewsItem;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(RSSNewsItem.TASKITEM)) {
            mNewsItem = intent.getParcelableExtra(RSSNewsItem.TASKITEM);

            // Time parsing and creating a nice textual version (should be changed to Calendar)
            String dateCreated = getNiceDate(mNewsItem.getCreated());
            String dateRegistered = getNiceDate(mNewsItem.getRegistered());
            String dateAssigned = getNiceDate(mNewsItem.getAssigned());

            task_title_text.setText(mNewsItem.getOwner());
            task_status.setText(mNewsItem.getStatus());
            task_created_date.setText(dateCreated);
            task_registered_date.setText(dateRegistered);
            task_assigned_date.setText(dateAssigned);
            task_responsible_name.setText(mNewsItem.getResponsible());
            task_description.setText(Html.fromHtml(mNewsItem.getDescription()));

        } else {
            // If there is no outside intent - fetch example photos
            ArrayList<String> dummyPhotos = new ArrayList<>();

            String[] photos = {
                    "http://adkdevelopment.com/download/photos/DSC_2729.jpg",
                    "http://adkdevelopment.com/download/photos/DSC_2730.jpg",
                    "http://adkdevelopment.com/download/photos/DSC_2733.jpg" };

            dummyPhotos.addAll(Arrays.asList(photos));

            mNewsItem = new RSSNewsItem();
            mNewsItem.setPhoto(dummyPhotos);
        }

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        // Horizontal LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Adapter with data about different activities
        MyAdapter myAdapter = new MyAdapter(mNewsItem.getPhoto(), getContext());
        recyclerView.setAdapter(myAdapter);

        return rootView;
    }

    /**
     * Adapter to populate recyclerview with photos from JSON response
     */
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> mDataset;
        private Context mContext;

        // Simple version of the viewholder with only one view
        public class ViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.poster_image) public ImageView mImageView;

            public ViewHolder(View v) {
                super(v);
                ButterKnife.bind(this, v);
            }
        }

        // Context kind of unnecessary here, but in the future it will be easier to refactor
        public MyAdapter(List<String> myDataset, Context context) {
            mContext = context;
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_task, parent, false);

            return new ViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            // Download image from the internet, onError - use a drawable
            Picasso.with(mContext).load(mNewsItem.getPhoto().get(position)).error(R.drawable.m100).into(holder.mImageView);
            holder.mImageView.setContentDescription(getString(R.string.task_image_text));

            // Click on each image in the recyclerview shows a toast
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, getString(R.string.task_image_text) + " " + position, Toast.LENGTH_SHORT).show();
                }
            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    /**
     * Parses date in RFC 3339 format and returns nice textual version
     * @param unformattedDate date in RFC 3339 format.
     * @return textual version in format (Locale aware) Day / Month / Year
     */
    private String getNiceDate(String unformattedDate) {

        Time time = new Time();
        time.parse3339(unformattedDate);

        return DateUtils
                .getRelativeTimeSpanString(time.toMillis(false))
                .toString();
    }
}
