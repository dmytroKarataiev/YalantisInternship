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

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adkdevelopment.yalantisinternship.remote.RSSNewsItem;
import com.adkdevelopment.yalantisinternship.remote.RemoteEndpointUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItemListActivityFragment extends Fragment {

    private final String TAG = ItemListActivityFragment.class.getSimpleName();

    // List of tasks
    ArrayList<RSSNewsItem> itemsList;

    // Global Variables
    RecyclerView recyclerView;
    MyAdapter myAdapter;

    public ItemListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_list, container, false);

        FetchData fetchData = new FetchData();
        fetchData.execute();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new MyAdapter(itemsList, getContext());

        recyclerView.setAdapter(myAdapter);

        return rootView;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private ArrayList<RSSNewsItem> mDataset;
        private Context mContext;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView mImageView;
            public ViewHolder(View v) {
                super(v);
                mImageView = (TextView) v.findViewById(R.id.title);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<RSSNewsItem> itemsList, Context context) {
            mContext = context;
            mDataset = itemsList;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(RSSNewsItem.TASKITEM, mDataset.get(recyclerView.getChildAdapterPosition(v)));

                    // Shared Transitions for SDK >= 21
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        @SuppressWarnings("unchecked") Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle();
                        startActivity(intent, bundle);
                    } else {
                        startActivity(intent);
                    }
                }
            });

            return new ViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            RSSNewsItem rssnews = mDataset.get(position);
            String listItemTitle = rssnews.getTitle() + " - " + rssnews.getOwner() + " - " + rssnews.getStatus();
            holder.mImageView.setText(listItemTitle);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {

            if (mDataset != null) {
                return mDataset.size();
            } else {
                return 0;
            }
        }
    }

    private class FetchData extends AsyncTask<Void, Void, ArrayList<RSSNewsItem>> {

        @Override
        protected ArrayList<RSSNewsItem> doInBackground(Void... params) {

            try {
                BlockingQueue<List<RSSNewsItem>> blockingQueue = RemoteEndpointUtil.fetchItems();
                itemsList = (ArrayList<RSSNewsItem>) blockingQueue.take();
            } catch (InterruptedException e) {
                Log.e(TAG, "Error downloading content.", e);
            }
            return itemsList;
        }

        @Override
        protected void onPostExecute(ArrayList<RSSNewsItem> rssList) {
            myAdapter = new MyAdapter(itemsList, getContext());
            recyclerView.swapAdapter(myAdapter, false);
        }
    }
}
