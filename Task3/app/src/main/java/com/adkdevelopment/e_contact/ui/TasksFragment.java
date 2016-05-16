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

package com.adkdevelopment.e_contact.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adkdevelopment.e_contact.MainActivity;
import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.data.local.TaskRealm;
import com.adkdevelopment.e_contact.interfaces.ItemClickListener;
import com.adkdevelopment.e_contact.ui.adapters.TasksAdapter;
import com.adkdevelopment.e_contact.ui.base.BaseFragment;
import com.adkdevelopment.e_contact.ui.contract.TasksContract;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by karataev on 5/10/16.
 */
public class TasksFragment extends BaseFragment implements TasksContract.View, ItemClickListener<TaskRealm, View> {

    private static final String TAG = TasksFragment.class.getSimpleName();

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_STATE = "section_state";

    @Inject TasksPresenter mPresenter;
    @Inject TasksAdapter mAdapter;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.list_empty_text)
    TextView mListEmpty;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    private Unbinder mUnbinder;

    private int mCurrentPosition;
    private int mCurrentPage = 1;
    private boolean isUpdating;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TasksFragment newInstance(int sectionNumber, String sectionState) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_SECTION_STATE, sectionState);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) getActivity()).getActivityComponent().injectFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);

        mUnbinder = ButterKnife.bind(this, rootView);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPresenter.attachView(this);

        mPresenter.getData(getArguments().getInt(ARG_SECTION_NUMBER));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // on force refresh downloads all data
                mPresenter.fetchData(getArguments().getInt(ARG_SECTION_NUMBER),
                        TaskRealm.QUERY_ALL,
                        TaskRealm.QUERY_ALL);
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int adapterItems = mRecyclerView.getAdapter().getItemCount();
                if (adapterItems > 0 && dy > 0) {
                    mCurrentPosition = ((LinearLayoutManager) mRecyclerView
                            .getLayoutManager()).findFirstVisibleItemPosition();

                    if (mCurrentPosition >= adapterItems - TaskRealm.QUERY_START && !isUpdating) {
                        mCurrentPage = adapterItems / TaskRealm.QUERY_AMOUNT;
                        int offset = mCurrentPage * TaskRealm.QUERY_OFFSET;
                        mCurrentPage++;
                        mPresenter.fetchData(getArguments().getInt(ARG_SECTION_NUMBER),
                                mCurrentPage, offset);
                        isUpdating = true;
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void addData(List<TaskRealm> taskObjects) {
        Log.d(TAG, "addData: ");
        mListEmpty.setVisibility(View.GONE);
        isUpdating = false;
        mAdapter.addTasks(taskObjects, this);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void getData(List<TaskRealm> taskObjects) {
        Log.d(TAG, "getData taskObjects.size():" + taskObjects.size());
        mListEmpty.setVisibility(View.GONE);
        mAdapter.setTasks(taskObjects, this);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTasks(boolean isEmpty) {
        Log.d(TAG, "showTasks: " + isEmpty);
        if (isEmpty) {
            mListEmpty.setText(getString(R.string.recyclerview_empty_text));
            mListEmpty.setVisibility(View.VISIBLE);
            mAdapter.setTasks(null, null);
            mAdapter.notifyDataSetChanged();
        }
        mPresenter.fetchData(getArguments().getInt(ARG_SECTION_NUMBER),
                mCurrentPage,
                TaskRealm.QUERY_FIRST_PAGE);
    }

    @Override
    public void showError() {
        Log.d(TAG, "showError: ");
        mListEmpty.setVisibility(View.VISIBLE);
        // TODO: 5/13/16 add meaningfull something 
        mListEmpty.setText("Error somewhere");
    }

    @Override
    public void showProgress(boolean isInProgress) {
        Log.d(TAG, "showProgress: " + isInProgress);
        if (isInProgress) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClicked(TaskRealm item, View view) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(TaskRealm.TASK_EXTRA, item);
        intent.putExtra(TaskRealm.TASK_EXTRA_TITLE, String.valueOf(item.getId()));
        startActivity(intent);
    }

    /**
     * Helper method which is called from PagerActivity when press on TabLayout twice
     * Scrolls the screen to the top
     */
    public void scrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }
}


