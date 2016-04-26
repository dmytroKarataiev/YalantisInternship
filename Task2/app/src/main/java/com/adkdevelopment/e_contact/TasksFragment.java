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

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.adkdevelopment.e_contact.adapters.ListviewAdapter;
import com.adkdevelopment.e_contact.adapters.PagerAdapter;
import com.adkdevelopment.e_contact.adapters.TasksAdapter;
import com.adkdevelopment.e_contact.interfaces.OnAdapterClick;
import com.adkdevelopment.e_contact.provider.tasks.TasksColumns;
import com.adkdevelopment.e_contact.remote.TaskItem;
import com.adkdevelopment.e_contact.utils.Utilities;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment with two adapters to show that I can handle both RecyclerView and ListView.
 * I didn't want to separate it in two different classes as they would've had too many repetitive code
 * Created by karataev on 4/8/16.
 */
public class TasksFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        SharedPreferences.OnSharedPreferenceChangeListener, OnAdapterClick {

    // indices tied to the Tasks Columns, if they change - these must change
    public static final int COL_TASKS_ID = 0;
    public static final int COL_TASKS_TITLE = 13;
    public static final int COL_TASKS_TYPE = 3;
    public static final int COL_TASKS_STATUS = 2;
    public static final int COL_TASKS_CREATED = 7;
    public static final int COL_TASKS_REGISTERED = 8;
    public static final int COL_TASKS_ASSIGNED = 9;
    public static final int COL_TASKS_RESPONSIBLE = 6;
    public static final int COL_TASKS_DESCRIPTION = 4;
    public static final int COL_TASKS_LIKES = 12;
    public static final int COL_TASKS_ADDRESS = 5;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int CURSOR_LOADER_ID = 0;

    private Cursor mCursor;
    private TasksAdapter mTasksAdapter;
    private ListviewAdapter mListviewAdapter;
    private LinearLayoutManager mLayoutManager;

    @Bind(R.id.recyclerview) RecyclerView mRecyclerView;
    @Bind(R.id.listview) ListView mListview;
    @Bind(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.list_empty_text) TextView mListEmpty;

    public TasksFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TasksFragment newInstance(int sectionNumber) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(CURSOR_LOADER_ID, this.getArguments(), this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tasks_layout, container, false);
        ButterKnife.bind(this, rootView);

        // to reuse this fragment we check the argument and inflate corresponding view with an adapter
        if (getArguments().getInt(ARG_SECTION_NUMBER) == PagerAdapter.LISTVIEW_FRAGMENT) {
            initListviewAdapter();
        } else {
            initRecyclerAdapter();
        }

        mListEmpty.setVisibility(View.INVISIBLE);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utilities.isOnline(getContext())) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    // call PagerActivity to refetch data
                    ((PagerActivity) getActivity()).fetchData();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        return rootView;
    }

    /**
     * Initializes Listview if pager is in corresponding view
     */
    private void initListviewAdapter() {
        mRecyclerView.setVisibility(View.GONE);

        mListviewAdapter = new ListviewAdapter(getActivity(), mCursor, 0, this);
        mListview.setAdapter(mListviewAdapter);

        // to allow FAB scrolling with the view
        ViewCompat.setNestedScrollingEnabled(mListview, true);

        mListview.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (mListview == null ||
                                mListview.getChildCount() == 0) ? 0 : mListview.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(firstVisibleItem == 0 &&
                        topRowVerticalPosition >= 0);
            }
        });
    }

    /**
     * Initializes RecyclerView if pager is in corresponding view
     */
    private void initRecyclerAdapter() {
        mListview.setVisibility(View.GONE);

        mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mTasksAdapter = new TasksAdapter(getActivity(), mCursor, this);

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // depending on the argument - query the contentprovider to receive relevant data
        // that's how we can reuse this fragment for different purposes
        int request = args.getInt(ARG_SECTION_NUMBER);
        String statusArgument = String.valueOf(TaskItem.STATUS_ALL);
        switch (request) {
            case PagerAdapter.FRAGMENT_PROGRESS:
                statusArgument = String.valueOf(TaskItem.STATUS_PROGRESS);
                break;
            case PagerAdapter.FRAGMENT_COMPLETED:
                statusArgument = String.valueOf(TaskItem.STATUS_COMPLETED);
                break;
            case PagerAdapter.FRAGMENT_WAITING:
                statusArgument = String.valueOf(TaskItem.STATUS_WAITING);
                break;
        }

        String selection;
        String[] selectionArgs;

        int sortingPreference = Utilities.getSortingPreference(getContext());
        if (sortingPreference == 0) {
            selection = TasksColumns.STATUS + " = ?";
            selectionArgs = new String[]{ statusArgument };
        } else {
            selection = TasksColumns.STATUS + " = ? AND " + TasksColumns.TYPE + " = ?";
            selectionArgs = new String[]{ statusArgument, "" + sortingPreference };
        }

        return new CursorLoader(getActivity(),
                TasksColumns.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null || data.getCount() < 1) {
            mListEmpty.setVisibility(View.VISIBLE);
        } else {
            mListEmpty.setVisibility(View.INVISIBLE);
        }

        mCursor = data;
        if (mTasksAdapter != null) {
            mTasksAdapter.swapCursor(mCursor);
        } else if (mListviewAdapter != null) {
            mListviewAdapter.swapCursor(mCursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursor = null;
        if (mTasksAdapter != null) {
            mTasksAdapter.swapCursor(null);
        } else if (mListviewAdapter != null) {
            mListviewAdapter.swapCursor(null);
        }
    }

    /**
     * Helper method which is called from PagerActivity when press on TabLayout twice
     * Scrolls the screen to the top
     */
    public void scrollToTop() {
        if (mLayoutManager != null) {
            mRecyclerView.smoothScrollToPosition(0);
        }
        if (mListview != null) {
            // not the most elegant solution, but listview is a huuuge pain
            mListview.smoothScrollToPosition(0);
            mListview.setSelection(0);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.sharedprefs_key_sort))) {
            getLoaderManager().restartLoader(CURSOR_LOADER_ID, getArguments(), this);
            ActionBar actionBar = ((PagerActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(Utilities.getActionbarTitle(getContext(),
                        Utilities.getSortingPreference(getContext())));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onTaskClickTransition(TaskItem item, Pair<View, String> pair) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(TaskItem.TASKITEM, item);

        //noinspection unchecked always true
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(
                getActivity(), pair)
                .toBundle();
        startActivity(intent, bundle);
    }

    @Override
    public void onTaskClick(TaskItem item) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(TaskItem.TASKITEM, item);
        startActivity(intent);
    }
}
