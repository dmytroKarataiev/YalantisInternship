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
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adkdevelopment.e_contact.R;
import com.adkdevelopment.e_contact.data.local.TaskObjectRealm;
import com.adkdevelopment.e_contact.interfaces.ItemClickListener;
import com.adkdevelopment.e_contact.ui.base.BaseFragment;
import com.adkdevelopment.e_contact.ui.contract.DetailContract;
import com.adkdevelopment.e_contact.utils.Utilities;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by karataev on 5/12/16.
 */
public class DetailFragment extends BaseFragment implements DetailContract.View, ItemClickListener<Integer, View> {

    @Inject DetailPresenter mPresenter;

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
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    private Unbinder mUnbibder;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mUnbibder = ButterKnife.bind(this, rootView);

        mPresenter.attachView(this);
        mPresenter.loadData(getActivity().getIntent());

        return rootView;
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
        mUnbibder.unbind();
        mPresenter.detachView();
    }

    @Override
    public void showData(TaskObjectRealm taskObject) {
        Log.d("DetailFragment", "shows data");

        // Time parsing and creating a nice textual version (should be changed to Calendar)
        String dateCreated = Utilities.getFormattedDate(taskObject.getCreated());
        String dateRegistered = Utilities.getFormattedDate(taskObject.getRegistered());
        String dateAssigned = Utilities.getFormattedDate(taskObject.getAssigned());

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


    }

    @Override
    public void showTaskEmpty() {
        Log.d("DetailFragment", "empty");
    }

    @Override
    public void showError() {
        Log.d("DetailFragment", "error");
    }
}
