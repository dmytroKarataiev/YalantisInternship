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
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.data.local.TaskRealm;
import com.adkdevelopment.e_contact.interfaces.ItemClickListener;
import com.adkdevelopment.e_contact.ui.adapters.PhotoAdapter;
import com.adkdevelopment.e_contact.ui.base.BaseFragment;
import com.adkdevelopment.e_contact.ui.contract.DetailContract;
import com.adkdevelopment.e_contact.ui.presenters.DetailPresenter;
import com.adkdevelopment.e_contact.utils.Utilities;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Fragment which shows detailed information about Tasks
 * Created by karataev on 5/12/16.
 */
public class DetailFragment extends BaseFragment
        implements DetailContract.View, ItemClickListener<Integer, View> {

    private static final String TAG = DetailFragment.class.getSimpleName();

    @Inject PhotoAdapter mAdapter;
    @Inject DetailPresenter mPresenter;

    private OnFragmentInteraction mListener;

    @BindView(R.id.task_title_text)
    TextView mTaskTitleText;
    @BindView(R.id.task_status)
    TextView mTaskStatus;
    @BindView(R.id.task_created_date)
    TextView mTaskCreatedDate;
    @BindView(R.id.task_registered_date)
    TextView mTaskRegisteredDate;
    @BindView(R.id.task_assigned_date)
    TextView mTaskAssignedDate;
    @BindView(R.id.task_description)
    TextView mTaskDescription;
    @BindView(R.id.task_responsible_name)
    TextView mTaskResponsibleName;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.map_button)
    Button mButtonMap;
    private Unbinder mUnbinder;

    // As per specification - each element (or button? or what?) should have an onClickListener
    // which shows a toast with element name
    @OnClick({R.id.task_title_text,
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
    public void onAttach(Context context) {
        super.onAttach(context);
        ((DetailActivity) getActivity()).getActivityComponent().injectFragment(this);
        if (context instanceof  OnFragmentInteraction) {
            mListener = (OnFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString() +
                " must implement "  + OnFragmentInteraction.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.attachView(this);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.loadData(getActivity().getIntent());
    }

    @Override
    public void onItemClicked(Integer item, View view) {
        Toast.makeText(getContext(),
                view.getContentDescription() + " " + item,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mPresenter.detachView();
    }

    @Override
    public void showData(final TaskRealm taskObject) {

        // Time parsing and creating a nice textual version (should be changed to Calendar)
        String dateCreated = Utilities.getFormattedDate(taskObject.getCreated());
        String dateRegistered = Utilities.getFormattedDate(taskObject.getRegistered());

        String dateAssigned;
        if (taskObject.getAssigned() != 0) {
            dateAssigned = Utilities.getFormattedDate(taskObject.getAssigned());
        } else {
            dateAssigned = getString(R.string.task_not_assignded);
        }

        mTaskTitleText.setText(taskObject.getCategoryText());
        mTaskStatus.setText(taskObject.getStatusText());

        // sets color of a status TextView shape according to the schema
        GradientDrawable gradientDrawable = (GradientDrawable) mTaskStatus.getBackground();
        gradientDrawable.setColor(Utilities.getBackgroundColor(getContext(), taskObject.getStatus()));

        mTaskCreatedDate.setText(dateCreated);
        mTaskRegisteredDate.setText(dateRegistered);
        mTaskAssignedDate.setText(dateAssigned);
        mTaskResponsibleName.setText(taskObject.getResponsible());
        mTaskDescription.setText(Html.fromHtml(taskObject.getDescription()));

        mAdapter.setPhotos(taskObject.getPhoto(), this);
        mAdapter.notifyDataSetChanged();

        // send a title to an activity's actionBar
        if (mListener != null) {
            mListener.onFragmentInteraction(String.valueOf(taskObject.getId()));
        }

        if (taskObject.getLatitude() > 0.0) {
            mButtonMap.setVisibility(View.VISIBLE);
            mButtonMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), MapsActivity.class);
                    intent.putExtra(TaskRealm.TASK_EXTRA, taskObject);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void showError() {
        Log.d(TAG, "Error");
    }

    // interface to communicate with an activity to set a title
    public interface OnFragmentInteraction {
        void onFragmentInteraction(String title);
    }
}
