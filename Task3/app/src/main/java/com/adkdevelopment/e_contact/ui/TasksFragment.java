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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.data.local.TaskObjectRealm;
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
public class TasksFragment extends BaseFragment implements TasksContract.View, ItemClickListener<TaskObjectRealm, View> {

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
    private Unbinder mUnbinder;

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
        mPresenter.loadData(getArguments().getInt(ARG_SECTION_NUMBER));

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
        mUnbinder.unbind();
    }

    @Override
    public void showData(List<TaskObjectRealm> taskObjects) {
        mListEmpty.setVisibility(View.GONE);
        mAdapter.setTasks(taskObjects, this);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTasksEmpty() {
        mListEmpty.setText(getString(R.string.recyclerview_empty_text));
        mListEmpty.setVisibility(View.VISIBLE);
        mAdapter.setTasks(null, null);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        mListEmpty.setVisibility(View.VISIBLE);
        mListEmpty.setText("Error somewhere");
    }

    @Override
    public void onItemClicked(TaskObjectRealm item, View view) {
        // TODO: 5/13/16 fix add extras
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(TaskObjectRealm.TASK_EXTRA, item);
        intent.putExtra(TaskObjectRealm.TASK_EXTRA_TITLE, String.valueOf(item.getId()));
        startActivity(intent);
    }
}


