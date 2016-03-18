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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adkdevelopment.yalantisinternship.adapter.ListAdapter;
import com.adkdevelopment.yalantisinternship.remote.RSSNewsItem;
import com.adkdevelopment.yalantisinternship.utils.Utilities;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment of the main activity, which populates a recycler view
 * with task items. Each item leads to the detailed view.
 */
public class MainFragment extends Fragment {

    @Bind(R.id.my_recycler_view) RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.list_empty_text) TextView mListEmpty;

    // List of tasks
    private List<RSSNewsItem> mItemList;

    // Global Variables
    private ListAdapter mAdapter;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment_list, container, false);

        // Bind views to variables
        ButterKnife.bind(this, rootView);

        // Fetch data on background task through ApiManager
        mListEmpty.setVisibility(View.INVISIBLE);
        refresh();

        // To improve the performance
        mRecyclerView.setHasFixedSize(true);

        // Refresh data on scroll down
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utilities.isOnline(getContext())) {
                    refresh();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        // RecyclerView with vertical scroll
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(mItemList, getActivity());

        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }



    /**
     * Fetch data from the Internet with Retrofit Client
     */
    private void refresh() {

        Locale locale = getResources().getConfiguration().locale;

        Callback<List<RSSNewsItem>> callback = new Callback<List<RSSNewsItem>>() {
            @Override
            public void onResponse(Call<List<RSSNewsItem>> call, Response<List<RSSNewsItem>> response) {
                mItemList = response.body();
                mAdapter = new ListAdapter(mItemList, getActivity());
                mRecyclerView.swapAdapter(mAdapter, false);
                mSwipeRefreshLayout.setRefreshing(false);

                if (mItemList == null) {
                    mListEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<RSSNewsItem>> call, Throwable t) {
                mListEmpty.setVisibility(View.VISIBLE);
            }
        };

        if (locale.toString().contains("UA")) {
            App.getApiManager().getService().getUaData().enqueue(callback);
        } else {
            App.getApiManager().getService().getData().enqueue(callback);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
