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

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    @Bind(R.id.task_title_text) TextView tast_title_text;
    @Bind(R.id.task_status) TextView task_status;
    @Bind(R.id.task_created_date) TextView task_created_date;
    @Bind(R.id.task_registered_date) TextView task_registered_date;
    @Bind(R.id.task_assigned_date) TextView task_assigned_date;
    @Bind(R.id.task_description) TextView task_description;
    @Bind(R.id.task_responsible_name) TextView task_responsible_name;

    // Global variable to get links to the photos from the fragment
    RSSNewsItem rssNewsItem;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(RSSNewsItem.TASKITEM)) {
            rssNewsItem = intent.getParcelableExtra(RSSNewsItem.TASKITEM);

            // Time parsing and creating a nice textual version (should be changed to Calendar()
            Time time = new Time();
            time.parse3339(rssNewsItem.getCreated());
            String dateCreated = DateUtils
                    .getRelativeTimeSpanString(time.toMillis(false))
                    .toString();

            time.parse3339(rssNewsItem.getRegistered());
            String dateRegistered = DateUtils
                    .getRelativeTimeSpanString(time.toMillis(false))
                    .toString();

            time.parse3339(rssNewsItem.getAssigned());
            String dateAssigned = DateUtils
                    .getRelativeTimeSpanString(time.toMillis(false))
                    .toString();

            tast_title_text.setText(rssNewsItem.getOwner());
            task_status.setText(rssNewsItem.getStatus());
            task_created_date.setText(dateCreated);
            task_registered_date.setText(dateRegistered);
            task_assigned_date.setText(dateAssigned);
            task_responsible_name.setText(rssNewsItem.getResponsible());
            task_description.setText(Html.fromHtml(rssNewsItem.getDescription()));

        }

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        // Horizontal LayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Adapter with data about different activities
        MyAdapter myAdapter = new MyAdapter(rssNewsItem.getPhoto(), getContext());
        recyclerView.setAdapter(myAdapter);

        return rootView;
    }

    /**
     * Adapter to populate recyclerview with photos from JSON response
     */
    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> mDataset;
        private Context mContext;

        // Simple version of the vieholder with only one view
        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView mImageView;
            public ViewHolder(View v) {
                super(v);
                mImageView = (ImageView) v.findViewById(R.id.poster_image);
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
            Picasso.with(mContext).load(rssNewsItem.getPhoto().get(position)).error(R.drawable.m100).into(holder.mImageView);

            // Click on each image in the recyclerview shows a toast
            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Image: " + position, Toast.LENGTH_SHORT).show();
                }
            });

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
