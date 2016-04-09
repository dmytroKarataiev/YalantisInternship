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

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adkdevelopment.e_contact.provider.tasks.TasksColumns;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by karataev on 4/8/16.
 */
public class TasksFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = TasksFragment.class.getSimpleName();
    private static final int CURSOR_LOADER_ID = 0;
    private Cursor mCursor;
    private TasksAdapter mTasksAdapter;
    private LinearLayoutManager mLayoutManager;

    @Bind(R.id.recyclerview) RecyclerView mRecyclerView;
    @Bind(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.list_empty_text) TextView mListEmpty;

    public TasksFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(CURSOR_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tasks_layout, container, false);

        ButterKnife.bind(this, rootView);

        mListEmpty.setVisibility(View.INVISIBLE);

        mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mTasksAdapter = new TasksAdapter(getActivity(), mCursor);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mTasksAdapter);

        // Prevent Swipe to refresh if recyclerview isn't in top position
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int visible = mLayoutManager.findFirstCompletelyVisibleItemPosition();

                if (mSwipeRefreshLayout != null) {
                    if (visible != 0) {
                        mSwipeRefreshLayout.setEnabled(false);
                    } else {
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                }
            }
        });

        /*
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (Utilities.isOnline(getContext())) {
                SyncAdapter.syncImmediately(getContext());
                mSwipeRefreshLayout.setRefreshing(false);
            } else {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        */

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                TasksColumns.CONTENT_URI,
                null,
                TasksColumns.STATUS + " >= ?",
                new String[]{ "1" },
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mCursor == null || mCursor.getCount() < 1) {
            mListEmpty.setVisibility(View.VISIBLE);
        } else {
            mListEmpty.setVisibility(View.INVISIBLE);
        }

        mCursor = data;
        mTasksAdapter.swapCursor(mCursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursor = null;
        mTasksAdapter.swapCursor(null);
    }
}
